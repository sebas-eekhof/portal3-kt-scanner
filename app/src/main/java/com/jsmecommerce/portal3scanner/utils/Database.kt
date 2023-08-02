package com.jsmecommerce.portal3scanner.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(val context: Context) : SQLiteOpenHelper(context, "portal3", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE settings (name VARCHAR(64) PRIMARY KEY, value VARCHAR(512))")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun put(key: String, value: String) {
        val contentValues = ContentValues()
        contentValues.put("name", key)
        contentValues.put("value", value)
        writableDatabase.insertWithOnConflict("settings", "name", contentValues, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun get(key: String): String? {
        val result = readableDatabase.rawQuery("SELECT * FROM `settings` WHERE `name` = ? LIMIT 1", arrayOf(key))
        var res: String? = null
        if(result.moveToFirst())
            res = result.getString(result.getColumnIndexOrThrow("value"))
        result.close()
        return res
    }

    fun has(key: String): Boolean {
        return get(key) != null
    }

    fun del(key: String) {
        writableDatabase.delete("settings", "name = ?", arrayOf(key))
    }
}