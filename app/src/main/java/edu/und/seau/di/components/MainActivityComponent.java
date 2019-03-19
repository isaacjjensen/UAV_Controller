package edu.und.seau.di.components;

import dagger.Component;
import edu.und.seau.presentation.presenters.MainPresenter;
import edu.und.seau.uav_controller.news.NewsFragment;

@Component
public interface MainActivityComponent {
    MainPresenter getMainPresenter();
    //NewsFragment getNewsFragment();
}
