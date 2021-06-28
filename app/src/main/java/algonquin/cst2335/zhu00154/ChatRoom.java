package algonquin.cst2335.zhu00154;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chatlayout);

        MyOpenHelper opener = new MyOpenHelper();

        chatlist = findViewById(R.id.myrecycler);

        chatlist.setAdapter(adt);

        chatlist.setLayoutManager(new LinearLayoutManager(this));

        EditText messageTyped = findViewById(R.id.textMessage);
        Button sentBtn = findViewById(R.id.sendButton);
        Button receiveBtn = findViewById(R.id.recButton);

        sentBtn.setOnClickListener(clk->{
            ChatMessage thisMessage = new ChatMessage(messageTyped.getText().toString(),1,new Date());
            messages.add(thisMessage);
            messageTyped.setText("");
            adt.notifyItemInserted(messages.size()-1);
        });

        receiveBtn.setOnClickListener(clk->{
            ChatMessage thisMessage = new ChatMessage(messageTyped.getText().toString(),2,new Date());
            messages.add(thisMessage);
            messageTyped.setText("");
            adt.notifyItemInserted(messages.size()-1);
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
                            Snackbar.make(messageText,"You deleted message #" + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo",clk -> {

                                        messages.add(position,removedMessage);
                                        adt.notifyItemInserted(position);

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
            thisRowLayout.timeText.setText(sdf.format(messages.get(position).getTimeSent()));
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
        Date timeSent;

        public ChatMessage(String message, int sentOrReceive, Date timeSent) {
            this.message = message;
            this.sentOrReceive = sentOrReceive;
            this.timeSent = timeSent;
        }

        public String getMessage() {
            return message;
        }

        public int getSentOrReceive() {
            return sentOrReceive;
        }

        public Date getTimeSent() {
            return timeSent;
        }
    }
}