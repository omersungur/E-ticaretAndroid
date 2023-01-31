package com.omersungur.e_ticaretandroid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omersungur.e_ticaretandroid.R
import com.omersungur.e_ticaretandroid.adapter.BasketRecyclerView
import com.omersungur.e_ticaretandroid.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_basket.*

class BasketFragment : Fragment() {

    private val basketViewModel : ProductViewModel by activityViewModels()
    private var basketAdapter : BasketRecyclerView? = null
    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition // kaydırılan elemanın pozisyonunu alıyoruz.
            if(basketAdapter != null) {
                val selectedProduct = basketAdapter!!.productList[layoutPosition]
                basketViewModel.deleteProductFromBasket(selectedProduct)
                basketAdapter!!.notifyDataSetChanged()
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        basketRecyclerView.layoutManager = LinearLayoutManager(activity?.baseContext)
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(basketRecyclerView)

        basketViewModel.basket.observe(viewLifecycleOwner, Observer {
            basketAdapter = BasketRecyclerView(it)
            basketRecyclerView.adapter = basketAdapter
        })

        basketViewModel.totalBasket.observe(viewLifecycleOwner, Observer {
            totalBasketText.text = "Toplam Sepet : ${it}"
        })
    }

}