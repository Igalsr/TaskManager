package model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppUser (
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: Long = 0,
    val fcmToken: String = "",
): Parcelable