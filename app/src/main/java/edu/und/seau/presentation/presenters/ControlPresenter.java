package edu.und.seau.presentation.presenters;

import javax.inject.Inject;

import edu.und.seau.presentation.views.ControlFragmentView;
import edu.und.seau.presentation.views.ControllerView;

public class ControlPresenter {

    ControlFragmentView view;

    @Inject
    public ControlPresenter()
    {

    }

    public void setView(ControlFragmentView view)
    {
        this.view = view;
    }
}
