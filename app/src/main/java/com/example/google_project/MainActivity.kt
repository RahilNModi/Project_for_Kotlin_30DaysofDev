package com.example.google_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        txt_sign_up.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
            finish()
        }

        btn_login.setOnClickListener {
            complete_login()
        }
    }

    private fun complete_login() {
        if(user_id.text.toString().isEmpty()){
            user_id.error = "Please enter Email ID..."
            user_id.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(user_id.text.toString()).matches()){
            user_id.error = "Please enter valid Email ID..."
            user_id.requestFocus()
            return
        }
        if(pwd.text.toString().isEmpty()){
            pwd.error = "Please enter password..."
            pwd.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(user_id.text.toString(), pwd.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }

    fun updateUI(currentUser: FirebaseUser?){
        if(currentUser != null){
            if(currentUser.isEmailVerified) {
                Toast.makeText(baseContext,"Login Successful...",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DemoActivity::class.java))
                finish()
            } else{
                Toast.makeText(baseContext, "Please verify your email address.",
                    Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(baseContext, "Login failed, please enter valid email and password or sign up if account is not registered.",
                Toast.LENGTH_SHORT).show()
        }
    }
}