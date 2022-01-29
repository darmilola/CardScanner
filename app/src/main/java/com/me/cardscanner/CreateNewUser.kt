package com.me.cardscanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateNewUser : AppCompatActivity() {

    lateinit var userFirstname: EditText
    lateinit var userLastname: EditText
    lateinit var userPhone: EditText
    lateinit var userGender: EditText
    lateinit var userId: EditText
    lateinit var userSubmit: MaterialButton
    lateinit var db: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_user)
        userFirstname = findViewById(R.id.add_user_firstname)
        userLastname = findViewById(R.id.add_user_lastname)
        userPhone = findViewById(R.id.add_user_phone)
        userGender = findViewById(R.id.add_user_gender)
        userId = findViewById(R.id.add_user_id)
        userSubmit = findViewById(R.id.add_user_submit_button)


        db = Room.databaseBuilder(
            applicationContext,
            Database::class.java, "users"
        ).build()


        userSubmit.setOnClickListener {
            if(isValidForm()){
                GlobalScope.launch {
                    var newUser = UserEntity(
                        0,
                        userId.text.toString(),
                        userFirstname.text.toString(),
                        userLastname.text.toString(),
                        userPhone.text.toString(),
                        userGender.text.toString()
                    )
                    db.getUserDao().insertUser(newUser)
                }
            }
            else{
                Toast.makeText(this@CreateNewUser,"Invalid Input",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun isValidForm(): Boolean{
        var isValid = true

        if(userFirstname.text.contentEquals("",true)){
            isValid = false
            return isValid
        }
        if(userLastname.text.contentEquals("",true)){
            isValid = false
            return isValid
        }
        if(userPhone.text.contentEquals("",true)){
            isValid = false
            return isValid
        }
        if(userGender.text.contentEquals("",true)){
            isValid = false
            return isValid
        }
        if(userId.text.contentEquals("",true)){
            isValid = false
            return isValid
        }
        return isValid
    }
}