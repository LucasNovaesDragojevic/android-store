package com.store.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.store.R
import com.store.ui.recyclerview.adapter.ListaPagamentosAdapter
import com.store.ui.viewmodel.MainViewModel
import com.store.ui.viewmodel.PaymentViewModel
import com.store.ui.viewmodel.VisualComponent
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentListFragment : BaseFragment() {

    private val adapter: ListaPagamentosAdapter by inject()
    private val viewModel: PaymentViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.lista_pagamentos, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.lista_pagamentos_recyclerview).adapter = adapter
        viewModel.findAll().observe(viewLifecycleOwner) {
            it?.let(adapter::add)
        }
        mainViewModel.hasComponents = VisualComponent(appBar = true, bottomNavigation = false)
    }
}