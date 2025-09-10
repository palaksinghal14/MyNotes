package com.example.mynotes.Repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val auth: FirebaseAuth
){
    // to tell us if the user is logged in or not
    fun isLoggedIn() :Boolean =auth.currentUser != null

    // to create new user
    suspend fun SignUp(email:String ,
                      password: String
    ):FirebaseUser? {
       try{
            val result= auth.createUserWithEmailAndPassword(email,password).await()
            return result.user
        }
        catch (e:Exception){
           return null
        }
    }

    // to login current user in app
    suspend fun SignIn(email:String ,
                       password: String
    ):FirebaseUser? {
        try{
            val result= auth.signInWithEmailAndPassword(email,password).await()
            return result.user
        }
        catch (e:Exception){
            return null
        }

        /*
        // we can also use Result in here to be more specific
        //Kotlin has a built-in Result type that can wrap success OR failure with the actual exception.
        suspend fun signIn(email: String, password: String): Result<FirebaseUser> {
         return try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        Result.success(result.user!!) // success case
         } catch (e: Exception) {
        Result.failure(e)             // failure case
        }
         }
        */
    }

    // to log out from the app
    fun SignOut():Unit =auth.signOut()

    // give us the current user object
    fun getCurrentuser(): FirebaseUser?=auth.currentUser
}
