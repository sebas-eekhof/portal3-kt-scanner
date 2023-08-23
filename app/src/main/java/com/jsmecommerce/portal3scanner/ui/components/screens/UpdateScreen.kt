package com.jsmecommerce.portal3scanner.ui.components.screens

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.general.DownloadProgress
import com.jsmecommerce.portal3scanner.models.general.UpdateVersion
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleTextBold
import com.jsmecommerce.portal3scanner.ui.components.general.Title
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.DownloadTracker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(version: UpdateVersion) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var isUpdating by remember { mutableStateOf(false) }
    var updateProgress by remember { mutableStateOf<DownloadProgress?>(null) }

    fun performUpdate() {
        isUpdating = true
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadRequest = DownloadManager.Request(Uri.parse(version.url))
        downloadRequest.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "update-${version.version_code}.apk"
        )
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        downloadRequest.setTitle("portal3-scanner-${version.version_name}.apk")
        val downloadId = downloadManager.enqueue(downloadRequest)
        DownloadTracker(
            context = context,
            downloadId = downloadId
        ).observe(lifecycleOwner) {
            println(it)
            updateProgress = it
            if(it != null) {
                if(it.status == DownloadProgress.DownloadStatus.FAILED) {

                } else if(it.status == DownloadProgress.DownloadStatus.SUCCESSFUL) {
                    isUpdating = false
                    updateProgress = null
                    context.startActivity(
                        Intent(Intent.ACTION_INSTALL_PACKAGE)
                            .setDataAndType(downloadManager.getUriForDownloadedFile(downloadId), "application/vnd.android.package-archive")
                            .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_download),
            contentDescription = stringResource(id = R.string.update_available),
            tint = Color.White,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Title(text = stringResource(id = R.string.update_available))
        Spacer(modifier = Modifier.height(8.dp))
        Description(text = String.format(stringResource(id = R.string.update_available_text), version.version_name))
        Spacer(modifier = Modifier.height(32.dp))
        if(isUpdating) {
            LinearProgressIndicator(
                trackColor = Color.Element,
                color = Color.Primary.Regular,
                progress = (updateProgress?.progress ?: 0).toFloat() / 100
            )
        } else {
            if(updateProgress != null && updateProgress!!.status == DownloadProgress.DownloadStatus.FAILED) {
                SimpleTextBold(
                    text = stringResource(id = R.string.update_went_wrong),
                    color = Color.Danger.Regular
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
            Surface(
                color = Color.Primary.Regular,
                shape = RoundedCornerShape(100),
                onClick = { performUpdate() }
            ) {
                SimpleTextBold(
                    text = String.format(
                        stringResource(id = R.string.update_button),
                        version.version_name
                    ),
                    modifier = Modifier.padding(
                        start = 32.dp,
                        end = 32.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    )
                )
            }
        }
    }
}