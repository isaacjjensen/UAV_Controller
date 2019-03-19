package edu.und.seau.uav_controller.control;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import edu.und.seau.di.components.DaggerControlFragmentComponent;
import edu.und.seau.presentation.presenters.ControlPresenter;
import edu.und.seau.presentation.views.ControlFragmentView;
import edu.und.seau.uav_controller.R;
import edu.und.seau.uav_controller.databinding.ControlScreenBinding;

public class ControlScreenFragment extends Fragment implements ControlFragmentView {

    ControlScreenBinding binding;
    ControlPresenter presenter;
    DaggerControlFragmentComponent component;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.control_screen,container,false);


        return binding.getRoot();
    }

    public static ControlScreenFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ControlScreenFragment fragment = new ControlScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public Fragment getFragmentInstance() {
        return this;
    }
}
