package edu.und.seau.di.components;

import dagger.Component;
import edu.und.seau.di.module.InteractionModule;
import edu.und.seau.presentation.presenters.ControlPresenter;
import edu.und.seau.presentation.presenters.DebugPresenter;
import edu.und.seau.presentation.presenters.LogoutPresenter;
import edu.und.seau.presentation.presenters.MainPresenter;
import edu.und.seau.presentation.presenters.RegisterPresenter;
import edu.und.seau.presentation.presenters.SelectUAVPresenter;
import edu.und.seau.presentation.presenters.WelcomePresenter;
import edu.und.seau.presentation.views.ControllerView;
import edu.und.seau.presentation.views.LogoutView;
import edu.und.seau.presentation.views.NewsView;
import edu.und.seau.presentation.views.SelectUAVView;

@Component(modules = InteractionModule.class)
public interface PresentationComponent {

    ControlPresenter getControlPresenter();
    LogoutPresenter getLogoutPresenter();
    MainPresenter getMainPresenter();
    RegisterPresenter getRegisterPresenter();
    SelectUAVPresenter getSelectUAVPresenter();
    WelcomePresenter getWelcomePresenter();
    DebugPresenter getDebugPresenter();


    LogoutView getLogoutView();
    NewsView getNewsView();
    SelectUAVView getSelectUAVView();
    ControllerView getControllerView();


}
