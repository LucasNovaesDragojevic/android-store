package com.store.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.store.R
import com.store.ui.fragment.PaymentFragment
import com.store.ui.fragment.ProductDetailsFragment
import com.store.ui.fragment.ProductListFragment
import org.koin.android.ext.android.inject

private const val COMPRA_REALIZADA = "Compra realizada"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
//            val produtosFragment: ProductListFragment by inject()
//            fragmentTransaction {
//                replace(R.id.container, produtosFragment)
//            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is ProductListFragment -> {
                fragment.quandoProdutoSelecionado = { produtoSelecionado ->
                    val detalhesProdutoFragment: ProductDetailsFragment by inject()
                    val argumentos = Bundle()
                    argumentos.putLong(CHAVE_PRODUTO_ID, produtoSelecionado.id)
                    detalhesProdutoFragment.arguments = argumentos
                    fragmentTransaction {
                        addToBackStack(null)
                        replace(R.id.container, detalhesProdutoFragment)
                    }
                }
            }
            is ProductDetailsFragment -> {
                fragment.quandoProdutoComprado = { produtoComprado ->
                    val pagamentoFragment: PaymentFragment by inject()
                    val dado = Bundle()
                    dado.putLong(CHAVE_PRODUTO_ID, produtoComprado.id)
                    pagamentoFragment.arguments = dado
                    fragmentTransaction {
                        addToBackStack(null)
                        replace(R.id.container, pagamentoFragment)
                    }
                }
            }
            is PaymentFragment -> {
                fragment.quandoPagamentoRealizado = {
                    Toast.makeText(this, COMPRA_REALIZADA, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}