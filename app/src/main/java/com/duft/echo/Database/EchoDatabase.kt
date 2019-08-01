package com.duft.echo.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.duft.echo.Database.EchoDatabase.Staticated.COLUMN_ID
import com.duft.echo.Database.EchoDatabase.Staticated.COLUMN_SONG_ARTIST
import com.duft.echo.Database.EchoDatabase.Staticated.COLUMN_SONG_PATH
import com.duft.echo.Database.EchoDatabase.Staticated.COLUMN_SONG_TITLE
import com.duft.echo.Database.EchoDatabase.Staticated.TABLE_NAME
import com.duft.echo.Songs
import java.lang.Exception

class EchoDatabase : SQLiteOpenHelper {
    var _songList = ArrayList<Songs>()



    object Staticated{
        var DB_VERSION=1
        val DB_NAME = "FavouriteDatabae"
        val TABLE_NAME = "FavouriteTable"
        val COLUMN_ID = "SongID"
        val COLUMN_SONG_TITLE = "SOngTitle"
        val COLUMN_SONG_ARTIST = "SongArtist"
        val COLUMN_SONG_PATH = "SongPath"
    }
    override fun onCreate(SqliteDatabase: SQLiteDatabase?) {
        SqliteDatabase?.execSQL("CREATE TABLE " + Staticated.TABLE_NAME + " ( " + Staticated.COLUMN_ID + " INTEGER, " + Staticated.COLUMN_SONG_ARTIST + " STRING, "
                + Staticated.COLUMN_SONG_TITLE + " STRING, " + Staticated.COLUMN_SONG_PATH + " STRING);")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : super(context, name, factory, version)
    constructor(context: Context?) : super(context, Staticated.DB_NAME, null, Staticated.DB_VERSION)

    fun storeAsFavourite(id: Int?, artist: String?, songTitle: String?, path: String?) {
        val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COLUMN_ID, id)
        contentValues.put(COLUMN_SONG_ARTIST, artist)
        contentValues.put(COLUMN_SONG_TITLE, songTitle)
        contentValues.put(COLUMN_SONG_PATH, path)
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun queryDBList(): ArrayList<Songs>? {
        try {
            val db = this.readableDatabase
            val query_params = "SELECT * FROM" + Staticated.TABLE_NAME
            var cSor = db.rawQuery(query_params, null)
            if (cSor.moveToFirst()) {
                do {
                    var _id = cSor.getInt(cSor.getColumnIndexOrThrow(COLUMN_ID))
                    var _artist = cSor.getString(cSor.getColumnIndexOrThrow(COLUMN_SONG_ARTIST))
                    var _title = cSor.getString(cSor.getColumnIndexOrThrow(COLUMN_SONG_TITLE))
                    var _sonngPath = cSor.getString(cSor.getColumnIndexOrThrow(COLUMN_SONG_PATH))
                    _songList.add(Songs(_id.toLong(), _title, _artist, _sonngPath, 0))

                } while (cSor.moveToNext())
            } else {
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return _songList
    }

    fun checkifIdExist(_id: Int): Boolean{
        var storeId=-1090
        val db=this.readableDatabase
        val query_params = "SELECT * FROM " + Staticated.TABLE_NAME + " WHERE SongId = '$_id'"
        val cSor=db.rawQuery(query_params, null)
        if(cSor.moveToFirst()){
            do{
                storeId=cSor.getInt(cSor.getColumnIndexOrThrow(COLUMN_ID))
            }while (cSor.moveToNext())
        }else{
            return false
        }
        return storeId!=-1090
    }
    fun deleteFavourite(_id: Int){
        val db=this.writableDatabase
        db.delete(TABLE_NAME,COLUMN_ID+"="+_id,null)
        db.close()
    }
    fun checkSize():Int{
        var counter=0
        val db=this.readableDatabase
        var query_params="SELECT * FROM " + Staticated.TABLE_NAME
        val cSor=db.rawQuery(query_params,null)
        if (cSor.moveToFirst()){
            do {
                counter=counter+1
            }while (cSor.moveToNext())
        }else{
            return 0
        }
        return counter

    }
}