package com.example.jerye.popfilms2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.jerye.popfilms2.util.Utils;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager pager;

    private String movieTvToggle = "movie";
    String TAG = "MainActivity.java";
    SimpleFragmentStatePagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToggle();
        startUpAnimation();

        adapter = new SimpleFragmentStatePagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setupWithViewPager(pager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setSelectedTabIndicatorColor(Color.WHITE);
        tabs.setTabTextColors(Color.parseColor("#99FFFFFF"), Color.WHITE);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_language:
                LanguageDialog picker = LanguageDialog.newInstance();
                picker.show(getSupportFragmentManager(), "full review");
                return true;
            case R.id.menu_about:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setView(R.layout.about_dialog);
                builder.show();
                return true;
            default:
                return true;
        }

    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        Log.d("test", "onEnterAnimation");
    }


    public class SimpleFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
        public SimpleFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "popular";
                case 1:
                    return "top rated";
                default:
                    return "upcoming";
            }
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MoviesFragment.newInstance("popular", movieTvToggle);
                case 1:
                    return MoviesFragment.newInstance("top_rated", movieTvToggle);
                default:
                    return MoviesFragment.newInstance("upcoming", movieTvToggle);
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public void startUpAnimation() {
        int height = Utils.dpToPx(56);
        getToolbar().setTranslationY(-height);
        getAppTitle().setTranslationY(-height);
        getBox1().setTranslationY(-height);
        getBox2().setTranslationY(-height);

        getToolbar().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(300);

        getAppTitle().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(400);
        getBox1().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(500);

        getBox2().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(600)
                .start();
    }

    public void setToggle() {
        getBox1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(movieTvToggle){
                    case "movie":
                        view.setBackground(getDrawable(R.drawable.ic_local_movies_black_24dp));
                        movieTvToggle = "tv";
                        adapter.notifyDataSetChanged();
                        break;
                    case "tv":
                        view.setBackground(getDrawable(R.drawable.ic_tv_black_24dp));
                        movieTvToggle = "movie";
                        adapter.notifyDataSetChanged();
                        break;
                }

            }
        });
    }


}
