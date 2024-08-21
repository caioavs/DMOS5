package com.example.dmos5_projetofinal.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.dmos5_projetofinal.model.Employee

class EmployeeViewModel : ViewModel() {

    private val repository = EmployeeRepository()  // Ajuste para não passar o application context
    private val _employee = MutableLiveData<Employee?>()  // Permite valores nulos
    val employee: LiveData<Employee?> get() = _employee

    fun isLogged(): LiveData<Employee?> {
        return repository.getLoggedEmployee()
    }

    fun login(email: String, password: String) {
        repository.login(email, password).observeForever {
            _employee.value = it
        }
    }

}
