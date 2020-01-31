package oop.customer

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.beust.klaxon.Klaxon
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import kotlinx.android.synthetic.main.activity_product_page.*
import oop.customer.api.networktask.NetworkTask
import oop.customer.api.networktask.jsonRequestBody
import oop.customer.api.snackMessage
import oop.customer.fragments.CommentsFragment
import oop.customer.fragments.DescriptionFragment

class ProductPageActivity : AppCompatActivity() {
    private val klaxon = Klaxon()
    private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)
        settings = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        fetchProductDetailDataFromServer(intent.extras!!.getInt(PRODUCT_ID))
    }

    private fun fetchProductDetailDataFromServer(productID: Int){
        NetworkTask(
            "$PRODUCTS_LINK$productID/",
            NetworkTask.Method.GET,
            null,
            this,
            getString(R.string.message_wait)
        ).setOnCallBack { response, s ->
            if(response?.code == 200 && s != null) {
                val product = klaxon.parse<ProductDetail>(s)!!
                setImagesOfSlider(product.id)
                setTitle(product.name)
                setWeAreGood()
                setCost(product.Price)
                setAddToBasket(product.id)
                setCommentsAndProductDescription(product.id, product.description!!)
            }
            else
                product_page.snackMessage(getString(R.string.time_out_request))
        }.send()
    }


    private fun setImagesOfSlider(productID: Int) {
        NetworkTask(
            "$PRODUCTS_LINK$productID/images",
            NetworkTask.Method.GET,
            null,
            null,
            null
        ).setOnCallBack { response, s ->
            if(response?.code == 200 && s != null){
                klaxon.parseArray<Image>(s)!!.forEach {
                    val textSlider = TextSliderView(this)
                    textSlider
                        .image("$SERVER_LINK${it.imageContent}")
                        .setScaleType(BaseSliderView.ScaleType.CenterInside)
                        .setOnSliderClickListener {
                            // TODO zoom on image
                        }
                    slider.addSlider(textSlider)
            }

            }
        }.send()
        slider.setPresetTransformer(SliderLayout.Transformer.Accordion)
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        slider.setDuration(4000)
    }

    private fun setTitle(title: String) {
        productTitle.text = title
    }

    private fun setWeAreGood() {
        weAreGood.text = getString(R.string.we_are_good)
    }

    private fun setCost(price: Int) {
        cost.text = price.toString()
    }

    private fun createBasketAndAddProductToBasket(productID: Int) {
        NetworkTask(
            BASKET_LINK,
            NetworkTask.Method.POST,
            """{}""".jsonRequestBody,
            this,
            getString(R.string.wait_for_create_basket),
            "Authorization" to "Token ${settings.getString(AUTH_KEY, "")}"
        ).setOnCallBack { response, _ ->
            if (response?.code == 201) {
                settings.edit().putBoolean(BASKET_EXISTS_KEY, true).apply()
                AddToBasket(productID)
            }
            else
                product_page.snackMessage(getString(R.string.time_out_request))
        }.send()

    }

    private fun AddToBasket(productID: Int) {
        NetworkTask(
            ADD_PRODUCT_TO_BASKET_LINK,
            NetworkTask.Method.POST,
            """{
                                    "product":$productID,
                                    "count": 1
                                    }""".trimIndent().jsonRequestBody,
            this,
            getString(R.string.wait_for_add_to_basket),
            "Authorization" to "Token ${settings.getString(AUTH_KEY, "")}"
        ).setOnCallBack { response, _ ->
            if (response?.code == 201)
                product_page.snackMessage(getString(R.string.addedToBasket))
            else
                product_page.snackMessage(getString(R.string.time_out_request))
        }.send()
    }

    private fun setAddToBasket(productID: Int) {
        addToBasket.visibility = View.VISIBLE
        addToBasket.text = getString(R.string.addToBasket)
        addToBasket.setOnClickListener {
            if (!settings.getBoolean(BASKET_EXISTS_KEY, false))
                createBasketAndAddProductToBasket(productID)
            else
                AddToBasket(productID)
        }
    }


    private fun setCommentsAndProductDescription(productID: Int, description: String) {
        tabs.visibility = View.VISIBLE
        tabs.setupWithViewPager(tab_pages)
        tab_pages.adapter = TabsPageAdapter(supportFragmentManager,productID, description )
    }
    class TabsPageAdapter(fm: FragmentManager, private val productID: Int, private val description: String) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int  = 2

        override fun getItem(i: Int): Fragment = when(i){
            1-> CommentsFragment(productID)
            2-> DescriptionFragment(description)
            else -> CommentsFragment(productID)
        }

        override fun getPageTitle(position: Int): CharSequence = when(position){
            1 -> "نظرات"
            2 -> "مشحصات کالا"
            else ->"مشحصات کالا"
        }
    }


}
