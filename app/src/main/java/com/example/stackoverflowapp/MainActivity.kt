package com.example.stackoverflowapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import com.example.stackoverflowapp.core.ui.theme.StackUsersTheme
import com.example.stackoverflowapp.features.users.presentation.screen.UserListScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StackUsersTheme {
                UserListScreen()
            }
        }
    }
}
