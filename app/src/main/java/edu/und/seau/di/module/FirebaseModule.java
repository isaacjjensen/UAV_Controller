package edu.und.seau.di.module;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Provides
    FirebaseAuth provideFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    @Provides
    FirebaseFirestore provideFirebaseDatabase(){
        return FirebaseFirestore.getInstance();
    }

}
