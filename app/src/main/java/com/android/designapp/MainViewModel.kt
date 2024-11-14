package com.android.designapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _submitButton = MutableStateFlow<Boolean>(false)

    val submitButton = _submitButton.asStateFlow()


    private val validatorMediator = ConcreteFormMediator()

    private var nameFormField = FormField(validatorMediator) { it.isNotEmpty() && it.length <= 20 && it.matches(Regex("^[a-zA-Z ]*$")) }
    private var lastNameFormField = FormField(validatorMediator) { it.isNotEmpty() && it.length <= 20 && it.matches(Regex("^[a-zA-Z ]*$")) }
    private var emailFormField = FormField(validatorMediator) { it.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() }
    private var phoneFormField = FormField(validatorMediator) { it.isNotEmpty() && android.util.Patterns.PHONE.matcher(it).matches() }


    fun initFormField() {
        validatorMediator.addField(nameFormField)
        validatorMediator.addField(lastNameFormField)
        validatorMediator.addField(emailFormField)
        validatorMediator.addField(phoneFormField)
    }

    fun setName(name: String) {
        viewModelScope.launch {
           val flag =  nameFormField.setValue(name)
            Log.e("validatortest", " ===== setName ${flag}")

            _submitButton.emit(flag)
        }
    }

    fun setLastName(name: String) {
        viewModelScope.launch {
            val flag = lastNameFormField.setValue(name)
            Log.e("validatortest", " ===== setLastName ${flag}")

            _submitButton.emit(flag)

        }

    }


    fun setEmail(email: String) {
        viewModelScope.launch {
            val flag = emailFormField.setValue(email)
            Log.e("validatortest", " ===== setEmail ${flag}")

            _submitButton.emit(flag)
        }

    }

    fun setPhone(phone: String) {
        viewModelScope.launch {
            val flag = phoneFormField.setValue(phone)
            Log.e("validatortest", " ===== setPhone ${flag}")
            _submitButton.emit(flag)
        }
    }



    // Component
    class FormField(private val mediator: ConcreteFormMediator,
                    private var validator: (String) -> Boolean ) {
        private var dataVal : String = ""
        fun setValue(value: String): Boolean {
            dataVal = value
          return  mediator.onFieldChanged(this)
        }

        fun isValid(): Boolean {

            return validator(dataVal)
        }

    }

    interface Mediator {
        fun onFieldChanged(field: FormField): Boolean
    }

    //Mediator for communication with all the components.
    // This will notify all the components.
    // In this case it will check all the form field whether it is valid or not and subsequently enable/disable the save button
    class ConcreteFormMediator() : Mediator {

        private var fieldList = mutableListOf<FormField>()

        fun addField(form: FormField) {
            fieldList.add(form)
        }
        override fun onFieldChanged(field: FormField): Boolean {

            if(fieldList.isNullOrEmpty()){
                return false
            }
          val isValid = fieldList.all { it.isValid() }

            return isValid
        }
    }
}
