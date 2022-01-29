package com.me.cardscanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.google.android.material.button.MaterialButton
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.kroegerama.kaiteki.bcode.BarcodeResultListener
import com.kroegerama.kaiteki.bcode.ui.showBarcodeAlertDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), BarcodeResultListener {
    private val MY_CAMERA_REQUEST_CODE = 100

    lateinit var userInfoId: TextView
    lateinit var userInfoFirstname: TextView
    lateinit var userInfoLastname: TextView
    lateinit var userInfoGender: TextView
    lateinit var userInfoPhone: TextView
    lateinit var scanUser: MaterialButton
    lateinit var rootLayout: LinearLayout
    lateinit var db: Database
    lateinit var createUser: MaterialButton
    lateinit var userEntity: UserEntity
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scanUser = findViewById(R.id.start_scan)
        createUser = findViewById(R.id.create_user)
        userInfoId = findViewById(R.id.user_info_user_id)
        userInfoLastname = findViewById(R.id.user_info_lastname)
        userInfoFirstname = findViewById(R.id.user_info_firstname)
        userInfoGender = findViewById(R.id.user_info_gender)
        userInfoPhone = findViewById(R.id.user_info_phone)
        rootLayout = findViewById(R.id.user_info_root)

        db = Room.databaseBuilder(
            applicationContext,
            Database::class.java, "users"
        ).build()

        createUser.setOnClickListener {
            startActivity(Intent(this@MainActivity,CreateNewUser::class.java))
        }

        scanUser.setOnClickListener {


            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_REQUEST_CODE);
            }
            else{
                showBarcodeAlertDialog(
                    owner = this,
                    listener = this,
                    formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.AZTEC),
                    barcodeInverted = false
                )
            }
        }
    }

    override fun onBarcodeResult(result: Result): Boolean {

        Toast.makeText(this,result.toString(),Toast.LENGTH_LONG).show()

        GlobalScope.launch {
             userEntity = db.getUserDao().getUser(result.toString())
        }

        userInfoId.setText(userEntity.userId)
        userInfoFirstname.setText(userEntity.firstname)
        userInfoLastname.setText(userEntity.lastname)
        userInfoPhone.setText(userEntity.phone)
        userInfoGender.setText(userEntity.gender)
        rootLayout.visibility = View.VISIBLE

        return false
    }

    override fun onBarcodeScanCancelled() {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showBarcodeAlertDialog(
                    owner = this,
                    listener = this,
                    formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.AZTEC),
                    barcodeInverted = false
                )
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

}