package com.handysparksoft.ainimations.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handysparksoft.ainimations.ui.theme.AiNimationsTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AiNimationsHorizontalPager(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val pagerState = rememberPagerState()
    val pages = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

    Box(modifier = modifier.fillMaxSize()) {
        HorizontalPager(
            pageCount = pages.size,
            pageSpacing = 16.dp,
            beyondBoundsPageCount = 2,
            state = pagerState
        ) { page ->
            AiNimationsCard(
                pagerState = pagerState,
                page = page,
                modifier = Modifier.padding(horizontal = 64.dp, vertical = 48.dp),
                content = content
            )
        }

        PageIndicator(
            pageCount = pages.size,
            selectedPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        )
    }
}

@Preview
@Composable
internal fun HorizontalPagerPreview() {
    AiNimationsTheme {
        AiNimationsHorizontalPager {
            Text(text = "Sample Content")
        }
    }
}
