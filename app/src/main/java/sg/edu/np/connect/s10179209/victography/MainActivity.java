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

        //check if login record exists
        LoginDBHelper dbHandler = new LoginDBHelper(this, null, null, 1);
        Login login = dbHandler.getRecord();
        if (login!=null){
            //send to login page
            Intent in = new Intent(this, LoginActivity.class);
            startActivity(in);
        }
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

