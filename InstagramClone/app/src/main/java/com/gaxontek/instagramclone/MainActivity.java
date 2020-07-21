package com.gaxontek.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.gaxontek.instagramclone.login.LoginActivity;
import com.gaxontek.instagramclone.model.StoryCircleModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String STORIES_DATABASE_LOCATION = "stories";
    private final String NEWS_FEED_DATABASE_LOCATION = "posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_add, R.id.navigation_notifications, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out: {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.action_add_data: {
                AddData();
                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return true;
    }

    public void AddData(){
        DatabaseReference dataRefFeed = FirebaseDatabase.getInstance().getReference().child(NEWS_FEED_DATABASE_LOCATION).push();
        DatabaseReference dataRefStories = FirebaseDatabase.getInstance().getReference().child(STORIES_DATABASE_LOCATION).push();

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> mapStories = new HashMap<>();

        StoryCircleModel storyCircleModel = new StoryCircleModel();
        storyCircleModel.mId = dataRefFeed.getKey();
        storyCircleModel.mImage = "Pic.jpeg";
        storyCircleModel.mName = "Nolan Deutsch";

        mapStories.put("id", dataRefFeed.getKey());
        mapStories.put("image", storyCircleModel.mImage);
        mapStories.put("name", storyCircleModel.mName);

        map.put("id", dataRefFeed.getKey());
        map.put("postUsername", "Nolan");
        map.put("postUserImage", "Pic.jpeg");
        map.put("postImage", "BigPic.jpeg");
        map.put("postLikes", 0);
        map.put("postDescription", "stuff");
        map.put("postTime", Calendar.getInstance().getTime());
        map.put("userImage", "pic.jpg");

        dataRefFeed.setValue(map);
    }
}