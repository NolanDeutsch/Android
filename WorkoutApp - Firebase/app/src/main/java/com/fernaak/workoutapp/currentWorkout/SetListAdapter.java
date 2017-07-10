package com.fernaak.workoutapp.currentWorkout;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fernaak.workoutapp.R;

import java.util.List;

public class SetListAdapter extends ArrayAdapter<SetObject>{

    public SetListAdapter(Context context, int resource, List<SetObject> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.current_workout_list_item, parent, false);
        }

        TextView setTextView = (TextView) convertView.findViewById(R.id.tv_set);

        SetObject item = getItem(position);

        setTextView.setText(item.getSetNumber());

        return convertView;
    }
}
