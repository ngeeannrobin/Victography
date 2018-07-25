package sg.edu.np.connect.s10179209.victography;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnFocusChangeListener;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvPassword = findViewById(R.id.tvPassword);

        linkTVtoET(tvUsername,etUsername, getString(R.string.Username));
        linkTVtoET(tvPassword,etPassword, getString(R.string.Password));

        //check if login record exists
        LoginDBHelper dbHandler = new LoginDBHelper(this, null, null, 1);
        Login login = dbHandler.getRecord();
        if (login!=null){
            //login
            (new UserAccount(login.getUsername(),login.getPassword(),LoginActivity.this)).Login();
        }
    }
    //Method for linking TextView to EditText.
    //When EditText is selected, TextView will be visible.
    //When EditText is deselected, TextView will be invisible.
    //This method serves no functional purpose, purely aesthetics.
    protected void linkTVtoET(final TextView tv, final EditText et, final String hint){
        et.setOnFocusChangeListener(
                new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){
                            tv.setVisibility(View.VISIBLE);
                            et.setHint("");
                            tv.setTextColor(getResources().getColor(R.color.colorAccent));
                        }
                        else{
                            et.setHint(hint);
                            if (et.getText().toString().trim().length() == 0){
                                tv.setVisibility(View.INVISIBLE);
                            }
                            else{
                                tv.setVisibility(View.VISIBLE);
                                tv.setTextColor(getResources().getColor(R.color.txt));
                            }
                        }
                    }
                }
        );
    }

    public void onClickLogin(View v){
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        (new UserAccount(username,password,LoginActivity.this)).Login();
    }

}
