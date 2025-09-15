package com.example.mynotes.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mynotes.viewmodel.AuthViewModel
import javax.inject.Inject


@Composable
fun LoginScreen  (
     viewmodel:AuthViewModel=hiltViewModel(),
     OnNavToSignUpPage:() -> Unit,
     OnNavToHomePage:() -> Unit

){
    val state= viewmodel.state.value

    var email by remember{mutableStateOf("")}

    var password by remember{mutableStateOf("")}

    Column( modifier= Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {

        Text(text = "Login",
             fontSize =40.sp
        )

        Spacer(modifier = Modifier.padding(16.dp))

        TextField(value = email ,
            onValueChange ={email=it} ,
            label = { Text(text = "email")})

        Spacer(modifier = Modifier.padding(16.dp))

        TextField(value = password ,
            onValueChange ={password=it},
            label = { Text(text = "password")})

        Spacer(modifier = Modifier.padding(16.dp))

        // this will call the sign in function and will pass the input
        Button(onClick = { viewmodel.SignIn(email, password) }) {
            Text(text = "Sign In")
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "Dont have an Account?")

            //navigation
            TextButton(onClick = { OnNavToSignUpPage() }) {
                Text(text = "Sign Up")
            }
        }

    }

    when (state) {
        is AuthViewModel.AuthState.Idle -> { /* do nothing */ }
        is AuthViewModel.AuthState.isLoading -> {
            CircularProgressIndicator()
        }
        is AuthViewModel.AuthState.Success -> {
            Text("Welcome ${state.user.email}")
            // or navigate to HomeScreen
            OnNavToHomePage()
        }
        is AuthViewModel.AuthState.error -> {
            Text(state.msg, color = Color.Red)
        }
    }
}


@Composable
fun SignUpScreen  (
    viewmodel:AuthViewModel=hiltViewModel(),
    OnNavToSignInPage:() -> Unit,
    OnNavToHomePage:() -> Unit
){
    val state= viewmodel.state.value

    var email by remember{mutableStateOf("")}

    var password by remember{mutableStateOf("")}

    var confirmpassword by remember{mutableStateOf("")}

    var errormessage by remember{mutableStateOf<String?>("")}

    Column( modifier= Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Sign Up",
            fontSize =40.sp
        )

        Spacer(modifier = Modifier.padding(16.dp))

        TextField(value = email ,
            onValueChange ={email=it} ,
            label = { Text(text = "email")})

        Spacer(modifier = Modifier.padding(16.dp))

        TextField(value = password ,
            onValueChange ={password=it},
            label = { Text(text = "password")})

        Spacer(modifier = Modifier.padding(16.dp))

        // if they dont match only then we will show error
        TextField(value = confirmpassword ,
            onValueChange ={ confirmpassword=it
                          errormessage=if (password!=confirmpassword)"password not match" else null},
            label = { Text(text = "confirm password")})

        // if there is error show it
        if (errormessage != null) {
            Text(errormessage!!, color = Color.Red)
        }

        Spacer(modifier = Modifier.padding(10.dp))

        // only it both password matches , we will calll signup
        Button(onClick = {
            if(password==confirmpassword)
                viewmodel.SignUp(email, password)
        }) {
            Text(text = "Sign Up")
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "Already have an Account?")

            //navigation
            TextButton(onClick = { OnNavToSignInPage() }) {
                Text(text = "Sign In")
            }
        }
    }

    when (state) {
        is AuthViewModel.AuthState.Idle -> { /* do nothing */ }
        is AuthViewModel.AuthState.isLoading -> {
            CircularProgressIndicator()
        }
        is AuthViewModel.AuthState.Success -> {
            Text("Welcome ${state.user.email}")
            //  navigate to HomeScreen
            OnNavToHomePage()
        }
        is AuthViewModel.AuthState.error -> {
            Text(state.msg, color = Color.Red)
        }
    }
}




