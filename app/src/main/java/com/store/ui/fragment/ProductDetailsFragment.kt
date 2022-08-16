package com.store.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.store.R
import com.store.extension.formatToBrazilianMoney
import com.store.ui.viewmodel.MainViewModel
import com.store.ui.viewmodel.ProductDetailsViewModel
import com.store.ui.viewmodel.VisualComponent
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailsFragment : BaseFragment() {

    private val arguments by navArgs<ProductDetailsFragmentArgs>()
    private val productId by lazy { arguments.productId }
    private val navController by lazy { findNavController() }
    private val viewModel by viewModel<ProductDetailsViewModel> { parametersOf(productId) }
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.product_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.hasComponents = VisualComponent(appBar = true, bottomNavigation = false)
        buscaProduto()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_product_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_product_details_edit -> goToForm()
            R.id.menu_product_details_delete -> deleteProduct()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteProduct() {
        viewModel.delete().observe(viewLifecycleOwner) {
            navController.popBackStack()
        }
    }

    private fun goToForm() {
        ProductDetailsFragmentDirections.actionProductDetailsFragmentToFormProductFragment(productId)
            .let(navController::navigate)
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
