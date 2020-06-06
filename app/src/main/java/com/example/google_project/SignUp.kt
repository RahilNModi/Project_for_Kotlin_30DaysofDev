package com.example.google_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        btn_sign_up.setOnClickListener {
           sign_up_code()
        }
    }

    private fun sign_up_code() {
        if(user_id1.text.toString().isEmpty()){
            user_id1.error = "Please enter Email ID..."
            user_id1.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(user_id1.text.toString()).matches()){
            user_id1.error = "Please enter valid Email ID..."
            user_id1.requestFocus()
            return
        }
        if(pwd1.text.toString().isEmpty()){
            pwd1.error = "Please enter password..."
            pwd1.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(user_id1.text.toString(), pwd1.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user:FirebaseUser? = auth.currentUser

                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this,"Sign Up successful, please verify email address and then log in",Toast.LENGTH_LONG).show()
                                startActivity(Intent(this,MainActivity::class.java))
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(baseContext, "Sign Up Failed Try after sometime.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}