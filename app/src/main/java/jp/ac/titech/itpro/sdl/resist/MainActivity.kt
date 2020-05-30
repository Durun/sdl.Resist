package jp.ac.titech.itpro.sdl.resist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var manager: SensorManager
    private lateinit var gyroscope: Sensor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?
                ?: run {
                    Toast.makeText(this, R.string.toast_no_sensor_manager, Toast.LENGTH_LONG).show()
                    finish()
                    return
                }
        gyroscope = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
                ?: run {
                    Toast.makeText(this, R.string.toast_no_gyroscope, Toast.LENGTH_LONG).show()
                    finish()
                    return
                }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        manager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        manager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val omegaZ = event.values[2] // z-axis angular velocity (rad/sec)
        // TODO: calculate right direction that cancels the rotation
        rotation_view.setDirection(omegaZ.toDouble())
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        Log.d(TAG, "onAccuracyChanged: accuracy=$accuracy")
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}