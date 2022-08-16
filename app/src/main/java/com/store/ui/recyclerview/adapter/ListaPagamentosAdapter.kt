package com.store.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.store.R
import com.store.extension.formatToBrazilianMoney
import com.store.model.Payment

class ListaPagamentosAdapter(
    private val context: Context,
    pagamentos: List<Payment> = listOf()
) : RecyclerView.Adapter<ListaPagamentosAdapter.ViewHolder>() {

    private val pagamentos = pagamentos.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(
            R.layout.item_pagamento,
            parent,
            false
        )
        return ViewHolder(viewCriada)
    }

    override fun getItemCount(): Int = pagamentos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pagamento = pagamentos[position]
        holder.vincula(pagamento)
    }

    fun add(pagamentos: List<Payment>) {
        notifyItemRangeRemoved(0, this.pagamentos.size)
        this.pagamentos.clear()
        this.pagamentos.addAll(pagamentos)
        notifyItemRangeInserted(0, this.pagamentos.size)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val id by lazy {
            itemView.findViewById<TextView>(R.id.item_pagamento_id)
        }
        private val preco by lazy {
            itemView.findViewById<TextView>(R.id.item_pagamento_preco)
        }

        fun vincula(pagamento: Payment) {
//            id.text = pagamento.id.toString()
            preco.text = pagamento.price.formatToBrazilianMoney()
        }

    }
}
