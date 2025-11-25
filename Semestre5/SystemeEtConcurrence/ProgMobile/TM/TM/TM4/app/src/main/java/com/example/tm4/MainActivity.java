package com.example.tm4;
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Terrain terrain;
    private SensorManager sensorMgr;
    private Sensor accelSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        terrain = new Terrain(this);
        setContentView(terrain);

        // Initialise la bille et l’objectif (ex. centre de l’écran)
        float cx = getResources().getDisplayMetrics().widthPixels / 2f;
        float cy = getResources().getDisplayMetrics().heightPixels / 2f;

        terrain.setBille(new Bille(cx, cy));
        terrain.setObjectif(new Objectif(cx + 200, cy));

        // Sensors
        sensorMgr   = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMgr.unregisterListener(this);
    }

    /* ---------- Accéléromètre ---------- */

    @Override public void onSensorChanged(SensorEvent event) {
        // Exemple simple : convertir l’accélération en déplacement
        float dx = -event.values[0] * 5; // sens inversé selon besoin
        float dy = event.values[1] * 5;
        terrain.moveBille(dx, dy);
    }

    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
