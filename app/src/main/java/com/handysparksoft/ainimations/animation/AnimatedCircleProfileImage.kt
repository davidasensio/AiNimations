package com.handysparksoft.ainimations.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.ainimations.R
import com.handysparksoft.ainimations.components.ColumnWithCenteredContent

@Composable
fun AnimatedCircleProfileImage(modifier: Modifier = Modifier) {
    val imageSize = 112.dp
    val animatedAngle = remember { Animatable(0f) }

    // Create an animation that sweeps the border over time
    LaunchedEffect(Unit) {
        animatedAngle.animateTo(
            targetValue = 360f,
            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
        )
    }

    // Add the animated border around the profile image
    val colorBorder = Color(0xFFC6F16D)
    Box(
        modifier = Modifier
            .size(imageSize)
            .clip(CircleShape)
            .drawWithContent {
                drawContent()
                drawArc(
                    startAngle = 0f,
                    sweepAngle = animatedAngle.value,
                    useCenter = false,
                    /*brush = Brush.sweepGradient(
                        0f to Color(0x00EF7B7B),
                        0.9f to Color(0xFFC6F16D),
                        0.91f to Color(0x00EF7B7B),
                        1f to Color(0x00EF7B7B)
                    ),*/
                    color = colorBorder,
                    style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                )
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.sample_ai_face),
            contentDescription = "Profile Image Circle",
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
        )
    }
}

@Preview
@Composable
fun AnimatedProfileImagePreview() {
    ColumnWithCenteredContent {
        AnimatedCircleProfileImage()
    }
}
