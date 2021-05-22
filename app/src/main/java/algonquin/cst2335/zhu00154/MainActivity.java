package algonquin.cst2335.zhu00154;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mytext = findViewById(R.id.textview);
        Button mybutton = findViewById(R.id.mybutton);
        EditText myedit = findViewById(R.id.myedittext);

// regular way to code
//        mybutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mytext.setText("You edit text has:" + editString);
//            }
//        });

        //Lambda functions
        mybutton.setOnClickListener( vw -> {
                    String editString = myedit.getText().toString();
                    mytext.setText("You edit text has:" + editString);
                });


// update checkbox1 and checkbox 2 by setText
        CheckBox mycb = findViewById(R.id.thecheckbox);

        mycb. setOnCheckedChangeListener( ( buttonView,  isChecked) ->
        {
            Toast.makeText(getApplicationContext(),  "You clicked on the checkbox and it is now:"+ isChecked, Toast.LENGTH_SHORT).show();
        });
        CheckBox mycb2 = findViewById(R.id.my2ndcheckbox);
        mycb2. setOnCheckedChangeListener( ( buttonView,  isChecked) ->
        {
            Toast.makeText(getApplicationContext(),  "You clicked on the checkbox 2 and it is now:"+ isChecked, Toast.LENGTH_SHORT).show();
        });


        Switch mySwitch =findViewById(R.id.myswitch);
        mySwitch. setOnCheckedChangeListener( ( buttonView,  isChecked) ->
        {
            Toast.makeText(getApplicationContext(),  "You clicked on the checkbox and it is now:"+ isChecked, Toast.LENGTH_SHORT).show();
        });



        Switch mySecondSwitch = findViewById(R.id.my2ndswitch);
        mySecondSwitch. setOnCheckedChangeListener( ( buttonView,  isChecked) ->
        {
            Toast.makeText(getApplicationContext(),  "You clicked on the checkbox and it is now:"+ isChecked, Toast.LENGTH_SHORT).show();
        });

        RadioButton myRadio = findViewById(R.id.radiobutton1);
        myRadio. setOnCheckedChangeListener( ( buttonView,  isChecked) ->
        {
            Toast.makeText(getApplicationContext(),  "You clicked on the radio button and it is now:"+ isChecked, Toast.LENGTH_SHORT).show();
        });


        RadioButton myRadio2 = findViewById(R.id.radiobutton2);
        myRadio2. setOnCheckedChangeListener( ( buttonView,  isChecked) ->
        {
            Toast.makeText(getApplicationContext(),  "You clicked on the radio button 2 and it is now:"+ isChecked, Toast.LENGTH_SHORT).show();
        });

        ImageView myimg = findViewById(R.id.imageView);

        ImageButton imgbtn = findViewById(R.id.myImageButton);
        imgbtn.setOnClickListener((v)->{
            int width = imgbtn.getWidth();
            int height = imgbtn.getHeight();

            Toast.makeText(getApplicationContext(),"The width = " + width + "and height = " + height, Toast.LENGTH_LONG).show();
        });


    }
}