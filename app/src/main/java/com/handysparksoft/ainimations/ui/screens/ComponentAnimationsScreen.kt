package com.handysparksoft.ainimations.ui.screens

import androidx.compose.runtime.Composable
import com.handysparksoft.ainimations.AiNimationItem
import com.handysparksoft.ainimations.AiNimationItemList
import com.handysparksoft.ainimations.animation.component.AnimatedBorderGradientSquareWithWhiteFrameProfileImagePreview
import com.handysparksoft.ainimations.animation.component.AnimatedBorderSquareProfileImageWithPaddingPreview
import com.handysparksoft.ainimations.animation.component.AnimatedCoolCircleProfileImagePreview
import com.handysparksoft.ainimations.animation.component.AnimatedCoolCircleProfileImageWithGradientColorPreview
import com.handysparksoft.ainimations.animation.component.AnimatedCoolCircleProfileImageWithPaddingPreview
import com.handysparksoft.ainimations.animation.component.AnimatedNonInfiniteBorderSquareProfileImagePreview
import com.handysparksoft.ainimations.animation.component.AnimatedRotationGradientProfileImagePreview
import com.handysparksoft.ainimations.animation.component.AnimatedSweepGradientProfileImagePreview
import com.handysparksoft.ainimations.animation.component.AnimatedSweepProfileImagePreview
import com.handysparksoft.ainimations.animation.component.AnimatedSweepWithWhiteFrameProfileImagePreview
import com.handysparksoft.ainimations.components.AiNimationsHorizontalPager

@Composable
fun ComponentAnimationsScreen() {
    AiNimationsHorizontalPager(
        itemList = getComponentItems()
    )
}

@Suppress("LongMethod")
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
                AnimatedSweepWithWhiteFrameProfileImagePreview()
            }
        ),
        AiNimationItem(
            title = "Square Profile Image",
            subtitle = "Animated Square Border (Non Repeat Mode)",
            content = {
                AnimatedNonInfiniteBorderSquareProfileImagePreview()
            }
        ),
        AiNimationItem(
            title = "Square Profile Image",
            subtitle = "Animated Square Border (With white frame and gradient color)",
            content = {
                AnimatedBorderGradientSquareWithWhiteFrameProfileImagePreview()
            }
        ),
        AiNimationItem(
            title = "Square Profile Image",
            subtitle = "Animated Square Border (With padding)",
            content = {
                AnimatedBorderSquareProfileImageWithPaddingPreview()
            }
        ),
        AiNimationItem(
            title = "Circle Profile Image",
            subtitle = "Cool Sweep Animation",
            content = {
                AnimatedCoolCircleProfileImagePreview()
            }
        ),
        AiNimationItem(
            title = "Circle Profile Image",
            subtitle = "Cool Sweep Animation (With padding)",
            content = {
                AnimatedCoolCircleProfileImageWithPaddingPreview()
            }
        ),
        AiNimationItem(
            title = "Circle Profile Image",
            subtitle = "Cool Sweep Animation (With gradient color)",
            content = {
                AnimatedCoolCircleProfileImageWithGradientColorPreview()
            }
        )/*,
        AiNimationItem(
            title = "Superellipse Profile Image",
            subtitle = "Animated Superellipse Profile Image",
            content = { *//*AnimatedSuperellipseProfileImage()*//* }
        )*/
    )
)
