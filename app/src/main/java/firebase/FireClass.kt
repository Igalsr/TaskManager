package firebase

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import il.co.solvi.taskmanager.activities.LoginActivity
import il.co.solvi.taskmanager.activities.SignUpActivity
import model.AppUser
import utils.Constants

open class FireClass {

    private val TAG = "FireClass"

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, appUserInfo: AppUser){

        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(appUserInfo, SetOptions.merge()).addOnSuccessListener {
                activity.registeredSuccess()
            }
    }

    fun loginUser(activity: LoginActivity){

        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId()).get()
            .addOnSuccessListener { document ->

                val loggedInUser = document.toObject(AppUser::class.java)
                if (loggedInUser != null){
                    activity.loginSuccess(loggedInUser)
                }

            }.addOnFailureListener { err ->
                Log.d(TAG, "loginUserError: ${err.message}")
            }
    }

    fun getCurrentUserId(): String {

        val currentUser = FirebaseAuth.getInstance().currentUser

        var userId = ""
        if (currentUser != null){
            userId = currentUser.uid
        }
        return userId
    }


}