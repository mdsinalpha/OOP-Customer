import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_basket.*
import oop.customer.Product
import oop.customer.R
import oop.customer.api.networktask.NetworkTask
import oop.customer.api.networktask.jsonRequestBody
import oop.customer.api.recyclerview.bind

val testProducts = listOf (
    Product(".۱", "یخچال سای بای ساید سامسونگ K540"),
    Product(".۲", "مایکروفر من"))
val testProducts2 = ArrayList<Product>()

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

      /*  status_code.setOnClickListener { val d = Dialog(this@MainActivity)
            d.setContentView(R.layout.dialog_code)
            d.show()
        }
        for(i in 0..10)
            testProducts2.addAll(testProducts)
        basket_recycler.bind(testProducts2, this, R.layout.listitem_product).apply()
        val et: EditText? = null
        NetworkTask(LOGIN_LINK, NetworkTask.Method.POST, """
"name": "${et?.text}",
        }""".jsonRequestBody, this, "Please wait...").setOnCallBack { response, s ->
            s?.let {
                val p: Person? = Klaxon().parse<Person>(it)

            }
        } .send()*/


    }


}
