package oop.customer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.product_wc_list.*
import oop.customer.*
import oop.customer.api.networktask.NetworkTask
import oop.customer.api.open
import oop.customer.api.recyclerview.bind
import oop.customer.api.replaceWith
import oop.customer.api.snackMessage
import oop.customer.api.withDivider

class ProductsFragment(private val category: Category) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.product_wc_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        wc_search_bar_layout.hint = "جستجو در ${category.name}"
        (activity as MainActivity).onBackPressedCallback = {
            replaceWith<ProductCategoriesFragment>(R.id.activity_main_frame)
        }
        request(null)
        wc_searchBtn.setOnClickListener {
            var search: String? = wc_search_bar_field.text.toString().trim()
            if(search == "")
                search = null
            request(search)
        }
    }

    private fun request(search: String?){
        val link = "$PRODUCTS_LINK?category=${category.id}${if(search != null) "&search=$search" else ""}"
        NetworkTask(link, NetworkTask.Method.GET, null, activity, getString(R.string.message_wait))
            .setOnCallBack { response, s ->
                if(response?.code == 200 && s != null){
                    val products = Klaxon().parseArray<ProductDetail>(s)!!.map { it.abstract() }
                    wc_itemsRecyclerView.withDivider().bind(
                        products,
                        context!!,
                        R.layout.product_item
                    ).setOnItemClickListener{
                        activity?.open<ProductPageActivity>("id" to this.id)
                    }.apply()
                }
                else{
                    product_wc_list_layout.snackMessage(getString(R.string.message_net_error))
                }
            }.send()
    }
}