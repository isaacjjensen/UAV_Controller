package edu.und.seau.di.components;

import dagger.Component;
import edu.und.seau.presentation.presenters.ControlPresenter;

@Component
public interface ControlFragmentComponent {

    ControlPresenter getControlPresenter();
}
