package com.handysparksoft.ainimations

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.handysparksoft.ainimations.components.ColumnWithCenteredContent
import com.handysparksoft.ainimations.ui.screens.ComponentAnimationsScreen
import com.handysparksoft.ainimations.ui.theme.AiNimationsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiNimationsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen: String = backStackEntry?.destination?.route ?: AiNimationsCategoryType.Component.route

    Scaffold(
        modifier = modifier,
        topBar = {
            AiNimationsAppBar(
                currentScreen = screenItems.first { it.route == currentScreen },
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                onShareButtonClick = {
                    shareApp(context = context)
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.secondary,
        bottomBar = { AiNimationsNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AiNimationsCategoryType.Component.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AiNimationsCategoryType.Component.route) {
                ComponentAnimationsScreen()
            }
            composable(route = AiNimationsCategoryType.Canvas.route) {
                CanvasAnimationsScreenMock(
                    onBackButtonClicked = { navController.popBackStack() }
                )
            }
            composable(route = AiNimationsCategoryType.Misc.route) {
                MiscAnimationsScreenMock(
                    onBackButtonClicked = { navController.popBackStack() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiNimationsAppBar(
    currentScreen: AiNimationsCategoryType,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onShareButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(id = currentScreen.title)) },
        modifier = modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onShareButtonClick) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = stringResource(id = R.string.share)
                )
            }
        }
    )
}

@Composable
fun AiNimationsNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var selectedScreenIndex by remember { mutableStateOf(0) }

    NavigationBar(modifier = modifier) {
        screenItems.forEachIndexed { index, item ->
            val itemLabel = stringResource(id = item.title)
            NavigationBarItem(
                selected = index == selectedScreenIndex,
                icon = { Icon(item.icon, contentDescription = itemLabel) },
                label = { Text(text = itemLabel) },
                onClick = {
                    selectedScreenIndex = index
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun CanvasAnimationsScreenMock(
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit = {}
) {
    ColumnWithCenteredContent(
        modifier = modifier
    ) {
        Text(text = "Canvas Animations Screen")
        Spacer(modifier = Modifier.height(80.dp))
        Button(onClick = onBackButtonClicked) {
            Text(text = "Back")
        }
    }
}

@Composable
fun MiscAnimationsScreenMock(
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    ColumnWithCenteredContent(
        modifier = modifier
    ) {
        Text(text = "Misc Animations Screen")
        Spacer(modifier = Modifier.height(80.dp))
        Button(onClick = onBackButtonClicked) {
            Text(text = "Back")
        }
    }
}

private fun shareApp(context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share_subject))
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_text))
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.share_app)
        )
    )
}

private val screenItems = listOf(
    AiNimationsCategoryType.Component,
    AiNimationsCategoryType.Canvas,
    AiNimationsCategoryType.Misc
)

sealed class AiNimationsCategoryType(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    object Component : AiNimationsCategoryType("Component", R.string.screen_component, Icons.Filled.Favorite)
    object Canvas : AiNimationsCategoryType("Canvas", R.string.screen_canvas, Icons.Filled.Create)
    object Misc : AiNimationsCategoryType("Misc", R.string.screen_misc, Icons.Filled.Build)
}

@Preview
@Composable
fun AiNimationScreenPreview() {
    AiNimationsTheme {
        AiNimationsApp()
    }
}
