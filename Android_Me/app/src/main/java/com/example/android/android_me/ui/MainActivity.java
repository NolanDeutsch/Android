package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener{

    //Variables to store the image clicked
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    public Button nextButton;
    public GridView gridView;
    //The bool to check if it is a tablet or not
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = (Button)findViewById(R.id.next_button);
        gridView = (GridView)findViewById(R.id.images_grid_view);

        //If there is an android_me_linear_layout in activity_main then it is the tablet version
        if(findViewById(R.id.android_me_linear_layout) != null){
            mTwoPane = true;

            //Hides the next button
            nextButton.setVisibility(View.GONE);
            //Sets gridview columns
            gridView.setNumColumns(2);
            //If the screen state changes
            if(savedInstanceState == null) {
                //Deal with the 3 fragments setting fragment images
                FragmentManager fragmentManager = getSupportFragmentManager();

                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setImageIds(AndroidImageAssets.getHeads());

                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());

                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImageIds(AndroidImageAssets.getLegs());

                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .add(R.id.body_container, bodyFragment)
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onImageSelected(int position) {
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        int bodyPartNumber = position / 12;
        int listIndex = position - 12 * bodyPartNumber;

        //If it is a tablet
        if (mTwoPane) {
            BodyPartFragment newFragment = new BodyPartFragment();
            //Replace the fragment image with the selected fragment
            switch (bodyPartNumber){
                case 0:
                    newFragment.setImageIds(AndroidImageAssets.getHeads());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;
                case 1:
                    newFragment.setImageIds(AndroidImageAssets.getBodies());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, newFragment)
                            .commit();
                    break;
                case 2:
                    newFragment.setImageIds(AndroidImageAssets.getLegs());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, newFragment)
                            .commit();
                    break;
            }
        } else {
            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;
            }
            //Save all the selected fragments in a bundle to pass to AndroidMeActivity
            Bundle b = new Bundle();
            b.putInt("headIndex", headIndex);
            b.putInt("bodyIndex", bodyIndex);
            b.putInt("legIndex", legIndex);

            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(b);

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
        }
    }
}
