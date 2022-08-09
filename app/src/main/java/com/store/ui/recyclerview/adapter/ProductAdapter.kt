package com.store.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.store.R
import com.store.extension.formatToBrazilianMoney
import com.store.model.Product

class ProductAdapter(
    private val context: Context,
    private val products: MutableList<Product> = mutableListOf(),
    var onItemClickListener: (product: Product) -> Unit = {}
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCreated = LayoutInflater.from(context).inflate(
            R.layout.product_item,
            parent,
            false
        )
        return ViewHolder(viewCreated)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vincula(products[position])
    }

    fun atualiza(produtosNovos: List<Product>) {
        notifyItemRangeRemoved(0, products.size)
        products.clear()
        products.addAll(produtosNovos)
        notifyItemRangeInserted(0, products.size)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var product: Product

        private val campoNome by lazy {
            itemView.findViewById<TextView>(R.id.item_produto_nome)
        }

        private val campoPreco by lazy {
            itemView.findViewById<TextView>(R.id.item_produto_preco)
        }

        init {
            itemView.findViewById<TextView>(R.id.item_produto_nome)

            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    onItemClickListener(product)
                }
            }
        }

        fun vincula(product: Product) {
            this.product = product
            campoNome.text = product.name
            campoPreco.text = product.price.formatToBrazilianMoney()
        }

    }

}
