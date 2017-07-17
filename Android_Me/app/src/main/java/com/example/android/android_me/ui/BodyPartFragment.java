package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import java.util.ArrayList;
import java.util.List;
//------------------------------------------------------------------------------------------------//
//This class handles all the fragments
//and controls any interactions they can have with other classes
//------------------------------------------------------------------------------------------------//
public class BodyPartFragment extends Fragment {
    //Logging tag
    private static final String TAG = "BodyPartFragment";

    //Strings to store state info about image list
    public static final String IMAGE_ID_LIST = "image_ids";
    public static final String LIST_INDEX = "list_index";

    //Variables for storing image resources
    private List<Integer> mImageIds;
    private int mListIndex;

    //Mandatory constructor
    public BodyPartFragment(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //If the app changed states
        if(savedInstanceState != null){
            //Retrieve the image states from before state change
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        final ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);

        if(mImageIds != null){
            imageView.setImageResource(mImageIds.get(mListIndex));
            //If am image is clicked it replaces it with next in list
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListIndex < mImageIds.size()-1){
                        mListIndex++;
                    } else{
                        mListIndex = 0;
                    }
                    imageView.setImageResource(mImageIds.get(mListIndex));
                }
            });
        } else {
            Log.v(TAG, "This fragment has a null list of image id's");
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState){
        currentState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImageIds);
        currentState.putInt(LIST_INDEX, mListIndex);
    }

    public void setImageIds(List<Integer> imageIds){ mImageIds = imageIds; }
    public  void setListIndex(int index){ mListIndex = index; }
}
