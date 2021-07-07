package algonquin.cst2335.zhu00154;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyOpenHelper extends SQLiteOpenHelper {
    public static final String name ="TheDatabase";
    public static final int version =3;
    public static final String TABLE_NAME = "Messages";
    public static final String col_message = "Message";
    public static final String col_send_receive = "SendOrReceive";
    public static final String col_time_sent = "TimeSent";


    public MyOpenHelper( Context context) {
        super(context, name, null, version);
        // all the constructor does
    }

    // sql creation statement:
    @Override       //sql interprets SQL commands
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE Table " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                col_message + " TEXT, " + col_send_receive
                + " INTEGER," + col_time_sent + " TEXT);"); // run some sql
    } // create table words

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
}
