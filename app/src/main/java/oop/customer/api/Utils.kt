package oop.customer.api

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import org.intellij.lang.annotations.Language

private val frameLayoutCache = HashMap<FragmentActivity, @androidx.annotation.LayoutRes Int>()

infix fun FragmentActivity.switchToFragment(fragment: Fragment) {
    if (this in frameLayoutCache) {
        supportFragmentManager.beginTransaction().replace(frameLayoutCache[this]!!, fragment)
            .commit()
        return
    }
    val root = window.decorView.rootView as ViewGroup
    val frameLayoutId =
        findFrameLayout(root)?.id ?: throw Exception("No FrameLayout Found in root layout")
    frameLayoutCache += this to frameLayoutId
    supportFragmentManager.beginTransaction().replace(frameLayoutId, fragment).commit()
}

private fun findFrameLayout(root: ViewGroup): FrameLayout? {
    root.forEach {
        if (it is FrameLayout) return it
        if (it is ViewGroup) {
            val gft = findFrameLayout(it)
            if (gft is FrameLayout)
                return gft
        }
    }
    return null
}

@Language("kotlin")
private const val example = """
class MainActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        //or wherever you want
        switchToFragment(MyFragment())
    }
}
"""