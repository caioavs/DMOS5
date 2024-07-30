package com.example.dmos5_projetofinal.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dmos5_projetofinal.model.Employee
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UsersRepository(application: Application) {

    private val firestore: FirebaseFirestore = Firebase.firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String): LiveData<Employee?> {
        val liveData = MutableLiveData<Employee?>()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        load(userId).observeForever { user ->
                            liveData.value = user
                        }
                    }
                } else {
                    liveData.value = null
                }
            }

        return liveData
    }

    fun createUser(email: String, password: String, employee: Employee) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        employee.id = userId
                        firestore.collection("employees").document(userId).set(employee)
                            .addOnSuccessListener {
                                // Sucesso ao criar o usuário e salvar no Firestore
                            }
                            .addOnFailureListener { exception ->
                                // Falha ao salvar no Firestore
                            }
                    }
                }
            }
    }

    fun update(employee: Employee) {
        val userId = employee.id ?: return
        firestore.collection("employees").document(userId).set(employee)
            .addOnSuccessListener {
                // Sucesso ao atualizar o documento
            }
            .addOnFailureListener { exception ->
                // Falha ao atualizar o documento
            }
    }

    fun load(userId: String): LiveData<Employee?> {
        val liveData = MutableLiveData<Employee?>()

        firestore.collection("employees").document(userId).get()
            .addOnSuccessListener { document ->
                val employee = document.toObject(Employee::class.java)
                if (employee != null) {
                    employee.id = userId
                }
                liveData.value = employee
            }

        return liveData
    }

    fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Email de redefinição de senha enviado
                }
            }
    }
}
