package oop.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_product_page.*

class ProductPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        // TODO get information from server

        // set slider
        val images = mutableListOf<String>()
        images.forEach {
            val textSlider = TextSliderView(this)
            textSlider
                .image(it)
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener {
                    // TODO zoom on image
                }
            slider.addSlider(textSlider)
        }
        slider.setPresetTransformer(SliderLayout.Transformer.Accordion)
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        slider.setDuration(4000)

        // TODO set product title from server
        productTitle.text= ""

        // set we are good
        weAreGood.text = getString(R.string.we_are_good)

        // TODO set product cost from server
        cost.text = ""


        addToBasket.text = getString(R.string.addToBasket)
        addToBasket.setOnClickListener {
            // TODO add to basket of user
            Snackbar.make(it,getString(R.string.addedToBasket),Snackbar.LENGTH_SHORT).show()
           }

        tabs.addTab(tabs.newTab().setText(getString(R.string.productDescription)))
        tabs.addTab(tabs.newTab().setText(getString(R.string.comments)))
    }

}
