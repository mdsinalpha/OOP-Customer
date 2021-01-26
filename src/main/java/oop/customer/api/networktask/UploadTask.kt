package oop.customer.api.networktask

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.beust.klaxon.Klaxon
import oop.customer.api.progressdialog.HorizontalProgressBarDialog
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.TimeUnit

class UploadTask(
    val url: String,
    val source: File,
    val sourceName: String,
    val ctx: Context,
    val message: String = "Uploading...",
    val callback: (Boolean) -> Unit = {}):
    AsyncTask<Unit, Int, Boolean>(){

    private val LOGTAG = "UploadTask"
    private val dialog = HorizontalProgressBarDialog(ctx, message)
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS).build()

    private data class ID(val id: String)
    private var pk: String? = null

    fun upload(){
        executeOnExecutor(THREAD_POOL_EXECUTOR)
    }

    override fun onPreExecute() {
        super.onPreExecute()
        dialog.show()
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        dialog.animateProgress(values[0]?:0)
    }

    override fun doInBackground(vararg p0: Unit?): Boolean  = try{
        if (source.isDirectory)
            throw Exception("Source file should be a file, not a directory.")
        if (!source.exists())
            throw Exception("No such source found!")
        val totalLength = source.length()
        val `in` = FileInputStream(source)
        val buffer = ByteArray(1024*1024)
        var index = 0
        var length = `in`.read(buffer)
        var readLength = length
        do{
            val header = "bytes $index-${index+length-1}/$length"
            Log.d(LOGTAG,"Content-Range: $header")
            val request = Request.Builder().url("$url${if(pk != null) "$pk/" else ""}")
                .header("Content-Range", "bytes $index-${index+length-1}/$length")
                .put(
                    MultipartBody
                    .Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("filename", sourceName)
                    .addFormDataPart(
                        "file", sourceName,
                        buffer.toRequestBody(MediaType.FormMultipart.encode(), 0, length)
                    )
                    .build())
                .build()
            val response = client.newCall(request).execute()
            if(!response.isSuccessful){
                throw Exception("Unsuccessful.")
            }
            if(pk == null)
                pk = Klaxon().parse<ID>(response.body?.string()?:"")?.id
            publishProgress(((readLength*100)/totalLength).toInt())
            if(isCancelled)
                throw Exception("Upload canceled by user!")
            index += length
            length = `in`.read(buffer)
            readLength += length
        }while (length > 0)
        val request = Request.Builder().url("$url$pk/")
            .post(FormBody.Builder().add("md5", "e3").build())
            .build()
        client.newCall(request).execute()
        true
    } catch (e: Exception){
        e.printStackTrace()
        false
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        dialog.dismiss()
        callback(result?:false)
    }
}