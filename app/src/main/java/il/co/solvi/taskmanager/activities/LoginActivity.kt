package il.co.solvi.taskmanager.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import firebase.FireClass
import il.co.solvi.taskmanager.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import model.AppUser

class LoginActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        login_close_btn.setOnClickListener {
            finish()
        }

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        login_btn.setOnClickListener {
            loginUser()
        }


    }

    private fun loginUser() {

        val email: String = login_email_et.text.toString().trim()
        val password: String = login_pass_et.text.toString().trim()

        if (validateForm(email, password)){

            showProgressDialog("Signing in...")
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task->


                    if (task.isSuccessful){

                        FireClass().loginUser(this)

                    } else {

                        hideProgressDialog()
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()

                    }


            }

        }

    }


    private fun validateForm(email: String, password: String): Boolean {

        return when {

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

    fun loginSuccess(loggedInUser: AppUser){

        hideProgressDialog()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }



}