package com.noministic.userslogintest.data.models.User


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.noministic.userslogintest.others.Constants

@Entity(tableName = Constants.USERS_TABLE_NAME)
data class UsersItem @Ignore constructor(
    @Ignore
    @SerializedName("address")
    val address: Address? = null,
    @Ignore
    @SerializedName("company")
    val company: Company? = null,
    @SerializedName("email")
    var email: String,
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("website")
    var website: String,
    var loggedIn: Boolean
) {
    constructor(
        email: String, id: Int, name: String, phone: String,
        username: String, website: String, loggedIn: Boolean
    ) : this(
        null, null,
        email, id, name, phone, username, website, loggedIn
    )
}