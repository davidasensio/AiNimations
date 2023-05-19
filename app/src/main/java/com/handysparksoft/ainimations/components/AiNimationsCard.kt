package com.handysparksoft.ainimations.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.handysparksoft.ainimations.R
import com.handysparksoft.ainimations.ui.theme.AiNimationsTheme
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AiNimationsCard(
    pagerState: PagerState,
    page: Int,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var refreshAnimation by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(16.dp, ambientColor = Color.LightGray),
        shape = roundedShape
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
        ) {
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

            AiNimationBox(pageOffset) {
                key(refreshAnimation) {
                    refreshAnimation = false
                    content()
                }
            }
            AiNimationDetails(title = title, subtitle = subtitle)
            AiNimationDragToReplay(pageOffset = pageOffset, onDrag = {
                refreshAnimation = true
            })
        }
    }
}

@Composable
private fun AiNimationBox(
    pageOffset: Float,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(ANIMATION_BOX_ASPECT_RATIO)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = roundedShape
            )
            .clip(roundedShape)
            .clipToBounds()
            .graphicsLayer {
                val scale = lerp(1f, 2f, pageOffset)
                scaleX = scale
                scaleY = scale
            },
        content = content
    )
}

@Composable
private fun AiNimationDetails(
    title: String,
    subtitle: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = subtitle, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
    }
}

@Composable
private fun AiNimationDragToReplay(
    pageOffset: Float,
    onDrag: () -> Unit = {}
) {
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp * (1 - pageOffset))
            .graphicsLayer {
                alpha = 1 - pageOffset
            }
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    val deltaFactor = 10
                    offsetY += delta / deltaFactor
                },
                onDragStopped = {
                    onDrag()
                    offsetY = 0f
                }
            )
            .offset(x = 0.dp, y = offsetY.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Drag to Replay",
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = stringResource(id = R.string.drag_to_replay).uppercase(),
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.size(8.dp))
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Drag to Replay")
        }
    }
}

private const val ANIMATION_BOX_ASPECT_RATIO = 1.2f
private const val BORDER_RADIUS = 24
private val roundedShape = RoundedCornerShape(BORDER_RADIUS.dp)

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
internal fun AiNimationsCardPreview() {
    val pagerState = rememberPagerState()
    AiNimationsTheme {
        AiNimationsCard(pagerState = pagerState, page = 0, title = "Title", subtitle = "Subtitle") {
            Text(text = "Sample AiNimation")
        }
    }
}
