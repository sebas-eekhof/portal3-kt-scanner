package com.jsmecommerce.portal3scanner.ui.components.auth

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.theme.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthInput(@StringRes label: Int, keyboardType: KeyboardType, error: Boolean = false, value: String, onValueChange: (String) -> Unit, keyboardActions: KeyboardActions? = null, imeAction: ImeAction? = null, disabled: Boolean = false) {
    Column {
        Description(text = "${stringResource(id = label)} *", color = if (error) Color.Danger.Regular else Color.TextSecondary)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value,
            onValueChange,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = keyboardType,
                imeAction = imeAction ?: ImeAction.None
            ),
            visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .background(
                    color = Color.Element,
                    shape = RoundedCornerShape(8.dp)
                )
                .height(48.dp)
                .fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Element,
                cursorColor = Color.Primary.Regular,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardActions = keyboardActions ?: KeyboardActions(),
            enabled = !disabled
        )
    }
}