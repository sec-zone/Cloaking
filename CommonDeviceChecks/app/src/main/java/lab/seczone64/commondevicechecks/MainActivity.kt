package lab.seczone64.commondevicechecks

import android.content.Context
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val deviceCheck = DeviceCheck(this)
        if(deviceCheck.hashFlashUnit()){
            Log.v("deviceCheck", "[+] Flash unit available")
        }else{
            Log.v("deviceCheck", "[+] Device doesn't have flash unit.")
        }
        Log.v("deviceCheck", "[+] Camera counts: ${deviceCheck.getCameraCounts()}")
        deviceCheck.checkAccelerometerSensor()
        deviceCheck.checkLightSensor()
        deviceCheck.checkDeviceKernel()
        deviceCheck.checkBuildName()
        deviceCheck.checkDeviceDrivers()
        deviceCheck.checkCpuInfo()
        deviceCheck.checkAndroidOsBuild()
        deviceCheck.gettingSimOperator()
        disableWiFi()
    }

    fun disableWiFi(){
        val wifiManager = this.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.setWifiEnabled(false)
    }
}