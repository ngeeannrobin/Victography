package sg.edu.np.connect.s10179209.victography;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView description;
    public Button like;

    public TaskViewHolder(View itemView) {
        super(itemView);
        image =
                (ImageView)itemView.findViewById(R.id.imgPhoto);
        description =
                (TextView)itemView.findViewById(R.id.tvDescription);
        like =
                (Button)itemView.findViewById(R.id.btnLike);
    }
}
