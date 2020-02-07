package com.makan.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
import com.makan.R;
import com.makan.app.app.AppState;
import com.makan.app.draweritems.DrawerAdapter;
import com.makan.app.draweritems.DrawerClickListener;
import com.makan.app.draweritems.DrawerModelClass;
import com.makan.app.fragment.BaseFragment;
import com.makan.app.fragment.BestDealsFragment;
import com.makan.app.fragment.BusinessFragment;
import com.makan.app.fragment.CategoryFragment;
import com.makan.app.fragment.ContactUsFragment;
import com.makan.app.fragment.DealersFragment;
import com.makan.app.fragment.FeedbackFragment;
import com.makan.app.fragment.HomeFragment;
import com.makan.app.fragment.HomeNewFragment;
import com.makan.app.fragment.MortgageFragment;
import com.makan.app.fragment.NewFilterFragment;
import com.makan.app.fragment.NewsFragment;
import com.makan.app.fragment.NotificationFragment;
import com.makan.app.fragment.SettingsFragment;
import com.makan.app.fragment.WishListFragment;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.professional.ProfessionalsActivity;
import com.makan.app.util.Utility;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements DrawerClickListener {

    Button btLanguageChange;
    Button btLanguageChangeAr;
    private NavigationView navigationView;
    private View headerView;
    public static DrawerLayout drawer;
    private Toolbar toolbar;
    private FloatingActionButton fab;
//    private Button bt_languageChange, bt_languageChangeAr;
    private DrawerAdapter adapter;
    private PreferenceManager preferenceManager;
    private LinearLayout ll_proffesionalPackage,ll_agency_Package;
    private ImageView imageProfile;

    //nazil added on navigation drawer
    private ImageView iv_facebook, iv_instagram, iv_twitter;
    private Button bt_arabicButton,bt_login;


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
    private static final String TAG_SEARCH = "search";

    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    ArrayList<DrawerModelClass> drawerItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
           /* setWindowFlag(this, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);*/
        }
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        ll_proffesionalPackage = (LinearLayout) findViewById(R.id.ll_proffesionalPackage);
        ll_agency_Package = (LinearLayout) findViewById(R.id.ll_agency_Package);
        imageProfile = (ImageView) headerView.findViewById(R.id.img_profile);
        Glide.with(this)
                .load("https://ephoneoman.com/ephone/assets/uploads/images.jpeg")
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.placeholder_error)
                .error(R.drawable.error)
                .into(imageProfile);

        bt_login = (Button) headerView.findViewById(R.id.bt_login);
        ll_proffesionalPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(HomeActivity.this,ProfessionalPackageActivity.class);
               startActivity(intent);
            }
        });
        ll_agency_Package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,AgencyPackageActivity.class);
                startActivity(intent);
            }
        });





        btLanguageChange = (Button) headerView.findViewById(R.id.bt_languageChange);
        btLanguageChangeAr = (Button) headerView.findViewById(R.id.bt_languageChangeAr);
        preferenceManager = new PreferenceManager();
        if (preferenceManager.getValue(HomeActivity.this, PrefKey.CURRENT_DATA).equals("ar")) {
            setLocale("ar");
            btLanguageChangeAr.setVisibility(View.GONE);
            btLanguageChange.setVisibility(View.VISIBLE);
        } else {
            setLocale("en");
            btLanguageChangeAr.setVisibility(View.VISIBLE);
            btLanguageChange.setVisibility(View.GONE);

        }

//        if (preferenceManager.getValue(HomeActivity.this, PrefKey.CURRENT_DATA).equals("ar")) {
//            setLocale("ar");
//            btLanguageChangeAr.setVisibility(View.INVISIBLE);
//            btLanguageChange.setVisibility(View.VISIBLE);
//        } else {
//            setLocale("en");
//            btLanguageChangeAr.setVisibility(View.VISIBLE);
//            btLanguageChange.setVisibility(View.INVISIBLE);
//
//        }

        if (AppState.getInstance().isLoginStatus()) {
            bt_login.setText(getResources().getString(R.string.logout));
        } else {
            bt_login.setText(getResources().getString(R.string.login));
        }

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bt_login.getText().equals(getResources().getString(R.string.login))) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 0);

                } else if (bt_login.getText().equals(getResources().getString(R.string.logout))) {
                    showProgressDialog();
                    new PreferenceManager().setValue(HomeActivity.this, PrefKey.USER_DATA, null);
                    AppState.getInstance().setLoginStatus(false);
                    AppState.getInstance().setUserId(null);
                    LoginManager.getInstance().logOut();
                    finish();
                    startActivity(getIntent());
                    dismissProgressDialog();
                }

            }
        });

        btLanguageChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("en");
            }
        });

        btLanguageChangeAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("ar");
            }
        });

        RecyclerView rv_navigationDrawer = (RecyclerView) findViewById(R.id.rv_navigationDrawer);
        rv_navigationDrawer.setLayoutManager(new LinearLayoutManager(this));

        fillDrawerItems();

        adapter = new DrawerAdapter(drawerItems, this, this);
