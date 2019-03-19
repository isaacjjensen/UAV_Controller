package edu.und.seau.presentation.presenters;

import javax.inject.Inject;

import edu.und.seau.presentation.views.MainView;

public class MainPresenter extends BasePresenter {
    MainView view;

    @Inject
    public MainPresenter() {
    }

    public void setView(MainView view)
    {
        this.view = view;
    }
}
