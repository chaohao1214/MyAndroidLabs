package algonquin.cst2335.zhu00154;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {

    RecyclerView chatlist;

    ArrayList<ChatMessage> messages = new ArrayList<>();

    MyChatAdapter adt = new MyChatAdapter();

    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
    String time = sdf.format(new Date());
    SQLiteDatabase db;


    //put something in each column, except _id:


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chatlayout);

        MyOpenHelper opener = new MyOpenHelper(this);
        db= opener.getWritableDatabase();
        Cursor results = db.rawQuery("Select * from " + MyOpenHelper.TABLE_NAME + ";", null);
        int _idCol = results.getColumnIndex("_id");
        int messageCol = results.getColumnIndex(MyOpenHelper.col_message);
        int sendCol = results.getColumnIndex(MyOpenHelper.col_send_receive);
        int timeCol = results.getColumnIndex(MyOpenHelper.col_time_sent);

        while(results.moveToNext()){
            long id = results.getInt(_idCol);
            String message = results.getString(messageCol);
            String time  = results.getString(timeCol);
            int sendOrReceive = results.getInt(sendCol);
            messages.add(new ChatMessage(message, sendOrReceive, time, id));
        }


        chatlist = findViewById(R.id.myrecycler);

        chatlist.setAdapter(adt);

        chatlist.setLayoutManager(new LinearLayoutManager(this));

        EditText messageTyped = findViewById(R.id.textMessage);
        Button sentBtn = findViewById(R.id.sendButton);
        Button receiveBtn = findViewById(R.id.recButton);

        sentBtn.setOnClickListener(clk->{
            ChatMessage thisMessage = new ChatMessage(messageTyped.getText().toString(),1,sdf.format(new Date()));
            messages.add(thisMessage);
            messageTyped.setText("");
            adt.notifyItemInserted(messages.size()-1);
            ContentValues newRow = new ContentValues();
            newRow.put(MyOpenHelper.col_message, thisMessage.getMessage());
            newRow.put(MyOpenHelper.col_send_receive, thisMessage.getSentOrReceive());
            newRow.put(MyOpenHelper.col_time_sent,thisMessage.getTimeSent());
           long id =  db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow );
            thisMessage.setId(id);
         //   messages.add(thisMessage);

        });

        receiveBtn.setOnClickListener(clk->{
            ChatMessage thisMessage = new ChatMessage(messageTyped.getText().toString(),2,sdf.format(new Date()));
            messages.add(thisMessage);
            messageTyped.setText("");
            adt.notifyItemInserted(messages.size()-1);
            ContentValues newRow = new ContentValues();
            newRow.put(MyOpenHelper.col_message, thisMessage.getMessage());
            newRow.put(MyOpenHelper.col_send_receive, thisMessage.getSentOrReceive());
            newRow.put(MyOpenHelper.col_time_sent,thisMessage.getTimeSent());
            long id = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow );
            thisMessage.setId(id);
         //   messages.add(thisMessage);
        });
    }

    private class MyRowViews extends RecyclerView.ViewHolder{

        TextView messageText;
        TextView timeText;
        int position = -1;

        public MyRowViews(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(click->{

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);

                builder.setMessage("Do you want to delete the message: " + messageText.getText())

                        .setTitle("Questions:")

                        .setPositiveButton("Yes",(dialog,cl)->{
                            position = getAbsoluteAdapterPosition();
                            ChatMessage removedMessage = messages.get(position);
                            messages.remove(position);
                            adt.notifyItemRemoved(position);
                            db.delete(MyOpenHelper.TABLE_NAME, "_id=?", new String[]{ Long.toString(removedMessage.getId())});

                            Snackbar.make(messageText,"You deleted message #" + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo",clk -> {
                                        messages.add(position,removedMessage);
                                        adt.notifyItemInserted(position);
                                        db.execSQL("Insert into " + MyOpenHelper.TABLE_NAME + " values('" + removedMessage.getId()
                                        + "','" + removedMessage.getMessage() + "','" + removedMessage.getSentOrReceive() +
                                                "','" + removedMessage.getTimeSent() + "');");
                                    })
                                    .show();
                        })

                        .setNegativeButton("No",(dialog,cl)->{

                        })
                        .create().show();

            });


        }

        public void setPosition(int p){
            position = p;
        }


    }

    private class MyChatAdapter extends RecyclerView.Adapter{

        @Override
        public int getItemViewType(int position) {
            return messages.get(position).sentOrReceive;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = getLayoutInflater();


            int layoutID;
            if(viewType == 1){
                layoutID = R.layout.sent_message;
            }else{
                layoutID = R.layout.receive_message;
            }
            View loadedRow = inflater.inflate(layoutID, parent, false);
            MyRowViews initRow = new MyRowViews((loadedRow));
            return initRow;

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyRowViews thisRowLayout = (MyRowViews) holder;
            thisRowLayout.messageText.setText(messages.get(position).getMessage());
            thisRowLayout.timeText.setText(messages.get(position).getTimeSent());
            thisRowLayout.setPosition(position);


        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

    }

    private class ChatMessage{

        String message;
        int sentOrReceive;
        String timeSent;
        long id;

        public void setId(long l){
            id = l;
        }

        public long getId(){
            return id;
        }


        public ChatMessage(String message, int sentOrReceive, String timeSent) {
            this.message = message;
            this.sentOrReceive = sentOrReceive;
            this.timeSent = timeSent;
        }

        public ChatMessage(String message, int sentOrReceive, String timeSent, long id){
            this.message = message;
            this.sentOrReceive = sentOrReceive;
            this.timeSent = timeSent;
            setId(id);
        }

        public String getMessage() {
            return message;
        }

        public int getSentOrReceive() {
            return sentOrReceive;
        }

        public String getTimeSent() {
            return timeSent;
        }
    }
}