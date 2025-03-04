package co.edu.unipiloto.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StopwatchActivity extends Activity {

    private int seconds = 0;
    private boolean running;
    private boolean guardar;
    private int vueltas = 0;

    private List<String> informacion = new ArrayList<>();
    private String dato = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if(savedInstanceState !=null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        //super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }

    public void onClickStart(View view){
        running = true;
    }

    public void onClickStop(View view){
        running = false;
    }

    public void onClickReset(View view){
        if(vueltas<5){
            vueltas++;
            running = false;
            guardar = false;

            if(seconds > 0){
                informacion.add(dato);
            }

            seconds = 0;
        }
        if(vueltas==5){
            vueltas = 0;
            mostrarInfo();
        }
    }

    private void mostrarInfo(){
        TextView resultados = findViewById(R.id.results);
        String salida = "";
        for(int i = 0; i < informacion.size(); i++){
            salida += "Vuelta #" + (i+1) + " : " + informacion.get(i) + "\n";
        }
        resultados.setText(salida);
        informacion.clear();
    }

    private void runTimer(){
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable(){
            @Override
            public void run(){
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                //String arrancar = "00:00:00" ;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);


                timeView.setText(time);
                if(running){
                    seconds++;
                    guardar = false;
                }
                else if(!guardar && seconds > 0 && vueltas < 5){
                    dato = time;
                    //informacion.add(dato);
                    guardar = true;
                }

                handler.postDelayed(this, 1000);
            }
        });

    }

}