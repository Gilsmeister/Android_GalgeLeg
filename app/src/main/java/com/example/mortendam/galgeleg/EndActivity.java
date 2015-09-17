package com.example.mortendam.galgeleg;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EndActivity extends AppCompatActivity {

    int wrong;
    String pName;
    String word;
    boolean win;
    String used;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        ImageView progress = (ImageView) findViewById(R.id.progress);
        TextView congrats = (TextView) findViewById(R.id.Congrats);
        TextView theWord = (TextView) findViewById(R.id.theWord);
        TextView usedLetters = (TextView) findViewById(R.id.usedLetters);
        res = getResources();

        Bundle extras = getIntent().getExtras();

        if(!(extras == null)){
            wrong = extras.getInt("wrong");
            pName = extras.getString("pName");
            word = extras.getString("word");
            win = extras.getBoolean("win");
            used = extras.getString("used");
        }

        ArrayList imgArr = setImages();

        if (win == false){
            progress.setImageDrawable((Drawable) imgArr.get(5));
        } else {
            progress.setImageDrawable((Drawable) imgArr.get(wrong));
        }
        String result;
        if (win){
            result = "Congrats " + pName + " you won";
        } else {
            result = "Sorry " + pName + " today a man was hung";
        }
        congrats.setText(result);

        theWord.setText("The word was: " + word);

        usedLetters.setText("You used: " + used);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_end, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Drawable> setImages(){
        ArrayList imgArray = new ArrayList<Drawable>();
        imgArray.add(res.getDrawable(R.drawable.galge));
        imgArray.add(res.getDrawable(R.drawable.forkert1));
        imgArray.add(res.getDrawable(R.drawable.forkert2));
        imgArray.add(res.getDrawable(R.drawable.forkert3));
        imgArray.add(res.getDrawable(R.drawable.forkert4));
        imgArray.add(res.getDrawable(R.drawable.forkert5));
        return imgArray;
    }
}
