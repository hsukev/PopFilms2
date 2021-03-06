package com.urbanutility.jerye.popfilms2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import java.util.Locale;

/**
 * Created by jerye on 10/28/2017.
 */

public class LanguageDialog extends DialogFragment {
    private SharedPreferences sharedPreferences;
    private NumberPicker picker;
    public LanguageDialog() {

    }

    public static LanguageDialog newInstance() {
        Bundle bundle = new Bundle();
        LanguageDialog dialog = new LanguageDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        picker = new NumberPicker(getContext());
        picker.setLayoutParams(layoutParams);
        picker.setWrapSelectorWheel(true);
        picker.setMinValue(0);
        picker.setMaxValue(123);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//        picker.setDisplayedValues(LanguageCode.languages);
        picker.setValue(sharedPreferences.getInt("language",42));
        Log.d("LanguageDialog", Locale.getISOCountries().toString());
        Log.d("LanguageDialog", Locale.getISOLanguages().toString());

        FrameLayout dialogView = new FrameLayout(getContext());
        dialogView.addView(picker);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Language");
        builder.setMessage("Contents such as Trailer and Reviews might not be available for some languages");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sharedPreferences.edit().putInt("language",picker.getValue()).apply();
            }
        });
        builder.setView(dialogView);
        return builder.create();
    }






}
