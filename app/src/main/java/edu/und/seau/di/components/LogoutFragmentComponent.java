package edu.und.seau.di.components;

import javax.inject.Singleton;

import dagger.Component;
import edu.und.seau.di.module.InteractionModule;
import edu.und.seau.presentation.presenters.LogoutPresenter;

@Singleton
@Component(modules = InteractionModule.class)
public interface LogoutFragmentComponent {
    LogoutPresenter getLogoutPresenter();
}
