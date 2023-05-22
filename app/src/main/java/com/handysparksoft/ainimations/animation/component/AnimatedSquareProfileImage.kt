package com.handysparksoft.ainimations.animation.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.handysparksoft.ainimations.R
import com.handysparksoft.ainimations.components.ColumnWithCenteredContent
import com.handysparksoft.ainimations.ui.theme.AiNimationsTheme
import com.handysparksoft.ainimations.ui.theme.DarkBackground
import com.handysparksoft.ainimations.ui.theme.Lime

@Composable
fun AnimatedSquareProfileImage(
    modifier: Modifier = Modifier,
    animationType: SquareProfileImageAnimationType = SquareProfileImageAnimationType.BorderGradient,
    strokeColor: Color = SquareAnimationTokens.StrokeColor,
    strokeTrackColor: Color = SquareAnimationTokens.StrokeTrackColor,
    strokeWidth: Dp? = null,
    contentPadding: Dp = Dp(0f),
    isInfiniteAnimation: Boolean = true,
    durationMillis: Int = SquareAnimationTokens.Duration,
    content: @Composable BoxScope.() -> Unit = { SampleProfileImageForPreview() }
) {
    val animatedFraction = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        val animationSpec: AnimationSpec<Float> =
            if (isInfiniteAnimation) {
                infiniteRepeatable(
                    animation = tween(durationMillis = durationMillis, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            } else {
                tween(durationMillis = durationMillis, easing = LinearEasing)
            }

        animatedFraction.animateTo(
            targetValue = 1f,
            animationSpec = animationSpec
        )
    }

    Box(modifier = modifier.aspectRatio(1f, true)) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RectangleShape) // Makes the border to be inside the shape (If no padding defined)

        ) {
            // Content
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(contentPadding)
                    .clip(RectangleShape),
                content = content
            )

            // Border
            AnimatedSquareBorder(
                animatedFraction = animatedFraction.value,
                isGradientColor = animationType == SquareProfileImageAnimationType.BorderGradient,
                strokeColor = strokeColor,
                strokeTrackColor = strokeTrackColor,
                strokeWidth = strokeWidth
            )
        }
    }
}

@Composable
internal fun AnimatedSquareBorder(
    animatedFraction: Float,
    isGradientColor: Boolean,
    strokeColor: Color,
    strokeTrackColor: Color,
    strokeWidth: Dp? = null

) {
    var strokeWidthDefault by remember { mutableStateOf(0.dp) }
    val strokeWidthCalculated = strokeWidth ?: strokeWidthDefault
    val strokeWidthFactor = 40f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                val squarePath = getRectPath()

                squarePath.let {
                    // Track border
                    drawPath(
                        squarePath,
                        color = strokeTrackColor,
                        style = Stroke(width = strokeWidthCalculated.value.dp.toPx())
                    )

                    // Animated border
                    val pathMeasure = PathMeasure()
                    pathMeasure.setPath(it, false)
                    val pathLength = pathMeasure.length
                    val interpolatedPath = Path().apply {
                        val stop = (pathLength) * animatedFraction
                        pathMeasure.getSegment(0f, stop, this, true)
                    }

                    if (isGradientColor) {
                        drawPath(
                            path = interpolatedPath,
                            style = Stroke(width = strokeWidthCalculated.value.dp.toPx()),
                            brush = Brush.sweepGradient(
                                colors = listOf(
                                    strokeColor.copy(alpha = .1f),
                                    strokeColor
                                )
                            )
                        )
                    } else {
                        drawPath(
                            path = interpolatedPath,
                            style = Stroke(width = strokeWidthCalculated.value.dp.toPx()),
                            color = Lime
                        )
                    }
                }
            }
            .onSizeChanged { strokeWidthDefault = Dp(it.width / strokeWidthFactor) }
    )
}

private fun DrawScope.getRectPath() = Path().apply {
    moveTo(size.width, size.height / 2)
    lineTo(size.width, size.height)
    lineTo(0f, size.height)
    lineTo(0f, 0f)
    lineTo(size.width, 0f)
    close()
}

private object SquareAnimationTokens {
    const val Duration = 2500
    val StrokeColor = Lime
    val StrokeTrackColor = Color.Transparent
}

enum class SquareProfileImageAnimationType {
    Border,
    BorderGradient
}

private val previewImageSize = 92.dp
private val contentPadding = 8.dp

@Preview
@Composable
internal fun AnimatedNonInfiniteBorderSquareProfileImagePreview() {
    AiNimationsTheme {
        AnimatedSquareProfileImage(
            animationType = SquareProfileImageAnimationType.Border,
            isInfiniteAnimation = false,
            modifier = Modifier.size(previewImageSize)
        )
    }
}

@Preview
@Composable
internal fun AnimatedBorderSquareProfileImagePreview() {
    AiNimationsTheme {
        AnimatedSquareProfileImage(
            animationType = SquareProfileImageAnimationType.Border,
            modifier = Modifier.size(previewImageSize)
        )
    }
}

@Preview
@Composable
internal fun AnimatedBorderGradientSquareProfileImagePreview() {
    AiNimationsTheme {
        AnimatedSquareProfileImage(
            animationType = SquareProfileImageAnimationType.BorderGradient,
            modifier = Modifier.size(previewImageSize)
        )
    }
}

@Preview
@Composable
internal fun AnimatedBorderGradientSquareWithWhiteFrameProfileImagePreview() {
    AiNimationsTheme {
        AnimatedSquareProfileImage(
            animationType = SquareProfileImageAnimationType.BorderGradient,
            strokeTrackColor = Color.White,
            modifier = Modifier.size(previewImageSize)
        )
    }
}

@Preview
@Composable
internal fun AnimatedBorderSquareProfileImageWithPaddingPreview() {
    AiNimationsTheme {
        AnimatedSquareProfileImage(
            animationType = SquareProfileImageAnimationType.Border,
            contentPadding = contentPadding,
            modifier = Modifier.size(previewImageSize)
        )
    }
}

@Preview
@Composable
internal fun AnimatedSquareProfileImagePreview() {
    ColumnWithCenteredContent(
        modifier = Modifier.background(color = DarkBackground)
    ) {
        AnimatedNonInfiniteBorderSquareProfileImagePreview()
        AnimatedBorderSquareProfileImagePreview()
        AnimatedBorderGradientSquareProfileImagePreview()
        AnimatedBorderGradientSquareWithWhiteFrameProfileImagePreview()
        AnimatedBorderSquareProfileImageWithPaddingPreview()
    }
}

@Composable
private fun BoxScope.SampleProfileImageForPreview() {
    Image(
        painter = painterResource(id = R.drawable.sample_ai_face),
        contentDescription = "Profile Image Square",
        modifier = Modifier.matchParentSize()
    )
}
