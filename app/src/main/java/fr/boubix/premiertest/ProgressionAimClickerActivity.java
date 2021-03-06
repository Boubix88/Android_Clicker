package fr.boubix.premiertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ProgressionAimClickerActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    private File file;
    private ArrayList res = new ArrayList<String>();
    private View view;
    private TextView text;
    private ImageView back_button;
    private GraphView graph;
    private String sound_check;
    private int counterTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progression_aim_clicker);

        view = this.getWindow().getDecorView();
        MediaPlayer back_sound = MediaPlayer.create(this, R.raw.back_sound);
        text = (TextView) findViewById(R.id.progression_text_aim_clicker);
        back_button = (ImageView) findViewById(R.id.button_back_progression_aim_clicker);

        setOption();
        getSaveGame();
        setGraph();

        ImageView back = (ImageView) findViewById(R.id.button_back_progression_aim_clicker);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_check.equals("on")) {
                    back_sound.start();
                }
                Intent nextActivity = new Intent(getApplicationContext(), ProgressionActivity.class);
                startActivity(nextActivity);
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
                finish();
            }
        });
    }

    private void setGraph(){
        double x, y;
        x = 0;
        y = 0;

        graph = (GraphView) findViewById(R.id.graph_aim_clicker);
        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < res.size() - 1; i++){
            x += 1;
            series.appendData(new DataPoint(x, Integer.parseInt((String) res.get(i))), true, res.size() - 1);
        }
        graph.addSeries(series);
    }

    private void getSaveGame(){
        File path = getApplicationContext().getExternalFilesDir("");
        file  = new File(path, "save_game_aim_clicker_" + String.valueOf(counterTime) + ".txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null){
                line = reader.readLine();
                res.add(line);
            }
            reader.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed(){
        Intent nextActivity = new Intent(getApplicationContext(), ProgressionActivity.class);
        startActivity(nextActivity);
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
        finish();
    }

    private void setOption(){
        File path = getApplicationContext().getExternalFilesDir("");
        File file  = new File(path, "save_data_clicker.txt");
        ArrayList res = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null){
                line = reader.readLine();
                res.add(line);
            }
            reader.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

        //Set theme
        if (res.get(4).equals("clair")){
            view.setBackgroundResource(R.color.white); //Set theme white
            text.setTextColor(0xff000000);
        }else if (res.get(4).equals("sombre")){
            view.setBackgroundResource(R.color.black); //Set theme black
            text.setTextColor(0xffffffff);
            back_button.setColorFilter(0xffffffff);
        } else if (res.get(4).equals("galaxie")){
            view.setBackgroundResource(R.drawable.background); //Set theme galaxie
            text.setTextColor(0xffffffff);
            back_button.setColorFilter(0xffffffff);
        }
        counterTime = Integer.parseInt(res.get(1).toString());
        sound_check = res.get(3).toString();
    }
}