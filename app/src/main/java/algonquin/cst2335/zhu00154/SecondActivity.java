package algonquin.cst2335.zhu00154;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        profileImage = findViewById(R.id.imageView);
        TextView welcomeMessage = findViewById(R.id.welcome);
        welcomeMessage.setText("Welcom back " + emailAddress);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String phoneNumber = prefs.getString("phoneNumber", "");
        EditText editText = findViewById(R.id.editTextPhone);
        editText.setText(phoneNumber);


        EditText phone = findViewById(R.id.editTextPhone);
        Button callButton = findViewById(R.id.button);
        callButton.setOnClickListener(clk ->{
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phone.getText().toString()));

            //save to shared prefs


            startActivityForResult(call, 5432);
        });


        Button changePic = findViewById(R.id.changePIC);

        changePic.setOnClickListener(event -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,3456);
        });

        String path = getFilesDir().getPath();
        File file = new File(path+"/Picture.png");
        if (file.exists())
        {
            Bitmap theImage = BitmapFactory.decodeFile(path+"/Picture.png");
            profileImage.setImageBitmap(theImage);
        }


        }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = prefs.edit();
        EditText editPhone = findViewById(R.id.editTextPhone);
        editor.putString("phoneNumber", editPhone.getText().toString());
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3456){
            if(resultCode == RESULT_OK){

                try {
                    Bitmap thumbnail = data.getParcelableExtra("data");

                    profileImage.setImageBitmap(thumbnail);
                    FileOutputStream fOut = null;

                    fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}