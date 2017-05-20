package com.portfolio.arshan.portfolio;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements MyAdapter.ClickListener{

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    //public static final String PREF_FILE_NAME = "testpref";
    //public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    //private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        myAdapter = new MyAdapter(getActivity(), getData());
        myAdapter.setClickListener(this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<Information> getData() {
        List<Information> data = new ArrayList<>();
        int[] icons = {R.drawable.experience, R.drawable.education, R.drawable.projects, R.drawable.skills, R.drawable.awards, R.drawable.bbc};
        String[] titles = {"Experience", "Education", "Projects", "Key Skills", "Honors & Awards", "BBC News"};
        for (int i = 0; i < titles.length && i < icons.length; i++) {
            Information current = new Information();
            current.iconId = icons[i];
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                /*if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }*/
                getActivity().invalidateOptionsMenu();
                //Toast.makeText(NavigationDrawerFragment.this,"Opened",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
                //Toast.makeText(NavigationDrawerFragment.this,"Closed",Toast.LENGTH_SHORT).show();
            }

            //3rd type navigation drawer(fade action bar)
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                ((MainActivity) getActivity()).onDrawerSlide(slideOffset);
                /*if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }*/
            }
        };

        /*if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }*/
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();

            }
        });

    }

    /*public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }*/

    @Override
    public void itemClicked(View view, int position) {
        if(position == 0){
            startActivity(new Intent(getActivity(), Experience.class));
        } else if(position == 1) {
            startActivity(new Intent(getActivity(), Education.class));
        } else if (position == 2){
            startActivity(new Intent(getActivity(), Projects.class));
        } else if (position == 3){
            startActivity(new Intent(getActivity(), SkillsTools.class));
        } else if (position == 4){
            startActivity(new Intent(getActivity(), HonorsAwards.class));
        } else if (position == 5){
            boolean mobileNwInfo = false;
            ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            try {
                mobileNwInfo = conMgr.getActiveNetworkInfo().isConnected();
            } catch (NullPointerException e) { mobileNwInfo = false; }
            if(mobileNwInfo == false) {
                final boolean mnwI = mobileNwInfo;
                Snackbar snackbar = Snackbar
                        .make(mDrawerLayout, "Plz enable WiFi/Mobile data", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(mnwI == true) {
                                    Snackbar snackbar1 = Snackbar.make(mDrawerLayout, "Connected! Enjoy news", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                } else {
                                    Snackbar snackbar = Snackbar.make(mDrawerLayout, "Sorry! Not yet connected", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        });

                snackbar.show();
            } else {
                Toast.makeText(getActivity(), "Got bored of my Profile!! Read news", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), BBCNews.class));}
        }
    }
}
