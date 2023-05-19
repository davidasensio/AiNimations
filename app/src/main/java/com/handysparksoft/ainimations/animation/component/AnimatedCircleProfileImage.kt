package com.handysparksoft.ainimations.animation.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.draw.rotate
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
import com.handysparksoft.ainimations.components.ColumnWithCenteredContent
import com.handysparksoft.ainimations.ui.theme.Lime

/**
 * Create an animation that sweeps the border one time
 */

@Composable
internal fun AnimatedCircleProfileImage(
    modifier: Modifier = Modifier,
    animationType: CircleProfileImageAnimationType = CircleProfileImageAnimationType.RotationGradient,
    startAngle: Float = AnimationTokens.StartAngle,
    strokeColor: Color = AnimationTokens.StrokeColor,
    strokeTrackColor: Color = AnimationTokens.StrokeTrackColor,
    strokeWidth: Dp? = null,
    durationMillis: Int = AnimationTokens.Duration
) {
    val isGradientColor = animationType != CircleProfileImageAnimationType.Sweep
    val animatedSweepAngle = remember { Animatable(0f) }
    val animatedRotationAngle = remember { Animatable(startAngle) }

    val sweepDurationMillis = when (animationType) {
        CircleProfileImageAnimationType.RotationGradient -> 100
        else -> durationMillis
    }

    LaunchedEffect(Unit) {
        animatedSweepAngle.animateTo(
            targetValue = AnimationTokens.EndAngle,
            animationSpec = tween(durationMillis = sweepDurationMillis, easing = LinearEasing)
        )
        if (animationType == CircleProfileImageAnimationType.RotationGradient) {
            animatedRotationAngle.animateTo(
                targetValue = startAngle + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = durationMillis, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    Box(
        modifier = modifier
            .aspectRatio(1f, true)
    ) {
        Box(modifier = Modifier.matchParentSize()) {
            Image(
                painter = painterResource(id = R.drawable.sample_ai_face),
                contentDescription = "Profile Image Circle",
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
            )
            AnimatedCircleBorder(
                animatedSweepAngle = animatedSweepAngle.value,
                isGradientColor = isGradientColor,
                strokeColor = strokeColor,
                strokeTrackColor = strokeTrackColor,
                strokeWidth = strokeWidth,
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .rotate(animatedRotationAngle.value)
            )
        }
    }
}

@Composable
private fun AnimatedCircleBorder(
    animatedSweepAngle: Float,
    isGradientColor: Boolean,
    strokeColor: Color,
    strokeTrackColor: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp? = null
) {
    var strokeWidthDefault by remember { mutableStateOf(0.dp) }
    val strokeWidthCalculated = strokeWidth ?: strokeWidthDefault
    val strokeWidthFactor = 40f

    Box(
        modifier = modifier
            .drawBehind {
                drawBorderArc(
                    strokeTrackColor = strokeTrackColor,
                    strokeWidth = strokeWidthCalculated
                )

                if (isGradientColor) {
                    drawAnimatedGradientArc(
                        animatedSweepAngle = animatedSweepAngle,
                        strokeColor = strokeColor,
                        strokeWidth = strokeWidthCalculated
                    )
                } else {
                    drawAnimatedArc(
                        animatedSweepAngle = animatedSweepAngle,
                        strokeColor = strokeColor,
                        strokeWidth = strokeWidthCalculated
                    )
                }
            }
            .onSizeChanged { strokeWidthDefault = Dp(it.width / strokeWidthFactor) }
    )
}

private fun DrawScope.drawBorderArc(
    strokeTrackColor: Color,
    strokeWidth: Dp
) {
    drawArc(
        startAngle = AnimationTokens.StartAngle,
        sweepAngle = 360f,
        useCenter = false,
        color = strokeTrackColor,
        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawAnimatedArc(
    animatedSweepAngle: Float,
    strokeColor: Color,
    strokeWidth: Dp
) {
    drawArc(
        startAngle = AnimationTokens.StartAngle,
        sweepAngle = animatedSweepAngle,
        useCenter = false,
        color = strokeColor,
        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawAnimatedGradientArc(
    animatedSweepAngle: Float,
    strokeColor: Color,
    strokeWidth: Dp
) {
    drawArc(
        startAngle = AnimationTokens.StartAngle,
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

private object AnimationTokens {
    const val SafeStartAngle = 5f // Avoids the cap being painted wrongly in the Gradient version
    const val StartAngle = SafeStartAngle // The sweep begins relative to 3 o'clock so 0f means 3 o'clock
    const val EndAngle = 360f // Aka SweepAngle which is relative to startAngle
    const val Duration = 5000
    val StrokeColor = Lime
    val StrokeTrackColor = Color.Transparent
}

enum class CircleProfileImageAnimationType {
    Sweep,
    SweepGradient,
    RotationGradient
}

private val previewImageSize = 112.dp

@Preview
@Composable
fun AnimatedSweepProfileImagePreview() {
    AnimatedCircleProfileImage(
        animationType = CircleProfileImageAnimationType.Sweep,
        modifier = Modifier.size(previewImageSize)
    )
}

@Preview
@Composable
fun AnimatedSweepGradientProfileImagePreview() {
    AnimatedCircleProfileImage(
        animationType = CircleProfileImageAnimationType.SweepGradient,
        startAngle = 180f,
        modifier = Modifier.size(previewImageSize)
    )
}

@Preview
@Composable
fun AnimatedRotationGradientProfileImagePreview() {
    AnimatedCircleProfileImage(
        animationType = CircleProfileImageAnimationType.RotationGradient,
        startAngle = 0f,
        modifier = Modifier.size(previewImageSize)
    )
}

@Preview
@Composable
fun AnimatedSweepWithWhiteBorderProfileImagePreview() {
    AnimatedCircleProfileImage(
        animationType = CircleProfileImageAnimationType.Sweep,
        modifier = Modifier.size(previewImageSize),
        strokeTrackColor = Color.White
    )
}

@Preview
@Composable
fun AnimatedProfileImagesPreview() {
    ColumnWithCenteredContent {
        AnimatedSweepProfileImagePreview()
        AnimatedSweepGradientProfileImagePreview()
        AnimatedRotationGradientProfileImagePreview()
        AnimatedSweepWithWhiteBorderProfileImagePreview()
    }
}
