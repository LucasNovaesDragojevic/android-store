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
import com.store.ui.viewmodel.MainViewModel
import com.store.ui.viewmodel.UserRegistrationViewModel
import com.store.ui.viewmodel.VisualComponent
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserRegistrationFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val userRegistrationViewModel: UserRegistrationViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.user_registration, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.hasComponents = VisualComponent()
        configRegisterButtom(view)
    }

    private fun configRegisterButtom(view: View) {
        view.findViewById<Button>(R.id.cadastro_usuario_botao_cadastrar).setOnClickListener {

            val (viewEmail, viewPassword, viewPasswordConfirmation) = getFormViews(view)

            clearViewsContent(viewEmail, viewPassword, viewPasswordConfirmation)

            val (email, password, passwordConfirmation) = getFieldsContent(
                viewEmail,
                viewPassword,
                viewPasswordConfirmation
            )

            val valid = validFields(
                email,
                viewEmail,
                password,
                viewPassword,
                passwordConfirmation,
                viewPasswordConfirmation
            )

            if (valid) {
                register(User(email, password), view)
            }
        }
    }

    private fun register(user: User, view: View) {
        userRegistrationViewModel.register(user)
            .observe(viewLifecycleOwner) {
                it?.let { resource ->
                    if (resource.data) {
                        view.snackbar("User registered")
                        findNavController().popBackStack()
                    } else {
                        val errorMessage = resource.error ?: "Fail on registration"
                        view.snackbar(errorMessage)
                    }
                }
            }
    }

    private fun getFieldsContent(
        viewEmail: TextInputLayout,
        viewPassword: TextInputLayout,
        viewPasswordConfirmation: TextInputLayout
    ): Triple<String, String, String> {
        val email = viewEmail.editText?.text.toString()
        val password = viewPassword.editText?.text.toString()
        val passwordConfirmation = viewPasswordConfirmation.editText?.text.toString()
        return Triple(email, password, passwordConfirmation)
    }

    private fun validFields(
        email: String,
        viewEmail: TextInputLayout,
        password: String,
        viewPassword: TextInputLayout,
        passwordConfirmation: String,
        viewPasswordConfirmation: TextInputLayout
    ): Boolean {
        var valid = true
        if (email.isBlank()) {
            viewEmail.error = "E-mail is necessary"
            valid = false
        }
        if (password.isBlank()) {
            viewPassword.error = "Password is necessary"
            valid = false
        }
        if (password != passwordConfirmation) {
            viewPasswordConfirmation.error = "Passwords is different"
            valid = false
        }
        return valid
    }

    private fun getFormViews(view: View): Triple<TextInputLayout, TextInputLayout, TextInputLayout> {
        val viewEmail = view.findViewById<TextInputLayout>(R.id.cadastro_usuario_id_usuario)
        val viewPassword = view.findViewById<TextInputLayout>(R.id.cadastro_usuario_senha)
        val viewPasswordConfirmation =
            view.findViewById<TextInputLayout>(R.id.cadastro_usuario_confirma_senha)
        return Triple(viewEmail, viewPassword, viewPasswordConfirmation)
    }

    private fun clearViewsContent(
        viewEmail: TextInputLayout,
        viewPassword: TextInputLayout,
        viewPasswordConfirmation: TextInputLayout
    ) {
        viewEmail.error = null
        viewPassword.error = null
        viewPasswordConfirmation.error = null
    }
}