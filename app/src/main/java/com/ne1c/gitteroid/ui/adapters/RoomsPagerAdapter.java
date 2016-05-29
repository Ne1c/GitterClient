package com.ne1c.gitteroid.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ne1c.gitteroid.models.view.RoomViewModel;
import com.ne1c.gitteroid.ui.fragments.ChatRoomFragment;

import java.util.ArrayList;

public class RoomsPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<RoomViewModel> mRoomsList;
    public RoomsPagerAdapter(FragmentManager fm, ArrayList<RoomViewModel> roomsList) {
        super(fm);

        mRoomsList = roomsList;
    }

    @Override
    public Fragment getItem(int position) {
        return ChatRoomFragment.newInstance(mRoomsList.get(position));
    }

    @Override
    public int getCount() {
        return mRoomsList.size();
    }
}