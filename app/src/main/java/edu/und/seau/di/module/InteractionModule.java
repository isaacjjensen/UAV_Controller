package edu.und.seau.di.module;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import edu.und.seau.firebase.authentication.FirebaseAuthenticationInterface;
import edu.und.seau.firebase.authentication.FirebaseAuthenticationManager;
import edu.und.seau.firebase.database.FirebaseDatabaseInterface;
import edu.und.seau.firebase.database.FirebaseDatabaseManager;
import edu.und.seau.uav_controller.googlemaps.GoogleMapsInterface;
import edu.und.seau.uav_controller.googlemaps.GoogleMapsManager;

@Module(includes = FirebaseModule.class)
abstract public class InteractionModule {

    @Binds
    abstract FirebaseAuthenticationInterface bindAuthenticationInterface(FirebaseAuthenticationManager authenticationManager);

    @Binds
    abstract FirebaseDatabaseInterface bindDatabaseInterface(FirebaseDatabaseManager firebaseDatabaseManager);

    @Binds
    abstract GoogleMapsInterface bindGoogleMapsInterface(GoogleMapsManager googleMapsManager);
}
