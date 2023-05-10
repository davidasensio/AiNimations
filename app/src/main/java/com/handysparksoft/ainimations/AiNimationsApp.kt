package com.handysparksoft.ainimations

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.handysparksoft.ainimations.ui.theme.AiNimationsTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AiNimationsApp(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AiNimationsScreenType
        .valueOf(backStackEntry?.destination?.route ?: AiNimationsScreenType.Start.name)

    Scaffold(
        topBar = {
            AiNimationsAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                onShareButtonClick = {
                    shareApp(context = context)
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.secondary
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AiNimationsScreenType.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AiNimationsScreenType.Start.name) {
                StartScreen(onNextButtonClicked = {
                    navController.navigate(AiNimationsScreenType.SuperellipseProfileImage.name)
                })
            }
            composable(route = AiNimationsScreenType.SuperellipseProfileImage.name) {
                SuperellipseProfileImageScreenMock(
                    onBackButtonClicked = { navController.popBackStack() },
                    onNextButtonClicked = { navController.navigate(AiNimationsScreenType.CircleProfileImage.name) }
                )
            }
            composable(route = AiNimationsScreenType.CircleProfileImage.name) {
                CircleProfileImageScreenMock(
                    onBackButtonClicked = { navController.popBackStack() },
                    onHomeButtonClicked = {
                        navController.popBackStack(
                            AiNimationsScreenType.Start.name,
                            inclusive = false
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiNimationsAppBar(
    currentScreen: AiNimationsScreenType,
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
fun StartScreen(onNextButtonClicked: () -> Unit = {}) {
    ColumnWithCenteredContent {
        Text(text = "Start Screen")
        Spacer(modifier = Modifier.height(80.dp))
        Button(onClick = onNextButtonClicked) {
            Text(text = "SuperellipseProfileImage")
        }
    }
}

@Composable
fun SuperellipseProfileImageScreenMock(
    onNextButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit
) {
    ColumnWithCenteredContent {
        Text(text = "SuperellipseProfileImage")
        Spacer(modifier = Modifier.height(80.dp))
        Button(onClick = onNextButtonClicked) {
            Text(text = "Next")
        }
        Button(onClick = onBackButtonClicked) {
            Text(text = "Back")
        }
    }
}

@Composable
fun CircleProfileImageScreenMock(
    onBackButtonClicked: () -> Unit,
    onHomeButtonClicked: () -> Unit
) {
    ColumnWithCenteredContent {
        Text(text = "CircleProfileImage")
        Spacer(modifier = Modifier.height(80.dp))
        Button(onClick = onHomeButtonClicked) {
            Text(text = "Home")
        }
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

enum class AiNimationsScreenType(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    SuperellipseProfileImage(title = R.string.screen_superellipse_profile_image),
    CircleProfileImage(title = R.string.screen_circle_profile_image),
    FlashLight(title = R.string.screen_flashlight),
    TrashIcon(title = R.string.screen_trash_icon)
}

@Preview
@Composable
fun AiNimationScreenPreview() {
    AiNimationsTheme {
        AiNimationsApp()
    }
}
