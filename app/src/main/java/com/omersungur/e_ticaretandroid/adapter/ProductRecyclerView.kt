package com.omersungur.e_ticaretandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.omersungur.e_ticaretandroid.databinding.ProductsRowBinding
import com.omersungur.e_ticaretandroid.model.Product

class ProductRecyclerView(private val productList : List<Product>, private val listener : Listener) : RecyclerView.Adapter<ProductRecyclerView.ProductViewHolder>() {
    class ProductViewHolder(val binding : ProductsRowBinding) : ViewHolder(binding.root)  {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductsRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.priceTextView.text = productList[position].price
        holder.binding.productNameTextView.text = productList[position].name
        Glide.with(holder.itemView.context).load(productList[position].url).into(holder.binding.productImageView)
        holder.binding.buttonAddBasket.setOnClickListener {
            Toast.makeText(it.context,"Sepete Eklendi: ${productList[position].name}",Toast.LENGTH_LONG).show()
            listener.OnItemClick(productList[position])
        }
    }

    interface Listener {
        fun OnItemClick(product : Product)
    }
}