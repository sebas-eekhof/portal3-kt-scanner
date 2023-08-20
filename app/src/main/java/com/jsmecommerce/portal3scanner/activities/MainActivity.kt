package com.jsmecommerce.portal3scanner.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.utils.Device
import com.jsmecommerce.portal3scanner.utils.Permissions
import com.jsmecommerce.portal3scanner.utils.Static
import com.jsmecommerce.portal3scanner.workers.ProbeWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    fun runWorker() {
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork("probe-worker", ExistingPeriodicWorkPolicy.UPDATE, PeriodicWorkRequestBuilder<ProbeWorker>(15, TimeUnit.MINUTES).build())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("HWID: ${Device(this).getHWID()}")

        val perms = Permissions(this)

        if(!perms.has(Static.requiredPermissions.map { it.permission })) {
            startActivity(Intent(this, PermissionsActivity::class.java))
            finish()
            return
        }

        val auth = Auth(applicationContext)
        val user = auth.getUser()
        if(user == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        runWorker()

        setContent {
            MainView()
        }
    }
}