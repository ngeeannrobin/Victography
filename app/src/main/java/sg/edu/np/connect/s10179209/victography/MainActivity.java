package sg.edu.np.connect.s10179209.victography;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClickLogin(View v){
        //send to login page
        Intent in = new Intent(this, LoginActivity.class);
        startActivity(in);
    }
    public void onClickSign(View v){
        //send to sign up page
        Intent in = new Intent(this,SignUpActivity.class);
        startActivity(in);
    }
}

