package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.ui.theme.Font
import com.jsmecommerce.portal3scanner.ui.theme.Portal3ScannerTheme

@Composable
fun Title(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontFamily = Font.Roboto,
        fontWeight = FontWeight.Bold,
        color = Color.TextPrimary,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TitlePreview() {
    Portal3ScannerTheme {
        Title("Portal3 Scanner")
    }
}

@Composable
fun SimpleText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontFamily = Font.Roboto,
        fontWeight = FontWeight.Normal,
        color = Color.TextPrimary,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SimpleTextPreview() {
    Portal3ScannerTheme {
        SimpleText("Portal3 Scanner")
    }
}

@Composable
fun Description(text: String, textAlign: TextAlign ?= null, color: androidx.compose.ui.graphics.Color = Color.TextSecondary, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontFamily = Font.Roboto,
        fontWeight = FontWeight.Normal,
        color = color,
        textAlign = textAlign,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun DescriptionPreview() {
    Portal3ScannerTheme {
        Description("Portal3 Scanner")
    }
}