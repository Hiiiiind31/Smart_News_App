package brand.smart_news_app.Activities;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import brand.smart_news_app.Drawer.MenuAdapter;
import brand.smart_news_app.PrefManager.PrefManager;
import brand.smart_news_app.R;
import brand.smart_news_app.Tabs_Fragments.ViewPagerAdapter;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

import static brand.smart_news_app.R.id.container;
import static brand.smart_news_app.R.id.drawer;


public class Activity_Main extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {

    private ViewPager mViewPager;
    Toolbar toolbar ;

    //drawer
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    private ArrayList<String> mTitles = new ArrayList<>();
    private PrefManager prefManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);


        toolbar=(Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        prefManager = new PrefManager(this);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.vpContainer);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(3);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(Activity_Main.this, R.color.colorPrimary);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        int position = tab.getPosition();
                        if (position == 0) {
                            toolbar.setVisibility(View.GONE);
                        } else {
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(Activity_Main.this, R.color.colorGray);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

        //An array containing your icons from the drawable directory
        final int[] ICONS = new int[]{
                R.mipmap.user,
                R.mipmap.saved,
                R.mipmap.search,
                R.mipmap.home,
        };
        //ADD icons to tab
        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        tabLayout.getTabAt(3).setIcon(ICONS[3]);

        ///drawer
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));

        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

        ImageView menuRight = (ImageView) findViewById(R.id.open_drawer);
        menuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mViewHolder.mDuoDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                     mViewHolder.mDuoDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    mViewHolder.mDuoDrawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });


    }
    public void H_Search (View v){
        Intent i = new Intent(Activity_Main.this,Activity_Search.class);
        startActivity(i);
    }


    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);
        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
        mViewHolder.H_Name_view.setText(prefManager.getUser_name());
        mViewHolder.H_Email_view.setText(prefManager.getUser_email());
        String String_pic = prefManager.getUser_pro_pic();
        Picasso.with(Activity_Main.this).load("http://smart-news-sa.com/news/public/uploads/profile/"+String_pic).into(mViewHolder.H_Pro_pic_view);

    }

    @Override
    public void onFooterClicked() {
        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {

        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);

        // Navigate to the right fragment
        switch (position) {
            case 0 :
                Toast.makeText(this, R.string.main, Toast.LENGTH_SHORT).show();
                mViewPager.setCurrentItem(3);
                break;
            case 1 :
                mViewPager.setCurrentItem(2);
                Toast.makeText(this, R.string.discover, Toast.LENGTH_SHORT).show();
                break;
            case 2 :
                mViewPager.setCurrentItem(1);
                Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show();
                break;
            case 3 :
                mViewPager.setCurrentItem(0);
                Toast.makeText(this, R.string.profile, Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Intent i = new Intent(Activity_Main.this,Activity_Notifications.class);
                startActivity(i);
                Toast.makeText(this, R.string.notifications, Toast.LENGTH_SHORT).show();
                break;
            case 5 :
                Intent a = new Intent(Activity_Main.this,Activity_Call_us.class);
                startActivity(a);
                Toast.makeText(this, R.string.call_us, Toast.LENGTH_SHORT).show();
                break;
            case 6 :
//                Intent s = new Intent(Activity_Main.this,Activity_Notifications.class);
//                startActivity(s);
                Toast.makeText(this, R.string.rules, Toast.LENGTH_SHORT).show();
                break;
            case 7 :
//                Intent q = new Intent(Activity_Main.this,Activity_Notifications.class);
//                startActivity(q);
                Toast.makeText(this, R.string.about_us, Toast.LENGTH_SHORT).show();
                break;
                 }
        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private TextView H_Name_view ,H_Email_view;
        private ImageView H_Pro_pic_view ;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(drawer);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            H_Name_view = mDuoMenuView.findViewById(R.id.H_Name_id);
            H_Email_view = mDuoMenuView.findViewById(R.id.H_Email_id);
            H_Pro_pic_view = mDuoMenuView.findViewById(R.id.H_Pro_pic_id);


        }
    }
}
