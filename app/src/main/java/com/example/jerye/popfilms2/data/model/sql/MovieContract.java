package com.example.jerye.popfilms2.data.model.sql;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jerye on 12/4/2016.
 *
 * FilmContract defines all the necessary variables and methods for any communication with the LOCAL CONTENT DATABASE:
 * INCLUES:
 * 1. SQLite database column names
 * 2. Methods to build content URI for general, detailed, favorites, review, and trailer tables
 *
 */

public class MovieContract {
    // Log tag
    private static String LOG_TAG = MovieContract.class.getSimpleName();

    // FilmContracts defines all variables needed for SQLiteDB
    public static final String CONTENT_AUTHORITY = "com.example.android.popfilms";
    public static final String SCHEMA = "content://";


    // The base of all Uri in which apps will use to query.
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEMA + CONTENT_AUTHORITY);

    // Path to our movie database
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_FAVORITES = "favorites";

    public static final class FilmEntry implements BaseColumns {

        // Table Uris
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
        public static final Uri FAVORITES_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        // Table nameS
        public static final String FILM_TABLE_NAME = "film";
        public static final String REVIEW_TABLE_NAME ="review";
        public static final String TRAILER_TABLE_NAME = "trailer";
        public static final String FAVORITES_TABLE_NAME = "favorites";


        // Film Table Columns
        // COLUMN_ID already defined inside BaseColumns class "_id"
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_SPECIFIC_ID = "id";
        public static final String COLUMN_FAVORITE = "favorite";

        // Review Table Columns
        public static final String COLUMN_REVIEW_AUTHOR = "author";
        public static final String COLUMN_REVIEW_CONTENT = "content";


        // Trailer Table Columns
        public static final String COLUMN_TRAILER_NAME = "name";
        public static final String COLUMN_TRAILER_KEY = "key";

        // Uri types (for getType in ContentProvider MIME type matcher)
        // ie. vnd.android.cursor.dir/com.example.android.popfilms/movie for .dir type
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;


        // Build film data table Uri with unique _id identifier for each row
        public static Uri buildFilmUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getMovieIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildFilmUriWithId(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static Uri buildFavoritesUriWithId(String id){
            return FAVORITES_URI.buildUpon().appendPath(id).build();
        }


    }
}
