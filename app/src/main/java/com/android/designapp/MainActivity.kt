package com.android.designapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.android.designapp.databinding.ActivityMainLayoutBinding
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    private lateinit var binding: ActivityMainLayoutBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.submitButton.collect{

                Log.e("validatortest", " =====  submit button ${it}")
                binding.btnSave.isEnabled =  it
            }
        }

    }

    private fun initUi() {

        viewModel.initFormField()
        binding.nameInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("validatortest", " ===== onTextChanged nameInput ${s}")

                viewModel.setName(s.toString())

            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.lastNameInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("validatortest", " ===== onTextChanged lastNameInput ${s}")

                viewModel.setLastName(s.toString())

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.emailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("validatortest", " ===== onTextChanged emailInput ${s}")

                viewModel.setEmail(s.toString())

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.phoneNoInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("validatortest", " ===== onTextChanged phoneNoInput ${s}")

                viewModel.setPhone(s.toString())

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

    }
}
