package sg.edu.np.mad.dontslack;

import static java.sql.Types.INTEGER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "Don'tSlack.db";
    public static String TABLE_ACCOUNT = "Account";
    public static String COLUMN_USERID = "UserID";
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
        String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + " ( " + COLUMN_USERID +  " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT " + " ) ";
        String CREATE_TASKLIST_TABLE = "CREATE TABLE " + TABLE_TASK + " ( " + COLUMN_TASKNAME + " TEXT, "
                +  COLUMN_TASKSTATUS + " TEXT, " + COLUMN_TASKDETAILS + " TEXT, "
                + COLUMN_TASKDODATE + " TEXT, " + COLUMN_TASKDEADLINE + " TEXT" + " ) " ;
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
            queryData.setUsername(cursor.getString(1));
            queryData.setPassword((cursor.getString(2)));
        }
        else{
            queryData = null;
        }
        cursor.close();
        db.close();
        return queryData;
    }

    //TABLE_TASK_SECTION
    //Add a task
    public void addTask(TaskObject task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Put user details into ContentValues
        values.put(COLUMN_TASKNAME, task.getTaskName());
        values.put(String.valueOf(COLUMN_TASKSTATUS),task.isTaskStatus());
        values.put(COLUMN_TASKDETAILS, task.getTaskDetails());
        values.put(COLUMN_TASKDODATE, task.getTaskDoDate());
        values.put(COLUMN_TASKDEADLINE, task.getTaskDeadLine());
        //Insert ContentValues into DataBase
        db.insert(TABLE_TASK, null, values);
        db.close();
    }

    //Retrieve a task from the DataBase
    public TaskObject findTASK(String taskName){
        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_TASKNAME + "=\"" + taskName + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        //Set cursor to search for specific account details
        Cursor cursor = db.rawQuery(query, null);
        //Creating new user using data return from cursor
        TaskObject queryData = new TaskObject();
        if (cursor.moveToFirst()){
            queryData.setTaskName(cursor.getString(0));
            queryData.setTaskStatus(Boolean.parseBoolean(cursor.getString(1)));
            queryData.setTaskDetails(cursor.getString(2));
            queryData.setTaskDoDate(cursor.getString(3));
            queryData.setTaskDeadLine(cursor.getString(4));
        }
        else{
            queryData = null;
        }
        cursor.close();
        db.close();
        return queryData;
    }

    Cursor readAllTaskData(){
        String query = "SELECT * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return  cursor;
    }
}
