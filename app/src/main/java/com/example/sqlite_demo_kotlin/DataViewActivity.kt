package com.example.sqlite_demo_kotlin

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite_demo_kotlin.Adapter.DataAdapter
import com.example.sqlite_demo_kotlin.Database.CustomerData
import com.example.sqlite_demo_kotlin.Model.Customer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException


class DataViewActivity : AppCompatActivity() {
    private var adapter: DataAdapter? = null
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            // .setAction("Action", null).show();
            val intent = Intent(this@DataViewActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        recyclerView = findViewById(R.id.tabLayoutLableItemList) as RecyclerView

       // tabLayoutItemList = findViewById(R.id.tabLayoutLableItemList)
        rowItems = ArrayList()
        allData
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setLayoutManager(linearLayoutManager) // set LayoutManager to RecyclerView


      /*  adapter = DataAdapter(this@DataViewActivity, rowItems as ArrayList<Customer?>)
        recyclerView.setAdapter(adapter)
        adapter!!.notifyDataSetChanged()*/
    }

    val allData: Unit
        get() {
            rowItems!!.clear()

            Thread {


                try {
                    val customerData = CustomerData(this)
                    val featch_Array = customerData.viewdata()
                    if (featch_Array == null) {
                    } else {
                        for (p in 0 until featch_Array.length()) {
                            try {
                                val ItemCategoryDetails = featch_Array.getJSONObject(p)
                                val SCustomerId = ItemCategoryDetails.getString("SCustomerId").toInt()
                                val FirstName = ItemCategoryDetails.getString("FirstName")
                                val MiddleName = ItemCategoryDetails.getString("MiddleName")
                                val LastName = ItemCategoryDetails.getString("LastName")
                                val ContactNo = ItemCategoryDetails.getString("ContactNo")
                                val item = Customer(SCustomerId, FirstName, MiddleName, LastName, ContactNo)
                                rowItems!!.add(item)

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                        adapter = DataAdapter(this@DataViewActivity, rowItems as ArrayList<Customer?>)
                        recyclerView.setAdapter(adapter)
                        adapter!!.notifyDataSetChanged()
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
            }.start()

        }

    companion object {
        //val tabLayoutItemList: RecyclerView? = null
        private var rowItems: ArrayList<Customer?>? = null
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        finish()

    }
}