package com.example.jerye.popfilms2.data.model;

import android.util.Log;
import android.util.SparseArray;

import java.util.List;

/**
 * Created by jerye on 8/22/2017.
 *
 *
 */

public class GenreScheme {

    public static String getGenre(List<Integer> list) {
        SparseArray<String> genre = new SparseArray<>();
        genre.append(28, "Action");
        genre.append(12,"Adventure");
        genre.append(16,"Animation");
        genre.append(35,"Comedy");
        genre.append(80,"Crime");
        genre.append(99,"Documentary");
        genre.append(18,"Drama");
        genre.append(10751,"Family");
        genre.append(14,"Fantasy");
        genre.append(36,"History");
        genre.append(27,"Horror");
        genre.append(10402,"Music");
        genre.append(9648,"Mystery");
        genre.append(10749,"Romance");
        genre.append(878,"Science Fiction");
        genre.append(10770,"TV Movie");
        genre.append(53,"Thriller");
        genre.append(10752,"War");
        genre.append(37,"Western");

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < list.size(); i++){
            builder.append(genre.get(i));
            if(i!=list.size()-1) builder.append("\n");
        }

        Log.d("GenereScheme", builder.toString());

        return builder.toString();
    }
}
