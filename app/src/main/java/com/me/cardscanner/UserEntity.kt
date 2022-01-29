package com.me.cardscanner

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int, @ColumnInfo(name = "userId") var userId: String?,
                       @ColumnInfo(name = "firstname") var firstname: String?, @ColumnInfo(name = "lastname") var lastname: String?,
                       @ColumnInfo(name = "phone") var phone: String?, @ColumnInfo(name = "gender") var gender: String?
) {


}