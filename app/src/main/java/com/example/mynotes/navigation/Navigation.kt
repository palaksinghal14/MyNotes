package com.example.mynotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.screen.HomeScreen
import com.example.mynotes.screen.LoginScreen
import com.example.mynotes.screen.SignUpScreen

@Composable
fun App(){
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination ="login"  )
    {
      composable("login"){
          LoginScreen(
              OnNavToSignUpPage = {
                  navController.navigate("signup")
              },
              OnNavToHomePage = {
                  navController.navigate("home")
              }
          )
      }

     composable("signup"){
         SignUpScreen(
             OnNavToSignInPage = {
                 navController.navigate("login")
             },
             OnNavToHomePage = {
                 navController.navigate("home")
             }
         )
     }

     composable("home"){
         HomeScreen()
     }
    }
}