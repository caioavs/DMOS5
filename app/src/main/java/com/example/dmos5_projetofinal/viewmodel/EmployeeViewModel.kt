package com.example.dmos5_projetofinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.dmos5_projetofinal.model.Employee
import com.example.dmos5_projetofinal.repository.UsersRepository

class EmployeeViewModel : ViewModel() {

    private val repository = UsersRepository()  // Ajuste para não passar o Application context
    private val _employee = MutableLiveData<Employee?>()  // Permite valores nulos
    val employee: LiveData<Employee?> get() = _employee

    fun isLogged(): LiveData<Employee?> {
        // Lógica para verificar se o usuário está logado
        return repository.getLoggedEmployee()
    }

    fun login(email: String, password: String) {
        repository.login(email, password).observeForever {
            _employee.value = it
        }
    }
}
