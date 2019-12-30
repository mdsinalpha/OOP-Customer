package oop.customer.api

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.fragment.app.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import java.io.Serializable
import kotlin.reflect.full.createInstance

inline fun <reified T : Activity> Activity.open(vararg args: Pair<String, Serializable?>) {
    val move = Intent(this, T::class.java)
    args.forEach { (name, value) ->
        move.putExtra(name, value)
    }
    startActivity(move)
}

@JvmName("openWithParcelableArgs")
inline fun <reified T : Activity> Activity.open(vararg args: Pair<String, Parcelable?>) {
    val move = Intent(this, T::class.java)
    args.forEach { (name, value) ->
        move.putExtra(name, value)
    }
    startActivity(move)
}

inline fun <reified T : Activity> Activity.open() {
    val args = emptyArray<Pair<String, Serializable>>()
    open<T>(*args)
}

inline fun <reified T : Activity> Activity.replaceWith(vararg args: Pair<String, Serializable?>) {
    open<T>(*args)
    finish()
}

inline fun <reified T : Activity> Activity.replaceWith() {
    val args = emptyArray<Pair<String, Serializable>>()
    replaceWith<T>(*args)
}

@JvmName("replaceWithParcelableArgs")
inline fun <reified T : Activity> Activity.replaceWith(vararg args: Pair<String, Parcelable?>) {
    open<T>(*args)
    finish()
}

class GenericFragmentPagerAdapter(
    fragmentManager: FragmentManager,
    private vararg val fragments: Pair<String, Fragment>
) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int) = fragments[position].second

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = fragments[position].first

}

inline fun <reified T : Fragment> Fragment.replaceWith(@IdRes frameLayout: Int) {
    fragmentManager!!.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        .replace(frameLayout, T::class.createInstance()).commit()
}

fun Fragment.replaceWith(replacement: Fragment, @IdRes frameLayout: Int) {
    fragmentManager!!.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        .replace(frameLayout, replacement).commit()
}


inline infix fun <reified T : Fragment> FragmentActivity.showFragment(@IdRes frameLayout: Int) {
    supportFragmentManager.beginTransaction()
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        .replace(frameLayout, T::class.createInstance()).commit()
}

/**
 * Sets up a simple [FragmentPagerAdapter] that binds the receiver [TabLayout] to [viewPager] with
 * [Pair]<[String],[Fragment]> as a map of Fragments to their names to bind.
 * @author mohamadhassan ebrahimi
 */
fun TabLayout.setupWithViewPager(
    fragmentManager: FragmentManager,
    viewPager: ViewPager,
    vararg fragments: Pair<String, Fragment>
) {
    viewPager.adapter = GenericFragmentPagerAdapter(fragmentManager, *fragments)
    setupWithViewPager(viewPager)
}

/**
 * Simple Listener action for [TextView].
 *
 * [onChanged] is called each time the text is changed. If the result of `oldText` and `newText` is
 * `true`, the text will change. Otherwise it will be vetoed and the `oldText` will be retained to
 * the text.
 *
 * Note that when adding this to the [TextView], the previous value in the text should be valid
 * respecting [onChanged]. This is needed because if the first try to change the text is invalid, it
 * will try to change the text to default. (So if the default is not valid, it will fall in an
 * infinite loop.)
 *
 * @author mohamadhassan ebrahimi
 */
fun TextView.setOnTextChangedListener(onChanged: (oldText: CharSequence, newText: CharSequence) -> Boolean) {
    require(
        onChanged(
            text,
            text
        )
    ) { "Previous filled text doesn't match criteria. It may cause infinite loop. (See docs for more details)" }
    lateinit var oldText: CharSequence
    var isValid = true
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable) {
            if (!isValid) {
                p0.replace(0, p0.length, oldText)
            }
        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            oldText = p0
        }

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            isValid = onChanged(oldText, p0.toString())
        }
    })
}

private val enToFaDigits = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")

/**
 * Converts any digit numbers in English language in a text to their Persian counterparts.
 * @author mohamadhassan ebrahimi
 */
fun String.convertDigitsToPersian() =
    replace("\\d".toRegex()) {
        enToFaDigits[it.value.toInt()]
    }

enum class Orientation(val orientation: Int) {
    Horizontal(DividerItemDecoration.VERTICAL), Vertical(DividerItemDecoration.HORIZONTAL)
}

/**
 * Adds a divider to [RecyclerView] and returns it.
 * @param orientation Orientation
 * @author mohamadhassan ebrahimi
 */
fun RecyclerView.withDivider(orientation: Orientation = Orientation.Horizontal): RecyclerView {
    addItemDecoration(DividerItemDecoration(context, orientation.orientation))
    return this
}

fun View.snackMessage(text: String) {
    val bar = Snackbar.make(this, text, Snackbar.LENGTH_LONG)
    val textView = bar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.gravity = Gravity.CENTER_HORIZONTAL
    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    bar.show()
}

fun View.snackMessage(textId: Int) {
    val bar = Snackbar.make(this, textId, Snackbar.LENGTH_LONG)
    val textView = bar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.gravity = Gravity.CENTER_HORIZONTAL
    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    bar.show()
}

fun String.trimAround(prefix: String, suffix: String): String {
    val prefixReg = ".*?$prefix".toRegex()
    val suffixReg = ".*?$suffix".toRegex()
    val pref = prefixReg.find(this)?.value ?: ""
    val suff = suffixReg.find(this.reversed())?.value ?: ""
    return removeSurrounding(pref, suff)
}