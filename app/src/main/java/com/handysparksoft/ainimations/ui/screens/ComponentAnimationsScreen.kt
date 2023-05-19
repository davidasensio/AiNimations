package com.handysparksoft.ainimations.ui.screens

import androidx.compose.runtime.Composable
import com.handysparksoft.ainimations.AiNimationItem
import com.handysparksoft.ainimations.AiNimationItemList
import com.handysparksoft.ainimations.animation.AnimatedRotationGradientProfileImagePreview
import com.handysparksoft.ainimations.animation.AnimatedSweepGradientProfileImagePreview
import com.handysparksoft.ainimations.animation.AnimatedSweepProfileImagePreview
import com.handysparksoft.ainimations.animation.AnimatedSweepWithWhiteBorderProfileImagePreview
import com.handysparksoft.ainimations.components.AiNimationsHorizontalPager

@Composable
fun ComponentAnimationsScreen() {
    AiNimationsHorizontalPager(
        itemList = getComponentItems()
    )
}

fun getComponentItems() = AiNimationItemList(
    listOf(
        AiNimationItem(
            title = "Circle Profile Image",
            subtitle = "Animated Circle Profile Image",
            content = {
                AnimatedSweepProfileImagePreview()
            }
        ),
        AiNimationItem(
            title = "Circle Profile Image",
            subtitle = "Animated Circle Profile Image",
            content = {
                AnimatedSweepGradientProfileImagePreview()
            }
        ),
        AiNimationItem(
            title = "Circle Profile Image",
            subtitle = "Animated Circle Profile Image",
            content = {
                AnimatedRotationGradientProfileImagePreview()
            }
        ),
        AiNimationItem(
            title = "Circle Profile Image",
            subtitle = "Animated Circle Profile Image",
            content = {
                AnimatedSweepWithWhiteBorderProfileImagePreview()
            }
        ),
        AiNimationItem(
            title = "Superellipse Profile Image",
            subtitle = "Animated Superellipse Profile Image",
            content = { /*AnimatedSuperellipseProfileImage()*/ }
        ),
        AiNimationItem(
            title = "Flashlight",
            subtitle = "Flashlight",
            content = { /*AnimatedFlashLight()*/ }
        )
    )
)
