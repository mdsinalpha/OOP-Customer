package oop.customer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.product_list.*
import oop.customer.MainActivity
import oop.customer.Product2
import oop.customer.ProductPageActivity
import oop.customer.R
import oop.customer.api.open
import oop.customer.api.recyclerview.bind
import oop.customer.api.replaceWith
import oop.customer.api.withDivider

class ProductsFragment(private val category: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.product_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        search_bar_layout.hint = "جستجو در $category"
        itemsRecyclerView.withDivider().bind(
            listOf(Product2("شلوار", R.drawable.shoe), Product2("کلاه", R.drawable.shoe)),
            context!!,
            R.layout.product_item
        ).setOnItemClickListener{
            activity?.open<ProductPageActivity>()
        }.apply()
        (activity as MainActivity).onBackPressedCallback = {
            replaceWith<ProductCategoriesFragment>(R.id.activity_main_frame)
        }
    }
}