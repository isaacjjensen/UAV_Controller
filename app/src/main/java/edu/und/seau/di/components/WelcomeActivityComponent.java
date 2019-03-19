package edu.und.seau.di.components;

import javax.inject.Singleton;

import dagger.Component;
import edu.und.seau.di.module.InteractionModule;
import edu.und.seau.presentation.presenters.WelcomePresenter;

@Singleton
@Component(modules = InteractionModule.class)
public interface WelcomeActivityComponent {

    WelcomePresenter getWelcomePresenter();
}
