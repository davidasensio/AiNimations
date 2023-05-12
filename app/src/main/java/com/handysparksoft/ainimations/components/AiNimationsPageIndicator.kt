package com.handysparksoft.ainimations.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun PageIndicator(
    pageCount: Int,
    selectedPage: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        repeat(pageCount) {
            val isSelectedPage = it == selectedPage
            val dotColor = getDotColor(isSelectedPage = isSelectedPage)
            Dot(color = dotColor)
        }
    }
}

@Composable
private fun getDotColor(isSelectedPage: Boolean) = when {
    isSelectedPage -> MaterialTheme.colorScheme.secondaryContainer
    else -> MaterialTheme.colorScheme.outline
}

@Composable
private fun Dot(color: Color) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .padding(4.dp)
            .background(color = color, shape = CircleShape)
    )
}
