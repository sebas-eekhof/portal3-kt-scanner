package com.jsmecommerce.portal3scanner.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.ui.components.general.SmallTitle
import com.jsmecommerce.portal3scanner.ui.components.permissions.PermissionsCheckItem
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.Permissions
import com.jsmecommerce.portal3scanner.utils.Static

class PermissionsActivity : ComponentActivity() {
    override fun onResume() {
        super.onResume()
        val perms = Permissions(this)
        if(perms.has(Static.requiredPermissions.map { it.permission })) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val perms = Permissions(this)
        if(perms.has(Static.requiredPermissions.map { it.permission })) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
        perms.request(Static.requiredPermissions.map { it.permission }) { result ->
            if(Static.requiredPermissions.none { result[it.permission] == false }) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                val filteredPermissions = Static.requiredPermissions.sortedBy { if (result[it.permission] == false) 1 else 0 }
                setContent {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Background)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                SmallTitle(stringResource(id = R.string.permissions_title), textAlign = TextAlign.Center)
                                Spacer(modifier = Modifier.height(8.dp))
                                Description(stringResource(id = R.string.permissions_description), textAlign = TextAlign.Center)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            for(i in 0 until filteredPermissions.count()) {
                                if(i != 0)
                                    Spacer(modifier = Modifier.height(8.dp))
                                PermissionsCheckItem(permission = filteredPermissions[i], granted = result[filteredPermissions[i].permission] == true)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Surface(
                                color = Color.Element,
                                shape = RoundedCornerShape(4),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClick = {
                                    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package", packageName, null)))
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    SimpleText(text = stringResource(id = R.string.permissions_allow_in_settings))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}