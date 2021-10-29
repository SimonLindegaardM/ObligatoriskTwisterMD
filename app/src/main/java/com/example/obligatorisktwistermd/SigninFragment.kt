package com.example.obligatorisktwistermd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.obligatorisktwistermd.databinding.FragmentSigninBinding
import com.google.firebase.auth.FirebaseUser
import androidx.lifecycle.Observer;
import models.AuthViewModel


class SigninFragment : Fragment() {
    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        //authViewModel = ViewModelProviders.of(this).get(authViewModel::class.java)
        authViewModel.userInfoData.observe(this,{user ->
                if (user != null)
                {
                    findNavController().navigate(R.id.action_signinFragment_to_FirstFragment)
                }
        })
        authViewModel.errorMessage.observe(this, {message ->
            binding.errorMessageView.text = message
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //authViewModel.userInfoData.observe(viewLifecycleOwner) {

        //}

        binding.signIn.setOnClickListener{
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isNullOrEmpty()){
                binding.emailInputField.error = "No email Input"
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()){
                binding.passwordInputField.error = "No password input"
                return@setOnClickListener
            }
            authViewModel.signIn(email, password)


        }
        binding.buttonCreateUser.setOnClickListener{
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isNullOrEmpty()){
                binding.emailInputField.error = "No email Input"
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()){
                binding.passwordInputField.error = "No password input"
                return@setOnClickListener
            }
            authViewModel.createUser(email, password)
        }

        //authViewModel.errorMessage.observe(viewLifecycleOwner) {
          //  errorMessage -> binding.errorMessageView.text = errorMessage
        //}

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}