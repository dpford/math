package net.dpford.math;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    TextView equation;
    TextView streakText;
    EditText answerEntry;
    Button checkButton;
    ProgressBar proBar;
    ImageView star1;
    ImageView star2;
    ImageView star3;
    int progress;
    int streak;
    int[] anArray;
    final Random rand = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anArray = new int[2];
        proBar = (ProgressBar) findViewById(R.id.correctProgressBar);
        star1 = (ImageView) findViewById(R.id.starImageView1);
        star2 = (ImageView) findViewById(R.id.starImageView2);
        star3 = (ImageView) findViewById(R.id.starImageView3);

        //nextInt is exclusive of top value, so do +1 to include it
        //replace 9 and 0 with max and min, respectively, if you want to do something else here
        anArray[0] = rand.nextInt((9 - 0) + 1) + 0;
        anArray[1] = rand.nextInt((9 - 0) + 1) + 0;
        equation = (TextView) findViewById(R.id.txtEquation);
        equation.setText(anArray[0] + " + " + anArray[1]);

        answerEntry = (EditText) findViewById(R.id.txtInput);

        checkButton = (Button) findViewById(R.id.btnSub);

        streakText = (TextView) findViewById(R.id.streakTextView);
        streak = 0;
        progress = 0;



        // Add setOnClickListeners for buttons
        setButtonOnClickListeners();




        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    private void setButtonOnClickListeners(){
        checkButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                int guess = Integer.parseInt(answerEntry.getText().toString());
                if (anArray[0] + anArray[1] == guess) {
                    Toast.makeText(getApplicationContext(), "Correct! " + anArray[0] + " + " + anArray[1] + " = " + guess, Toast.LENGTH_LONG).show();
                    anArray[0] = rand.nextInt((9 - 0) + 1) + 0;
                    anArray[1] = rand.nextInt((9 - 0) + 1) + 0;
                    equation.setText(anArray[0] + " + " + anArray[1]);
                    answerEntry.setText("");
                    streak++;
                    progress++;
                    if (progress == 5){
                        star1.setImageResource(R.drawable.mario_star);
                    }
                    if (progress == 10) {
                        star2.setImageResource(R.drawable.mario_star);
                    }
                    if (progress == 15) {
                        star3.setImageResource(R.drawable.mario_star);
                    }
                    proBar.setProgress(progress % 5);
                    if (streak > 2){
                        streakText.setText("Streak: " + streak);
                    }


                }
                else {
                    Toast.makeText(getApplicationContext(), "Try again!", Toast.LENGTH_LONG).show();
                    streak = 0;
                    streakText.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
