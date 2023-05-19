package com.handysparksoft.ainimations.ui.screens

import androidx.compose.runtime.Composable
import com.handysparksoft.ainimations.AiNimationItem
import com.handysparksoft.ainimations.AiNimationItemList
import com.handysparksoft.ainimations.animation.component.AnimatedRotationGradientProfileImagePreview
import com.handysparksoft.ainimations.animation.component.AnimatedSweepGradientProfileImagePreview
import com.handysparksoft.ainimations.animation.component.AnimatedSweepProfileImagePreview
import com.handysparksoft.ainimations.animation.component.AnimatedSweepWithWhiteBorderProfileImagePreview
import com.handysparksoft.ainimations.components.AiNimationsHorizontalPager

@Composable
fun ComponentAnimationsScreen() {
    AiNimationsHorizontalPager(
        itemList = getComponentItems()
    )
}

private fun getComponentItems() = AiNimationItemList(
    listOf(
        AiNimationItem(
            title = "Circle Profile Image",
            subtitle = "Animated Sweep",
            content = {
                AnimatedSweepProfileImagePreview()
            }
        ),
        AiNimationItem(
            title = "Circle Profile Image",
            subtitle = "Animated Sweep with gradient color",
            content = {
                AnimatedSweepGradientProfileImagePreview()
            }
        ),
        AiNimationItem(
            title = "Circle Profile Image",
            subtitle = "Animated Sweep with gradient color and rotation",
            content = {
                AnimatedRotationGradientProfileImagePreview()
            }
        ),
        AiNimationItem(
            title = "Circle Profile Image",
            subtitle = "Animated Sweep with white border",
            content = {
                AnimatedSweepWithWhiteBorderProfileImagePreview()
            }
        )/*,
        AiNimationItem(
            title = "Superellipse Profile Image",
            subtitle = "Animated Superellipse Profile Image",
            content = { *//*AnimatedSuperellipseProfileImage()*//* }
        )*/
    )
)
