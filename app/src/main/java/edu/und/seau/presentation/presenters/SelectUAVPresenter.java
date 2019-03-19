package edu.und.seau.presentation.presenters;

import java.util.ArrayList;

import javax.inject.Inject;

import edu.und.seau.firebase.database.FirebaseDatabaseInterface;
import edu.und.seau.firebase.models.uav.UAV;
import edu.und.seau.presentation.views.SelectUAVView;
import edu.und.seau.uav_controller.control.SelectUAVFragment;

public class SelectUAVPresenter {

    FirebaseDatabaseInterface databaseInterface;
    SelectUAVView view;

    @Inject
    public SelectUAVPresenter(FirebaseDatabaseInterface databaseInterface)
    {
        this.databaseInterface = databaseInterface;

    }

    public void setUAVSelection(ArrayList<UAV> uavs)
    {
        if(view != null)
        {
            ArrayList<String> uavStrings = new ArrayList<>();
            for (UAV uav:uavs) {
                uavStrings.add(uav.toString());
            }
            view.setListViewItems(uavStrings);
        }
    }

    public void setView(SelectUAVView view)
    {
        this.view = view;
        databaseInterface.getUAVList(this::setUAVSelection);
    }

    public void onEnterClicked(String uavID)
    {
        if(view != null)
        {
            view.onUAVSelected();
        }
    }
}
