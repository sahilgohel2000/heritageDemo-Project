package com.example.haritagedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.haritagedemo.Login.LoginActivity
import com.example.haritagedemo.RoomDatabase.UserViewModel
import com.example.haritagedemo.RoomDatabase.Users
import com.example.haritagedemo.Signup.DashboardActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class signupactivity : AppCompatActivity() {

    private val EMAIL = "email"

    companion object{
        public const val RC_SIGN_IN = 120
    }

    //Google SignUp
    private lateinit var signInBtn : Button

    private lateinit var mAuth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    //EditText that input email id for signup
    lateinit var emailId: EditText
    lateinit var password: EditText
    lateinit var next: Button
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private val passPattern = "[a-zA-Z]"

    //viewmodel
    lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupactivity)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        signInBtn = findViewById(R.id.googleSignup)
        signInBtn.setOnClickListener(View.OnClickListener {
            signIn()
        })

        //next button
        emailId = findViewById ( R.id.email_Field )
        password = findViewById ( R.id.password_Field )
        next = findViewById ( R.id.nextbtn )

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        next.setOnClickListener(View.OnClickListener {

            insertDataToDatabase()
        })

        val login:TextView = findViewById(R.id.signuplink)
        login.setOnClickListener(View.OnClickListener {
            val loginIntent:Intent = Intent(this,LoginActivity::class.java)
            startActivity(loginIntent)
        })

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

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
                    val intent:Intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SigninActivity", "signInWithCredential:failure", task.exception)
                }
            }
    }


    private fun insertDataToDatabase() {

        val emailid = emailId.text.toString()
        val pass = password.text.toString()

        if(inputCheck(emailid, pass)){
            if(emailid.matches(emailPattern.toRegex()))
            {

                val user = Users(0, emailid, pass)
                mUserViewModel.registerUser(user)
                //Toast.makeText(this,"Successfully login",Toast.LENGTH_LONG).show()
                Log.d("adddata","adddata")
                val intentss = Intent(baseContext, LoginActivity::class.java)
                startActivity(intentss)

            }
            else{
                Toast.makeText(this,"please enter valid email",Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this,"unseuccessfull",Toast.LENGTH_LONG).show()
        }
    }


    private fun inputCheck( emailid: String, pass: String ):Boolean {
        return !(TextUtils.isEmpty(emailid) && TextUtils.isEmpty(pass))
    }
}
