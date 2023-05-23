package com.handysparksoft.ainimations.animation.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.handysparksoft.ainimations.R
import com.handysparksoft.ainimations.ui.theme.Lime
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Create an animation that sweeps the border in a cool way
 *
 * Example of how to run **sequential** animations:
 * ```
 * val scope = rememberCoroutineScope()
 *
 * scope.launch {
 *  launch {
 *   animateTo()
 *   animateTo()
 *  }
 * }
 * ```
 *
 * Example of how to run **parallel** animations:
 * ```
 * val scope = rememberCoroutineScope()
 *
 * scope.launch {
 *  launch {
 *   animateTo()
 *  }
 *  launch {
 *   animateTo()
 *  }
 * }
 * ```
 *
 * See related MAD [video](https://www.youtube.com/watch?v=Z_T1bVjhMLk)
 */

@Suppress("LongMethod")
@Composable
fun AnimatedCoolCircleProfileImage(
    modifier: Modifier = Modifier,
    isGradientColor: Boolean = false,
    strokeColor: Color = CoolAnimationTokens.StrokeColor,
    strokeTrackColor: Color = CoolAnimationTokens.StrokeTrackColor,
    strokeWidth: Dp? = null,
    contentPadding: Dp = Dp(0f),
    durationMillis: Int = CoolAnimationTokens.Duration,
    content: @Composable BoxScope.() -> Unit = { SampleProfileImageForPreview() }
) {
    val animatedStartAngle = remember { Animatable(CoolAnimationTokens.StartAngle) }
    val animatedSweepAngle = remember { Animatable(0f) }
    var restartAnimation by remember { mutableStateOf(false) }
    val durationMillisFill = (durationMillis * 0.7).toInt()
    val durationMillisClear = (durationMillis * 0.3).toInt()

    LaunchedEffect(restartAnimation) {
        restartAnimation = false
        coroutineScope {
            // Fill
            launch {
                animatedSweepAngle.animateTo(
                    targetValue = CoolAnimationTokens.EndAngle,
                    animationSpec = tween(
                        durationMillis = durationMillisFill,
                        easing = FastOutSlowInEasing
                    )
                )

                // Clear
                launch {
                    animatedStartAngle.animateTo(
                        targetValue = CoolAnimationTokens.EndAngle,
                        animationSpec = tween(
                            durationMillis = durationMillisClear,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
                launch {
                    animatedSweepAngle.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(
                            durationMillis = durationMillisClear,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            }.invokeOnCompletion {
                // Restart animation
                launch {
                    animatedStartAngle.snapTo(CoolAnimationTokens.StartAngle)
                    restartAnimation = true
                }
            }
        }
    }

    Box(modifier = modifier.aspectRatio(1f, true)) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape) // Makes the border to be inside the shape (If no padding defined)
        ) {
            // Content
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(contentPadding)
                    .clip(CircleShape),
                content = content
            )

            // Border
            AnimatedCircleBorderV2(
                animatedSweepAngle = animatedSweepAngle.value,
                isGradientColor = isGradientColor,
                strokeColor = strokeColor,
                strokeTrackColor = strokeTrackColor,
                strokeWidth = strokeWidth,
                startAngle = animatedStartAngle.value,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}

@Composable
internal fun AnimatedCircleBorderV2(
    animatedSweepAngle: Float,
    isGradientColor: Boolean,
    strokeColor: Color,
    strokeTrackColor: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp? = null,
    startAngle: Float = 0f
) {
    var strokeWidthDefault by remember { mutableStateOf(0.dp) }
    val strokeWidthCalculated = strokeWidth ?: strokeWidthDefault
    val strokeWidthFactor = 40f

    Box(
        modifier = modifier
            .drawBehind {
                drawBorderArc(
                    strokeTrackColor = strokeTrackColor,
                    strokeWidth = strokeWidthCalculated,
                    startAngle = startAngle
                )

                if (isGradientColor) {
                    drawAnimatedGradientArc(
                        animatedSweepAngle = animatedSweepAngle,
                        strokeColor = strokeColor,
                        strokeWidth = strokeWidthCalculated,
                        startAngle = startAngle
                    )
                } else {
                    drawAnimatedArc(
                        animatedSweepAngle = animatedSweepAngle,
                        strokeColor = strokeColor,
                        strokeWidth = strokeWidthCalculated,
                        startAngle = startAngle
                    )
                }
            }
            .onSizeChanged { strokeWidthDefault = Dp(it.width / strokeWidthFactor) }
    )
}

private fun DrawScope.drawBorderArc(
    strokeTrackColor: Color,
    strokeWidth: Dp,
    startAngle: Float = 0f
) {
    drawArc(
        startAngle = startAngle,
        sweepAngle = 360f,
        useCenter = false,
        color = strokeTrackColor,
        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawAnimatedArc(
    animatedSweepAngle: Float,
    strokeColor: Color,
    strokeWidth: Dp,
    startAngle: Float = 0f
) {
    drawArc(
        startAngle = startAngle,
        sweepAngle = animatedSweepAngle,
        useCenter = false,
        color = strokeColor,
        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawAnimatedGradientArc(
    animatedSweepAngle: Float,
    strokeColor: Color,
    strokeWidth: Dp,
    startAngle: Float = 0f
) {
    drawArc(
        startAngle = startAngle,
        sweepAngle = animatedSweepAngle,
        useCenter = false,
        brush = Brush.sweepGradient(
            colors = listOf(
                strokeColor.copy(alpha = .1f),
                strokeColor
            )
        ),
        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
    )
}

private object CoolAnimationTokens {
    const val SafeStartAngle = 5f // Avoids the cap being painted wrongly in the Gradient version
    const val StartAngle = SafeStartAngle // The sweep begins relative to 3 o'clock. 0f is 3 o'clock
    const val EndAngle = 360f // Aka SweepAngle which is relative to startAngle
    const val Duration = 4000
    val StrokeColor = Lime
    val StrokeTrackColor = Color.Transparent
}

private val previewImageSize = 92.dp
private val contentPadding = 8.dp

@Preview
@Composable
fun AnimatedCoolCircleProfileImagePreview() {
    AnimatedCoolCircleProfileImage(
        modifier = Modifier.size(previewImageSize)
    )
}

@Preview
@Composable
fun AnimatedCoolCircleProfileImageWithPaddingPreview() {
    AnimatedCoolCircleProfileImage(
        contentPadding = contentPadding,
        modifier = Modifier.size(previewImageSize)
    )
}

@Preview
@Composable
fun AnimatedCoolCircleProfileImageWithGradientColorPreview() {
    AnimatedCoolCircleProfileImage(
        isGradientColor = true,
        modifier = Modifier.size(previewImageSize)
    )
}

@Composable
private fun BoxScope.SampleProfileImageForPreview() {
    Image(
        painter = painterResource(id = R.drawable.sample_ai_face),
        contentDescription = "Profile Image Circle",
        modifier = Modifier.matchParentSize()
    )
}
