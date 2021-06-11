package algonquin.cst2335.zhu00154;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {
    RecyclerView chatlist;
    ArrayList<ChatMessage> messages = new ArrayList();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chatlayout);

        RecyclerView chatList = findViewById(R.id.myrecycler);
        MyChatAdapter adt;
        chatList.setAdapter( adt = new MyChatAdapter());
        ChatMessage thisMessage = new ChatMessage();
    }
    private class MyChatAdapter extends RecyclerView.Adapter<MyRowViews>
    {

        @Override
        public  MyRowViews onCreateViewHolder( ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View loadedRow = inflater.inflate(R.layout.sent_message, parent, false);
            MyRowViews initRow = new MyRowViews(loadedRow);
            return initRow;
        }

        @Override
        public void onBindViewHolder( ChatRoom.MyRowViews holder, int position) {
            holder.messageText.setText("");
            holder.timeText.setText("");
        }



        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private class ChatMessage
    {
        String message;
        int   sendOrReceive;
        Date  timeSent;
        public ChatMessage() {

        }
        public ChatMessage(String message, int sendOrReceive, Date timeSent) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
        }

        public String getMessage() {
            return message;
        }

        public int getSendOrReceive() {
            return sendOrReceive;
        }

        public Date getTimeSent() {
            return timeSent;
        }

        String currentDateandTime = sdf.format(new Date());
    }

    private class MyRowViews extends RecyclerView.ViewHolder
    {
        TextView messageText;
        TextView timeText;

        public MyRowViews( View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }

}
