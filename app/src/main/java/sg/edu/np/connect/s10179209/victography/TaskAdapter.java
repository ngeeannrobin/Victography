package sg.edu.np.connect.s10179209.victography;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    PictureDBAdapter dbAdapter;
    private List<Image> items;
    private int itemLayout;
    public TaskAdapter(List<Image> items, int itemLayout)
    {
        this.items=items;
        this.itemLayout=itemLayout;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(itemLayout, viewGroup, false);
        return new TaskViewHolder(v);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    @Override
    public void onBindViewHolder(TaskViewHolder viewHolder, int position) {
        final Image item=items.get(position);
        viewHolder.description.setText(item.getDescription());
        Bitmap bmp= BitmapFactory.decodeByteArray(item.getImage(), 0 , item.getImage().length);
        viewHolder.image.setImageBitmap(bmp);
        if(dbAdapter.checkLikeStatus(item.getImageID(),item.getMemberID())==-1)
        {
            viewHolder.like.setText("cancel like");
            viewHolder.like.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dbAdapter.subtractLike(Integer.toString(item.getImageID()));
                }
            });

        }
        else
        {
            viewHolder.like.setText("like");
            viewHolder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbAdapter.addLike(Integer.toString(item.getImageID()));
                }
            });
        }
    }


}


