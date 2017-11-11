package com.example.jerye.popfilms2.data.model;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseArray;

import java.util.List;

/**
 * Created by jerye on 8/22/2017.
 *
 * Referenced @Link https://stackoverflow.com/questions/8405661/is-it-possible-to-change-the-text-color-in-a-string-to-multiple-colors-in-java
 */

public class GenreScheme {

    static int[] genreColors = {
            Color.parseColor("#D32F2F"),
            Color.parseColor("#F44336"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#455A64"),
            Color.parseColor("#795548"),
            Color.parseColor("#009688"),
            Color.parseColor("#4CAF50"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#5D4037"),
            Color.parseColor("#AD1457"),
            Color.parseColor("#03A9F4"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#4A148C"),
            Color.parseColor("#388E3C"),
            Color.parseColor("#455A64"),
            Color.parseColor("#827717"),
            Color.parseColor("#795548"),

    };

    public static SpannableStringBuilder getGenre(List<Integer> list) {
        SparseArray<String> genre = new SparseArray<>();
        genre.append(28, "Action");
        genre.append(12, "Adventure");
        genre.append(16, "Animation");
        genre.append(35, "Comedy");
        genre.append(80, "Crime");
        genre.append(99, "Documentary");
        genre.append(18, "Drama");
        genre.append(10751, "Family");
        genre.append(14, "Fantasy");
        genre.append(36, "History");
        genre.append(27, "Horror");
        genre.append(10402, "Music");
        genre.append(9648, "Mystery");
        genre.append(10749, "Romance");
        genre.append(878, "Science Fiction");
        genre.append(10770, "TV Movie");
        genre.append(53, "Thriller");
        genre.append(10752, "War");
        genre.append(37, "Western");

//        StringBuilder builder = new StringBuilder();
//        int size = list.size();
//        for(int i = 0; i < size-1; i++){
//            builder.append(genre.get(list.get(i))).append("\n");
//        }
//        builder.append(genre.get(list.get(size-2)));
//
//        Log.d("GenereScheme", builder.toString());

        Log.d("genre", genre.toString());
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            String item = genre.get(list.get(i));
            SpannableString spannableString = new SpannableString(genre.get(list.get(i)));
            spannableString.setSpan(new ForegroundColorSpan(genreColors[genre.indexOfValue(item)]), 0, item.length(), 0);
            builder.append(spannableString).append("\n");
        }
        builder.append(genre.get(list.get(size - 1)));
        return builder;
    }




}
