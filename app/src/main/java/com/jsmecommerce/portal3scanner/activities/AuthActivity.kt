package com.jsmecommerce.portal3scanner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.R;
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.components.general.Title
import com.jsmecommerce.portal3scanner.ui.components.auth.AuthButton
import com.jsmecommerce.portal3scanner.ui.components.auth.AuthInput
import com.jsmecommerce.portal3scanner.ui.theme.Portal3ScannerTheme
import com.jsmecommerce.portal3scanner.utils.Api
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.utils.Database
import com.jsmecommerce.portal3scanner.utils.Validator
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.concurrent.thread

class AuthActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val focusManager = LocalFocusManager.current
            var emailError by remember { mutableStateOf(false) }
            var email by remember { mutableStateOf("s.eekhof@portal3.nl") }
            var password by remember { mutableStateOf("Aardbeien1:") }
            var loading by remember { mutableStateOf(false) }

            val auth = Auth(applicationContext)

            fun login() {
                if(!Validator(email).isValidEmail()) {
                    emailError = true
                    Toast.makeText(applicationContext, "Ongeldig e-mail adres", Toast.LENGTH_SHORT).show()
                    return
                }
                GlobalScope.launch(Dispatchers.IO) {
                    loading = true
                    val res = Api.Request("/legacy/auth/login")
                        .setNamespace(Api.Namespace.REST)
                        .setMethod(Api.RequestMethod.POST)
                        .setBody(
                            JSONObject()
                                .put("email", email)
                                .put("password", password)
                        )
                        .exec()
                    if(res.hasError) {
                        loading = false
                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, res.error?.message, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val data = res.getJsonObject()
                        if((data != null) && (data.getString("type") == "jwt")) {
                            val valid = auth.login(data.getString("jwt"))
                            loading = false
                            if(valid) {
                                withContext(Dispatchers.Main) {
                                    startActivity(
                                        Intent(
                                            applicationContext,
                                            MainActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Ongeldige login",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            loading = false
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    applicationContext,
                                    "Ongeldige login",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }

            Portal3ScannerTheme(centerHorizontal = true, centerVertical = true) {
                Image(
                    painter = painterResource(id = R.drawable.icon_white),
                    contentDescription = "Portal3 Icon",
                    Modifier.size(72.dp)
                )
                Spacer(Modifier.size(24.dp))
                Title("Inloggen")
                Spacer(Modifier.size(8.dp))
                Description("Welkom terug! U moet eerst even inloggen voordat u verder kan.", textAlign = TextAlign.Center)
                Spacer(Modifier.size(64.dp))
                Column(Modifier.padding(horizontal = 32.dp)) {
                    AuthInput(
                        label = "E-Mail",
                        keyboardType = KeyboardType.Email,
                        error = emailError,
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = false
                        },
                        imeAction = ImeAction.Next,
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        disabled = loading
                    )
                    Spacer(Modifier.size(16.dp))
                    AuthInput(
                        label = "Wachtwoord",
                        keyboardType = KeyboardType.Password,
                        value = password,
                        onValueChange = { password = it },
                        imeAction = ImeAction.Go,
                        keyboardActions = KeyboardActions(
                            onGo = {
                                focusManager.clearFocus(true)
                                login()
                            }
                        ),
                        disabled = loading
                    )
                    Spacer(Modifier.size(32.dp))
                    AuthButton(text = "Inloggen", onClick = { login() }, loading = loading)
                }
            }
        }
    }
}