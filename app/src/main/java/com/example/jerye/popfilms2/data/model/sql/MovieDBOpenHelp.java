package com.example.jerye.popfilms2.data.model.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jerye on 9/25/2017.
 */

public class MovieDBOpenHelp extends SQLiteOpenHelper {
    public static final String MOVIE_DATABASE_NAME = "movie.db";
    public static final int MOVIE_DATABASE_VERSION = 1;

    public MovieDBOpenHelp(Context context){
        super(context, MOVIE_DATABASE_NAME, null, MOVIE_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
