package oop.customer.api.networktask

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import androidx.collection.LruCache
import com.beust.klaxon.Klaxon
import oop.customer.api.progressdialog.CircularProgressBarDialog
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.EMPTY_REQUEST
import org.intellij.lang.annotations.Language

fun Any.toJson(): String = Klaxon().toJsonString(this)

enum class MediaType(private val mimeType: String) {
    JSON("application/json"),
    PlainText("text/plain"),
    FormMultipart("multipart/form-data");

    fun encode(encoder: String = "utf-8") = "${this.mimeType}; charset=$encoder".toMediaType()
}

val String.jsonRequestBody get() = this.toRequestBody(MediaType.JSON.encode())

val Map<String, String>.formBody
    get() =
        FormBody.Builder().also {
            this.forEach { (key, value) ->
                it.add(key, value)
            }
        }.build()

fun formBody(vararg p0: Pair<String, String>) =
    FormBody.Builder().also {
        p0.forEach { it2 ->
            it.add(it2.first, it2.second)
        }
    }.build()

@Language("kotlin")
private const val multiPartBodyExample = """
    val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("key", "value")
            .addFormDataPart(
                "avatar", "avatar.jpeg",
                imageFile.toRequestBody(MediaType.FormMultiPart.encode()))
            .build()
"""

open class NetworkTask(
    val url: String,
    val method: Method = Method.GET,
    protected val body: RequestBody? = null,
    protected val ctx: Context? = null,
    val waitingMessage: String? = null,
    vararg val headers: Pair<String, String>
) {
    enum class Method {
        GET, POST, PUT, PATCH, DELETE, HEAD
    }

    init {
        when (method) {
            Method.GET, Method.HEAD -> {
                if (body != null) {
                    throw IllegalArgumentException("method ${method.name} cannot have body.")
                }
            }
            Method.PATCH, Method.PUT, Method.POST -> {
                if (body == null) {
                    throw IllegalArgumentException("method ${method.name} must have a body.")
                }
            }
        }
    }

    private var onCallBack: (Pair<Response?, String?>) -> Unit = {}

    fun setOnCallBack(callback: (Response?, String?) -> Unit): NetworkTask {
        onCallBack = {
            callback(it.first, it.second)
        }
        return this
    }

    fun send() {
        Task().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    @SuppressLint("StaticFieldLeak")
    inner class Task : AsyncTask<RequestBody, Unit, Pair<Response?, String?>>() {

        var progressDialog: CircularProgressBarDialog? = null

        override fun onPreExecute() {
            super.onPreExecute()
            ctx?.let {
                progressDialog = CircularProgressBarDialog(it, waitingMessage ?: "Please wait...")
                progressDialog?.show()
            }
        }

        override fun doInBackground(vararg params: RequestBody?) = try {
            val request = Request.Builder().url(url).let {
                when (method) {
                    Method.GET -> it.get()
                    Method.POST -> it.post(body!!)
                    Method.DELETE -> it.delete(body ?: EMPTY_REQUEST)
                    Method.PUT -> it.put(body!!)
                    Method.PATCH -> it.patch(body!!)
                    Method.HEAD -> it.head()
                }
            }.also {
                headers.forEach { header ->
                    it.addHeader(header.first, header.second)
                }
            }.build()
            val response = OkHttpClient().newCall(request).execute()
            response to response.body?.string()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        override fun onPostExecute(result: Pair<Response?, String?>) {
            super.onPostExecute(result)
            progressDialog?.dismiss()
            onCallBack(result)
        }
    }
}

class ImageLoadTask {
    @Deprecated(message = "Compressing problem!")
    fun load(url: String, imageView: ImageView) {
        val bitmap = cache[url]
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            NetworkTask(url).setOnCallBack {it, _ ->
                if (it == null) return@setOnCallBack
                if (it.isSuccessful) {
                    val bitmap2 = BitmapFactory.decodeStream(it.body?.byteStream())
                    if(bitmap2 != null){
                        cache.put(url, bitmap2)
                        imageView.setImageBitmap(bitmap2)
                    }
                }
            }.send()
        }
    }

    companion object {
        private val cache by lazy { LruCache<String, Bitmap>(50 * 1024) }
    }
}