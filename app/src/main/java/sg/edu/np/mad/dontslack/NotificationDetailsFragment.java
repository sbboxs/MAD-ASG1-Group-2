package sg.edu.np.mad.dontslack;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

public class NotificationDetailsFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_currentCategory = "currentCategory";
    View view;

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_notification_details_fragment, container, false);
        Bundle taskListBundle = this.getArguments();
        assert taskListBundle != null;

        sharedPreferences = this.requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String currentCategory = sharedPreferences.getString(KEY_currentCategory,"task");

        if(currentCategory.equals("task")){
            ArrayList<TaskObject> taskList = (ArrayList<TaskObject>) taskListBundle.getSerializable("objectList");
            //Setting up recyclerview
            RecyclerView recyclerView = view.findViewById(R.id.notificationRecycler);
            NotificationAdapter myAdapter = new NotificationAdapter(taskList);
            LinearLayoutManager notificationLayoutManager = new LinearLayoutManager(this.getContext());
            recyclerView.setLayoutManager(notificationLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(myAdapter);
        }
        else {
            ArrayList<CalendarEvent> eventList = (ArrayList<CalendarEvent>) taskListBundle.getSerializable("objectList");
        }
        return view;
    }
}