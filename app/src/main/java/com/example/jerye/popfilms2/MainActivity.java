package com.example.jerye.popfilms2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;

import com.example.jerye.popfilms2.remote.MoviesService;
import com.example.jerye.popfilms2.util.Utils;

import butterknife.BindView;

public class MainActivity extends BaseActivity{

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager pager;



    MoviesService moviesService;
    String TAG = "MainActivity.java";
    SimpleFragmentStatePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startUpAnimation();

        adapter = new SimpleFragmentStatePagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setupWithViewPager(pager);
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
        Log.d(TAG, "options menu");
        return true;

    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        Log.d("test", "onEnterAnimation");
    }



    public class SimpleFragmentStatePagerAdapter extends FragmentStatePagerAdapter{
        public SimpleFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "popular";
                case 1:
                    return "top rated";
                default:
                    return "upcoming";
            }
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return MoviesFragment.newInstance("popular");
                case 1:
                    return MoviesFragment.newInstance("top_rated");
                default:
                    return MoviesFragment.newInstance("upcoming");
            }
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




}
