package com.example.jerye.popfilms2.data.model.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.jerye.popfilms2.data.model.sql.MovieContract.FilmEntry.FAVORITES_TABLE_NAME;
import static com.example.jerye.popfilms2.data.model.sql.MovieContract.FilmEntry.FILM_TABLE_NAME;
import static com.example.jerye.popfilms2.data.model.sql.MovieContract.FilmEntry.REVIEW_TABLE_NAME;
import static com.example.jerye.popfilms2.data.model.sql.MovieContract.FilmEntry.TRAILER_TABLE_NAME;

/**
 * Created by jerye on 12/4/2016.
 * FilmDBHelper is where the SQLite databases are created.
 * Also clears all DB tables when version number is changed
 */

// FilmDBHelper is used to help us create the database tables and manage its versions.
public class MovieDBHelper extends SQLiteOpenHelper {
    // Log tag
    private String LOG_TAG = MovieDBHelper.class.getSimpleName();

    // Constructor for our class
    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Name and version of database
    private static final int DATABASE_VERSION = 8;
    static final String DATABASE_NAME = "film.db";


    // This is where the database table is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        // film Table containing 20 films' general information
        final String SQL_CREATE_FILM_TABLE =
                "CREATE TABLE " + FILM_TABLE_NAME + "("
                        + MovieContract.FilmEntry._ID + " INT AUTO_INCREMENT,"
                        + MovieContract.FilmEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL,"
                        + MovieContract.FilmEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL,"
                        + MovieContract.FilmEntry.COLUMN_OVERVIEW + " TEXT NOT NULL,"
                        + MovieContract.FilmEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL,"
                        + MovieContract.FilmEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "
                        + MovieContract.FilmEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, "
                        + MovieContract.FilmEntry.COLUMN_SPECIFIC_ID + " REAL NOT NULL, "
                        + MovieContract.FilmEntry.COLUMN_FAVORITE + " REAL"
                        + ");";
        db.execSQL(SQL_CREATE_FILM_TABLE);

        // Table for review of each film
        final String SQL_CREATE_REVIEW_TABLE =
                "CREATE TABLE " + REVIEW_TABLE_NAME + "("
                        + MovieContract.FilmEntry.COLUMN_SPECIFIC_ID + " TEXT NOT NULL,"
                        + MovieContract.FilmEntry.COLUMN_REVIEW_AUTHOR + " TEXT NOT NULL,"
                        + MovieContract.FilmEntry.COLUMN_REVIEW_CONTENT + " TEXT NOT NULL"
                        + ");";
        db.execSQL(SQL_CREATE_REVIEW_TABLE);

        // Table for trailer of each film
        final String SQL_CREATE_TRAILER_TABLE =
                "CREATE TABLE " + TRAILER_TABLE_NAME + "("
                        + MovieContract.FilmEntry.COLUMN_SPECIFIC_ID + " TEXT NOT NULL,"
                        + MovieContract.FilmEntry.COLUMN_TRAILER_NAME + " TEXT NOT NULL,"
                        + MovieContract.FilmEntry.COLUMN_TRAILER_KEY + " TEXT NOT NULL"
                        + ");";
        db.execSQL(SQL_CREATE_TRAILER_TABLE);


        final String SQL_CREATE_FAVORITES_TABLE =
                "CREATE TABLE " + FAVORITES_TABLE_NAME + "("
                        + MovieContract.FilmEntry._ID + " INT AUTO_INCREMENT,"
                        + MovieContract.FilmEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL,"
                        + MovieContract.FilmEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL,"
                        + MovieContract.FilmEntry.COLUMN_OVERVIEW + " TEXT NOT NULL,"
                        + MovieContract.FilmEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL,"
                        + MovieContract.FilmEntry.COLUMN_POSTER_PATH + " TEXT, "
                        + MovieContract.FilmEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, "
                        + MovieContract.FilmEntry.COLUMN_SPECIFIC_ID + " REAL NOT NULL, "
                        + MovieContract.FilmEntry.COLUMN_FAVORITE + " REAL"
                        + ");";
        db.execSQL(SQL_CREATE_FAVORITES_TABLE);

    }

    // Update when database version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db);
    }

    public void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + FILM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + REVIEW_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TRAILER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITES_TABLE_NAME);

        onCreate(db);
    }
}
