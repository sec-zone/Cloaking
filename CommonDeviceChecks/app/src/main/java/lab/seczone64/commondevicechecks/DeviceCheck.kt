package lab.seczone64.commondevicechecks

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.os.Build
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.util.Log
import java.io.BufferedReader
import java.io.FileReader
import java.lang.Exception

class DeviceCheck(private val context: Context) {
    fun hashFlashUnit(): Boolean{
        return context.packageManager.hasSystemFeature((PackageManager.FEATURE_CAMERA_FLASH))
    }
    fun getCameraCounts(): Int {
        val manager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        return manager.cameraIdList.size
    }
    fun checkAccelerometerSensor(){
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(MySensorListener(), accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL)
    }
    fun checkLightSensor(){
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        sensorManager.registerListener(MySensorListener(), lightSensor,
            SensorManager.SENSOR_DELAY_NORMAL)
    }
    fun checkDeviceKernel(){
        val deviceKernel = System.getProperty("os.version")
        Log.v("DeviceKernel", "[+] Device Kernel: $deviceKernel")
    }
    fun checkBuildName(){
        val buildName = Build.DISPLAY
        Log.v("BuildName", "[+] Build Name: $buildName")
    }
    fun checkCpuInfo(){
        val cpuInfo = BufferedReader(FileReader("/proc/cpuinfo")).readText()
        Log.v("CPUInfo", "[+] CPU Info: $cpuInfo")
    }
    fun checkDeviceDrivers(){
        Log.v("Drivers", "Drivers")
        Log.v("Drivers", "Drivers")
        var lines = listOf<String>("")
        try{
            lines = BufferedReader(FileReader("/proc/tty/drivers")).readLines()
        }catch (e: Exception)
        {
            Log.v("Drivers", "[+] ${e.message.toString()}")
            return
        }
        for(driver in lines){
            Log.v("Drivers","[+]$driver")
        }
    }

    fun gettingSimOperator(){
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        Log.v("SimOperator", "[+] Sim Operator Code: ${tm.simOperator}")
        Log.v("SimOperator", "[+] Sim Operator Name: ${tm.simOperatorName}")
        Log.v("SimOperator", "[+] Network Operator Name: ${tm.networkOperatorName}")
    }

    fun checkAndroidOsBuild(){
        Log.v("AndroidOSBuild", "\t[+] Board: ${Build.BOARD}")
        Log.v("AndroidOSBuild", "\t[+] Device: ${Build.DEVICE}")
        Log.v("AndroidOSBuild", "\t[+] Brand: ${Build.BRAND}")
        Log.v("AndroidOSBuild", "\t[+] Model: ${Build.MODEL}")
        Log.v("AndroidOSBuild", "\t[+] Manufacture: ${Build.MANUFACTURER}")
        Log.v("AndroidOSBuild", "\t[+] Product: ${Build.PRODUCT}")
    }


    
}