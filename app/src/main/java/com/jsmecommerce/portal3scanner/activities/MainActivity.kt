package com.jsmecommerce.portal3scanner.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.utils.Permissions
import com.jsmecommerce.portal3scanner.workers.ProbeWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    fun runWorker() {
        WorkManager.getInstance(applicationContext)
            .enqueueUniqueWork("probe-worker-once", ExistingWorkPolicy.REPLACE, OneTimeWorkRequestBuilder<ProbeWorker>().build())
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork("probe-worker", ExistingPeriodicWorkPolicy.UPDATE, PeriodicWorkRequestBuilder<ProbeWorker>(15, TimeUnit.MINUTES).build())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val perms = Permissions(this)

        if(
            !perms.has(Manifest.permission.ACCESS_FINE_LOCATION) ||
            !perms.has(Manifest.permission.ACCESS_COARSE_LOCATION) ||
            !perms.has(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        )
            perms.request(listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )) {
                if(it[Manifest.permission.ACCESS_FINE_LOCATION] == true && it[Manifest.permission.ACCESS_COARSE_LOCATION] == true && it[Manifest.permission.ACCESS_BACKGROUND_LOCATION] == true)
                    runWorker()
                else
                    Toast.makeText(applicationContext, "Please allow location permissions", Toast.LENGTH_SHORT).show()
            }
        else
            runWorker()

        val auth = Auth(applicationContext)
        val user = auth.getUser()
        if(user == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }
        setContent {
            MainView()
        }
    }
}