package oop.customer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.product_list.*
import oop.customer.MainActivity
import oop.customer.R
import oop.customer.api.recyclerview.bind
import oop.customer.api.replaceWith
import oop.customer.api.withDivider
import oop.customer.data.Category

class ProductCategoriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.product_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        itemsRecyclerView.withDivider().bind(
            listOf(
                Category("لباس", R.drawable.shoe),
                Category("لوازم التحریر", R.drawable.shoe)
            ), context!!, R.layout.product_item
        ).setOnItemClickListener {
            replaceWith(ProductsFragment(this.name), R.id.activity_main_frame)
        }.apply()
        (activity as MainActivity).onBackReset()
    }

}
