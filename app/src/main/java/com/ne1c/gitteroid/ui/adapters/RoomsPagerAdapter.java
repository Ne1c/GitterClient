package com.ne1c.gitteroid.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.ne1c.gitteroid.models.view.RoomViewModel;
import com.ne1c.gitteroid.ui.fragments.ChatRoomFragment;

import java.util.ArrayList;

public class RoomsPagerAdapter extends FragmentPagerAdapter  {
    private ArrayList<RoomViewModel> mRoomsList;

    public RoomsPagerAdapter(FragmentManager fm, ArrayList<RoomViewModel> roomsList) {
        super(fm);
        mRoomsList = roomsList;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }

    @Override
    public Fragment getItem(int position) {
        return ChatRoomFragment.newInstance(mRoomsList.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRoomsList.get(position).name;
    }

    @Override
    public long getItemId(int position) {
        return mRoomsList.get(position).id.hashCode();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mRoomsList.size();
    }
}
