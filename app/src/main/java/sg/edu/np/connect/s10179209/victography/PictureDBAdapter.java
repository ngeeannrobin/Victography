package sg.edu.np.connect.s10179209.victography;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PictureDBAdapter extends AppCompatActivity {
    myDbHelper myhelper;
    public PictureDBAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }
    static class myDbHelper extends SQLiteOpenHelper
    {
        //Create Member
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final int DATABASE_Version = 1;   // Database Version
        private static final String TABLE_NAME1 = "Member";   // Table Name
        private static final String UID1="memberID";     // Column I (Primary Key)
        private static final String NAME = "username";    //Column II
        private static final String MyPASSWORD= "password";    // Column III
        private static final String CREATE_MEMBER_TABLE = "CREATE TABLE "+TABLE_NAME1+
                " ("+UID1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+ MyPASSWORD+" VARCHAR(225));";
        //Drop table
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME1;
        private static final String TABLE_NAME2 = "Images";   // Table Name
        private static final String UID2="imageID";     // Column I (Primary Key)
        private static final String DESCRIPTION = "description";    //Column II
        private static final String IMAGE= "ImageByte";//Column III
        private static final String THEME= "Theme"; //Column IIII
        private static final String DATE= "DatePosted"; //Column IIIII
        private static final String LIKE= "Likes";    // Column IIIIII
        private static final String MEMBERID= "MemberID";    // Column IIIIIII
        //Image Table
        private static final String CREATE_IMAGE_TABLE =
                "CREATE TABLE "+TABLE_NAME2+
                        " ("
                        +UID2+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                        +IMAGE+"BLOB ,"
                        +DESCRIPTION+" VARCHAR(255) ,"
                        +THEME+" VARCHAR(255) ,"
                        +DATE+" DATETIME DEFAULT (datetime('now','localtime')) ,"
                        +LIKE+" BIGINT DEFAULT 0 ,"
                        +MEMBERID+" INTEGER ,"
                        +" FOREIGN KEY ("+MEMBERID+") REFERENCES "+TABLE_NAME1+"("+UID1+"));";
        //Like Table
        private static final String TABLE_NAME3 = "Like";   // Table Name
        private static final String IMAGE_ID="imageID";     // Column I (Primary Key)
        private static final String MEMBER_ID = "memberID";    //Column II
        private static final String STATUS= "Status";    // Column III
        private static final String CREATE_LIKE_TABLE = "CREATE TABLE "+TABLE_NAME1+
                " ("
                +IMAGE_ID +" INTEGER ,"
                +MEMBER_ID+" INTEGER ,"
                +STATUS+" BOOL DEFAULT 1,"
                +"PRIMARY KEY"+"("+IMAGE_ID+","+MEMBERID+"), "
                +"FOREIGN KEY ("+MEMBER_ID+") REFERENCES "+TABLE_NAME1+"("+UID1+"), "
                +"FOREIGN KEY ("+IMAGE_ID+") REFERENCES "+TABLE_NAME2+"("+UID2+"));";
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_MEMBER_TABLE);
                db.execSQL(CREATE_IMAGE_TABLE);
                db.execSQL(CREATE_LIKE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }
    //get all images
    public List<Image> getAllImageData()
    {
        List<Image> imageList= new ArrayList<>();
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Images WHERE MemberID = ? AND THEME=? AND DatePosted BETWEEN DATE('now', '-7 days')  AND Date('now')",new String[] {"1","Cats"});
        while (c.moveToNext())
        {
            int iid= c.getInt(c.getColumnIndex(myhelper.IMAGE_ID));
            int mid=c.getInt(c.getColumnIndex(myhelper.MEMBER_ID));
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

            }
        }
        return  imageList;
    }
    //Check if the status of the like if the like record dosen't exists returns 0
    public int checkLikeStatus(int imageid, int memberid)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT EXISTS (SELECT * FROM Likes WHERE imageID = ? AND memberID = ?)", new String[]{Integer.toString(imageid) , Integer.toString(memberid)});
        if(c.getInt(0)==1)
        {
            Cursor cs = db.rawQuery("SELECT Status FROM Likes WHERE imageID = ? AND memberID = ?", new String[]{Integer.toString(imageid) , Integer.toString(memberid)});
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
    //add a like or create a like record
    public void addLike(String id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        //Retrieve the previous likes
        Cursor c = db.rawQuery("SELECT Likes, MemberID, ImageID FROM Images Where imageID = ?", new String[]{id});
        Integer oldLikes=c.getInt(c.getColumnIndex(myhelper.LIKE));
        Integer memberID=c.getInt(c.getColumnIndex(myhelper.MEMBERID));
        Integer imageID=c.getInt(c.getColumnIndex(myhelper.IMAGE_ID));
        Integer newLikes=oldLikes+1;
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(myDbHelper.LIKE,newLikes);
        String[] whereArgs= {id};
        //Change the status of the row to no
        int count =db.update(myDbHelper.TABLE_NAME1,contentValues1, myDbHelper.UID2+" = ?",whereArgs );
        if(checkLikeStatus(imageID,memberID)==0) {
            // Create a record in the Like Table
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(myDbHelper.MEMBER_ID, memberID);
            contentValues2.put(myDbHelper.IMAGE_ID, imageID);
            long string = db.insert(myDbHelper.TABLE_NAME3, null, contentValues2);
        }
        else
        {
            //Change the status of the row to yes
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(myDbHelper.STATUS, 1);
            String[] whereArgs1= {memberID.toString(),imageID.toString()};
            int count2 =db.update(myDbHelper.TABLE_NAME3,contentValues2, myDbHelper.MEMBER_ID + " = ?" + " AND " + myDbHelper.IMAGE_ID + " = ?",whereArgs1 );
        }
    }
    //delete a like
    public  void subtractLike(String id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        //Retrieve the previous likes
        Cursor c = db.rawQuery("SELECT Likes, MemberID, ImageID FROM Images Where imageID = ?", new String[]{id});
        Integer oldLikes=c.getInt(c.getColumnIndex(myhelper.LIKE));
        Integer memberID=c.getInt(c.getColumnIndex(myhelper.MEMBERID));
        Integer imageID=c.getInt(c.getColumnIndex(myhelper.IMAGE_ID));
        Integer newLikes=oldLikes-1;
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(myDbHelper.LIKE,newLikes);
        String[] whereArgs= {id};
        //Update the new number of likes
        int count =db.update(myDbHelper.TABLE_NAME1,contentValues1, myDbHelper.UID2+" = ?",whereArgs );
        //Change the status of the row to no
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(myDbHelper.STATUS, 0);
        String[] whereArgs1= {memberID.toString(),imageID.toString()};
        int count2 =db.update(myDbHelper.TABLE_NAME3,contentValues2, myDbHelper.MEMBER_ID + " = ?" + " AND " + myDbHelper.IMAGE_ID + " = ?",whereArgs1 );
    }

}


