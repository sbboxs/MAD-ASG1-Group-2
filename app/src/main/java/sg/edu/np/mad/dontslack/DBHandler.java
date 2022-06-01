package sg.edu.np.mad.dontslack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "Don'tSlack.db";
    public static String TABLE_ACCOUNT = "Account";
    public static String COLUMN_USERNAME = "Username";
    public static String COLUMN_PASSWORD = "Password";
    public static String TABLE_TASK = "Tasks";
    public static String COLUMN_TASKNAME = "TaskName";
    public static String COLUMN_TASKSTATUS = "TaskStatus";
    public static String COLUMN_TASKDETAILS = "TaskDetails";
    public static String COLUMN_TASKDODATE = "TaskDoDate";
    public static String COLUMN_TASKDEADLINE = "TaskDeadLine";
    public static int DATABASE_VERSION = 1;
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + " ( " + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT " + " ) ";
        String CREATE_TASKLIST_TABLE = "CREATE TABLE " + TABLE_TASK + " ( " + COLUMN_TASKNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, " +  COLUMN_TASKSTATUS + " TEXT, " + COLUMN_TASKDETAILS + " TEXT, "
                + COLUMN_TASKDODATE + " TEXT, " + COLUMN_TASKDEADLINE + "TEXT" + " ) " ;
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TASKLIST_TABLE);
    }

    //On upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    //TABLE_ACCOUNT SECTION
    //Add new user to DataBase
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Put user details into ContentValues
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        //Insert ContentValues into DataBase
        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }

    //Find a user from the DataBase
    public User findUser(String userName){
        String query = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_USERNAME + "=\"" + userName + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        //Set cursor to search for specific account details
        Cursor cursor = db.rawQuery(query, null);
        //Creating new user using data return from cursor
        User queryData = new User();
        if (cursor.moveToFirst()){
            queryData.setUsername(cursor.getString(0));
            queryData.setPassword((cursor.getString(1)));
        }
        else{
            queryData = null;
        }
        cursor.close();
        db.close();
        return queryData;
    }

    //TABLE_TASK_SECTION

}
