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
import com.handysparksoft.ainimations.AiNimationItem
import com.handysparksoft.ainimations.AiNimationItemList
import com.handysparksoft.ainimations.ui.theme.AiNimationsTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AiNimationsHorizontalPager(
    itemList: AiNimationItemList,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()
    val itemCount = itemList.items.size

    Box(modifier = modifier.fillMaxSize()) {
        HorizontalPager(
            pageCount = itemCount,
            pageSpacing = 16.dp,
            state = pagerState
        ) { page ->
            val itemContent = itemList.items[page].content
            AiNimationsCard(
                pagerState = pagerState,
                page = page,
                modifier = Modifier.padding(horizontal = 64.dp, vertical = 48.dp),
                content = itemContent
            )
        }

        PageIndicator(
            pageCount = itemCount,
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
    val items = listOf(
        AiNimationItem(
            title = "Sample Title 1",
            subtitle = "Sample Subtitle 1",
            content = {
                Text(text = "Sample Content 1")
            }
        )
    )
    AiNimationsTheme {
        AiNimationsHorizontalPager(AiNimationItemList(items))
    }
}
