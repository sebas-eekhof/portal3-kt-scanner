package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.User

@Composable
fun UserBanner(user: User) {
    Card {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Jdenticon(user.jdenticon, size = 86, boxed = true)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                SmallTitle(text = user.fullName)
                Description(text = user.email)
            }
        }
    }
}