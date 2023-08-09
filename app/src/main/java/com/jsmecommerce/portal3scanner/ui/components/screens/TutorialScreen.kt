package com.jsmecommerce.portal3scanner.ui.components.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleTextBold
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText

@Composable
fun TutorialScreen(onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Background)
    ) {
        Column {
            Spacer(modifier = Modifier.height(100.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier
                    .height(100.dp)
                    .width(4.dp)
                    .background(androidx.compose.ui.graphics.Color.Yellow))
                Spacer(modifier = Modifier.width(32.dp))
                SimpleTextBold(text = stringResource(id = R.string.settings_tutorial_scan))
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                SimpleTextBold(text = stringResource(id = R.string.settings_tutorial_scan))
                Spacer(modifier = Modifier.width(32.dp))
                Box(modifier = Modifier
                    .height(100.dp)
                    .width(4.dp)
                    .background(androidx.compose.ui.graphics.Color.Yellow))
            }
        }
        Column {
            Spacer(modifier = Modifier.height(240.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier
                    .height(90.dp)
                    .width(4.dp)
                    .background(Color.Element))
                Spacer(modifier = Modifier.width(32.dp))
                SimpleTextBold(text = stringResource(id = R.string.settings_tutorial_back))
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(240.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                SimpleTextBold(text = stringResource(id = R.string.settings_tutorial_volume_up))
                Spacer(modifier = Modifier.width(32.dp))
                Box(modifier = Modifier
                    .height(40.dp)
                    .width(4.dp)
                    .background(Color.Element))
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(290.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                SimpleTextBold(text = stringResource(id = R.string.settings_tutorial_volume_down))
                Spacer(modifier = Modifier.width(32.dp))
                Box(modifier = Modifier
                    .height(40.dp)
                    .width(4.dp)
                    .background(Color.Element))
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_white),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(onClick = { onClose() }) {
                SimpleText(stringResource(id = R.string.close))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}