/*      rv_navigationDrawer.setHasFixedSize(true);
        rv_navigationDrawer.setLayoutManager(new LinearLayoutManager(this));
        rv_navigationDrawer.setAdapter(adapter);*/

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        rv_navigationDrawer.setLayoutManager(gridLayoutManager);
        rv_navigationDrawer.setAdapter(adapter);

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles_logged_in);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        setUpNavigationView();
        fillDrawerItems();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadFragment();
        }
    }

    private void setLocale(String LAN) {
        if (!LAN.equals(preferenceManager.getValue(HomeActivity.this, PrefKey.CURRENT_DATA))) {
            preferenceManager.setValue(HomeActivity.this, PrefKey.CURRENT_DATA, LAN);
            Locale locale = new Locale(LAN);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            startActivity(getIntent());
        }
    }

    @Override
    protected void onResume() {
        fillDrawerItems();
        super.onResume();
    }

    private void fillDrawerItems() {


        drawerItems.clear();

        DrawerModelClass drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.home));
        drawerModelClass.setImage(R.drawable.icon_home);
        drawerItems.add(drawerModelClass);

        drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.search));
        drawerModelClass.setImage(R.drawable.icon_search);
        drawerItems.add(drawerModelClass);

        drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.professional));
        drawerModelClass.setImage(R.drawable.icon_pro);
        drawerItems.add(drawerModelClass);

        drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.category));
        drawerModelClass.setImage(R.drawable.icon_cat);
        drawerItems.add(drawerModelClass);

        drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.dealers));
        drawerModelClass.setImage(R.drawable.icon_dealers);
        drawerItems.add(drawerModelClass);

        drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.wishlist));
        drawerModelClass.setImage(R.drawable.icon_wishlist);
        drawerItems.add(drawerModelClass);

        drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.settings));
        drawerModelClass.setImage(R.drawable.icon_tools);
        drawerItems.add(drawerModelClass);

        drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.feedback));
        drawerModelClass.setImage(R.drawable.icon_writeletter);
        drawerItems.add(drawerModelClass);


        drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.makanforbusiness));
        drawerModelClass.setImage(R.drawable.icon_makkanbusi);
        drawerItems.add(drawerModelClass);

        drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.find_deals));
        drawerModelClass.setImage(R.drawable.icon_deal);
        drawerItems.add(drawerModelClass);

        drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.notification));
        drawerModelClass.setImage(R.drawable.icon_notification);
        drawerItems.add(drawerModelClass);

        drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.news));
        drawerModelClass.setImage(R.drawable.icon_news);
        drawerItems.add(drawerModelClass);

        drawerModelClass = new DrawerModelClass();
        drawerModelClass.setName(getResources().getString(R.string.contact_us));
        drawerModelClass.setImage(R.drawable.icon_contactus);
        drawerItems.add(drawerModelClass);


