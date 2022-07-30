package sg.edu.np.mad.dontslack;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    TextView objectName;
    TextView objectDate;
    TextView dateStatus;

    public NotificationViewHolder(View itemView){
        super(itemView);
        objectName = itemView.findViewById(R.id.objectName);
        objectDate = itemView.findViewById(R.id.objectDate);
        dateStatus = itemView.findViewById(R.id.dateStatus);
    }
}
