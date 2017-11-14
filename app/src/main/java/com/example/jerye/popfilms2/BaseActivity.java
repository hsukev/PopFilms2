package com.example.jerye.popfilms2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerye on 8/20/2017.
 */

public class BaseActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.popfilms_title)
    TextView title;
    @BindView(R.id.test_box)
    ImageView box1;
    @BindView(R.id.test_box2)
    ImageView box2;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
    }

    public void bindViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TextView getAppTitle() {
        return title;
    }

    public ImageView getBox1() {
        return box1;
    }

    public ImageView getBox2() {
        return box2;
    }

    public void about(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setView(R.layout.about_dialog);
        builder.show();
    }

}
