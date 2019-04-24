package edu.und.seau.di.module;

import dagger.Module;
import dagger.Provides;
import edu.und.seau.uav_controller.control.ControlScreenFragment;
import edu.und.seau.uav_controller.control.SelectUAVFragment;
import edu.und.seau.uav_controller.logout.LogoutFragment;
import edu.und.seau.uav_controller.news.NewsFragment;

@Module
public class ViewModule {

    @Provides
    NewsFragment provideNewsFragment() {return NewsFragment.newInstance(); }
    @Provides
    LogoutFragment provideLogoutFragment() { return LogoutFragment.newInstance(); }
    @Provides
    SelectUAVFragment provideSelectUAVFragment() { return SelectUAVFragment.newInstance(); }
    @Provides
    ControlScreenFragment provideControlScreenFragment() {return ControlScreenFragment.newInstance();}

}
