package com.mazhar.diceclub;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mazhar.customnavigationdrawer2.data.MenuItem;
import com.mazhar.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class NavigationBarActivity extends BaseActivity {

    SNavigationDrawer sNavigationDrawer;
    int color1=0;
    Class fragmentClass;
    public static Fragment fragment;
    LinearLayout llNavigationdraw;
    @Override
    public void initialize() {
        llNavigationdraw = (LinearLayout) inflater.inflate(R.layout.navigation, null);
        llBody.addView(llNavigationdraw, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }

        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Play", R.drawable.news_bg));
        menuItems.add(new MenuItem("Withdraw", R.drawable.news_bg));
//        menuItems.add(new MenuItem("Feed", R.drawable.feed_bg));
//        menuItems.add(new MenuItem("Messages", R.drawable.message_bg));
//        menuItems.add(new MenuItem("Music", R.drawable.music_bg));
        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass = LeagueFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }


        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position " + position);

                switch (position) {
                    case 0: {
                        color1 = R.color.red;
                        fragmentClass = LeagueFragment.class;
                        break;
                    }
                    case 1: {
                        color1 = R.color.orange;
                        fragmentClass = WithdrawFragment.class;
                        break;
                    }
//                    case 2: {
//                        color1 = R.color.green;
//                        fragmentClass = MessagesFragment.class;
//                        break;
//                    }
//                    case 3: {
//                        color1 = R.color.blue;
//                        fragmentClass = MusicFragment.class;
//                        break;
//                    }

                }
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening() {

                    }

                    @Override
                    public void onDrawerClosing() {
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State " + newState);
                    }
                });
            }
        });

    }
}
