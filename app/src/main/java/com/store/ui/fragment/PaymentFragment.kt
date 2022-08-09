package com.store.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.store.R
import com.store.extension.formatToBrazilianMoney
import com.store.model.Payment
import com.store.model.Product
import com.store.ui.activity.CHAVE_PRODUTO_ID
import com.store.ui.viewmodel.PaymentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val FALHA_AO_CRIAR_PAGAMENTO = "Falha ao criar pagamento"

class PaymentFragment : Fragment() {

    private val produtoId by lazy {
        arguments?.getLong(CHAVE_PRODUTO_ID)
            ?: throw IllegalArgumentException(ID_PRODUTO_INVALIDO)
    }

    private val viewModel: PaymentViewModel by viewModel()

    private val navController by lazy {
        findNavController()
    }

    private lateinit var produtoEscolhido: Product

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.payment,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraBotaoConfirmaPagamento()
        buscaProduto()
    }

    private fun buscaProduto() {
        viewModel.findById(produtoId).observe(viewLifecycleOwner, Observer {
            it?.let { produtoEncontrado ->
                produtoEscolhido = produtoEncontrado
                view?.findViewById<TextView>(R.id.item_produto_preco)?.text = produtoEncontrado.price.formatToBrazilianMoney()
            }
        })
    }

    private fun configuraBotaoConfirmaPagamento() {
        view?.findViewById<Button>(R.id.pagamento_botao_confirma_pagamento)?.setOnClickListener {
            criaPagamento()?.let(this::salva) ?: Toast.makeText(
                context,
                FALHA_AO_CRIAR_PAGAMENTO,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun salva(pagamento: Payment) {
        if (::produtoEscolhido.isInitialized) {
            viewModel.save(pagamento).observe(this) {
                    it?.data?.let {
                        Toast.makeText(context, "Purchase concluded", Toast.LENGTH_SHORT).show()
                        navController.popBackStack(R.id.goToProductList, false)
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

    private fun geraPagamento(
        numeroCartao: String,
        dataValidade: String,
        cvc: String
    ): Payment? = try {
        Payment(
            cardNumber = numeroCartao.toInt(),
            cardExpiration = dataValidade,
            cvc = cvc.toInt(),
            productId = produtoId,
            price = produtoEscolhido.price
        )
    } catch (e: NumberFormatException) {
        null
    }
}