//        if (AppState.getInstance().isLoginStatus()) {
//            drawerModelClass = new DrawerModelClass();
//            drawerModelClass.setName(getResources().getString(R.string.logout));
//            drawerModelClass.setImage(R.drawable.icon_signout);
//            drawerItems.add(drawerModelClass);
//        } else {
//            drawerModelClass = new DrawerModelClass();
//            drawerModelClass.setName(getResources().getString(R.string.login));
//            drawerModelClass.setImage(R.drawable.icon_signout);
//            drawerItems.add(drawerModelClass);
//        }

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
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        //Setting the actionbarToggle to drawer layout
        drawer.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }


    private void loadFragment() {
//        selectNavMenu();
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


    public void naveOpen(View view){
        drawer.openDrawer(Gravity.LEFT);
    }
    private BaseFragment getFragment() {

        switch (navItemIndex) {

            case 0:

                return new HomeNewFragment();

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

            case 12:
                return new NewFilterFragment();

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

                        if (AppState.getInstance().getUserId() != null && AppState.getInstance().getUserId().length() > 0) {

                            navItemIndex = 3;
                            CURRENT_TAG = TAG_WISH_LIST;
                        } else {
                            new Utility().moveToActivity(HomeActivity.this, LoginActivity.class, null);
                        }


                        break;

                    case R.id.nav_mortgage:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_MORTGAGE;
                        break;

                    case R.id.nav_settings:

                        if (AppState.getInstance().getUserId() != null && AppState.getInstance().getUserId().length() > 0) {

                            navItemIndex = 5;
                            CURRENT_TAG = TAG_SETTINGS;

                        } else {
                            new Utility().moveToActivity(HomeActivity.this, LoginActivity.class, null);
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

                        showProgressDialog();
                        new PreferenceManager().setValue(HomeActivity.this, PrefKey.USER_DATA, null);
                        AppState.getInstance().setLoginStatus(false);
                        AppState.getInstance().setUserId(null);
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.home_nav_drawer_non_logged_in);

                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;

                        dismissProgressDialog();


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            startActivity(getIntent());
        } else {
            Toast.makeText(this, "asdg", Toast.LENGTH_SHORT).show();
        }


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

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        navigationView.getMenu().clear();
//
//        if(AppState.getInstance().isLoginStatus()){
//            navigationView.inflateMenu(R.menu.home_nav_drawer);
//        }else{
//            navigationView.inflateMenu(R.menu.home_nav_drawer_non_logged_in);
//        }
//
//
//    }


    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void recycleItemClick(String name) {
        //Check to see which item was being clicked and perform appropriate action

        if (name.equals(getResources().getString(R.string.home))) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
        } else if (name.equals(getResources().getString(R.string.category))) {
            navItemIndex = 1;
            CURRENT_TAG = TAG_CATEGORY;
        } else if (name.equals(getResources().getString(R.string.dealers))) {
            navItemIndex = 2;
            CURRENT_TAG = TAG_DEALERS;
        } else if (name.equals(getResources().getString(R.string.wishlist))) {
            if (AppState.getInstance().getUserId() != null && AppState.getInstance().getUserId().length() > 0) {
                navItemIndex = 3;
                CURRENT_TAG = TAG_WISH_LIST;
            } else {
                new Utility().moveToActivity(HomeActivity.this, LoginActivity.class, null);
            }
        } else if (name.equals(getResources().getString(R.string.search))) {

            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            navItemIndex = 12;
            CURRENT_TAG = TAG_SEARCH;

        } else if (name.equals(getResources().getString(R.string.settings))) {
            if (AppState.getInstance().getUserId() != null && AppState.getInstance().getUserId().length() > 0) {

                navItemIndex = 5;
                CURRENT_TAG = TAG_SETTINGS;

            } else {
                new Utility().moveToActivity(HomeActivity.this, LoginActivity.class, null);
            }
        } else if (name.equals(getResources().getString(R.string.feedback))) {

            if (AppState.getInstance().getUserId() != null && AppState.getInstance().getUserId().length() > 0) {

                navItemIndex = 6;
                CURRENT_TAG = TAG_FEEDBACK;

            } else {
                new Utility().moveToActivity(HomeActivity.this, LoginActivity.class, null);
            }

        } else if (name.equals(getResources().getString(R.string.makanforbusiness))) {
            navItemIndex = 7;
            CURRENT_TAG = TAG_BUSINESS;
        } else if (name.equals(getResources().getString(R.string.find_deals))) {
            navItemIndex = 8;
            CURRENT_TAG = TAG_FIND_DEALS;
        } else if (name.equals(getResources().getString(R.string.news))) {
            navItemIndex = 9;
            CURRENT_TAG = TAG_NEWS;
        } else if (name.equals(getResources().getString(R.string.notification))) {
            navItemIndex = 10;
            CURRENT_TAG = TAG_NOTIFICATION;
        } else if (name.equals(getResources().getString(R.string.contact_us))) {
            navItemIndex = 11;
            CURRENT_TAG = TAG_CONTACT_US;
        } else if (name.equals(getResources().getString(R.string.professional))) {
            new Utility().moveToActivity(HomeActivity.this, ProfessionalsActivity.class, null);
        } else if (name.equals(getResources().getString(R.string.login))) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivityForResult(intent, 0);

        } else if (name.equals(getResources().getString(R.string.logout))) {
            showProgressDialog();
            new PreferenceManager().setValue(HomeActivity.this, PrefKey.USER_DATA, null);
            AppState.getInstance().setLoginStatus(false);
            AppState.getInstance().setUserId(null);
            finish();
            startActivity(getIntent());
            fillDrawerItems();
            adapter.notifyDataSetChanged();
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            dismissProgressDialog();
        } else {
            navItemIndex = 0;
        }
        loadFragment();
    }


}