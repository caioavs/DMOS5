package com.example.dmos5_projetofinal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dmos5_projetofinal.model.Employee
import com.example.dmos5_projetofinal.repository.UsersRepository
import com.google.firebase.auth.FirebaseAuth

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val usersRepository = UsersRepository(application)
    private val auth = FirebaseAuth.getInstance()

    private val _currentUser = MutableLiveData<Employee?>()
    val currentUser: LiveData<Employee?> get() = _currentUser

    fun createUser(email: String, password: String, employee: Employee) {
        usersRepository.createUser(email, password, employee)
    }

    fun updateUser(employee: Employee) {
        usersRepository.update(employee)
    }

    fun login(email: String, password: String): LiveData<Employee?> {
        return usersRepository.login(email, password).apply {
            observeForever { user ->
                _currentUser.value = user
            }
        }
    }

    fun logout() {
        auth.signOut()
        _currentUser.value = null
    }

    fun loadLoggedUser() {
        val userId = auth.currentUser?.uid ?: return
        usersRepository.load(userId).observeForever { user ->
            _currentUser.value = user
        }
    }

    fun resetPassword(email: String) {
        usersRepository.resetPassword(email)
    }

    fun isLogged(): LiveData<Employee?> {
        loadLoggedUser() // Garante que o _currentUser seja atualizado
        return currentUser
    }
}
