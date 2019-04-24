package edu.und.seau.presentation.views;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public interface SelectUAVView extends ControllerView {

    void onUAVSelected(String uavID);
    void setSelectedUAVID(String uavID);
    String getSelectedUAVID();
    Fragment getFragment();
}
