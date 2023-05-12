package com.handysparksoft.ainimations.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val cardRadius = 24.dp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(16.dp, ambientColor = Color.LightGray),
        shape = RoundedCornerShape(cardRadius)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
        ) {
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .aspectRatio(ANIMATION_BOX_ASPECT_RATIO)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(cardRadius)
                    )
                    .clipToBounds()
            ) {
                Box(
                    modifier = Modifier.graphicsLayer {
                        val scale = lerp(1f, 2f, pageOffset)
                        scaleX = scale
                        scaleY = scale
                    }
                ) {
                    content()
                }
            }
            Spacer(modifier = Modifier.size(24.dp))
            AiNimationDetails()
            AiNimationDragToReplay(pageOffset = pageOffset, onDrag = {
                Toast.makeText(context, "Replay", Toast.LENGTH_SHORT).show()
            })
        }
    }
}

@Composable
private fun AiNimationDetails(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "Title", style = MaterialTheme.typography.headlineLarge)
        Text(text = "Subtitle", style = MaterialTheme.typography.titleSmall)
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

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
internal fun AiNimationsCardPreview() {
    val pagerState = rememberPagerState()
    AiNimationsTheme {
        AiNimationsCard(pagerState = pagerState, page = 0) {
            Text(text = "Sample AiNimation")
        }
    }
}
