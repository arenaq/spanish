package org.arenaq.czu.spanelstina.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import org.arenaq.czu.spanelstina.database.model.Lecture;
import org.arenaq.czu.spanelstina.database.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arenaq on 9.4.2015.
 */
public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String EXTERNAL_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final String DATABASE_NAME = "spanish.sqlite3";
    private static final String TABLE_LECTURES = "lectures";
    private static final String TABLE_SQLITE_SEQUENCE = "sqlite_sequence";
    private static final String TABLE_WORDS = "words";
    private static final String TABLE_WORDS_TO_LECTURES = "wordsToLectures";

    private Context context;
    private SQLiteDatabase db;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.db = SQLiteDatabase.openDatabase(EXTERNAL_PATH + "/" + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ;
    }

    public Word getWord(int id) {
        String query = "SELECT  * FROM " + TABLE_WORDS + " WHERE id='" + id + "'";

        Cursor cursor = db.rawQuery(query, null);
        Word word = null;

        if (cursor.moveToFirst()) {
            word = new Word();
            word.setId(Integer.parseInt(cursor.getString(0)));
            word.setSpanish(cursor.getString(1));
            word.setCzech(cursor.getString(2));
        }

        return word;
    }

    public List<Word> getAllWords() {
        List<Word> words = new ArrayList<Word>();
        String query = "SELECT  * FROM " + TABLE_WORDS;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setId(Integer.parseInt(cursor.getString(0)));
                word.setSpanish(cursor.getString(1));
                word.setCzech(cursor.getString(2));
                words.add(word);
            } while (cursor.moveToNext());
        }

        return words;
    }

    private List<Word> getWordsOfLecture(int lectureId) {
        List<Word> words = new ArrayList<Word>();
        String query = "SELECT * FROM " + TABLE_WORDS_TO_LECTURES + " WHERE lectureId='" + lectureId + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Word word = getWord(Integer.parseInt(cursor.getString(0)));
                words.add(word);
            } while (cursor.moveToNext());
        }

        return words;
    }

    public Lecture getLecture(int id) {
        String query = "SELECT * FROM " + TABLE_LECTURES + " WHERE lectureNumber='" + id + "'";

        Cursor cursor = db.rawQuery(query, null);
        Lecture lecture = null;

        if (cursor.moveToFirst()) {
            lecture = new Lecture();
            lecture.setId(id);
            lecture.setName(cursor.getString(1));
            lecture.setWords(getWordsOfLecture(id));
        }

        return lecture;
    }

    public List<Lecture> getAllLectures() {
        List<Lecture> lectures = new ArrayList<Lecture>();
        String query = "SELECT * FROM " + TABLE_LECTURES;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Lecture lecture = getLecture(Integer.parseInt(cursor.getString(0)));
                lectures.add(lecture);
            } while (cursor.moveToNext());
        }

        return lectures;
    }
}
