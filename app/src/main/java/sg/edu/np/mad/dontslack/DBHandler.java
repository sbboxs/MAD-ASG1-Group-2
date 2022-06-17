package sg.edu.np.mad.dontslack;

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
    public static String COLUMN_TASKCATEGORY = "Category";
    public static String COLUMN_TASKSTATUS = "TaskStatus";
    public static String COLUMN_TASKDISCRIPTION = "TaskDiscription";
    public static String COLUMN_TASKSTARTTIME = "TaskStartTime";
    public static String COLUMN_TASKDEADLINE = "TaskDeadLine";
    public static String TABLE_CALENDAR = "Calendar";
    public static String COLUMN_CALENDAR_TASKNAME = "CalendarName";
    public static String COLUMN_CALENDAR_TASKTIME = "CalendarTime";
    public static String COLUMN_CALENDAR_TASKDATE = "CalendarDate";
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
                + COLUMN_TASKCATEGORY + " TEXT,"
                +  COLUMN_TASKSTATUS + " TEXT, " + COLUMN_TASKDISCRIPTION + " TEXT, "
                + COLUMN_TASKSTARTTIME + " TEXT, " + COLUMN_TASKDEADLINE + " TEXT" + " ) " ;
        String CREATE_CALENDAR_TABLE = "CREATE TABLE " + TABLE_CALENDAR + " ( " + COLUMN_CALENDAR_TASKNAME + " TEXT, "
                + COLUMN_CALENDAR_TASKDATE+ " TEXT,"
                +  COLUMN_CALENDAR_TASKTIME +  " TEXT" + " ) " ;
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TASKLIST_TABLE);
        db.execSQL(CREATE_CALENDAR_TABLE);
    }

    //On upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALENDAR);
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

    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCOUNT,"Username=?", new String[]{user.getUsername()});
        db.close();
    }

    //TABLE_TASK_SECTION
    //Add a task
    public void addTask(TaskObject task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Put user details into ContentValues
        values.put(COLUMN_TASKNAME, task.getTaskName());
        values.put(COLUMN_TASKCATEGORY, task.getTaskCategory());
        values.put(String.valueOf(COLUMN_TASKSTATUS),task.isTaskStatus());
        values.put(COLUMN_TASKDISCRIPTION, task.getTaskDescription());
        values.put(COLUMN_TASKSTARTTIME, task.getTaskStartTime());
        values.put(COLUMN_TASKDEADLINE, task.getTaskDeadLine());
        //Insert ContentValues into DataBase
        db.insert(TABLE_TASK, null, values);
        db.close();
    }

    //Retrieve a task from the DataBase
    public TaskObject findTask(String taskName){
        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_TASKNAME + "=\"" + taskName + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        //Set cursor to search for specific account details
        Cursor cursor = db.rawQuery(query, null);
        //Creating new user using data return from cursor
        TaskObject queryData = new TaskObject();
        if (cursor.moveToFirst()){
            queryData.setTaskName(cursor.getString(0));
            queryData.setTaskCategory(cursor.getString(1));
            queryData.setTaskStatus(Boolean.parseBoolean(cursor.getString(2)));
            queryData.setTaskDescription(cursor.getString(3));
            queryData.setTaskStartTime(cursor.getString(4));
            queryData.setTaskDeadLine(cursor.getString(5));
        }
        else{
            queryData = null;
        }
        cursor.close();
        db.close();
        return queryData;
    }
    public void updateTaskData(TaskObject taskObject, String originalTaskname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASKNAME, taskObject.getTaskName());
        cv.put(COLUMN_TASKCATEGORY, taskObject.getTaskCategory());
        cv.put(COLUMN_TASKDISCRIPTION, taskObject.getTaskDescription());
        cv.put(COLUMN_TASKSTARTTIME, taskObject.getTaskStartTime());
        cv.put(COLUMN_TASKDEADLINE, taskObject.getTaskDeadLine());
        cv.put(String.valueOf(COLUMN_TASKSTATUS),taskObject.isTaskStatus());
        db.update(TABLE_TASK, cv, "TaskName=?", new String[]{originalTaskname});
    }

    public void deleteTask(TaskObject taskObject){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK,"TaskName=?", new String[]{taskObject.getTaskName()});
        db.close();
    }

    Cursor readAllTaskData(String taskCategory){
        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_TASKCATEGORY + "=\"" + taskCategory + "\"";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return  cursor;
    }

    //Add a task
    public void addCalendarTask(TaskObject task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Put user details into ContentValues
        values.put(COLUMN_CALENDAR_TASKNAME, task.getCalendarName());
        values.put(COLUMN_CALENDAR_TASKDATE, task.getCalendarDate());
        values.put(COLUMN_CALENDAR_TASKTIME, task.getCalendarTime());
        //Insert ContentValues into DataBase
        db.insert(TABLE_CALENDAR, null, values);
        db.close();
    }

    //Retrieve a task from the DataBase
    public TaskObject findCalendarTask(String calendarDate){
        String query = "SELECT * FROM " + TABLE_CALENDAR + " WHERE " + COLUMN_CALENDAR_TASKDATE + "=\"" + calendarDate + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        //Set cursor to search for specific account details
        Cursor cursor = db.rawQuery(query, null);
        //Creating new user using data return from cursor
        TaskObject queryData = new TaskObject();
        if (cursor.moveToFirst()){
            queryData.setCalendarName(cursor.getString(0));
            queryData.setCalendarDate(cursor.getString(1));
            queryData.setCalendarTime(cursor.getString(2));
        }
        else{
            queryData = null;
        }
        cursor.close();
        db.close();
        return queryData;
    }
    public void updateCalendarData(TaskObject taskObject, String originalTaskname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CALENDAR_TASKNAME, taskObject.getCalendarName());
        cv.put(COLUMN_CALENDAR_TASKDATE, taskObject.getCalendarDate());
        cv.put(COLUMN_CALENDAR_TASKTIME, taskObject.getCalendarTime());
        cv.put(String.valueOf(COLUMN_TASKSTATUS),taskObject.isTaskStatus());
        db.update(TABLE_CALENDAR, cv, "TaskName=?", new String[]{originalTaskname});
    }

    public void deleteCalendarTask(TaskObject taskObject){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CALENDAR,"TaskName=?", new String[]{taskObject.getTaskName()});
        db.close();
    }

    Cursor readCalendarTaskData(String taskCategory){
        String query = "SELECT * FROM " + TABLE_CALENDAR + " WHERE " + COLUMN_CALENDAR_TASKDATE + "=\"" + taskCategory + "\"";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return  cursor;
    }

    //add calendar, find calendar, read all calender, add delete to calendar task. for code ref refer to top.
}
