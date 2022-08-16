package com.store.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.store.R
import com.store.extension.snackbar
import com.store.model.Product
import com.store.ui.viewmodel.FormProductViewModel
import com.store.ui.viewmodel.MainViewModel
import com.store.ui.viewmodel.VisualComponent
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal

class FormProductFragment : BaseFragment() {

    private val arguments: FormProductFragmentArgs by navArgs()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val formProductViewModel: FormProductViewModel by viewModel()
    private val navController: NavController by lazy { findNavController() }
    private val productId: String? by lazy { arguments.productId }
    private val nameView by lazy {
        view?.findViewById<TextInputLayout>(R.id.formulario_produto_campo_nome)?.editText
    }
    private val priceView by lazy {
        view?.findViewById<TextInputLayout>(R.id.formulario_produto_campo_preco)?.editText
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tryFindProduct()
        mainViewModel.hasComponents = VisualComponent(appBar = true, bottomNavigation = false)
        configButtonSave(view)
    }

    private fun configButtonSave(view: View) {
        view.findViewById<Button>(R.id.formulario_produto_botao_salva).setOnClickListener {
            val name = nameView?.text.toString()
            val price =
                view.findViewById<TextInputLayout>(R.id.formulario_produto_campo_preco).editText?.text.toString()
            val product = Product(id = productId, name = name, price = BigDecimal(price))
            formProductViewModel.save(product).observe(viewLifecycleOwner) {
                it?.let { saved ->
                    if (saved) {
                        navController.popBackStack()
                        return@observe
                    }
                    view.snackbar("Fail on save product")
                }
            }
        }
    }

    private fun tryFindProduct() {
        productId?.let { id ->
            formProductViewModel.findById(id).observe(viewLifecycleOwner) {
                it?.let { product ->
                    val name = product.name
                    val price = product.price.toString()
                    nameView?.setText(name)
                    priceView?.setText(price)
                    requireActivity().title = "Edit"
                }
            }
        }
    }
}