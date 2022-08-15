package com.store.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.store.R
import com.store.ui.viewmodel.MainViewModel
import com.store.ui.viewmodel.VisualComponent
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserRegistrationFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.user_registration, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.hasComponents = VisualComponent()
        view.findViewById<Button>(R.id.cadastro_usuario_botao_cadastrar).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}