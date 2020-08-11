package com.gaxontek.instagramclone.ui.add;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.gaxontek.instagramclone.R;
import com.google.android.material.tabs.TabLayout;

public class AddFragment extends Fragment {

    private AddTabsAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_post, container, false);

        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        adapter = new AddTabsAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new GalleryFragment(), null);
        adapter.addFragment(new PhotoFragment(), null);
        adapter.addFragment(new VideoFragment(), null);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_grid_on_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_photo_camera_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_videocam_24);

        //----------------------------------------------------------------------------------------//
        //                                  Events When Tabs Selected                             //
        //----------------------------------------------------------------------------------------//
        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(getContext(), R.color.tabSelected);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(getContext(), R.color.tabUnselected);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

        return view;
    }
}