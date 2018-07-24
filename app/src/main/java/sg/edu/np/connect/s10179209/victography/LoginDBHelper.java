package sg.edu.np.connect.s10179209.victography;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "loginDB.db";
    private static final String TABLE_LOGIN = "login";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public LoginDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,DATABASE_NAME,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_LOGIN
                + " ("
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        onCreate(db);
    }

    public void Record(Login login){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, login.getUsername());
        values.put(COLUMN_PASSWORD, login.getPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_LOGIN, null, values);
        db.close();
    }

    public Login getRecord(){
        String query = "SELECT * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Login login = new Login();
        if (cursor.moveToFirst()){
            login.setUsername(cursor.getString(0));
            login.setPassword(cursor.getString(1));
            cursor.close();
        } else {
            login = null;
        }
        db.close();
        return login;
    }

    public void delet(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_LOGIN);
    }
}

