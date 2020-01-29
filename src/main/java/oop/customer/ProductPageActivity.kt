package oop.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beust.klaxon.Klaxon
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import kotlinx.android.synthetic.main.activity_product_page.*
import oop.customer.api.snackMessage

class ProductPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        // TODO get information from server

        // set slider
        listOf(
            "https://youshanshoes.com/wp-content/uploads/2019/01/Edit05.jpg",
            "http://sport.pama.shop/content/images/thumbs/0002638_2018.jpeg",
            "https://bolgano.com/9307-large_default_2x/%DA%A9%D9%81%D8%B4-%D8%A7%D8%B3%D9%BE%D8%B1%D8%AA-%D9%85%D8%B1%D8%AF%D8%A7%D9%86%D9%87-%DA%A9%D8%A7%D8%AA%D8%B1%D9%BE%DB%8C%D9%84%D8%A7%D8%B1-%D9%85%D8%AF%D9%84-caterpillar-intruder-p723902.jpg",
            "https://dkstatics-public.digikala.com/digikala-products/115231998.jpg?x-oss-process=image/resize,h_1600/quality,q_80"
        ).forEach {
            val textSlider = TextSliderView(this)
            textSlider
                .image(it)
                .setScaleType(BaseSliderView.ScaleType.CenterInside)
                .setOnSliderClickListener {
                    // TODO zoom on image
                }
            slider.addSlider(textSlider)
        }
        slider.setPresetTransformer(SliderLayout.Transformer.Accordion)
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        slider.setDuration(4000)

        // TODO set product title from server

        // set we are good
        weAreGood.text = getString(R.string.we_are_good)

        // TODO set product cost from server
        cost.text = "۲۰۰۰ تومان"


        addToBasket.text = getString(R.string.addToBasket)
        addToBasket.setOnClickListener {
            // TODO add to basket of user
            it.snackMessage(getString(R.string.addToBasket))
        }

        tabs.addTab(tabs.newTab().setText(getString(R.string.comments)))
        tabs.addTab(tabs.newTab().setText(getString(R.string.productDescription)))
    }

}
