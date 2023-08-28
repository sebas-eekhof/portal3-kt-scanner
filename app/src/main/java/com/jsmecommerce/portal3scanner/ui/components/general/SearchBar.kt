package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.ui.theme.Portal3ScannerTheme
import com.jsmecommerce.portal3scanner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var value by remember { mutableStateOf("test") }

    TextField(
        value = value,
        onValueChange = { value = it },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Element,
            cursorColor = Color.Primary.Regular,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            textColor = Color.White
        ),
        placeholder = { Description(text = stringResource(id = R.string.search)) },
        singleLine = true,
        shape = RoundedCornerShape(100),
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = stringResource(id = R.string.search), tint = Color.TextSecondary) }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    Portal3ScannerTheme {
        SearchBar()
    }
}