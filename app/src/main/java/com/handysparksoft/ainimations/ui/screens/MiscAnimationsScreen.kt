package com.handysparksoft.ainimations.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.handysparksoft.ainimations.AiNimationItem
import com.handysparksoft.ainimations.AiNimationItemList
import com.handysparksoft.ainimations.animation.misc.AnimatedFlashLight
import com.handysparksoft.ainimations.animation.misc.AnimatedHeart
import com.handysparksoft.ainimations.animation.misc.AnimatedWaves
import com.handysparksoft.ainimations.components.AiNimationsHorizontalPager
import com.handysparksoft.ainimations.ui.theme.Lime

@Composable
fun MiscAnimationsScreen() {
    AiNimationsHorizontalPager(
        itemList = getMiscItems()
    )
}

private fun getMiscItems() = AiNimationItemList(
    listOf(
        AiNimationItem(
            title = "Heart",
            subtitle = "Heart jump beat animation",
            content = {
                AnimatedHeart(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Lime)
                )
            }
        ),
        AiNimationItem(
            title = "Waves",
            subtitle = "Animated Waves",
            content = {
                AnimatedWaves(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Lime)
                )
            }
        ),
        AiNimationItem(
            title = "Flashlight",
            subtitle = "Move the flashlight and see the nice effect",
            content = {
                AnimatedFlashLight()
            }
        )
    )
)
