package com.makan.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.makan.R;
import com.makan.app.app.AppState;
import com.makan.app.fragment.BaseFragment;
import com.makan.app.fragment.BestDealsFragment;
import com.makan.app.fragment.BusinessFragment;
import com.makan.app.fragment.CategoryFragment;
import com.makan.app.fragment.ContactUsFragment;
import com.makan.app.fragment.DealersFragment;
import com.makan.app.fragment.FeedbackFragment;
import com.makan.app.fragment.HomeFragment;
import com.makan.app.fragment.MortgageFragment;
import com.makan.app.fragment.NewsFragment;
import com.makan.app.fragment.NotificationFragment;
import com.makan.app.fragment.SettingsFragment;
import com.makan.app.fragment.WishListFragment;
import com.makan.app.util.Utility;

public class HomeActivity extends BaseActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FloatingActionButton fab;


    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_OVERVIEW = "overview";
    private static final String TAG_CATEGORY = "category";
    private static final String TAG_DEALERS = "dealers";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_WISH_LIST = "wish_list";
    private static final String TAG_MORTGAGE = "mortgage";
    private static final String TAG_BUSINESS = "business";
    private static final String TAG_NEWS = "news";
    private static final String TAG_FIND_DEALS = "find_deals";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_FEEDBACK = "feedback";
    private static final String TAG_NOTIFICATION = "notification";
    private static final String TAG_CONTACT_US = "contact_us";

    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadFragment();
        }
    }


    private void loadFragment() {
        selectNavMenu();
        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            toggleFab();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {

                Fragment fragment = getFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        mHandler.post(mPendingRunnable);

        toggleFab();
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private BaseFragment getFragment() {

        switch (navItemIndex) {

            case 0:

                return new HomeFragment();

            case 1:
                return new CategoryFragment();

            case 2:
                return new DealersFragment();

            case 3:
                return new WishListFragment();

            case 4:
                return new MortgageFragment();

            case 5:
                return new SettingsFragment();

            case 6:
                return new FeedbackFragment();

            case 7:
                return new BusinessFragment();

            case 8:
                return new BestDealsFragment();

            case 9:
                return new NewsFragment();


            case 10:
                return new NotificationFragment();

            case 11:
                return new ContactUsFragment();

            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;

                    /*case R.id.nav_overview:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_OVERVIEW;
                        break;*/

                    case R.id.nav_category:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_CATEGORY;
                        break;

                    case R.id.nav_dealers:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_DEALERS;
                        break;


                    case R.id.nav_wishlist:

                        if(AppState.getInstance().getUserId()!=null&&AppState.getInstance().getUserId().length()>0){

                            navItemIndex = 3;
                            CURRENT_TAG = TAG_WISH_LIST;
                        }else{
                            new Utility().moveToActivity(HomeActivity.this,LoginActivity.class,null);
                        }


                        break;

                    case R.id.nav_mortgage:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_MORTGAGE;
                        break;

                    case R.id.nav_settings:

                        if(AppState.getInstance().getUserId()!=null&&AppState.getInstance().getUserId().length()>0){

                            navItemIndex = 5;
                            CURRENT_TAG = TAG_SETTINGS;

                        }else{
                            new Utility().moveToActivity(HomeActivity.this,LoginActivity.class,null);
                        }

                        break;

                    case R.id.nav_feedback:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_FEEDBACK;
                        break;

                    case R.id.nav_business:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_BUSINESS;
                        break;

                    case R.id.nav_find_deals:
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_FIND_DEALS;
                        break;

                    case R.id.nav_news:
                        navItemIndex = 9;
                        CURRENT_TAG = TAG_NEWS;
                        break;

                    case R.id.nav_notification:
                        navItemIndex = 10;
                        CURRENT_TAG = TAG_NOTIFICATION;
                        break;

                    case R.id.nav_contact_us:
                        navItemIndex = 11;
                        CURRENT_TAG = TAG_CONTACT_US;
                        break;

                    case R.id.nav_logout:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;


                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadFragment();
                return;
            }
        }

        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    private void toggleFab() {
       /* if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();*/
    }
}