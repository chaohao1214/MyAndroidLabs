package algonquin.cst2335.zhu00154;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ChatRoom extends AppCompatActivity {

    boolean isTablet = false;


    //put something in each column, except _id:
    ArrayList<MessageListFragment.ChatMessage> messages = new ArrayList<>();
    SQLiteDatabase db;
    MessageListFragment chatFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);

        isTablet = findViewById(R.id.detailsRoom) != null;

         chatFragment = new MessageListFragment();
        FragmentManager fMgr = getSupportFragmentManager();
        FragmentTransaction tx = fMgr.beginTransaction();
        tx.add(R.id.fragmentRoom, chatFragment);
        tx.commit();
    }

    public void userClickedMessage(MessageListFragment.ChatMessage chatMessage, int position) {
        MessageDetailsFragment mdFragment = new MessageDetailsFragment(chatMessage, position);

        if (isTablet)
        {
            // tablet has a second FrameLayout with id detailsRoom to load a second fragment
            getSupportFragmentManager().beginTransaction().add(R.id.detailsRoom, mdFragment).commit();
        } else // on a phone
            {
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentRoom, mdFragment).commit();
            }
        }

    public void notifyMessageDeleted(MessageListFragment.ChatMessage chosenMessage, int chosenPosition) {
       chatFragment.notifyMessageDeleted(chosenMessage, chosenPosition);
    }
}
