package com.urbanutility.jerye.popfilms2;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.urbanutility.jerye.popfilms2.util.Utils;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager pager;
    @BindView(R.id.blank)
    TextView blank;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    private String movieTvToggle = "movie";
    private String toggledThirdTab = "upcoming";
    String TAG = "MainActivity.java";
    Toast toast;
    SimpleFragmentStatePagerAdapter adapter;
    Window window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setToggle();
        startUpAnimation();
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
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
                    return getString(R.string.tab_popular);
                case 1:
                    return getString(R.string.tab_top_rated);
                default:
                    return isMovie(movieTvToggle)? getString(R.string.tab_upcoming): getString(R.string.tab_on_the_air);
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
                    return MoviesFragment.newInstance(getString(R.string.query_popular), movieTvToggle);
                case 1:
                    return MoviesFragment.newInstance(getString(R.string.query_top_rated), movieTvToggle);
                default:
                    return MoviesFragment.newInstance(toggledThirdTab, movieTvToggle);
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
        getToggledTitle().setTranslationY(-height);
        getToggleTv().setTranslationY(-height);
        getAbout().setTranslationY(-height);

        getToolbar().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(300).start();

        getAppTitle().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(400);

        getToggledTitle().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(500);
        getToggleTv().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(600);

        getAbout().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(700);
    }

    public void setToggle() {
        getToggleTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (movieTvToggle) {

                    case "movie":
                        view.setBackground(getDrawable(R.drawable.ic_local_movies_black_24dp));
                        movieTvToggle = getString(R.string.general_tv);
                        toggledThirdTab = getString(R.string.query_on_the_air);
                        getToggledTitle().setText(getString(R.string.title_tv_shows));
                        adapter.notifyDataSetChanged();

                        window.setStatusBarColor(MainActivity.this.getResources().getColor(R.color.colorAccentDark));

                        toast.cancel();
                        toast = Toast.makeText(MainActivity.this, getString(R.string.toast_tv_shows), Toast.LENGTH_SHORT);
                        toast.show();
                        revealTv();

                        break;
                    case "tv":
                        view.setBackground(getDrawable(R.drawable.ic_tv_black_24dp));
                        movieTvToggle = getString(R.string.general_movie);
                        toggledThirdTab = getString(R.string.query_upcoming);
                        getToggledTitle().setText(getString(R.string.title_movies));
                        adapter.notifyDataSetChanged();

                        window.setStatusBarColor(MainActivity.this.getResources().getColor(R.color.colorPrimaryDark));

                        toast.cancel();
                        toast = Toast.makeText(MainActivity.this, getString(R.string.toast_movies), Toast.LENGTH_SHORT);
                        toast.show();
                        revealMovie();
                        break;
                }

            }
        });
    }

    public void revealTv() {
        int x = (toggleTv.getLeft() + toggleTv.getRight()) / 2;
        int y = (toggleTv.getTop() + toggleTv.getBottom()) / 2;
        int radius = (int) Math.hypot(appbar.getLeft() - x, appbar.getBottom() - y);
        Animator anim = ViewAnimationUtils.createCircularReveal(blank, x, y, 0, radius);

        blank.setVisibility(View.VISIBLE);
        anim.start();
    }

    public void revealMovie() {
        int x = (toggleTv.getLeft() + toggleTv.getRight()) / 2;
        int y = (toggleTv.getTop() + toggleTv.getBottom()) / 2;
        int radius = (int) Math.hypot(appbar.getLeft() - x, appbar.getBottom() - y);
        Animator anim = ViewAnimationUtils.createCircularReveal(blank, toggleTv.getRight(), y, radius, 0);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                blank.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        anim.start();
    }

    public boolean isMovie(String movieTvToggle){
        return movieTvToggle.equals(getString(R.string.general_movie));
    }

    @Override
    protected void onPause() {
        toast.cancel();
        super.onPause();
    }
}
