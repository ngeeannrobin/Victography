package sg.edu.np.connect.s10179209.victography;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PictureDBAdapter extends AppCompatActivity {
    myDbHelper myhelper;
    public PictureDBAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }
    static class myDbHelper extends SQLiteOpenHelper
    {
        //myDbHelper attributes
        private Context context;
        //Defining the Member table variables
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final int DATABASE_Version = 1;   // Database Version
        private static final String MEMBER_TABLE = "Member";   // Table Name// Column I (Primary Key)
        private static final String NAME = "username";    //Column II
        private static final String PASSWORD= "password";    // Column III
        //Command to create Member table
        private static final String CREATE_MEMBER_TABLE =
                "CREATE TABLE "+MEMBER_TABLE
                        + " ("
                        + NAME+" VARCHAR(255) PRIMARY KEY ,"
                        + PASSWORD+" VARCHAR(225)"
                        + ");";
        //Drop table for Member
        private static final String DROP_MEMBER_TABLE ="DROP TABLE IF EXISTS "+MEMBER_TABLE;

        //Defining the Image table variables
        private static final String IMAGE_TABLE = "Images";   // Table Name
        private static final String IMAGE_ID="imageID";     // Column I (Primary Key)
        private static final String DESCRIPTION = "description";    //Column II
        private static final String IMAGE= "ImageByte";//Column III
        private static final String THEME= "Theme"; //Column IIII
        private static final String DATE= "DatePosted"; //Column IIIII
        private static final String LIKE= "Likes";    // Column IIIIII
        //Command to create the Image table
        private static final String CREATE_IMAGE_TABLE =
                "CREATE TABLE "+IMAGE_TABLE
                        + " ("
                        + IMAGE_ID+" INTEGER PRIMARY KEY, "
                        + IMAGE+"BLOB ,"
                        + DESCRIPTION+" VARCHAR(255) ,"
                        + THEME+" VARCHAR(255) ,"
                        + DATE+" DATETIME DEFAULT (datetime('now','localtime')) ,"
                        + LIKE+" BIGINT DEFAULT 0 ,"
                        + NAME+" VARCHAR(255) ,"
                        + " FOREIGN KEY ("+NAME+") REFERENCES "+MEMBER_TABLE+"("+NAME+")"
                        + ");";
        //Drop table for Image
        private static final String DROP_IMAGE_TABLE ="DROP TABLE IF EXISTS "+IMAGE_TABLE;

        //Defining the Like table variables
        private static final String LIKE_TABLE = "Likes";   // Table Name// Column I (Primary Key)//Column II
        private static final String STATUS= "Status";    // Column III
        //Command to create the Like table
        private static final String CREATE_LIKE_TABLE =
                "CREATE TABLE " + LIKE_TABLE
                + " ("
                + IMAGE_ID +" INTEGER ,"
                + NAME+" VARCHAR(255) ,"
                + STATUS+" BOOL DEFAULT 1,"
                + "PRIMARY KEY"+"("+IMAGE_ID+","+NAME+"), "
                + "FOREIGN KEY ("+NAME+") REFERENCES "+MEMBER_TABLE+"("+NAME+"), "
                + "FOREIGN KEY ("+IMAGE_ID+") REFERENCES "+IMAGE_TABLE+"("+IMAGE_ID+")"
                + ");";
        //Drop table for Like
        private static final String DROP_LIKE_TABLE ="DROP TABLE IF EXISTS "+ LIKE_TABLE;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }


        //Create the table when the activity is created
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(DROP_MEMBER_TABLE);
                db.execSQL(DROP_IMAGE_TABLE);
                db.execSQL(DROP_LIKE_TABLE);
                db.execSQL(CREATE_MEMBER_TABLE);
                db.execSQL(CREATE_IMAGE_TABLE);
                db.execSQL(CREATE_LIKE_TABLE);


            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }


        //Resets all the tables
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           /* try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }*/
        }
    }
    public void addMemberData()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Account");
        SQLiteDatabase db = myhelper.getWritableDatabase();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SQLiteDatabase db = myhelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(myDbHelper.NAME, snapshot.getKey());
                    long string = db.insert(myDbHelper.MEMBER_TABLE, null, contentValues);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void addImageData()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Image");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                SQLiteDatabase db = myhelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(myDbHelper.IMAGE_ID, snapshot.child("imageID").getValue(Integer.class));
                contentValues.put(myDbHelper.NAME, snapshot.child("userName").getValue(String.class));
                contentValues.put(myDbHelper.IMAGE, snapshot.child("imageByte").getValue(Byte.class));
                contentValues.put(myDbHelper.LIKE, snapshot.child("likes").getValue(Integer.class));
                contentValues.put(myDbHelper.DATE, snapshot.child("datePosted").getValue(String.class));
                long string = db.insert(myDbHelper.IMAGE_TABLE, null, contentValues);
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    });
    }
    public void addLikeData()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Like");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SQLiteDatabase db = myhelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(myDbHelper.IMAGE_ID, snapshot.child("imageID").getValue(Integer.class));
                    contentValues.put(myDbHelper.NAME, snapshot.child("userName").getValue(String.class));
                    contentValues.put(myDbHelper.STATUS, snapshot.child("Status").getValue(Integer.class));
                    long string = db.insert(myDbHelper.LIKE_TABLE, null, contentValues);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    //used to get all the image records from the Image table and convert them to image objects in a list
    public List<Image> getAllImageData()
    {
        List<Image> imageList= new ArrayList<>();
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Images WHERE MemberID = ? AND THEME=? AND DatePosted BETWEEN DATE('now', '-7 days')  AND Date('now')",new String[] {"1","Cats"});
        while (c.moveToNext())
        {
            int iid= c.getInt(c.getColumnIndex(myhelper.IMAGE_ID));
            String mid=c.getString(c.getColumnIndex(myhelper.NAME));
            byte[] img=c.getBlob(c.getColumnIndex(myhelper.IMAGE));
            String desc =c.getString(c.getColumnIndex(myhelper.DESCRIPTION));
            int like=c.getInt(c.getColumnIndex(myhelper.LIKE));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                Date date = format.parse(c.getString(c.getColumnIndex(myhelper.DATE)).toString());
                Image image = new Image(iid, mid, img, desc, like, date);
                imageList.add(image);
            }
            catch(ParseException e)
            {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT);
            }
        }
        return  imageList;
    }
    //Check the status of the Like, if the Like record dose not exist returns 0
    public int checkLikeStatus(int imageid, String membername)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT EXISTS (SELECT * FROM Likes WHERE imageID = ? AND memberID = ?)", new String[]{Integer.toString(imageid) , membername});
        if(c.getInt(0)==1)
        {
            Cursor cs = db.rawQuery("SELECT Status FROM Likes WHERE imageID = ? AND memberID = ?", new String[]{Integer.toString(imageid) , membername});
            if(cs.getInt(0)==1)
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return 0;
        }
    }
    //Creates a like record in the Like table that shows who liked the image and the status of the like, if the like record exists, change it to true
    public void addLike(String id,String membername)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        //Retrieve the previous likes
        Cursor c = db.rawQuery("SELECT Likes, MemberID, ImageID FROM Images Where imageID = ?", new String[]{id});
        Integer oldLikes=c.getInt(c.getColumnIndex(myhelper.LIKE));
        Integer imageID=c.getInt(c.getColumnIndex(myhelper.IMAGE_ID));
        Integer newLikes=oldLikes+1;
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(myDbHelper.LIKE,newLikes);
        String[] whereArgs= {id};
        //Change the status of the row to no
        int count =db.update(myDbHelper.MEMBER_TABLE,contentValues1, myDbHelper.NAME+" = ?",whereArgs );
        if(checkLikeStatus(imageID,membername)==0) {
            // Create a record in the Like Table
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(myDbHelper.NAME, membername);
            contentValues2.put(myDbHelper.IMAGE_ID, imageID);
            long string = db.insert(myDbHelper.LIKE_TABLE, null, contentValues2);
        }
        else
        {
            //Change the status of the row to true
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(myDbHelper.STATUS, 1);
            String[] whereArgs1= {membername,imageID.toString()};
            int count2 =db.update(myDbHelper.LIKE_TABLE,contentValues2, myDbHelper.NAME + " = ?" + " AND " + myDbHelper.IMAGE_ID + " = ?",whereArgs1 );
        }
    }
    //Delete a like and change the record of the like in the Like table to false
    public  void subtractLike(String id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        //Retrieve the previous likes
        Cursor c = db.rawQuery("SELECT Likes, MemberID, ImageID FROM Images Where imageID = ?", new String[]{id});
        Integer oldLikes=c.getInt(c.getColumnIndex(myhelper.LIKE));
        Integer memberID=c.getInt(c.getColumnIndex(myhelper.NAME));
        Integer imageID=c.getInt(c.getColumnIndex(myhelper.IMAGE_ID));
        Integer newLikes=oldLikes-1;
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(myDbHelper.LIKE,newLikes);
        String[] whereArgs= {id};
        //Update the new number of likes
        int count =db.update(myDbHelper.MEMBER_TABLE,contentValues1, myDbHelper.NAME+" = ?",whereArgs );
        //Change the status of the Like record in the Like table to no
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(myDbHelper.STATUS, 0);
        String[] whereArgs1= {memberID.toString(),imageID.toString()};
        int count2 =db.update(myDbHelper.LIKE_TABLE,contentValues2, myDbHelper.NAME + " = ?" + " AND " + myDbHelper.IMAGE_ID + " = ?",whereArgs1 );
    }

}


