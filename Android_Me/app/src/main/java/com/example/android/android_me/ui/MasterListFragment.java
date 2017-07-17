package com.example.android.android_me.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MasterListFragment extends Fragment {

    public MasterListFragment(){}

    OnImageClickListener mCallback;

    public interface OnImageClickListener{
        void onImageSelected(int position);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnImageClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.images_grid_view);

        MasterListAdapter mAdapter = new MasterListAdapter(getContext(), AndroidImageAssets.getAll());

        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                mCallback.onImageSelected(position);
            }
        });
        return rootView;
    }
}
