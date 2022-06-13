package sg.edu.np.mad.dontslack;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ToDoListViewHolder extends RecyclerView.ViewHolder {
    RelativeLayout taskSection;
    TextView taskName;
    public ToDoListViewHolder(View itemView){
        super(itemView);
        taskSection = itemView.findViewById(R.id.taskSection);
        taskName = itemView.findViewById(R.id.taskDetailButton);
    }
}
