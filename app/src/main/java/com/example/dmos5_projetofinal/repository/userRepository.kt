package com.example.dmos5_projetofinal.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dmos5_projetofinal.model.Employee
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UsersRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getLoggedEmployee(): LiveData<Employee?> {
        val liveData = MutableLiveData<Employee?>()
        val userId = auth.currentUser?.uid

        if (userId != null) {
            firestore.collection("employees").document(userId).get()
                .addOnSuccessListener { document ->
                    val employee = document.toObject(Employee::class.java)
                    liveData.value = employee
                }
                .addOnFailureListener { _ ->
                    liveData.value = null
                }
        } else {
            liveData.value = null
        }

        return liveData
    }

    fun login(email: String, password: String): LiveData<Employee?> {
        val liveData = MutableLiveData<Employee?>()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        firestore.collection("employees").document(it.uid).get()
                            .addOnSuccessListener { document ->
                                val loggedEmployee = document.toObject(Employee::class.java)
                                liveData.value = loggedEmployee
                            }
                            .addOnFailureListener { _ ->
                                liveData.value = null
                            }
                    }
                } else {
                    liveData.value = null
                }
            }

        return liveData
    }
}
