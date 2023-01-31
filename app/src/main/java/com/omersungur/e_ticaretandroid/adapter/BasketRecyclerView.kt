package com.omersungur.e_ticaretandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.omersungur.e_ticaretandroid.databinding.BasketRowBinding
import com.omersungur.e_ticaretandroid.model.Product

class BasketRecyclerView(val productList : List<Product>) : RecyclerView.Adapter<BasketRecyclerView.BasketRowHolder>() {
    class BasketRowHolder(val binding : BasketRowBinding) : ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketRowHolder {
        val binding = BasketRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BasketRowHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: BasketRowHolder, position: Int) {
        holder.binding.basketProductNameTextView.text = productList[position].name
        holder.binding.basketProductPriceTextView.text = productList[position].price
        holder.binding.basketProductCountTextView.text = "Adet : ${productList[position].count}"
        Glide.with(holder.itemView.context).load(productList[position].url).into(holder.binding.basketImageView)
    }
}