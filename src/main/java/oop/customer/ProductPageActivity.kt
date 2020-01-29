package oop.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beust.klaxon.Klaxon
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_product_page.*
import oop.customer.api.networktask.NetworkTask
import oop.customer.api.snackMessage

class ProductPageActivity : AppCompatActivity() {
    private val klaxon = Klaxon()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)
        val productDetail = fetchProductDetailDataFromServer(intent.getStringExtra(PRODUCT_ID)!!)
        if (productDetail != null) {
            setImagesOfSlider(productDetail.id)
            setTitle(productDetail.name)
            setWeAreGood()
            setCost(productDetail.Price)
            setAddToBasket(productDetail.id)
        }
    }

    private fun fetchProductDetailDataFromServer(productID: String): ProductDetail? {
        var product: ProductDetail? = null
        val networkTask =
            NetworkTask(
                "$SERVER_LINK/products/$productID/",
                method = NetworkTask.Method.GET,
                waitingMessage = getString(R.string.message_wait)
            )

        networkTask.setOnCallBack { response, s ->
            product = klaxon.parse<ProductDetail>(response!!.body.toString())
        }.send()
        repeat(5000) {
            Thread.sleep(1)
            if (product != null)
                return product
        }
        if (product == null) {
            Snackbar.make(
                product_page,getString(R.string.time_out_request), Snackbar.LENGTH_LONG).show()
            return null
        }
        return null
    }

    private fun fetchImagesOfProductFromServer(productID: Int): List<Image>? {
        var images: List<Image>? = null
        val networkTask =
            NetworkTask(
                "$SERVER_LINK/products/$productID/images",
                method = NetworkTask.Method.GET,
                waitingMessage = getString(R.string.message_wait)
            )
        networkTask.setOnCallBack { response, s ->
            images = klaxon.parseArray(response!!.body.toString())
        }
        return images
    }

    private fun fetchCommentsOfProduct(productID: Int): List<Comment>? {
        var comments: List<Comment>? = null
        val networkTask =
            NetworkTask(
                "$SERVER_LINK/comment/$productID/",
                method = NetworkTask.Method.GET,
                waitingMessage = getString(R.string.message_wait)
            )
        networkTask.setOnCallBack { response, s ->
            comments = klaxon.parseArray(response!!.body.toString())
        }
        return comments
    }

    private fun setImagesOfSlider(productID: Int) {
            NetworkTask(
                "$SERVER_LINK/products/$productID/images",
                method = NetworkTask.Method.GET,
                waitingMessage = getString(R.string.message_wait)
            ).setOnCallBack { response, s ->
            klaxon.parseArray<Image>(response!!.body.toString())!!.forEach {
                val textSlider = TextSliderView(this)
                textSlider
                    .image("$SERVER_LINK$it.imageContent")
                    .setScaleType(BaseSliderView.ScaleType.CenterInside)
                    .setOnSliderClickListener {
                        // TODO zoom on image
                    }
                slider.addSlider(textSlider)

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

    private fun setAddToBasket(productID: Int) {
        addToBasket.text = getString(R.string.addToBasket)
        addToBasket.setOnClickListener {
            // TODO add to basket of user
            it.snackMessage(getString(R.string.addToBasket))
        }

    }

    private fun setCommentsAndProductDescription(poductID: Int, description: String) {
        tabs.addTab(tabs.newTab().setText(getString(R.string.comments)))
        tabs.addTab(tabs.newTab().setText(getString(R.string.productDescription)))
    }

}
