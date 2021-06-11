package algonquin.cst2335.zhu00154;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

public class ChatRoom extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chatlayout);

        RecyclerView chatList = findViewById(R.id.myrecycler);
        MyChatAdapter adt;
        chatList.setAdapter( adt = new MyChatAdapter());

    }
    private class MyChatAdapter extends RecyclerView.Adapter
    {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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
    }


}
