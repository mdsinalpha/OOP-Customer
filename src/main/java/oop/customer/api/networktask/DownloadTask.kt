package oop.customer.api.networktask

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import oop.customer.api.progressdialog.HorizontalProgressBarDialog
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

class DownloadTask(
    val url: String,
    val target: File,
    val ctx: Context,
    val message: String = "Downloading...",
    val callback: (Boolean) -> Unit = {}
) : AsyncTask<Unit, Int, Boolean>() {

    private val LOGTAG = "DownloadTask"
    private val dialog = HorizontalProgressBarDialog(ctx, message)
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS).build()

    fun download(){
        executeOnExecutor(THREAD_POOL_EXECUTOR)
    }

    override fun onPreExecute() {
        super.onPreExecute()
        dialog.show()
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        dialog.animateProgress(values[0] ?: 0)
    }

    override fun doInBackground(vararg p0: Unit?): Boolean =
        try {
            if (target.isDirectory)
                throw Exception("Target file should be a file, not a directory.")
            if (!target.exists())
                target.createNewFile()
            val request = Request.Builder().url(url).get().build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val totalLength = response.body!!.contentLength()
                var downloadedLength = 0
                Log.d(LOGTAG, "Starting to download file with size $totalLength bytes.")
                val `in` = response.body!!.byteStream()
                val out = FileOutputStream(target)
                val buffer = ByteArray(1024*1024)
                var length = `in`.read(buffer)
                do {
                    downloadedLength += length
                    Log.d(LOGTAG, "$downloadedLength bytes downloaded.")
                    publishProgress(((downloadedLength * HorizontalProgressBarDialog.MAX_PROGRESS) / totalLength).toInt())
                    out.write(buffer, 0, length)
                    if (isCancelled)
                        throw Exception("Download canceled by user!")
                    length = `in`.read(buffer)
                } while (length > 0)
                out.flush()
                out.close()
                true
            }
            else false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        dialog.dismiss()
        callback(result ?: false)
    }
}