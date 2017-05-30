package kr.hs.emirim.wwhurin.lenseye;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //태그
    private static final String TAG = "MainActivity";

    //객체들 선언
    private SensorManager sensorManager;
    private Sensor sensor;
    private ConstraintLayout constraintLayout; //레이아웃
    private TextView textView; //텍스트

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //레이아웃과 텍스트 뷰 연결
        constraintLayout = (ConstraintLayout) findViewById(R.id.cl_sensor);
        textView = (TextView) findViewById(R.id.tv_status);

        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }


    //오버라이딩

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        Sensor mySensor=sensorEvent.sensor;

        if(mySensor.getType()==Sensor.TYPE_ACCELEROMETER){
            float x=sensorEvent.values[0];
            float y=sensorEvent.values[1];
            float z=sensorEvent.values[2];

            long curTime= System.currentTimeMillis(); //현재시간

            if((curTime-lastUpdate)>100){
                long diffTime=(curTime-lastUpdate);
                lastUpdate=curTime;

                float speed = Math.abs(x+y+z-last_x-last_y-last_z)/diffTime*10000;

                if(speed>SHAKE_THRESHOLD){
                    //지정된 수치 이상 흔들림이 있으면 실행
                    constraintLayout.setBackgroundColor(Color.rgb(255, 0, 0));
                    textView.setText("흔들림");
                }

                else if(speed<10){
                    constraintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
                    textView.setText("흔들림 없음.");
                }

                //갱신
                last_x=x;
                last_y=y;
                last_z=z;
            }
        }
    }

   @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }

    @Override
    protected  void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }
}
