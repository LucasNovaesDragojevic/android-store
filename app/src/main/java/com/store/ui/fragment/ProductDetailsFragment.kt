package com.store.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.store.R
import com.store.extension.formatToBrazilianMoney
import com.store.ui.viewmodel.ProductDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailsFragment : Fragment() {

    private val arguments by navArgs<ProductDetailsFragmentArgs>()
    private val productId by lazy { arguments.productId }
    private val navController by lazy { findNavController() }
    private val viewModel by viewModel<ProductDetailsViewModel> { parametersOf(productId) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.product_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buscaProduto()
        configuraBotaoComprar()
    }

    private fun configuraBotaoComprar() {
        val button = view?.findViewById<Button>(R.id.detalhes_produto_botao_comprar)
        button?.setOnClickListener {
            viewModel.productFound.value?.let {
                val directions = ProductDetailsFragmentDirections.goToPayment(productId)
                navController.navigate(directions)
            }
        }
    }

    private fun buscaProduto() {
        viewModel.productFound.observe(viewLifecycleOwner) {
            it?.let { product ->
                val productNameView = view?.findViewById<TextView>(R.id.detalhes_produto_nome)
                val productPriceView = view?.findViewById<TextView>(R.id.detalhes_produto_preco)
                productNameView?.text = product.name
                productPriceView?.text = product.price.formatToBrazilianMoney()
            }
        }
    }
}
