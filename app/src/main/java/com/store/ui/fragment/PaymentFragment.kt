package com.store.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.store.R
import com.store.extension.formatToBrazilianMoney
import com.store.model.Payment
import com.store.model.Product
import com.store.ui.viewmodel.MainViewModel
import com.store.ui.viewmodel.PaymentViewModel
import com.store.ui.viewmodel.VisualComponent
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val FALHA_AO_CRIAR_PAGAMENTO = "Falha ao criar pagamento"

class PaymentFragment : BaseFragment() {

    private val arguments by navArgs<PaymentFragmentArgs>()
    private val productId by lazy { arguments.productId }
    private val navController by lazy { findNavController() }
    private val viewModel by viewModel<PaymentViewModel>()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private lateinit var produtoEscolhido: Product

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.payment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.hasComponents = VisualComponent(appBar = true, bottomNavigation = false)
        configuraBotaoConfirmaPagamento()
    }

    private fun configuraBotaoConfirmaPagamento() {
        view?.findViewById<Button>(R.id.pagamento_botao_confirma_pagamento)
            ?.setOnClickListener {
                criaPagamento()
                    ?.let(this::salva)
                    ?: Toast.makeText(context, FALHA_AO_CRIAR_PAGAMENTO, Toast.LENGTH_LONG).show()
        }
    }

    private fun salva(pagamento: Payment) {
        if (::produtoEscolhido.isInitialized) {
            viewModel.save(pagamento).observe(this) {
                    it?.data?.let {
                        Toast.makeText(context, "Purchase concluded", Toast.LENGTH_SHORT).show()
                        val direction = PaymentFragmentDirections.goToProductList()
                        navController.navigate(direction)
                    }
                }
        }
    }

    private fun criaPagamento(): Payment? {
        val numeroCartao = view?.findViewById<TextInputLayout>(R.id.pagamento_numero_cartao)?.editText?.text.toString()
        val dataValidade = view?.findViewById<TextInputLayout>(R.id.pagamento_data_validade)?.editText?.text.toString()
        val cvc = view?.findViewById<TextInputLayout>(R.id.pagamento_cvc)?.editText?.text.toString()
        return geraPagamento(numeroCartao, dataValidade, cvc)
    }

    private fun geraPagamento(numeroCartao: String, dataValidade: String, cvc: String): Payment? = try {
        Payment(
            cardNumber = numeroCartao.toInt(),
            cardExpiration = dataValidade,
            cvc = cvc.toInt(),
            productId = productId,
            price = produtoEscolhido.price
        )
    } catch (e: NumberFormatException) {
        null
    }
}