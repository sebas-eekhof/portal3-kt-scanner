package com.jsmecommerce.portal3scanner.models

import android.app.DownloadManager

data class DownloadProgress(
    val totalSizeBytes: Long = 0,
    val downloadedSizeBytes: Long = 0,
    val progress: Float = (0).toFloat(),
    val status: DownloadStatus = DownloadStatus.PENDING
) {
    override fun toString(): String {
        return "(DownloadProgress) Status: $status, Progress: $progress, Total: $totalSizeBytes, Downloaded: $downloadedSizeBytes"
    }
    enum class DownloadStatus {
        PENDING,
        PAUSED,
        RUNNING,
        FAILED,
        SUCCESSFUL;
        companion object {
            fun fromDownloadManagerStatus(status: Int): DownloadStatus = when(status) {
                DownloadManager.STATUS_FAILED -> FAILED
                DownloadManager.STATUS_PAUSED -> PAUSED
                DownloadManager.STATUS_PENDING -> PENDING
                DownloadManager.STATUS_RUNNING -> RUNNING
                DownloadManager.STATUS_SUCCESSFUL -> SUCCESSFUL
                else -> PENDING
            }
        }
    }
}
