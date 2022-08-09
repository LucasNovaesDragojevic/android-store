package com.store.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.store.R
import com.store.model.Product
import com.store.ui.activity.CHAVE_PRODUTO_ID
import com.store.ui.recyclerview.adapter.ProductAdapter
import com.store.ui.viewmodel.ProductViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : Fragment() {

    private val viewModel: ProductViewModel by viewModel()
    private val adapter: ProductAdapter by inject()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaProdutos()
    }

    private fun buscaProdutos() {
        viewModel.findAll().observe(this, Observer { produtosEncontrados ->
            produtosEncontrados?.let {
                adapter.atualiza(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.product_list,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraRecyclerView()
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(context, LinearLayout.VERTICAL)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.lista_produtos_recyclerview)
        recyclerView?.addItemDecoration(divisor)
        adapter.onItemClickListener = {
            val data = Bundle()
            data.putLong(CHAVE_PRODUTO_ID, it.id)
            navController.navigate(R.id.goToProductDetails, data)
        }
        recyclerView?.adapter = adapter
    }

}
