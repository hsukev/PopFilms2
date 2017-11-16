package com.example.jerye.popfilms2;

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

import com.example.jerye.popfilms2.util.Utils;

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
    @BindView(R.id.toolbar_toggled_title)
    TextView toggledTitle;

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
                    return "popular";
                case 1:
                    return "top rated";
                default:
                    return isMovie(movieTvToggle)? "upcoming": "on the air";
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
                switch (movieTvToggle) {

                    case "movie":
                        view.setBackground(getDrawable(R.drawable.ic_local_movies_black_24dp));
                        movieTvToggle = "tv";
                        toggledThirdTab = "on_the_air";
                        toggledTitle.setText("tv shows");
                        adapter.notifyDataSetChanged();

                        window.setStatusBarColor(MainActivity.this.getResources().getColor(R.color.colorAccentDark));

                        toast.cancel();
                        toast = Toast.makeText(MainActivity.this, "Showing TV SHOWS", Toast.LENGTH_SHORT);
                        toast.show();
                        revealTv();

                        break;
                    case "tv":
                        view.setBackground(getDrawable(R.drawable.ic_tv_black_24dp));
                        movieTvToggle = "movie";
                        toggledThirdTab = "upcoming";
                        toggledTitle.setText("movies");
                        adapter.notifyDataSetChanged();

                        window.setStatusBarColor(MainActivity.this.getResources().getColor(R.color.colorPrimaryDark));

                        toast.cancel();
                        toast = Toast.makeText(MainActivity.this, "Showing MOVIES", Toast.LENGTH_SHORT);
                        toast.show();
                        revealMovie();
                        break;
                }

            }
        });
    }

    public void revealTv() {
        int x = (box1.getLeft() + box1.getRight()) / 2;
        int y = (box1.getTop() + box1.getBottom()) / 2;
        int radius = (int) Math.hypot(appbar.getLeft() - x, appbar.getBottom() - y);
        Animator anim = ViewAnimationUtils.createCircularReveal(blank, x, y, 0, radius);

        blank.setVisibility(View.VISIBLE);
        anim.start();
    }

    public void revealMovie() {
        int x = (box1.getLeft() + box1.getRight()) / 2;
        Log.d("Main", box1.getLeft() +"-"+ box1.getRight());
        int y = (box1.getTop() + box1.getBottom()) / 2;
        int radius = (int) Math.hypot(appbar.getLeft() - x, appbar.getBottom() - y);
        Animator anim = ViewAnimationUtils.createCircularReveal(blank, box1.getRight(), y, radius, 0);
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
        return movieTvToggle.equals("movie");
    }

    @Override
    protected void onPause() {
        toast.cancel();
        super.onPause();
    }
}
