package edu.und.seau.di.module;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import edu.und.seau.firebase.authentication.FirebaseAuthenticationInterface;
import edu.und.seau.firebase.authentication.FirebaseAuthenticationManager;
import edu.und.seau.firebase.database.FirebaseDatabaseInterface;
import edu.und.seau.firebase.database.FirebaseDatabaseManager;
import edu.und.seau.presentation.views.ControllerView;
import edu.und.seau.presentation.views.LogoutView;
import edu.und.seau.presentation.views.NewsView;
import edu.und.seau.presentation.views.SelectUAVView;
import edu.und.seau.uav_controller.control.ControlScreenFragment;
import edu.und.seau.uav_controller.control.SelectUAVFragment;
import edu.und.seau.uav_controller.googlemaps.GoogleMapsInterface;
import edu.und.seau.uav_controller.googlemaps.GoogleMapsManager;
import edu.und.seau.uav_controller.logout.LogoutFragment;
import edu.und.seau.uav_controller.news.NewsFragment;

@Module(includes = {FirebaseModule.class, ViewModule.class} )
abstract public class InteractionModule {

    @Binds
    abstract FirebaseAuthenticationInterface bindAuthenticationInterface(FirebaseAuthenticationManager authenticationManager);

    @Binds
    abstract FirebaseDatabaseInterface bindDatabaseInterface(FirebaseDatabaseManager firebaseDatabaseManager);

    @Binds
    abstract GoogleMapsInterface bindGoogleMapsInterface(GoogleMapsManager googleMapsManager);

    @Binds
    abstract LogoutView bindLogoutView(LogoutFragment logoutFragment);

    @Binds
    abstract NewsView bindNewsView(NewsFragment newsFragment);

    @Binds
    abstract SelectUAVView bindSelectUAVView(SelectUAVFragment selectUAVFragment);

    @Binds
    abstract ControllerView bindControllerView(ControlScreenFragment controlScreenFragment);
}
