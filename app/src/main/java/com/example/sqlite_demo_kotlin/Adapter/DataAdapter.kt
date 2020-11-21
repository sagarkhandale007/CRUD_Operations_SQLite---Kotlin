package com.example.sqlite_demo_kotlin.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite_demo_kotlin.Database.CustomerData
import com.example.sqlite_demo_kotlin.MainActivity
import com.example.sqlite_demo_kotlin.Model.Customer
import com.example.sqlite_demo_kotlin.R
import com.example.sqlite_demo_kotlin.utils.Constants
import com.google.gson.Gson


class DataAdapter(private val mContext: Context, listItemDesc: List<*>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    val orderDescItem: ArrayList<Customer>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        viewHolder.itemView.tag = orderDescItem[position]
        val currentOrder = orderDescItem[position]
        viewHolder.mTxtfName.text = currentOrder.firstName
        viewHolder.mTxtmName.text = currentOrder.middleName
        viewHolder.mTxtlName.text = currentOrder.lastName
        viewHolder.mTxtcontactNo.text = currentOrder.contactNo
        viewHolder.mBtnItemDelete.setOnClickListener {
            val currentOrder = orderDescItem[viewHolder.adapterPosition]
            //val custId = String.valueOf(currentOrder.sCustomerId)
            val custId = currentOrder.sCustomerId

            val customerData = CustomerData(mContext)
            // roleTable.deleteItem(roleid);
            customerData.deleteItem(custId)
            delete(viewHolder.adapterPosition) //calls the method above to delete

        }
        viewHolder.mTxtEdit.setOnClickListener {
            val gson = Gson()
            val customer = orderDescItem[viewHolder.adapterPosition]
            val data = gson.toJson(customer)
            val i = Intent(mContext, MainActivity::class.java)
            i.putExtra(Constants.UPDATE_Person_Id, data)
            mContext.startActivity(i)
            (mContext as Activity).finish()
        }
    }

    override fun getItemCount(): Int {
        return orderDescItem.size
    }

    inner class ViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView) {
        var mTxtfName: TextView
        var mTxtmName: TextView
        var mTxtlName: TextView
        var mTxtcontactNo: TextView
        var mTxtEdit: ImageView
        var mBtnItemDelete: ImageView

        init {
            mTxtfName = convertView.findViewById(R.id.emp_Fname)
            mTxtmName = convertView.findViewById(R.id.emp_Mname)
            mTxtlName = convertView.findViewById(R.id.emp_Lname)
            mTxtcontactNo = convertView.findViewById(R.id.emp_ContactNo)
            mTxtEdit = convertView.findViewById(R.id.edit_Image)
            mBtnItemDelete = convertView.findViewById(R.id.edit_delete)

            mTxtEdit.setColorFilter(ContextCompat.getColor(mContext, R.color.Red), android.graphics.PorterDuff.Mode.MULTIPLY);
            mBtnItemDelete.setColorFilter(ContextCompat.getColor(mContext, R.color.Red), android.graphics.PorterDuff.Mode.MULTIPLY);

        }
    }


    private fun delete(position: Int) {
        orderDescItem.removeAt(position)
        //notifyItemRemoved(position)
       // notifyItemRangeChanged(position, orderDescItem.size)
        notifyDataSetChanged();
    }


    init {
        orderDescItem = listItemDesc as ArrayList<Customer>;
    }
}




