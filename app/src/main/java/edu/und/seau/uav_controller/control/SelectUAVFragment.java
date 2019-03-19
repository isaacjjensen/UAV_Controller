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
import edu.und.seau.di.components.DaggerSelectUAVFragmentComponent;
import edu.und.seau.di.components.SelectUAVFragmentComponent;
import edu.und.seau.presentation.presenters.SelectUAVPresenter;
import edu.und.seau.presentation.views.SelectUAVView;
import edu.und.seau.uav_controller.R;
import edu.und.seau.uav_controller.databinding.SelectUavBinding;

public class SelectUAVFragment extends Fragment implements SelectUAVView {

    SelectUavBinding binding;
    SelectUAVPresenter presenter;
    SelectUAVFragmentComponent component;
    ArrayAdapter<String> adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.select_uav,container,false);
        component = DaggerSelectUAVFragmentComponent.create();
        presenter = component.getSelectUAVPresenter();
        presenter.setView(this);


        binding.btnEnterUAV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterOnClick();
            }
        });
        return binding.getRoot();
    }

    private void enterOnClick()
    {
        //TODO Update With Selection Name
        presenter.onEnterClicked("const");
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

    public void setListViewItems(ArrayList<String> items)
    {
        adapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()),R.layout.select_uav_row,items);
        binding.listView.setAdapter(adapter);
    }

    @Override
    public void onUAVSelected() {
        ((SelectUAVNotificationInterface)Objects.requireNonNull(getActivity())).uavSelected();
    }
}
