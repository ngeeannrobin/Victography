package sg.edu.np.connect.s10179209.victography;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAccount {

    //Properties
    public String username;
    public String hash;
    public String randStr;

    //Default constructor
    public UserAccount(){}

    //Constructor for logging in
    public UserAccount(String u,String h,String r){
        username = u;
        hash = h;
        randStr = r;
    }

    //Add account to database
    public void AddAccount(){
        if (username!=null && hash!=null && randStr!=null){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Account");

            myRef.child(username).child("hash").setValue(hash);
            myRef.child(username).child("string").setValue(randStr);
        }

    }
}
