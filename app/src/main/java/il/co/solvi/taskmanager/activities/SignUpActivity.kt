package il.co.solvi.taskmanager.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import firebase.FireClass
import il.co.solvi.taskmanager.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import model.AppUser

class SignUpActivity : BaseActivity() {

    private val TAG = "SignUpActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }


        sign_up_close_btn.setOnClickListener {
            finish()
        }

        sign_up_btn.setOnClickListener {
            registerUser()
        }


    }


    private fun registerUser(){

        val name: String = sign_up_name_et.text.toString().trim()
        val email: String = sign_up_email_et.text.toString().trim()
        val password: String = sign_up_pass_et.text.toString().trim()

        if (validateForm(name, email, password)){

            showProgressDialog("Signing in...")
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->


                        if (task.isSuccessful){

                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user = AppUser(
                                firebaseUser.uid,
                                name,
                                email,
                            )

                            FireClass().registerUser(this, user)

                        } else {
                            hideProgressDialog()
                            Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }




        } else {

            Toast.makeText(this, "Enter valid info", Toast.LENGTH_SHORT).show()
        }

    }


    private fun validateForm(name: String, email: String, password: String): Boolean {

        return when {

            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a name")
                return false
            }

            TextUtils.isEmpty(email) ->{
                showErrorSnackBar("Please enter an email")
                return false
            }

            TextUtils.isEmpty(password) ->{
                showErrorSnackBar("Please enter password")
                return false
            }

            else -> true
        }

    }

    fun registeredSuccess(){

        hideProgressDialog()
        Toast.makeText(this, "New User Created", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()

    }


}