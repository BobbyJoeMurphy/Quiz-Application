package com.example.mub2quizapp.activities.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Questions"
        private const val ID = "id"

        //region Questions
        private const val QUESTIONS_TABLE = "tblQuestions"
        private const val QUESTION = "Question"
        private const val QUESTION_BANK_ID = "question_bank_id"
        private const val CORRECT_ANSWER = "correct_answer"
        private const val ANSWER1 = "answer1"
        private const val ANSWER2 = "answer2"
        private const val ANSWER3 = "answer3"
        private const val ANSWER4 = "answer4"
        //endregion

        //region Question Banks
        private const val BANKS_TABLE = "tblQuestionBanks"
        private const val NAME = "name"
        //endregion

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuestionTable =
            ("CREATE TABLE $QUESTIONS_TABLE ($ID integer primary key, $CORRECT_ANSWER integer, " +
                    "$QUESTION_BANK_ID integer, $QUESTION text, $ANSWER1 text, " +
                    "$ANSWER2 text, $ANSWER3 text, $ANSWER4 text)")

        val createBanksTable = ("CREATE TABLE $BANKS_TABLE ($ID integer primary key, $NAME text)")

        db?.execSQL(createQuestionTable)
        db?.execSQL(createBanksTable)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) = db?.let {
        it.execSQL("DROP TABLE IF EXISTS $QUESTIONS_TABLE")
        it.execSQL("DROP TABLE IF EXISTS $BANKS_TABLE")
        onCreate(db)
    } ?: onCreate(db)

    //region Questions
    fun insertQuestions(std: QuestionsModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(QUESTION_BANK_ID, std.bankId)
        contentValues.put(QUESTION, std.question)
        contentValues.put(CORRECT_ANSWER, std.correctAnswer)
        contentValues.put(ANSWER1, std.answer1)
        contentValues.put(ANSWER2, std.answer2)
        contentValues.put(ANSWER3, std.answer3)
        contentValues.put(ANSWER4, std.answer4)
        val success = db.insert(QUESTIONS_TABLE, null, contentValues)
        db.close()
        return success
    }

    fun getQuestionsByBankId(questionBankId: Int): ArrayList<QuestionsModel>? {
        val stdList: ArrayList<QuestionsModel> = ArrayList()
        val selectQuery =
            "SELECT * FROM $QUESTIONS_TABLE WHERE $QUESTIONS_TABLE.$QUESTION_BANK_ID = $questionBankId"
        val db = this.readableDatabase

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
            var id: Int
            var bankId: Int
            var correctAnswer: Int
            var question: String
            var answer1: String
            var answer2: String
            var answer3: String
            var answer4: String

            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
                    bankId = cursor.getInt(cursor.getColumnIndexOrThrow(QUESTION_BANK_ID))
                    correctAnswer = cursor.getInt(cursor.getColumnIndexOrThrow(CORRECT_ANSWER))
                    question = cursor.getString(cursor.getColumnIndexOrThrow(QUESTION))
                    answer1 = cursor.getString(cursor.getColumnIndexOrThrow(ANSWER1))
                    answer2 = cursor.getString(cursor.getColumnIndexOrThrow(ANSWER2))
                    answer3 = cursor.getString(cursor.getColumnIndexOrThrow(ANSWER3))
                    answer4 = cursor.getString(cursor.getColumnIndexOrThrow(ANSWER4))

                    val std = QuestionsModel(
                        id = id,
                        bankId = bankId,
                        correctAnswer = correctAnswer,
                        question = question,
                        answer1 = answer1,
                        answer2 = answer2,
                        answer3 = answer3,
                        answer4 = answer4
                    )
                    stdList.add(std)
                } while (cursor.moveToNext())
            }

            return stdList
        } catch (e: Exception) {
            e.printStackTrace()
            cursor?.close()
            db.execSQL(selectQuery)
            return null
        } finally {
            cursor?.close()
        }
    }

    fun deleteQuestionById(id: Int): Int {
        val db = this.writableDatabase
        val success = db.delete(QUESTIONS_TABLE, "${ID}=?", arrayOf(id.toString()))
        db.close()
        return success
    }
    //endregion

    //region Question Banks
    fun insertQuestionBank(questionBank: QuestionBank): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, questionBank.id)
        contentValues.put(NAME, questionBank.name)
        val success = db.insert(BANKS_TABLE, null, contentValues)
        db.close()
        return success
    }

    fun deleteQuestionBankById(id: Int): Int {
        val db = this.writableDatabase
        val success = db.delete(BANKS_TABLE, "${ID}=?", arrayOf(id.toString()))
        db.close()
        return success
    }

    fun getQuestionBankById(bankId: Int): QuestionBank? {
        val bankList: ArrayList<QuestionBank> = ArrayList()
        val selectQuery =
            "SELECT * FROM $BANKS_TABLE WHERE $BANKS_TABLE.$ID = $bankId"
        val db = this.readableDatabase

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
            var id: Int
            var name: String

            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
                    name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
                    bankList.add(QuestionBank(id, name))
                } while (cursor.moveToNext())
            }
            return bankList.first()
        } catch (e: Exception) {
            cursor?.close()
            e.printStackTrace()
            return null
        } finally {
            cursor?.close()
        }
    }

    fun getAllQuestionBanks(): ArrayList<QuestionBank>? {
        val bankList: ArrayList<QuestionBank> = ArrayList()
        val selectQuery = "SELECT * FROM $BANKS_TABLE"
        val db = this.readableDatabase

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
            var id: Int
            var name: String

            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
                    name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
                    bankList.add(QuestionBank(id, name))
                } while (cursor.moveToNext())
            }
            return ArrayList(bankList.reversed())
        } catch (e: Exception) {
            cursor?.close()
            e.printStackTrace()
            return null
        } finally {
            cursor?.close()
        }
    }
    //endregion
}

