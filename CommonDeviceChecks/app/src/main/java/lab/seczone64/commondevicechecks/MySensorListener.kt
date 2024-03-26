package lab.seczone64.commondevicechecks

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import kotlin.math.sqrt

class MySensorListener : SensorEventListener {
    private val alpha = 0.8f
    private var gravity = FloatArray(3)
    private var linearAcceleration = FloatArray(3)
    private var lastLightSensorValue: Float = 0.0f

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if(event.sensor.type == Sensor.TYPE_LIGHT){
                if(lastLightSensorValue == 0.0f){
                    lastLightSensorValue = event.values[0]
                }
                if(lastLightSensorValue != event.values[0]){
                    lastLightSensorValue = event.values[0]
                    Log.v(this::class.java.name, "\t[+] Light changed... Probably it's not a emulator")
                }
            }

            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                // Apply a high-pass filter to remove the effects of gravity

                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0]
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1]
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2]

                linearAcceleration[0] = event.values[0] - gravity[0]
                linearAcceleration[1] = event.values[1] - gravity[1]
                linearAcceleration[2] = event.values[2] - gravity[2]

                val acceleration = sqrt(
                    linearAcceleration[0] * linearAcceleration[0] +
                            linearAcceleration[1] * linearAcceleration[1] +
                            linearAcceleration[2] * linearAcceleration[2]
                )

                if (acceleration > 2) {
                    // Device has moved
                    Log.v(this::class.java.name, "\t[+] Device moved... $acceleration")
                }

            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.v(this::class.java.name, "[+] Sensor accuracy changed")
    }
}