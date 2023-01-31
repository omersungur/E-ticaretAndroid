package com.omersungur.e_ticaretandroid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.omersungur.e_ticaretandroid.R
import com.omersungur.e_ticaretandroid.adapter.ProductRecyclerView
import com.omersungur.e_ticaretandroid.model.Product
import com.omersungur.e_ticaretandroid.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : Fragment(), ProductRecyclerView.Listener {

    private val productViewModel : ProductViewModel by activityViewModels() // farklı fragmentlerdan bir viewModel'a ulaşmak için.
    private var productAdapter : ProductRecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productRecyclerView.layoutManager = GridLayoutManager(activity?.baseContext,2)
        productViewModel.downloadData()

        productViewModel.productList.observe(viewLifecycleOwner, Observer {
            productAdapter = ProductRecyclerView(it,this) // fragment listenerı implement ettiği için this diyebiliriz.
            productRecyclerView.adapter = productAdapter
        })
    }

    override fun OnItemClick(product: Product) {
        //Sepete ekleme işlemi
        productViewModel.addToBasket(product)
    }
}