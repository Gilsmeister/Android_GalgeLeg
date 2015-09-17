package com.example.mortendam.galgeleg;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class gameActivity extends AppCompatActivity implements View.OnClickListener{
    hallowLogic hl;
    String guess;
    String playerName;
    EditText guessTxt;
    TextView guessedView;
    TextView wordView;
    ImageView progress;
    Resources res;
    ArrayList<Drawable> imgList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        hl = new hallowLogic();

        TextView pNV = (TextView) findViewById(R.id.playerNameGame);
        wordView = (TextView) findViewById(R.id.wordTxt);
        guessedView = (TextView) findViewById(R.id.triedTxt);
        Button submitBtn = (Button) findViewById(R.id.guessBtn);
        guessTxt = (EditText) findViewById(R.id.guessLetter);
        progress = (ImageView) findViewById(R.id.progressionImg);
        res = getResources();
        imgList = setImages();

        submitBtn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (!(extras == null)){
            playerName = (String) extras.get("playerName");
            pNV.setText(playerName);
        }


        wordView.setText(hl.getSynligtOrd());
        guessedView.setText("Used");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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

    public void onClick(View view) {
        guess = guessTxt.getText().toString();
        hl.gætBogstav(guess);

        if (hl.erSidsteBogstavKorrekt()){
            String ordet = hl.getSynligtOrd();
            System.out.println(ordet);
            wordView.setText(ordet);
        }
        boolean gameOver = hl.erSpilletSlut();
        System.out.println(gameOver);
        if(gameOver){
            Intent i = new Intent(gameActivity.this, EndActivity.class);
            boolean win = hl.erSpilletVundet();
            System.out.println(win);


            i.putExtra("word", hl.getOrdet());
            i.putExtra("pName", playerName);
            i.putExtra("used", getUsed());
            i.putExtra("win", win);
            i.putExtra("wrong", hl.getAntalForkerteBogstaver());
            this.startActivity(i);
        } else {
                int forkerte = hl.getAntalForkerteBogstaver();
            System.out.println(forkerte);
                progress.setImageDrawable(imgList.get(forkerte - 1));

            guessedView.setText(getUsed());
        }
    }

    public ArrayList<Drawable> setImages(){
        ArrayList imgArray = new ArrayList<Drawable>();
        imgArray.add(res.getDrawable(R.drawable.forkert1));
        imgArray.add(res.getDrawable(R.drawable.forkert2));
        imgArray.add(res.getDrawable(R.drawable.forkert3));
        imgArray.add(res.getDrawable(R.drawable.forkert4));
        imgArray.add(res.getDrawable(R.drawable.forkert5));
        return imgArray;
    }

    public String getUsed(){
        ArrayList<String> usedArr = hl.getBrugteBogstaver();
        String result = "Used:";
        for (int i = 0; i < usedArr.size(); i++){
            result = result + " " + usedArr.get(i);
        }
        return result;
    }
}
