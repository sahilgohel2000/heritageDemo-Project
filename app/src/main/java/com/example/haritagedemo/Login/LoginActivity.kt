@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.haritagedemo.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.haritagedemo.*
import com.example.haritagedemo.RoomDatabase.UserDatabase
import com.example.haritagedemo.Signup.DashboardActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    private lateinit var facebook:Button

    //google
    lateinit var googleLogin : Button
    lateinit var userEmail : String
    lateinit var userPassword : String

    lateinit var tv: TextView

    lateinit var emailId : EditText
    lateinit var password : EditText
    lateinit var next : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginactivity)

        facebook = findViewById(R.id.login_with_fb)

        //printKeyHash()
        facebook.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, FacebookAuthentication::class.java)
            startActivity(intent)
        })

        googleLogin = findViewById(R.id.login_with_google)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        googleLogin.setOnClickListener(View.OnClickListener {
            signIn()
        })

        //emailogin
        emailId = findViewById (R.id.email_Et)
        password = findViewById (R.id.password_Et)
        next = findViewById (R.id.next_Bt)

        next.setOnClickListener( View.OnClickListener {

            userEmail = emailId.text.toString()
            userPassword = password.text.toString()

            Log.d("login","setonclicklistener")

            if(TextUtils.isEmpty(userEmail)){

                Log.d("login","isemail")
                Toast.makeText(this,"is empty",Toast.LENGTH_SHORT).show()
            }

            else{
                Log.d("login","else email")
                val userDatabase = UserDatabase.getDatabase(applicationContext)
                val userdao = userDatabase.UserDao()
                val userEntity = userdao.getUserId( userEmail, userPassword )

                //code for verify email
                if (userEntity == null){

                    Log.d("login","email check")
                    Toast.makeText(this,"incorect",Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(this,"corect",Toast.LENGTH_SHORT).show()
                    userEmail=userEntity.userId
                    val intent = Intent(this, MainActivity::class.java).putExtra("name",userEmail)
                    startActivity( intent )
                }
            }
        })

        val signup:TextView = findViewById(R.id.signuplink)
        signup.setOnClickListener(View.OnClickListener {
            val signupIntent:Intent = Intent(this,signupactivity::class.java)
            startActivity(signupIntent)
        })

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, signupactivity.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //facebook callback manager
       // callbackManager!!.onActivityResult(requestCode,resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == signupactivity.RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SigninActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SigninActivity", "Google sign in failed", e)
                }
            }else{Log.w("SigninActivity", exception.toString())}
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SigninActivity", "signInWithCredential:success")
                    val intent:Intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SigninActivity", "signInWithCredential:failure", task.exception)
                }
            }
    }

}
