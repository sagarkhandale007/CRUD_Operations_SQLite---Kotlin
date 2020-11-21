package com.example.sqlite_demo_kotlin.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DataAccess     // TODO Auto-generated constructor stub
(private val mContext: Context) : SQLiteOpenHelper(mContext, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        // TODO Auto-generated method stub
        if (!db.isReadOnly) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;")
        }
        db.execSQL(CustomerData.CreateCustomerTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + CustomerData.TableName)
        onCreate(db)
    }

    fun insert(contentValues: ContentValues?, tableName: String?): Int {
        var db: SQLiteDatabase? = null
        return try {
            db = this.writableDatabase
            db.insert(tableName, null, contentValues).toInt()
        } catch (sqlException: Exception) {
            // TODO: handle exception
            sqlException.printStackTrace()
            Toast.makeText(mContext, sqlException.message.toString(), Toast.LENGTH_LONG).show()
            -1
        } finally {

            if (db!!.isOpen) {
                db.close()
            }
        }
    }

    fun update(contentValues: ContentValues?, tableName: String?, whereClause: String?, WhereArgs: Array<String?>?): Int {
        var db: SQLiteDatabase? = null
        return try {
            db = this.writableDatabase
            // updating row
            db.update(tableName, contentValues, whereClause, WhereArgs)
        } catch (sqlException: Exception) {
            // TODO: handle exception
            sqlException.printStackTrace()
            Toast.makeText(mContext, sqlException.message.toString(), Toast.LENGTH_LONG).show()
            -1
        } finally {

            if (db!!.isOpen) {
                db.close()
            }
        }
    }

    fun GetRecords(sql: String?): Cursor? {
        var cursor: Cursor? = null
        return try {
            val database = this.readableDatabase
            cursor = database.rawQuery(sql, null)
            cursor
        } catch (sqlException: Exception) {
            sqlException.printStackTrace()
            null
        } finally {

        }
    }

    fun deleteContact(contentValues: Array<String?>?, tableName: String?): Int {
        var db: SQLiteDatabase? = null
            return try {
                db = this.writableDatabase
                db.delete(tableName, null, contentValues)
            } catch (sqlException: Exception) {
                // TODO: handle exception
                sqlException.printStackTrace()
                Toast.makeText(mContext, sqlException.message.toString(), Toast.LENGTH_LONG).show()
                -1
            } finally {
                if (db!!.isOpen) {
                    db.close()
                }
            }

    }

    fun deletedync(tableName: String, sCustomerId: String, deleteObject: Int) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $tableName WHERE $sCustomerId = $deleteObject")
        db.close()
    }



    companion object {
        const val DATABASE_NAME = "Demo.db"
    }
}