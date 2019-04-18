package edu.und.seau.uav_controller.control;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import edu.und.seau.di.components.DaggerPresentationComponent;
import edu.und.seau.di.components.PresentationComponent;
import edu.und.seau.presentation.presenters.ControlPresenter;
import edu.und.seau.presentation.views.ControlFragmentView;
import edu.und.seau.uav_controller.R;
import edu.und.seau.uav_controller.databinding.ControlScreenBinding;

public class ControlScreenFragment extends Fragment implements ControlFragmentView {

    ControlScreenBinding binding;
    ControlPresenter presenter;
    PresentationComponent component;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.control_screen,container,false);
        component = DaggerPresentationComponent.create();
        presenter = component.getControlPresenter();
        presenter.setView(this);

        binding.btnStart.setOnClickListener(v -> startOnClick());
        binding.btnStop.setOnClickListener(v -> stopOnClick());
        binding.toggleCmdPoll.setOnCheckedChangeListener((cb, b) -> cmdOnToggle(b));
        binding.toggleLocPoll.setOnCheckedChangeListener((cb, b) -> locOnToggle(b));
        binding.leftStick.setJoystickListener((x, z, id) -> verticalOnMove(z));
        binding.rightStick.setJoystickListener((x, y, id) -> horizontalOnMove(x, y));

        Activity activity = getActivity();

        presenter.readyGoogleMap(activity.getFragmentManager().findFragmentById(R.id.map),
                getContext(), activity);



        return binding.getRoot();
    }

    private void startOnClick() {
        //TODO Update With Selection Name
        presenter.onStartClicked();
    }

    private void stopOnClick() {
        presenter.onStopClicked();
    }

    private void cmdOnToggle(boolean b) {
        presenter.onCmdToggle(b);
    }

    private void locOnToggle(boolean b) {
        presenter.onLocToggle(b, getActivity());
    }

    private void verticalOnMove(float z) {
        presenter.onVerticalMove(z);
    }

    private void horizontalOnMove(float x, float y) {
        presenter.onHorizontalMove(x, y);
    }

    private void ctrlFabOnClick() {
        presenter.onCtrlFabClicked();
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
