package com.jsmecommerce.portal3scanner.ui.components.form

import androidx.annotation.StringRes
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.SelectItem
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Select(
    @StringRes label: Int,
    @StringRes placeholder: Int = R.string.select_a_item,
    items: List<SelectItem>,
    value: String? = null,
    onChange: ((value: String?) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedItem: SelectItem? = if (value == null) null else items.firstOrNull { it.name == value }

    Column {
        Description(text = stringResource(id = label))
        Spacer(modifier = Modifier.height(8.dp))
        Box {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                color = Color.Element,
                onClick = { expanded = true },
                shape = RoundedCornerShape(7)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    if(selectedItem != null) {
                        if(selectedItem.component != null)
                            selectedItem.component!!()
                        else if(selectedItem.title != null)
                            SimpleText(selectedItem.title)
                    } else
                        SimpleText(stringResource(id = placeholder))
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.Subelement)
            ) {
                DropdownMenuItem(
                    text = {
                        SimpleText(stringResource(id = placeholder))
                    },
                    onClick = {
                        onChange?.let { it(null) }
                        expanded = false
                    }
                )
                items.forEach {
                    DropdownMenuItem(
                        text = {
                            if(it.component != null)
                                it.component!!()
                            else if(it.title != null)
                                SimpleText(text = it.title)
                        },
                        onClick = {
                            onChange?.let { func -> func(it.name) }
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}