package com.example.sqlite_demo_kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlite_demo_kotlin.DataViewActivity
import com.example.sqlite_demo_kotlin.Database.CustomerData
import com.example.sqlite_demo_kotlin.Model.Customer
import com.example.sqlite_demo_kotlin.utils.Constants
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var btnSave_Next: Button? = null
    private var name: EditText? = null
    private var M_name: EditText? = null
    private var L_name: EditText? = null
    private var txt_contact: EditText? = null
    private val context: Context = this
    //private var intent: Intent? = null
    var custId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById<View>(R.id.edt_FName) as EditText
        M_name = findViewById<View>(R.id.edt_MName) as EditText
        L_name = findViewById<View>(R.id.edt_LName) as EditText
        txt_contact = findViewById<View>(R.id.edt_ContactNo) as EditText
        btnSave_Next = findViewById<View>(R.id.btnSaveCustomer) as Button
        btnSave_Next!!.setOnClickListener(this)
        intent = getIntent()
        if (intent != null && intent!!.hasExtra(Constants.UPDATE_Person_Id)) {
            btnSave_Next!!.text = "Update"
            val customer = intent!!.getStringExtra(Constants.UPDATE_Person_Id)
            val gson = Gson()
            val rowItems = gson.fromJson(customer, Customer::class.java)
            val Fname = rowItems.firstName
            val Mname = rowItems.middleName
            val Lname = rowItems.lastName
            val contactNo = rowItems.contactNo
            custId = rowItems.sCustomerId
            name!!.setText(Fname)
            M_name!!.setText(Mname)
            L_name!!.setText(Lname)
            txt_contact!!.setText(contactNo)
            Log.d("custId", custId.toString())
        }
    }

    override fun onClick(view: View) {
        val dateFormatter: SimpleDateFormat
        dateFormatter = SimpleDateFormat("MMM dd,yyyy HH:mm:ss a", Locale.ENGLISH)
        val timeStamp = dateFormatter.format(Date())
        //-----------------------------------------------------------------------------------------------------------------------//
        if (!intent!!.hasExtra(Constants.UPDATE_Person_Id)) {
            val objModel = Customer()
            val objData = CustomerData(this)
            objModel.firstName = name!!.text.toString()
            objModel.middleName = M_name!!.text.toString()
            objModel.lastName = L_name!!.text.toString()
            objModel.contactNo = txt_contact!!.text.toString()
            val Result = objData.SaveCustomerData(objModel)
            if (Result != -1) {
                Toast.makeText(this, "Record Inserted Successfully", Toast.LENGTH_LONG).show()
                name!!.setText("")
                M_name!!.setText("")
                L_name!!.setText("")
                txt_contact!!.setText("")
            } else {
                Toast.makeText(this, "Record Not Inserted", Toast.LENGTH_LONG).show()
            }
        } else {
            val objModel = Customer()
            val objData = CustomerData(this)
            val fname = name!!.text.toString()
            val m_name = M_name!!.text.toString()
            val l_name = L_name!!.text.toString()
            val contact = txt_contact!!.text.toString()
            val id = objData.UpdateData(custId, fname, m_name, l_name, contact)
            Log.d("id", id.toString())
            Toast.makeText(this@MainActivity, "Update Record", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@MainActivity, DataViewActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }


}