package com.example.haritagedemo.Login

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.haritagedemo.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class FacebookAuthentication : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null
    private var callbackManager: CallbackManager? = null

    private lateinit var fbLogin:Button

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("FacebookAuthentication","OnCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_authentication)

        //printKeyHash()

        firebaseAuth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        fbLogin = findViewById(R.id.btnLogin)

        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))

        fbLogin.setOnClickListener(View.OnClickListener {
            Log.d("FacebookAuthentication","fbLogin")
            signIn()
        })
    }

    private fun signIn() {
        Log.d("FacebookAuthentication","signIn")

        LoginManager.getInstance().registerCallback(callbackManager,object :FacebookCallback<LoginResult>{
            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

            }

            override fun onSuccess(result: LoginResult) {
                Log.d("FacebookAuthentication","Onsucess/accessToken")

                handleFacebookAccessToken(result!!.accessToken)
            }

        })
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(accessToken!!.token)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnFailureListener { e ->
                Log.d("FacebookAuthentication","onfailure listenr")
                Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
            }
            .addOnSuccessListener { result ->
                Log.d("FacebookAuthentication","addonsuccesslistener")
                val email : String? = result.user?.email
                Log.d("FacebookAuthentication",email.toString())
                Toast.makeText(this,"email id:"+email,Toast.LENGTH_LONG).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("FacebookAuthentication","OnActivityListener")
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }

    private fun printKeyHash() {
        try{
            val info = packageManager.getPackageInfo("com.example.haritagedemo",PackageManager.GET_SIGNATURES)

            for (signature in info.signatures){
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEYHASH",Base64.encodeToString(md.digest(),Base64.DEFAULT))
            }
        }catch (e:PackageManager.NameNotFoundException){}
        catch (e:NoSuchAlgorithmException){}
    }
}