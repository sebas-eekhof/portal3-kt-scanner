package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.User
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun UserBanner(user: User) {
    Surface(
        shape = RoundedCornerShape(4),
        color = Color.Element,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
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