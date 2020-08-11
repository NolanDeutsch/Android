package com.gaxontek.instagramclone.ui.add;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.gaxontek.instagramclone.MainActivity;
import com.gaxontek.instagramclone.R;
import com.gaxontek.instagramclone.login.LoginActivity;
import com.gaxontek.instagramclone.model.PostModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddPost extends AppCompatActivity {

    private final String NEWS_FEED_DATABASE_LOCATION = "posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        BottomNavigationView navView = findViewById(R.id.nav_add_posts_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_gallery, R.id.navigation_photo, R.id.navigation_video)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_add_fragment);
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
                Intent intent = new Intent(AddPost.this, LoginActivity.class);
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

        PostModel post = new PostModel();
        post.id = dataRefFeed.getKey();
        post.postUsername = "Nolan";
        post.postUserImage = "Pic.png";
        post.postImage = "PostPic.jpeg";
        post.postLikes = 0;
        post.userLiked = null;
        post.postDescription = "This is the description of the post";
        post.postTime = Calendar.getInstance().getTime().toString();

        dataRefFeed.setValue(post);
    }
}