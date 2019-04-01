package edu.und.seau.presentation.presenters;

import java.util.ArrayList;

import javax.inject.Inject;

import edu.und.seau.firebase.database.FirebaseDatabaseInterface;
import edu.und.seau.firebase.models.uav.UAV;
import edu.und.seau.presentation.views.SelectUAVView;
import edu.und.seau.uav_controller.control.SelectUAVFragment;

public class SelectUAVPresenter {

    private FirebaseDatabaseInterface databaseInterface;
    private SelectUAVView view;

    @Inject
    public SelectUAVPresenter(FirebaseDatabaseInterface databaseInterface)
    {
        this.databaseInterface = databaseInterface;

    }


    public void setView(SelectUAVView view)
    {
        this.view = view;
    }

    public void onEnterClicked()
    {
        if(view != null)
        {
            databaseInterface.getUAVFromID(view.getSelectedUAVID(), this::onUavSeachResults);
        }
    }

    private void onUavSeachResults(UAV result)
    {
        if(result != null)
        {
            UAV uavInstance = UAV.getInstance();
            uavInstance.setName(result.getName());
            uavInstance.setId(result.getId());
            view.onUAVSelected();
        }
    }
}
