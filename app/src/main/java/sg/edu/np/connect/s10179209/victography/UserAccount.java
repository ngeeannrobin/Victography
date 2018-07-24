package sg.edu.np.connect.s10179209.victography;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.common.hash.Hashing;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class UserAccount {

    //Properties
    private String username;
    private String hash;
    private String randStr;
    private Context context;
    private String password;

    //Default constructor
    public UserAccount(){}

    //Constructor for SignUp/Login
    public UserAccount(String u,String p,Context c){
        username = u;
        context = c;
        password = p;
    }

    //Add account to database
    public void AddStep2(){
        //create hash with random stuff idk
        randStr = GenerateRandString();
        hash = DoubleHash(password,randStr);

        //Add account to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Account");
        myRef.child(username).child("hash").setValue(hash);
        myRef.child(username).child("string").setValue(randStr);
    }
    //Check if username exists in database
    public void AddStep1(){

        if (username!=null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //This method is called once with the initial value and again
                    //Whenever data at this location is updated.
                    String userd = dataSnapshot.child("Account").child(username).child("hash").getValue(String.class);
                    if (userd!=null) { //Exists
                        SignUpActivity.ShowError("Username already exists",context);
                    }
                    else{
                        AddStep2();
                        new AlertDialog.Builder(context)
                                .setMessage("Account created!")
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        Intent in = new Intent(context, MainActivity.class);
                                        context.startActivity(in);
                                    }
                                })
                                .show();
                    }
                    Log.d(TAG, "Value is: " + userd);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Failed to read value
                    Log.w(TAG, "Failed to read value. ", databaseError.toException());
                }
            });
        }
    }
    //Method for generating a random 254 long string
    //output will be enclosed in brackets
    //Therefore total length will be 256
    protected static String GenerateRandString(){
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
    //Double Hash
    private String DoubleHash(String pass, String randstr){
        String pwrand
                = pass
                + randstr;

        //Hash the password + random string (salting)
        String hashh = Hashing
                .sha512()
                .newHasher()
                .putString(pwrand, Charset.defaultCharset())
                .hash()
                .toString()
                .toUpperCase();
        //Double hashing for good measures
        hashh = Hashing
                .sha512()
                .newHasher()
                .putString(hashh,Charset.defaultCharset())
                .hash()
                .toString()
                .toUpperCase();
        return hashh;
    }
    //Fetch random string and hash
    public void Login(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //This method is called once with the initial value and again
                //Whenever data at this location is updated.
                String userh = dataSnapshot.child("Account").child(username).child("hash").getValue(String.class);
                String users = dataSnapshot.child("Account").child(username).child("string").getValue(String.class);
                if (userh!=null && users!=null) { //Exists
                    //Login or smth
                    if (DoubleHash(password,users).equals(userh)){ //Login success
                        SignUpActivity.ShowError("Login success",context);

                    }
                    else{
                        SignUpActivity.ShowError("Invalid login credentials! (password)",context);
                    }
                }
                else{
                    SignUpActivity.ShowError("Invalid login credentials! (username)",context);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Failed to read value
                Log.w(TAG, "Failed to read value. ", databaseError.toException());
            }
        });
    }
}
