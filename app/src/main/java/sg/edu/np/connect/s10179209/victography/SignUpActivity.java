package sg.edu.np.connect.s10179209.victography;


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
        //Declare variables
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
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etRepassword = findViewById(R.id.etRepassword);
        String username = etUsername.getText().toString();
        //Check if username is taken
        if (false){
            new AlertDialog.Builder(SignUpActivity.this)
                    .setMessage("Username is taken")
                    .show();
        }
        //Check if username length is at least 3
        else if (etUsername.getText().toString().length() < 3){
            new AlertDialog.Builder(SignUpActivity.this)
                    .setMessage("Username must be at least 3 chracters long")
                    .show();
        }
        //Check if password and confirm password are different
        else if (etPassword.getText().toString().compareTo(etRepassword.getText().toString()) != 0){
            new AlertDialog.Builder(SignUpActivity.this)
                    .setMessage("Passwords do not match")
                    .show();
        }
        //Check if password length is at least 8
        else if (etPassword.getText().toString().length() < 8){
            new AlertDialog.Builder(SignUpActivity.this)
                    .setMessage("Password must be at least 8 characters long")
                    .show();
        }
        else{
            EditText et = findViewById(R.id.editText);
            EditText et2 = findViewById(R.id.editText2);

            //Create string with password and random string
            String randStr = GenerateRandString();
            String pwrand
                    = etPassword.getText().toString()
                    + randStr;

            //Hash the password + random string (salting)
            String hash = Hashing
                    .sha512()
                    .newHasher()
                    .putString(pwrand,Charset.defaultCharset())
                    .hash()
                    .toString()
                    .toUpperCase();
            //Double hashing for good measures
            hash = Hashing
                    .sha512()
                    .newHasher()
                    .putString(hash,Charset.defaultCharset())
                    .hash()
                    .toString()
                    .toUpperCase();
            et.setText(pwrand);
            et2.setText(hash);

            //Add account to database
            (new UserAccount(username,hash,randStr)).AddAccount();
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

    //Method for generating a random 254 long string
    //output will be enclosed in brackets
    //Therefore total length will be 256
    protected String GenerateRandString(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"    +
                            "abcdefghijklmnopqrstuvwxyz"    +
                            "0123456789"                    ;
                            //"!@#$%^&*(){}[]<>-+_=:;,.?/"    ;
        String s = "(";
        Random rand = new Random();
        int i = 254;
        while (i-- != 0){
            s += characters.charAt(rand.nextInt(characters.length()));
        }
        return s + ")";
    }
}
