package algonquin.cst2335.zhu00154;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static  String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w( TAG, "In onCreate() - Loading Widgets" );

        MyOpenHelper myOpenHelper = new MyOpenHelper(this);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        //prefs.getString("VariableName", String defaultValue);

        String emailAddress = prefs.getString("Email", "");
        EditText emailEdit = findViewById(R.id.emailEditText);
        emailEdit.setText(emailAddress);

        Button loginButton = findViewById(R.id.nextPageButton);

        loginButton.setOnClickListener(clk ->{

            SharedPreferences.Editor editor = prefs.edit();


            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            EditText emailEditText = findViewById(R.id.emailEditText);
            nextPage.putExtra("EmailAddress", emailEditText.getText().toString());
            editor.putString("Email", emailEditText.getText().toString());
            editor.apply();
            startActivity(nextPage);
        });


    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG,"In onStop() - The application is no longer visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG,"In onDestroy() - Any memory used by the application is freed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG,"In onPause() - The application no longer responds to user input");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG,"In onResume() - The application is now responding to user input");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG,"In onStart() - The application is now visible on screen");
    }


}