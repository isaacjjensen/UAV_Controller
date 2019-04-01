package edu.und.seau.uav_controller.control;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import edu.und.seau.di.components.DaggerPresentationComponent;
import edu.und.seau.di.components.PresentationComponent;
import edu.und.seau.presentation.presenters.SelectUAVPresenter;
import edu.und.seau.presentation.views.SelectUAVView;
import edu.und.seau.uav_controller.R;
import edu.und.seau.uav_controller.databinding.SelectUavBinding;

public class SelectUAVFragment extends Fragment implements SelectUAVView {

    private SelectUavBinding binding;
    private SelectUAVPresenter presenter;
    private PresentationComponent component;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.select_uav,container,false);
        component = DaggerPresentationComponent.create();
        presenter = component.getSelectUAVPresenter();
        presenter.setView(this);


        binding.btnEnterUAV.setOnClickListener(v -> enterOnClick());
        return binding.getRoot();
    }

    private void enterOnClick()
    {
        //TODO Update With Selection Name
        presenter.onEnterClicked();
    }

    public String getSelectedUAVID(){
        String returnValue = null;
        if(binding != null){
            returnValue = binding.uavIDText.getText().toString();
        }
        return returnValue;
    }

    public void setSelectedUAVID(String uavID){
        if(binding != null){
            binding.uavIDText.setText(uavID);
        }
    }

    @Override
    public Fragment getFragmentInstance() {
        return this;
    }

    public static SelectUAVFragment newInstance() {

        Bundle args = new Bundle();

        SelectUAVFragment fragment = new SelectUAVFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onUAVSelected() {
        ((SelectUAVNotificationInterface)Objects.requireNonNull(getActivity())).uavSelected();
    }
}
