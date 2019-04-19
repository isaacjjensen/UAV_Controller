package edu.und.seau.di.components;

import javax.inject.Singleton;

import dagger.Component;
import edu.und.seau.di.module.InteractionModule;
import edu.und.seau.presentation.presenters.ControlPresenter;
import edu.und.seau.presentation.presenters.LogoutPresenter;
import edu.und.seau.presentation.presenters.MainPresenter;
import edu.und.seau.presentation.presenters.RegisterPresenter;
import edu.und.seau.presentation.presenters.SelectUAVPresenter;
import edu.und.seau.presentation.presenters.WelcomePresenter;

@Component(modules = InteractionModule.class)
public interface PresentationComponent {

    ControlPresenter getControlPresenter();
    LogoutPresenter getLogoutPresenter();
    MainPresenter getMainPresenter();
    RegisterPresenter getRegisterPrensenter();
    SelectUAVPresenter getSelectUAVPresenter();
    WelcomePresenter getWelcomePresenter();

}
