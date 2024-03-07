package com.example.finalproject.presentation.auth_feature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemRegisterPageLayoutBinding
import com.example.finalproject.presentation.auth_feature.model.UserRegisterInformation
import com.example.finalproject.presentation.extension.setupPasswordToggle

class RegisterPageRecyclerViewAdapter(
    private val onAlreadyRegisteredCLicked: () -> Unit,
    private val registerUser : (UserRegisterInformation) -> Unit
    ) : RecyclerView.Adapter<RegisterPageRecyclerViewAdapter.RegisterPageViewHolder>(){

    inner class RegisterPageViewHolder(private val binding : ItemRegisterPageLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding) {

                btnRegister.setOnClickListener {
                    registerUser(
                        UserRegisterInformation(
                            email = etEmail.text.toString(),
                            password = etPassword.text.toString(),
                            repeatPassword = etRepeatPassword.text.toString(),
                            firstName = etFirstName.text.toString(),
                            lastName = etLastName.text.toString())
                    )
                }

                togglePasswordVisibility(etPassword)
                togglePasswordVisibility(etRepeatPassword)

                btnAlreadyRegistered.setOnClickListener {
                    onAlreadyRegisteredCLicked()
                }
            }
        }

        private fun togglePasswordVisibility(et : EditText) {
            et.setupPasswordToggle()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterPageViewHolder {
        val binding = ItemRegisterPageLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegisterPageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: RegisterPageViewHolder, position: Int) {
        holder.bind()
    }
}