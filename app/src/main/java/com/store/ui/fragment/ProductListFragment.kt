package com.store.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout.VERTICAL
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.store.R
import com.store.ui.recyclerview.adapter.ProductAdapter
import com.store.ui.viewmodel.MainViewModel
import com.store.ui.viewmodel.ProductViewModel
import com.store.ui.viewmodel.VisualComponent
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : BaseFragment() {

    private val viewModel: ProductViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val adapter: ProductAdapter by inject()
    private val navController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaProdutos()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.product_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.hasComponents = VisualComponent(appBar = true, bottomNavigation = true)
        configuraRecyclerView()
        configAddFab(view)
    }

    private fun configAddFab(view: View) {
        view.findViewById<FloatingActionButton>(R.id.fragment_product_list_fab_add)
            .setOnClickListener {
                val direction =
                    ProductListFragmentDirections.actionProductListFragmentToFormProductFragment()
                navController.navigate(direction)
            }
    }

    private fun buscaProdutos() {
        viewModel.findAll().observe(this) { produtosEncontrados ->
            produtosEncontrados?.let(adapter::atualiza)
        }
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(context, VERTICAL)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.lista_produtos_recyclerview)
        recyclerView?.addItemDecoration(divisor)
        adapter.onItemClickListener = { product ->
            product.id?.let { id ->
                val directions = ProductListFragmentDirections.goToProductDetails(id)
                navController.navigate(directions)
            }
        }
        recyclerView?.adapter = adapter
    }

}
