package sg.edu.np.connect.s10179209.victography;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnFocusChangeListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.Random;


public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etRepassword = findViewById(R.id.etRepassword);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvPassword = findViewById(R.id.tvPassword);
        TextView tvRepassword = findViewById(R.id.tvRepassword);

        //Initialise aesthetic codes
        linkTVtoET(tvUsername,etUsername, getString(R.string.Username));
        linkTVtoET(tvPassword,etPassword, getString(R.string.Password));
        linkTVtoET(tvRepassword,etRepassword, getString(R.string.Repassword));

        //Initialise functional codes

    }

    public void onClickSignUp(View v){
        Check();
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



    //Check stuff username length, password match, password length
    private void Check(){
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etRepassword = findViewById(R.id.etRepassword);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String rePassword = etRepassword.getText().toString();
        //Check if username length is at least 3
        if (username.length() < 3){
            ShowError("Username must be at least 3 characters long",SignUpActivity.this);
        }
        //Check if password and confirm password are different
        else if (password.compareTo(rePassword) != 0){
            ShowError("Passwords do not match",SignUpActivity.this);
        }
        //Check if password length is at least 8
        else if (password.length() < 8){
            ShowError("Password must be at least 8 characters long",SignUpActivity.this);
        }
        else{
            //Check username existence, then i dont even know what i coded
            (new UserAccount(username,password,SignUpActivity.this)).AddStep1();
        }
    }

    public static void ShowError(String text, Context c){
        new AlertDialog.Builder(c)
                .setMessage(text)
                .show();
    }

}
