package com.houndware.golfscorecard.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.houndware.golfscorecard.R;
import com.houndware.golfscorecard.adapters.ListAdapter;
import com.houndware.golfscorecard.model.Hole;

public class MainActivity extends ListActivity {

    private static final String PREFS_FILE = "com.houndware.golfscorecard.preferences";
    private static final String KEY_STROKECOUNT = "KEY_STROKECOUNT";
    private static final String KEY_PAR = "KEY_PAR";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Hole[] holeArray = new Hole[18];
    private ListAdapter listAdapter;

    private TextView scoreView;
    private TextView totalStrokesView;
    private int score = 0;
    private int totalPar;
    private int totalStrokes = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreView = (TextView) findViewById(R.id.scoreNum);
        totalStrokesView = (TextView) findViewById(R.id.strokeNum);

        sharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //initialize holeArray
        int strokes = 0;
        int par = 0;
        for (int i = 0; i < holeArray.length; i++) {
            strokes = sharedPreferences.getInt(KEY_STROKECOUNT + i, 0);
            par = sharedPreferences.getInt(KEY_PAR + i, 0);
            holeArray[i] = new Hole("Hole " + (i + 1) + " :", strokes, par);
            totalStrokes += holeArray[i].getStrokeCount();
            totalPar += holeArray[i].getPar();

        }
        score = totalStrokes - totalPar;


        listAdapter = new ListAdapter(this, holeArray);
        setListAdapter(listAdapter);

        scoreView.setText(String.valueOf(score));
        totalStrokesView.setText(String.valueOf(totalStrokes));
    }

    @Override
    protected void onPause() {
        super.onPause();

        for(int i = 0; i < holeArray.length; i++) {
            editor.putInt(KEY_STROKECOUNT + i, holeArray[i].getStrokeCount());
            editor.putInt(KEY_PAR + i, holeArray[i].getPar());
        }
        editor.apply();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear_strokes) {
            editor.clear();
            editor.apply();

            for(Hole hole: holeArray) {
                hole.setStrokeCount(0);
            }
            listAdapter.notifyDataSetChanged();

            return true;
        }
        else if (id == R.id.action_clear_pars) {
            editor.clear();
            editor.apply();

            for(Hole hole: holeArray) {
                hole.setPar(0);
            }
            listAdapter.notifyDataSetChanged();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
