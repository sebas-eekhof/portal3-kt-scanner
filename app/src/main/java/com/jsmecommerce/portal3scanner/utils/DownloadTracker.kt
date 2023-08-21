package com.jsmecommerce.portal3scanner.utils

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import androidx.lifecycle.LiveData
import com.jsmecommerce.portal3scanner.models.general.DownloadProgress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DownloadTracker(
    private val context: Context,
    private val downloadId: Long
) : LiveData<DownloadProgress?>(), CoroutineScope {
    private val downloadManager by lazy { context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
    private val job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.IO + job

    @SuppressLint("Range")
    override fun onActive() {
        super.onActive()
        launch {
            while(isActive) {
                var item: DownloadProgress? = null
                val cursor = downloadManager.query(DownloadManager.Query().setFilterById(downloadId))
                while(cursor.moveToNext()) {
                    if (cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID)) == downloadId) {
                        val status = DownloadProgress.DownloadStatus.fromDownloadManagerStatus(
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                        )
                        val totalSizeBytes =
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                                .toLong()
                        val downloadedSizeBytes =
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                                .toLong()
                        item = DownloadProgress(
                            status = status,
                            downloadedSizeBytes = downloadedSizeBytes,
                            totalSizeBytes = totalSizeBytes,
                            progress = ((100).toFloat() / totalSizeBytes.toFloat() * downloadedSizeBytes.toFloat())
                        )
                    }
                }
                postValue(item)
                if(item != null && listOf(DownloadProgress.DownloadStatus.FAILED, DownloadProgress.DownloadStatus.SUCCESSFUL).contains(item.status))
                    job.cancel()
                delay(300)
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        job.cancel()
    }
}