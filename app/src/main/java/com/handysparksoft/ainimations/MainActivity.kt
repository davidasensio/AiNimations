package com.handysparksoft.ainimations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.handysparksoft.ainimations.ui.theme.AiNimationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AiNimationsTheme {
                AiNimationsApp()
            }
        }
    }
}
