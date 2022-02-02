package com.example.haritagedemo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.preference.PreferenceManager
import android.util.Log
import com.example.haritagedemo.API.PreferanceManager
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class DBHelper:SQLiteOpenHelper {

    lateinit var context: Context
    val DATABASE_NAME = "NHeritageDB.sqlite"

    //  val HERITAGE_SITE_TABLE_NAME = "BeaconMaster"
    val HERITAGE_SITE_TABLE_NAME = "HeritageSitesMasterData"

    val HERITAGE_SITE_COLUMN_TITLE = "Names"
    val HERITAGE_SITE_COLUMN_UUID = "UUID"
    val HERITAGE_SITE_COLUMN_MAJOR = "Major"
    val HERITAGE_SITE_COLUMN_MINOR = "Minor"
    val HERITAGE_SITE_COLUMN_HERITAGE_ID = "NID"
    val HERITAGE_SITE_COLUMN_LATITUDE = "Site_Latitude"
    val HERITAGE_SITE_COLUMN_LONGITUDE = "Site_Longitude"
    val HERITAGE_SITE_COLUMN_TYPE = "Type"
    val HERITAGE_QUIZ_TABLE_NAME = "QuizMaster"
    val HERITAGE_QUIZ_COLUMN_QUESTION = "Question"
    val HERITAGE_QUIZ_COLUMN_ANSWER = "Answer"
    val HERITAGE_QUIZ_COLUMN_OPTION1 = "Option1"
    val HERITAGE_QUIZ_COLUMN_OPTION2 = "Option2"
    val HERITAGE_QUIZ_COLUMN_OPTION3 = "Option3"
    val HERITAGE_QUIZ_COLUMN_OPTION4 = "Option4"
    val HERITAGE_HINDI_NAMES ="HindiName"
    val HERITAGE_GUJARATI_NAMES ="GujaratiName"
    val db: SQLiteDatabase? = null

    val DATABASE_PATH = "/data/data/com.amc.amcheritage/databases/"

    constructor(context: Context) : super(context, "NHeritageDB.sqlite", null, 2) {
        if (db != null && db.isOpen()) close()
        this.context = context
        if (!isTableExists(HERITAGE_SITE_TABLE_NAME)) {
       //     copyDataBase()
        }
    }

//    //Copies your database from your local assets-folder to the just created empty database in the system folder
//    @Throws(IOException::class)
//    private fun copyDataBase() {
//        val database: SQLiteDatabase = this.readableDatabase
//        val mInput: InputStream = context.assets.open(DATABASE_NAME)
//        val outFileName: String = database.path
//        val mOutput: OutputStream = FileOutputStream(outFileName)
//        val mBuffer = ByteArray(2024)
//        var mLength = 0
//        while (mInput.read(mBuffer).also { mLength = it } > 0) {
//            mOutput.write(mBuffer, 0, mLength)
//        }
//        database.close()
//        mOutput.flush()
//        mOutput.close()
//        mInput.close()
//    }

    override fun onCreate(db: SQLiteDatabase?) {}

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $HERITAGE_SITE_TABLE_NAME")
        onCreate(db)
    }

    fun insertLayer(heritageData: HeritageData): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(HERITAGE_SITE_COLUMN_TITLE, heritageData.heritageTitle)
        contentValues.put(HERITAGE_SITE_COLUMN_UUID, heritageData.heritageUuid)
        contentValues.put(HERITAGE_SITE_COLUMN_MAJOR, heritageData.heritageMajor)
        contentValues.put(HERITAGE_SITE_COLUMN_MINOR, heritageData.heritageMinor)
        contentValues.put(HERITAGE_SITE_COLUMN_HERITAGE_ID, heritageData.heritageId)
        return db.insert(HERITAGE_SITE_TABLE_NAME, null, contentValues) > 0
    }

    @SuppressLint("Range")
    fun getHeritagesiteInfo(context: Context): HeritageData? {
        val db = this.readableDatabase
        var heritageData: HeritageData? = null
        val sql =
            "select * from " + HERITAGE_SITE_TABLE_NAME + " where " + HERITAGE_SITE_COLUMN_UUID + "= '" + context + "' and " + HERITAGE_SITE_COLUMN_MAJOR + "= '" + context + "' and " + HERITAGE_SITE_COLUMN_MINOR + "= '" + context + "'"
        val res = db.rawQuery(sql, null)
        if (res.count > 0) {
            res.moveToFirst()
            heritageData = HeritageData(
                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_TITLE)),
                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_TYPE)),
                res.getInt(res.getColumnIndex(HERITAGE_SITE_COLUMN_HERITAGE_ID)),
            )
            heritageData.heritageTitleInGujarati = res.getString(res.getColumnIndex(HERITAGE_GUJARATI_NAMES))
            heritageData.heritageTitleInHindi = res.getString(res.getColumnIndex(HERITAGE_HINDI_NAMES))

        }


        // println(" names $heritageData")
        res.close()
        db.close()
        return heritageData
    }

    @SuppressLint("Range")
    fun getHeritagesiteInfo(beacon: Beacon): HeritageData? {
        val db = this.readableDatabase
        var heritageData: HeritageData? = null
        val sql =
            "select * from " + HERITAGE_SITE_TABLE_NAME + " where " + HERITAGE_SITE_COLUMN_UUID + "= '" + beacon.uuid + "' and " + HERITAGE_SITE_COLUMN_MAJOR + "= '" + beacon.major + "' and " + HERITAGE_SITE_COLUMN_MINOR + "= '" + beacon.minor + "'"
        val res = db.rawQuery(sql, null)
        Log.e("SQLite Query", "sql: $sql")
        if (res.count > 0) {
            res.moveToFirst()
            heritageData = HeritageData(
                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_TITLE)),
                res.getInt(res.getColumnIndex(HERITAGE_SITE_COLUMN_HERITAGE_ID))
            )
        }

        res.close()
        db.close()
        // println(" names $heritageData")
        return heritageData
    }

    @SuppressLint("Range")
    suspend fun getAllHeritagesInfo(): ArrayList<HeritageData> {
        val db = this.readableDatabase
        val heritageData = ArrayList<HeritageData>()
        val res = db.rawQuery("select * from $HERITAGE_SITE_TABLE_NAME where $HERITAGE_SITE_COLUMN_TYPE='heritage_site'", null)
        // val toast = Toast.makeText(context, PreferenceManager(context).getLanguage()?.name, Toast.LENGTH_LONG)
//        toast.show()
        if (res.moveToFirst()) {
            do {
                when (PreferanceManager(context).getLanguage()?.name) {
                    "English" -> {
                        heritageData.add(
                            HeritageData(
                                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_TITLE)),
                                res.getInt(res.getColumnIndex(HERITAGE_SITE_COLUMN_HERITAGE_ID)),
                                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_LATITUDE)),
                                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_LONGITUDE)),
                                res.getInt(res.getColumnIndex(HERITAGE_SITE_COLUMN_MAJOR)),
                                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_TYPE))
                            )
                        )
                    }
                    "Hindi" -> {
                        heritageData.add(
                            HeritageData(
                                res.getString(res.getColumnIndex(HERITAGE_HINDI_NAMES)),
                                res.getInt(res.getColumnIndex(HERITAGE_SITE_COLUMN_HERITAGE_ID)),
                                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_LATITUDE)),
                                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_LONGITUDE)),
                                res.getInt(res.getColumnIndex(HERITAGE_SITE_COLUMN_MAJOR)),
                                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_TYPE))
                            )
                        )
                    }
                    "Gujarati" -> {
                        heritageData.add(
                            HeritageData(
                                res.getString(res.getColumnIndex(HERITAGE_GUJARATI_NAMES)),
                                res.getInt(res.getColumnIndex(HERITAGE_SITE_COLUMN_HERITAGE_ID)),
                                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_LATITUDE)),
                                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_LONGITUDE)),
                                res.getInt(res.getColumnIndex(HERITAGE_SITE_COLUMN_MAJOR)),
                                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_TYPE))
                            )
                        )
                    }
                }

            } while (res.moveToNext())
        }
        res.close()
        db.close()
        return heritageData
    }


    @SuppressLint("Range")
    fun getSiteNameFromID(nid: Int): HeritageData? {
        val db = this.readableDatabase
        var siteDetails: HeritageData? = null
        val sql = "select * from $HERITAGE_SITE_TABLE_NAME where $HERITAGE_SITE_COLUMN_HERITAGE_ID= '$nid'"
        val res = db.rawQuery(sql, null)
        if (res.count > 0) {
            res.moveToFirst()
            siteDetails = HeritageData(
                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_TITLE)),
                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_TYPE)),
                nid
            )
            siteDetails.heritageTitleInGujarati = res.getString(res.getColumnIndex(HERITAGE_GUJARATI_NAMES))
            siteDetails.heritageTitleInHindi = res.getString(res.getColumnIndex(HERITAGE_HINDI_NAMES))


        }
        res.close()
        db.close()
        return siteDetails
    }

    @SuppressLint("Range")
    fun getHeritagesiteInfo1(proximityUUID: String, minor: String, major: String): HeritageData? {
        val db = this.readableDatabase
        var heritageData: HeritageData? = null
        val res = db.rawQuery(
            "select * from " + HERITAGE_SITE_TABLE_NAME + " where " + HERITAGE_SITE_COLUMN_UUID + "= '" + proximityUUID + "' and " + HERITAGE_SITE_COLUMN_MAJOR + "= '" + major + "' and " + HERITAGE_SITE_COLUMN_MINOR + "= '" + minor + "'",
            null
        )
        if (res.count > 0) {
            res.moveToFirst()
            heritageData = HeritageData(
                res.getString(res.getColumnIndex(HERITAGE_SITE_COLUMN_TITLE)),
                res.getInt(res.getColumnIndex(HERITAGE_SITE_COLUMN_HERITAGE_ID))
            )
        }


        // println(" names $heritageData")
        res.close()
        db.close()
        return heritageData
    }

    fun isTableExists(tableName: String): Boolean {
        var db = this.readableDatabase
        var isExist = false
        try {
            val cursor: Cursor? = db!!.rawQuery(
                "select * from $tableName",
                null
            )

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    isExist = true
                }
                cursor.close()
            }
        } catch (e: SQLiteException) {
            isExist = false
        }
        return isExist
    }


    @SuppressLint("Range")
    fun getQuizList(): ArrayList<QuizData> {
        val db = this.readableDatabase
        var quizData = ArrayList<QuizData>()
        val res = db.rawQuery(
            "select * from " + HERITAGE_QUIZ_TABLE_NAME,
            null
        )
        if (res.moveToFirst()) {
            do {

                quizData.add(
                    QuizData(
                        res.getString(res.getColumnIndex(HERITAGE_QUIZ_COLUMN_QUESTION)),
                        res.getString(res.getColumnIndex(HERITAGE_QUIZ_COLUMN_ANSWER)),
                        res.getString(res.getColumnIndex(HERITAGE_QUIZ_COLUMN_OPTION1)),
                        res.getString(res.getColumnIndex(HERITAGE_QUIZ_COLUMN_OPTION2)),
                        res.getString(res.getColumnIndex(HERITAGE_QUIZ_COLUMN_OPTION3)),
                        res.getString(res.getColumnIndex(HERITAGE_QUIZ_COLUMN_OPTION4))
                    )
                )
            } while (res.moveToNext());
        }
        res.close()
        db.close()
        return quizData
    }

    fun insertQuiz(quizList: ArrayList<QuizData?>?) {
        val db = this.writableDatabase

        for (quizData in quizList!!) {
            val contentValues = ContentValues()
            contentValues.put(HERITAGE_QUIZ_COLUMN_QUESTION, quizData!!.question)
            contentValues.put(
                HERITAGE_QUIZ_COLUMN_ANSWER,
                quizData.optionArray!!.get(quizData.correctIndex)
            )
            contentValues.put(HERITAGE_QUIZ_COLUMN_OPTION1, quizData.optionArray!!.get(0))
            contentValues.put(HERITAGE_QUIZ_COLUMN_OPTION2, quizData.optionArray!!.get(1))
            contentValues.put(HERITAGE_QUIZ_COLUMN_OPTION3, quizData.optionArray!!.get(2))
            contentValues.put(HERITAGE_QUIZ_COLUMN_OPTION4, quizData.optionArray!!.get(3))

            db.insert(HERITAGE_QUIZ_TABLE_NAME, null, contentValues)
        }
        db.close()
    }

    fun clearQuizData() {
        val db = this.writableDatabase
        db.execSQL("delete from " + HERITAGE_QUIZ_TABLE_NAME);
    }

    companion object {
        fun isTableExists(dbHelper: DBHelper, tableName: String): Boolean {
            var db = dbHelper.readableDatabase
            var isExist = false
            try {
                val cursor: Cursor? = db!!.rawQuery(
                    "select * from $tableName",
                    null
                )

                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        isExist = true
                    }
                    cursor.close()
                }
            } catch (e: SQLiteException) {
                isExist = false
            }
            return isExist
        }
    }

}