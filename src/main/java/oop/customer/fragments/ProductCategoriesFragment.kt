package oop.customer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.product_list.*
import oop.customer.CATEGORIES_LINK
import oop.customer.Category
import oop.customer.MainActivity
import oop.customer.R
import oop.customer.api.networktask.NetworkTask
import oop.customer.api.recyclerview.bind
import oop.customer.api.replaceWith
import oop.customer.api.snackMessage
import oop.customer.api.withDivider

class ProductCategoriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.product_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).onBackReset()
        NetworkTask(CATEGORIES_LINK, NetworkTask.Method.GET, null, activity, getString(R.string.message_wait))
            .setOnCallBack { response, s ->
                if(response?.code == 200 && s != null){
                    itemsRecyclerView.withDivider().bind(
                        Klaxon().parseArray<Category>(s)!!,
                        context!!, R.layout.product_item
                    ).setOnItemClickListener {
                        replaceWith(ProductsFragment(this), R.id.activity_main_frame)
                    }.apply()
                }
                else{
                    product_list_layout.snackMessage(getString(R.string.message_wait))
                }
            }.send()
    }

}
