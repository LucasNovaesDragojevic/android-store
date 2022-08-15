package com.store.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.store.R
import com.store.extension.snackbar
import com.store.model.User
import com.store.ui.viewmodel.LoginViewModel
import com.store.ui.viewmodel.MainViewModel
import com.store.ui.viewmodel.VisualComponent
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val navController by lazy { findNavController() }

    private val viewModel by viewModel<LoginViewModel>()

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.hasComponents = VisualComponent()
        configLoginButton(view)
        configRegisterButton(view)
    }

    private fun configRegisterButton(view: View) {
        view.findViewById<Button>(R.id.login_btn_user_registration).setOnClickListener {
            navController.navigate(LoginFragmentDirections.loginToUserRegistration())
        }
    }

    private fun configLoginButton(view: View) {
        view.findViewById<Button>(R.id.login_botao_logar).setOnClickListener {
            val viewEmail = view.findViewById<TextInputLayout>(R.id.login_usuario_id)
            val viewPassword = view.findViewById<TextInputLayout>(R.id.login_senha)

            clearFields(viewEmail, viewPassword)

            val email = viewEmail.editText?.text.toString()
            val password = viewPassword.editText?.text.toString()

            val valid = validateFields(email, viewEmail, password, viewPassword)

            if (valid) {
                authenticate(email, password, view)
            }
        }
    }

    private fun authenticate(email: String, password: String, view: View) {
        viewModel.login(User(email, password))
            .observe(viewLifecycleOwner) {
                it?.let { resource ->
                    if (resource.data) {
                        goToProductList()
                    } else {
                        val errorMessage = resource.error ?: "Error on login"
                        view.snackbar(errorMessage)
                    }
                }
            }
    }

    private fun validateFields(
        email: String,
        viewEmail: TextInputLayout,
        password: String,
        viewPassword: TextInputLayout
    ): Boolean {
        var valid = true

        if (email.isBlank()) {
            viewEmail.error = "Required e-mail"
            valid = false
        }
        if (password.isBlank()) {
            viewPassword.error = "Required password"
            valid = false
        }
        return valid
    }

    private fun clearFields(
        viewEmail: TextInputLayout,
        viewPassword: TextInputLayout
    ) {
        viewEmail.error = null
        viewPassword.error = null
    }

    private fun goToProductList() {
        navController.navigate(LoginFragmentDirections.loginToProductList())
    }
}