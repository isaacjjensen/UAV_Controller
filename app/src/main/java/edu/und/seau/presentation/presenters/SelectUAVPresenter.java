package edu.und.seau.presentation.presenters;

import android.app.AlertDialog;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import edu.und.seau.common.FirebaseConstants;
import edu.und.seau.firebase.authentication.FirebaseAuthenticationInterface;
import edu.und.seau.firebase.commands.CommandManager;
import edu.und.seau.firebase.commands.enumerations.ControlStatus;
import edu.und.seau.firebase.database.FirebaseDatabaseInterface;
import edu.und.seau.firebase.models.uav.UAV;
import edu.und.seau.presentation.views.SelectUAVView;
import edu.und.seau.uav_controller.R;

public class SelectUAVPresenter {

    private FirebaseDatabaseInterface databaseInterface;
    private FirebaseAuthenticationInterface authenticationInterface;
    private CommandManager commandManager;
    private SelectUAVView view;
    private ListenerRegistration requestResponse;


    @Inject
    public SelectUAVPresenter(FirebaseDatabaseInterface databaseInterface, FirebaseAuthenticationInterface authenticationInterface, CommandManager manager)
    {
        this.databaseInterface = databaseInterface;
        commandManager = manager;
        this.authenticationInterface = authenticationInterface;
    }

    public void setView(SelectUAVView view)
    {
        this.view = view;
    }

    public void onEnterClicked()
    {
        if(view != null)
        {
            String userID = authenticationInterface.getUserId();
            String uavID = view.getSelectedUAVID();

            requestResponse = commandManager.ListenForResponse(userID, uavID,stringObjectMap -> OnResponseReceived(uavID, stringObjectMap));
            commandManager.SendCommand2(userID,uavID);
        }
    }

    private void OnResponseReceived(String uavID, Map<String, Object> ResponseData){
        if(ResponseData.containsKey(FirebaseConstants.KEY_CONTROL_STATUS)){
            Long controlStatusValue = (Long)Objects.requireNonNull(ResponseData.getOrDefault(FirebaseConstants.KEY_CONTROL_STATUS, -1));
            ControlStatus status = ControlStatus.getControlStatus(controlStatusValue.intValue());
            if(status == ControlStatus.CONTROL_REQUEST_SUCCESS){
                ShowConnectionSuccessfulDialog();
                OnControlSuccess(uavID);
            }
            else {
                ShowAlreadyControlledDialog();
            }
            databaseInterface.DeleteResponse(authenticationInterface.getUserId(), uavID,null);
            requestResponse.remove();
        }
    }

    private void ShowAlreadyControlledDialog(){
        ShowOKDialog(view.getFragment().getResources().getString(R.string.already_controlled_dialog_message)
                ,view.getFragment().getResources().getString(R.string.already_controlled_dialog_title));
    }

    private void ShowConnectionSuccessfulDialog(){
        ShowOKDialog(view.getFragment().getResources().getString(R.string.connection_success)
                ,view.getFragment().getString(R.string.success));
    }

    private void ShowOKDialog(String content, String title){
        if(view != null){
            AlertDialog.Builder ADBuilder = new AlertDialog.Builder(view.getFragment().getActivity());
            ADBuilder.setMessage(content)
                    .setTitle(title);
            ADBuilder.setPositiveButton(R.string.ok,null);
            AlertDialog dialog = ADBuilder.create();
        }
    }

    private void OnControlSuccess(String uavID){
        if(view != null){
            view.onUAVSelected(uavID);
        }
    }
}
