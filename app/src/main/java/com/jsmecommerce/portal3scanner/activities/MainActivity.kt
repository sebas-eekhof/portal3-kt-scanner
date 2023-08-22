package com.jsmecommerce.portal3scanner.activities

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jsmecommerce.portal3scanner.ui.components.popups.SecretMenuPopup
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.utils.Device
import com.jsmecommerce.portal3scanner.utils.Permissions
import com.jsmecommerce.portal3scanner.utils.Static
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel
import com.jsmecommerce.portal3scanner.workers.ProbeWorker
import com.jsmecommerce.portal3scanner.R
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    var keysDown: ArrayList<Int> = ArrayList()
    var secretMenuStepActivated: Boolean = false
    val mvm = MainViewModel()

    fun runWorker() {
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork("probe-worker", ExistingPeriodicWorkPolicy.UPDATE, PeriodicWorkRequestBuilder<ProbeWorker>(15, TimeUnit.MINUTES).build())
    }

    override fun onBackPressed() {
        if(mvm.backEnabled.value == true)
            super.onBackPressed()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if(keysDown.contains(keyCode)) {
            if(secretMenuStepActivated && keyCode == 103) {
                keysDown.removeAll(keysDown.toSet())
                mvm.setDrawer(R.string.secret_menu_title) {
                    SecretMenuPopup(activity = this)
                }
            }
            secretMenuStepActivated = false
            keysDown.remove(keyCode)
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(!keysDown.contains(keyCode)) {
            keysDown.add(keyCode)
            if(
                keysDown.contains(25) &&
                keysDown.contains(103) &&
                keysDown.contains(10036)
            )
                secretMenuStepActivated = true
        }
        return super.onKeyDown(keyCode, event)
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
            MainView(mvm = mvm)
        }
    }
}