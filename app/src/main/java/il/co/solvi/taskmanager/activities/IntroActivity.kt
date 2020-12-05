package il.co.solvi.taskmanager.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import firebase.FireClass
import il.co.solvi.taskmanager.R
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        loadViews()

    }


    private fun loadViews() {

        intro_sign_btn.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        intro_login_btn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }


}