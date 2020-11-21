package com.example.sqlite_demo_kotlin.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.sqlite_demo_kotlin.Model.Customer
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class CustomerData(private val mContext: Context) {
    private var objDA: DataAccess? = null

    // data save in table//
    fun SaveCustomerData(customer: Customer): Int {
        val objDA = DataAccess(mContext)
        val contentValues = ContentValues()
        contentValues.put(FirstName, customer.firstName)
        contentValues.put(MiddleName, customer.middleName)
        contentValues.put(LastName, customer.lastName)
        contentValues.put(ContactNo, customer.contactNo)
        return objDA.insert(contentValues, TableName)
    }

    fun viewdata(): JSONArray? {
        var jsonObject: JSONObject
        val jsonArray = JSONArray()
        var cursor: Cursor? = null
        try {
            if (objDA == null) {
                objDA = DataAccess(mContext)
            }
            val sql = "select * from " + TableName
            cursor = objDA!!.GetRecords(sql)
            if (cursor?.moveToFirst()!!) {
                do {
                    jsonObject = JSONObject()
                    val PrintFarmerId = cursor.getString(0)
                    jsonObject.put("SCustomerId", PrintFarmerId)
                    jsonObject.put(FirstName, cursor.getString(1))
                    jsonObject.put(MiddleName, cursor.getString(2))
                    jsonObject.put(LastName, cursor.getString(3))
                    jsonObject.put(ContactNo, cursor.getString(4))
                    Log.v("JsonObject", jsonObject.toString())
                    jsonArray.put(jsonObject)
                } while (cursor.moveToNext())
                return jsonArray
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return null
    }

    fun UpdateData(custId: Int, fname: String?, m_name: String?, l_name: String?, contact: String?): Int {
        return try {
            if (objDA == null) {
                objDA = DataAccess(mContext)
            }
            val contentValues = ContentValues()
            contentValues.put(FirstName, fname)
            contentValues.put(MiddleName, m_name)
            contentValues.put(LastName, l_name)
            contentValues.put(ContactNo, contact)
            objDA!!.update(contentValues, TableName, SCustomerId + " = ?", arrayOf(custId.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

    fun deleteItem(custId: Int): Int? {
        try {
            if (objDA == null) {
                objDA = DataAccess(mContext)
            }
            objDA!!.deletedync(TableName, SCustomerId, custId)
            Log.d("JsonObject", objDA.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    companion object {
        const val TableName = "Customer"
        const val SCustomerId = "SCustomerId"
        const val FirstName = "FirstName"
        const val MiddleName = "MiddleName"
        const val LastName = "LastName"
        const val ContactNo = "ContactNo"


		@JvmField
		var CreateCustomerTable = ("create table "
                + TableName + "(" + SCustomerId + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, "
                + FirstName + " TEXT NOT NULL,"
                + MiddleName + " TEXT NOT NULL,"
                + LastName + " TEXT NOT NULL,"
                + ContactNo + " INTEGER NOT NULL)")
    }
}