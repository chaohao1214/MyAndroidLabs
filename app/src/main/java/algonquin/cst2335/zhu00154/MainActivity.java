package algonquin.cst2335.zhu00154;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author  Chaohao Zhu
 * @version  1.0
 */

public class MainActivity extends AppCompatActivity {
    /**
     * this holds the text at the centre of the screen
     */
    private TextView tv = null;
    /**
     *  the place to type password
     */
    private EditText et = null;
    /**
     *  the button to submit
     */
    private Button btn = null;
    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.button);
        et = findViewById(R.id.password);

        btn.setOnClickListener( clk -> {
            String password = et.getText().toString();

            if(checkPasswrodComplexity(password))
            {
                tv.setText("Your password meets the requirements");
            }
            else
                tv.setText("You shall not pass!");
        });
    }

    /** This function is for validation
     *
     * @param pw The String object that we are checking
     * @return  return true if the password is complex enough
     */
    private boolean checkPasswrodComplexity(String pw) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundLowerCase = foundUpperCase = foundNumber = foundSpecial = false;
        Context context = getApplicationContext();
        for (char c : pw.toCharArray()){
            if (Character.isDigit(c))
            {
                foundNumber = true;
            } else if(Character.isUpperCase(c))
            {
                foundUpperCase = true;
            } else if(Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (isSpecialCharacter(c)){
                foundSpecial = true;
            }
            else
                return  true;

        }
        if (!foundNumber){
            Toast.makeText(context,"the password is missing numbers", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!foundSpecial) {
            Toast.makeText(context,"the password is missing special symbol", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundUpperCase){
            Toast.makeText(context,"the password is missing Upper Case", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase){
            Toast.makeText(context,"the password is missing Lower Case", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /** this method is testing the special symbol
     *
     * @param c different cases for special symbol
     * @return return false if the password doesn't have special symbol
     */
    boolean isSpecialCharacter (char c)
    {
        switch (c)
        {
            case '#':
            case '?':
            case '*':
            case '$':
            case '!':
            case '^':
            case '%':
            case '&':
            case '@':
                return true;
            default:
                return false;
        }
    }
}