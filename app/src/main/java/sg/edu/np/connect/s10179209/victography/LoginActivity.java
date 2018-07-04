package sg.edu.np.connect.s10179209.victography;

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
    }

    protected void linkTVtoET(final android.widget.TextView tv, final EditText et, final String hint){
        et.setOnFocusChangeListener(
            new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus){
                        tv.setVisibility(View.VISIBLE);
                        et.setHint("");
                    }
                    else{
                        tv.setVisibility(View.INVISIBLE);
                        et.setHint(hint);
                    }
                }
            }
        );
    }
}
