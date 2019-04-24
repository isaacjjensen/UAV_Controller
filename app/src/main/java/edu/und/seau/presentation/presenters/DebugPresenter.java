package edu.und.seau.presentation.presenters;

import javax.inject.Inject;

import edu.und.seau.firebase.authentication.FirebaseAuthenticationInterface;
import edu.und.seau.firebase.commands.CommandManager;
import edu.und.seau.firebase.database.FirebaseDatabaseInterface;

public class DebugPresenter extends BasePresenter {

    public CommandManager manager;
    public FirebaseAuthenticationInterface authenticationInterface;
    public FirebaseDatabaseInterface databaseInterface;

    @Inject
    public  DebugPresenter(CommandManager manager, FirebaseAuthenticationInterface authenticationInterface, FirebaseDatabaseInterface databaseInterface){
        this.manager = manager;
        this.authenticationInterface = authenticationInterface;
        this.databaseInterface = databaseInterface;
    }


}
