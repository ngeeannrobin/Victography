package sg.edu.np.connect.s10179209.victography;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

public class ViewActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    PictureDBAdapter dbadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbadapter.addMemberData();
        dbadapter.addImageData();
        dbadapter.addLikeData();
        setContentView(R.layout.activity_view);
        ImageView picture=findViewById(R.id.imgPhoto);

        recyclerView =
                (RecyclerView) findViewById(R.id.rvDisplay);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TaskAdapter(dbadapter.getAllImageData(),R.layout.main_card_layout);
        recyclerView.setAdapter(adapter);
    }
}

