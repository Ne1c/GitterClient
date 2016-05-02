package com.ne1c.gitteroid.ui.views;

import android.support.annotation.StringRes;

import com.ne1c.gitteroid.models.view.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public interface RoomsListView {
    void showRooms(List<RoomViewModel> rooms, boolean fresh);

    void showError(@StringRes int resId);

    void showDialog();

    void dismissDialog();

    void errorSearch();

    void resultSearch(ArrayList<RoomViewModel> rooms);

    void resultSearchWithOffset(ArrayList<RoomViewModel> rooms);
}