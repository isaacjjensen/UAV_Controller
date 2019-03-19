package edu.und.seau.di.components;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;
import edu.und.seau.di.module.InteractionModule;
import edu.und.seau.firebase.authentication.FirebaseAuthenticationInterface;
import edu.und.seau.firebase.authentication.FirebaseAuthenticationManager;
import edu.und.seau.firebase.database.FirebaseDatabaseInterface;
import edu.und.seau.firebase.database.FirebaseDatabaseManager;
import edu.und.seau.presentation.presenters.RegisterPresenter;


@Singleton
@Component(modules = InteractionModule.class)
public interface RegisterActivityComponent {

    RegisterPresenter getRegisterPrensenter();
}
