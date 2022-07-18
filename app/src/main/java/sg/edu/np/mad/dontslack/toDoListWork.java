package sg.edu.np.mad.dontslack;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class toDoListWork extends Fragment implements SelectListener{
    View view;
    ArrayList<TaskObject> taskList = new ArrayList<>();
    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle taskListBundle = this.getArguments();
        assert taskListBundle != null;
        taskList = (ArrayList<TaskObject>) taskListBundle.getSerializable("taskList");
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_to_do_list_work, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.toDoListRecyclerView);
        ToDoListAdapter myAdapter = new ToDoListAdapter(taskList, this);
        LinearLayoutManager toDOListLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(toDOListLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
        return view;
    }

    @Override
    public void onItemClicked(TaskObject taskObject) {
        Intent myIntent = new Intent(view.getContext(),ToDoListTaskDetails.class);
        Bundle args = new Bundle();
        args.putSerializable("TaskObject", (Serializable) taskObject);
        myIntent.putExtra("Bundle",args);
        startActivity(myIntent);
    }
}