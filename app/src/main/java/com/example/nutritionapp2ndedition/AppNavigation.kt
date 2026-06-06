package com.example.nutritionapp2ndedition

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutritionapp2ndedition.ui.theme.MainScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

//import com.example.nutritionapp2ndedition.ui.theme.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var userProfile by remember { mutableStateOf(UserProfile())}
    NavHost(
        navController = navController,
        startDestination = "splash",
        enterTransition = { fadeIn(animationSpec = tween(800)) },
        exitTransition = { fadeOut(animationSpec = tween(800)) }
    ) {
        composable("splash") {
            HomeScreen(onFinished = { navController.navigate("onboarding") })
        }
        composable("onboarding") {
            OnboardingScreen(onFinished = { profile ->
                userProfile = profile
                navController.navigate("main") })
        }
        composable("main") {
            MainScreen(userProfile=userProfile)
    }
}}
