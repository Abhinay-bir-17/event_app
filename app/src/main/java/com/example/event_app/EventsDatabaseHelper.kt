package com.example.event_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/* curser factory as null means using  default cursor*/
class EventsDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "eventsapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allevents"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TIME = "time"
    } // focus
    //  we will create table using create table query
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT, $COLUMN_DATE TEXT, $COLUMN_TIME TEXT)"
        db?.execSQL(createTableQuery)
    }
    override fun onUpgrade (db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
        // here we drop a table with similar table name if it exists, then we call to create
        // a new table using onCreate(db)
    }
    fun insertEvent (event: Event){
        val db = writableDatabase // means db can be modified
        // id is provided by sqlite db
        val values = ContentValues().apply{ //contentValues is a class that is used to store vals
            put(COLUMN_TITLE, event.title)
            put(COLUMN_CONTENT, event.content)
            put(COLUMN_DATE, event.date)
            put(COLUMN_TIME, event.time)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getAllNotes(): List<Event> {
        val eventsList = mutableListOf <Event>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        //Executes the SQL query and returns a Cursor object that points to the result set.
        //cursor: Allows iteration over the rows in the result set.
        val cursor = db.rawQuery (query, null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow (COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow (COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
            val event = Event(id, title, content,date,time)
            eventsList.add(event)
        }
        cursor.close()
        db.close()
        return eventsList
    }
    fun updateEvent (event: Event) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, event.title)
            put(COLUMN_CONTENT, event.content)
            put(COLUMN_DATE, event.date)
            put(COLUMN_TIME, event.time)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(event.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getEventByID(eventId: Int):Event{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $eventId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow (COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
        val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
        val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
        cursor.close()
        db.close()
        return Event(id, title, content,date,time)
    }
    fun deleteNote(eventId: Int){
        val db = writableDatabase
        val whereClause  = "$COLUMN_ID =?"
        val whereArgs = arrayOf(eventId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }
}