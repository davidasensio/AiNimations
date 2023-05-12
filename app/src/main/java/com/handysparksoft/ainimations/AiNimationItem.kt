package com.handysparksoft.ainimations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
data class AiNimationItem(
    val title: String,
    val subtitle: String,
    val content: @Composable () -> Unit
)

@Immutable
data class AiNimationItemList(val items: List<AiNimationItem>)
