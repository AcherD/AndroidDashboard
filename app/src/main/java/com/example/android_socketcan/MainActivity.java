package com.example.android_socketcan;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;

import android.graphics.PorterDuff;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    android_socketcan can0;

    private TextView tv_time;
    private TextView tv_gear;
    private ImageView iv_leftlight;
    private ImageView iv_rightlight;
    private ImageView iv_highbeam;
    private ImageView iv_lowbeam;
    private ImageView iv_wiper;
    private ImageView iv_lock;
    private ImageView iv_aeblevel;
    private DashboardView speedView;
    private DashboardView engineView;
//这里是自己定义的灯
    private ImageView iv_abs;
    private ImageView iv_BrakingSysErr;
    private ImageView iv_ChargingSysErr;
    private ImageView iv_EngineErr;
    private ImageView iv_ESP;
    private ImageView iv_EngineOverheat;
    private ImageView iv_LowEngineOil;
    private ImageView iv_TirePressure;
    private ImageView iv_MasterWaring;
    private ImageView iv_Airbag;
    private long id186WindowStartMs = 0;
    private int  id186Cnt = 0;
    //增加自动熄灭功能
    private final android.os.Handler lampHandler =
            new android.os.Handler(android.os.Looper.getMainLooper());
    private final java.util.Map<ImageView, Runnable> lampHideTasks = new java.util.HashMap<>();
    /** 当 on==true：立即显示，并在 500 ms 后自动隐藏；on==false：立即隐藏并取消定时 */
    private void pulseLamp(ImageView lamp, boolean on) {
        Runnable existing = lampHideTasks.get(lamp);
        if (existing != null) {
            lampHandler.removeCallbacks(existing);
            lampHideTasks.remove(lamp);
        }
        if (on) {
            lamp.setVisibility(View.VISIBLE);
            Runnable hide = new Runnable() {
                @Override public void run() { lamp.setVisibility(View.INVISIBLE); }
            };
            lampHideTasks.put(lamp, hide);
            lampHandler.postDelayed(hide, 500);
        } else {
            lamp.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get DisplayManager
        DisplayManager displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        // get Display object
        Display[] displays = displayManager.getDisplays();
        System.out.println("onCreate: displays length: " + displays.length);

        // set full screen
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // hide ui nvigation
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);

        setContentView(R.layout.activity_main);

        // find TextView and ImageView
        tv_time = findViewById(R.id.tv_time);
        tv_gear = findViewById(R.id.tv_gear);
        iv_leftlight = findViewById(R.id.iv_leftlight);
        iv_rightlight = findViewById(R.id.iv_rightlight);
        iv_highbeam = findViewById(R.id.iv_highbeam);
        iv_lowbeam = findViewById(R.id.iv_lowbeam);
        iv_wiper = findViewById(R.id.iv_wiper);
        iv_lock = findViewById(R.id.iv_lock);
        iv_aeblevel = findViewById(R.id.iv_aeblevel);

        speedView = findViewById(R.id.speedView);
        engineView = findViewById(R.id.engineView);

        iv_leftlight.setVisibility(View.INVISIBLE);
        iv_rightlight.setVisibility(View.INVISIBLE);
        iv_highbeam.setVisibility(View.INVISIBLE);
        iv_lowbeam.setVisibility(View.INVISIBLE);
        iv_wiper.setVisibility(View.INVISIBLE);
        iv_lock.setVisibility(View.INVISIBLE);
        iv_aeblevel.setVisibility(View.INVISIBLE);

        // find 自定义的灯
        iv_abs = findViewById(R.id.iv_abs);
        iv_BrakingSysErr = findViewById(R.id.iv_BrakingSysErr);
        iv_ChargingSysErr = findViewById(R.id.iv_ChargingSysErr);
        iv_EngineErr = findViewById(R.id.iv_EngineErr);
        iv_ESP = findViewById(R.id.iv_ESP);
        iv_EngineOverheat = findViewById(R.id.iv_EngineOverheat);
        iv_LowEngineOil = findViewById(R.id.iv_LowEngineOil);
        iv_TirePressure = findViewById(R.id.iv_TirePressure);
        iv_MasterWaring = findViewById(R.id.iv_MasterWaring);
        iv_Airbag = findViewById(R.id.Airbag);
        //set默认不可见
//        iv_abs.setVisibility(View.INVISIBLE);
//        iv_BrakingSysErr.setVisibility(View.INVISIBLE);
//        iv_ChargingSysErr.setVisibility(View.INVISIBLE);
//        iv_EngineErr.setVisibility(View.INVISIBLE);
//        iv_ESP.setVisibility(View.INVISIBLE);
//        iv_EngineOverheat.setVisibility(View.INVISIBLE);
//        iv_LowEngineOil.setVisibility(View.INVISIBLE);
//        iv_TirePressure.setVisibility(View.INVISIBLE);
//        iv_MasterWaring.setVisibility(View.INVISIBLE);
//        iv_Airbag.setVisibility(View.INVISIBLE);
//        iv_Airbag.setVisibility(View.INVISIBLE);




//        iv_leftlight.setAlpha(127);

        can0 = new android_socketcan();
        can0.fd = can0.socketcanOpen("can0");


        //send
//        new Thread() {
//            int[] data = {0xA0, 0xA1, 0xA2, 0xA3, 0xA4, 0xA5, 0xA6, 0xA7};
//            @Override
//            public void run() {
//                while (true) {
//                    // 获取当前时间
//                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//                    String currentTime = sdf.format(new Date());
//
//                    // show current time
//                    tv_time.setText(currentTime);
//
//                    try {
//                        sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    data[0] = (data[0] + 1) % 0xFF;
//                    can0.socketcanWrite(can0.fd, 0x123, 0, 0, 8, data);
//                }
//            }
//        }.start();

        //receive
        new Thread() {
            long[] ret = new long[12];
            @Override
            public void run() {
                while (true) {
                    ret = can0.socketcanRead(can0.fd);
                    long can0id = ret[0];
                    long can0eff = ret[1];
//                    long can0rtr = ret[2];
                    long can0len = ret[3];
                    long[] can0data = Arrays.copyOfRange(ret, 4, (int) (4+can0len));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String str = "";
                            String strid = Long.toHexString(can0id);
                            if(can0eff == 0) {
                                for(int i=0; i<3-strid.length(); i++) {
                                    strid = '0' + strid;
                                }
                            } else {
                                for(int i=0; i<8-strid.length(); i++) {
                                    strid = '0' + strid;
                                }
                            }
                            for(int i=0; i<can0len; i++) {
                                String hex = Long.toHexString(can0data[i]);
                                hex = (hex.length()==1) ? ('0'+hex) : hex;
                                str = str + hex;
                            }
                            str = str.toUpperCase();

                            // data frame process
                            switch (strid){
                                case "213":
                                    //beam head
                                    int BeamHeadlightSt = (int) CanSignalProcessor.SignalGet(str, "BeamHeadlightSt");

                                    iv_lowbeam.setVisibility((BeamHeadlightSt == 1)?View.VISIBLE:View.INVISIBLE);
                                    iv_highbeam.setVisibility((BeamHeadlightSt == 2)?View.VISIBLE:View.INVISIBLE);

                                    //turn light animation
                                    int TurnLightSt = (int) CanSignalProcessor.SignalGet(str, "TurnLightSt");
                                    iv_leftlight.setVisibility((TurnLightSt == 1)?View.VISIBLE:View.INVISIBLE);
                                    iv_rightlight.setVisibility((TurnLightSt == 2)?View.VISIBLE:View.INVISIBLE);

                                    //body lock
                                    int VehicleBodyLockSt = (int) CanSignalProcessor.SignalGet(str, "VehicleBodyLockSt");
                                    iv_lock.setVisibility((VehicleBodyLockSt == 1)?View.VISIBLE:View.INVISIBLE);

                                    break;
                                case "214":
                                    //wiper
                                    int WindshieldWiperSt = (int) CanSignalProcessor.SignalGet(str, "WindshieldWiperSt");

                                    if (WindshieldWiperSt == 1){
                                        iv_wiper.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                                        iv_wiper.setVisibility(View.VISIBLE);
                                    }else if (WindshieldWiperSt == 2){
                                        iv_wiper.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                                        iv_wiper.setVisibility(View.VISIBLE);
                                    }else if (WindshieldWiperSt == 3){
                                        iv_wiper.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                                        iv_wiper.setVisibility(View.VISIBLE);
                                    }else{
                                        iv_wiper.setVisibility(View.INVISIBLE);
                                    }


//                                    int color = ContextCompat.getColor(getApplicationContext(), R.color.light_blue_400);

//                                    ColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
//                                    iv_wiper.setColorFilter(colorFilter);

                                    break;
                                case "0e0":
                                    //vehicle speed
                                    float VehicleSpeed = (float) CanSignalProcessor.SignalGet(str, "VehicleSpeed");
                                    speedView.setSpeed(VehicleSpeed); // Set initial speed

                                    //engine speed
                                    float EngineSpeed = (float) CanSignalProcessor.SignalGet(str, "EngineSpeed");
                                    engineView.setSpeed(EngineSpeed);

                                    break;

                                case "0f0":
                                    //gear
                                    int Gear = (int) CanSignalProcessor.SignalGet(str, "Gear");
                                    if (Gear == -1){
                                        tv_gear.setText("R");
                                    }else if (Gear == 0){
                                        tv_gear.setText("N");
                                    }else if (Gear > 0){
                                        tv_gear.setText(String.valueOf(Gear));
                                    }

                                    break;

                                case "102":
                                    //AEB Level
                                    int AEBLvl = (int) CanSignalProcessor.SignalGet(str, "AEBLvl");
                                    if (AEBLvl == 1){
                                        iv_aeblevel.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                                        iv_aeblevel.setVisibility(View.VISIBLE);
                                    }else if (AEBLvl == 2){
                                        iv_aeblevel.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                                        iv_aeblevel.setVisibility(View.VISIBLE);
                                    }else if (AEBLvl == 3){
                                        iv_aeblevel.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                                        iv_aeblevel.setVisibility(View.VISIBLE);
                                    }else{
                                        iv_aeblevel.setVisibility(View.INVISIBLE);
                                    }

                                    break;

                                    //0x180 ABS[0,8] esp[16,24] tirePressure[28,32]
                                case "180": {
                                    // ABS
                                    int ABSWarn = (int) CanSignalProcessor.SignalGet(str, "ABSWarn");
                                    pulseLamp(iv_abs, (ABSWarn == 1));
                                    //esp
                                    int EngineErr = (int) CanSignalProcessor.SignalGet(str, "EngineErr");
                                    pulseLamp(iv_EngineErr,(EngineErr == 1));
                                    //tirePressure
                                    int TirePressure = (int) CanSignalProcessor.SignalGet(str, "TirePressure");
                                    pulseLamp(iv_TirePressure,(TirePressure == 16));
                                    break;
                                }
                                case "181":{
                                    //EngineOverheat
                                    double EngineTemperature = CanSignalProcessor.SignalGet(str, "EngineTemperature");
                                    if (EngineTemperature >= 21845.0){
                                        pulseLamp(iv_EngineOverheat,true);} //engine temperature over 5555 in HEX
                                    break;
                                }
                                case "182":{
                                    // LowEngineOil
                                    double LowEngineOil = CanSignalProcessor.SignalGet(str, "LowEngineOil");
                                    if (LowEngineOil <=50.0){
                                        pulseLamp(iv_LowEngineOil,true); //LowEngineOil under 32 in HEX
                                    }
                                    break;
                                }
                                case "183":{
                                    int Brake_System_Status = (int)CanSignalProcessor.SignalGet(str,"BrakeSystemStatus");
                                    int Hand_Brake= (int)CanSignalProcessor.SignalGet(str,"HandBrake");
                                    if (Brake_System_Status == 1 && Hand_Brake==16){
                                        pulseLamp(iv_BrakingSysErr,true);
                                    }

                                    break;
                                }
                                case "184":{
                                    double Battery_Voltage = CanSignalProcessor.SignalGet(str,"BatteryVoltage");
                                    double Ele_Generator_Power = CanSignalProcessor.SignalGet(str,"EleGeneratorPower");
                                    if (Battery_Voltage <= 150.0 && Ele_Generator_Power<=4.0){
                                        pulseLamp(iv_ChargingSysErr,true);
                                    }
                                    break;
                                }
                                case "185":{
                                    double Steering_Angle = CanSignalProcessor.SignalGet(str,"SteeringAngle");
                                    double Brake_System_Status = CanSignalProcessor.SignalGet(str,"BrakeSystemStatus");
                                    if (Steering_Angle >=90.0 && Brake_System_Status == 1.0){
                                        pulseLamp(iv_ESP,true);
                                    }
                                    break;
                                }
                                case "186":{
                                    int Airbag = (int) CanSignalProcessor.SignalGet(str, "Airbag");
                                    if (Airbag == 1) {
                                        long now = android.os.SystemClock.uptimeMillis();

                                        // 如果窗口还没开始，或者已经超出2000ms，则重开窗口
                                        if (id186WindowStartMs == 0 || (now - id186WindowStartMs) > 3000) {
                                            id186WindowStartMs = now;
                                            id186Cnt = 1;
                                        } else {
                                            id186Cnt++;
                                        }
                                        // 2000ms内累计>=3条 -> 点亮 Airbag warning
                                        if (id186Cnt >= 3) {
                                            pulseLamp(iv_Airbag, true);

                                            // 触发后重置，方便下一轮再次触发
                                            id186WindowStartMs = 0;
                                            id186Cnt = 0;
                                        }
                                    }
                                    break;
                                }

                                /*
                                case "180": {
                                    // ABS
                                    int ABSWarn = (int) CanSignalProcessor.SignalGet(str, "ABSWarn");
                                    pulseLamp(iv_abs, (ABSWarn == 1));
//                                    iv_abs.setVisibility((ABSWarn == 1) ? View.VISIBLE : View.INVISIBLE);
                                    break;
                                }
                                case "181": {
                                    // 刹车系统故障
                                    int BrakingSysErr = (int) CanSignalProcessor.SignalGet(str, "BrakingSysErr");
                                    pulseLamp(iv_BrakingSysErr,(BrakingSysErr == 1));
//                                    iv_BrakingSysErr.setVisibility((BrakingSysErr == 1) ? View.VISIBLE : View.INVISIBLE);
                                    break;
                                }
                                case "182": {
                                    // 充电系统故障
                                    int ChargingSysErr = (int) CanSignalProcessor.SignalGet(str, "ChargingSysErr");
                                    pulseLamp(iv_ChargingSysErr,(ChargingSysErr == 1));
//                                    iv_ChargingSysErr.setVisibility((ChargingSysErr == 1) ? View.VISIBLE : View.INVISIBLE);
                                    break;
                                }
                                case "183": {
                                    // 发动机故障
                                    int EngineErr = (int) CanSignalProcessor.SignalGet(str, "EngineErr");
                                    pulseLamp(iv_EngineErr,(EngineErr == 1));
//                                    iv_EngineErr.setVisibility((EngineErr == 1) ? View.VISIBLE : View.INVISIBLE);
                                    break;
                                }
                                case "184": {
                                    // ESP/稳定系统
                                    int ESPWarn = (int) CanSignalProcessor.SignalGet(str, "ESPWarn");
                                    pulseLamp(iv_ESP,(ESPWarn == 1));
//                                    iv_ESP.setVisibility((ESPWarn == 1) ? View.VISIBLE : View.INVISIBLE);
                                    break;
                                }
                                case "185": {
                                    // 发动机过热
                                    int EngineOverheat = (int) CanSignalProcessor.SignalGet(str, "EngineOverheat");
                                    pulseLamp(iv_EngineOverheat,(EngineOverheat == 1));
//                                    iv_EngineOverheat.setVisibility((EngineOverheat == 1) ? View.VISIBLE : View.INVISIBLE);
                                    break;
                                }
                                case "186": {
                                    // 机油压力/油位过低
                                    int LowEngineOil = (int) CanSignalProcessor.SignalGet(str, "LowEngineOil");
                                    pulseLamp(iv_LowEngineOil,(LowEngineOil == 1));
//                                    iv_LowEngineOil.setVisibility((LowEngineOil == 1) ? View.VISIBLE : View.INVISIBLE);
                                    break;
                                }
                                case "187": {
                                    // 胎压
                                    int TirePressure = (int) CanSignalProcessor.SignalGet(str, "TirePressure");
                                    pulseLamp(iv_TirePressure,(TirePressure == 1));
//                                    iv_TirePressure.setVisibility((TirePressure == 1) ? View.VISIBLE : View.INVISIBLE);
                                    break;
                                }
                                case "188": {
                                    //  主警报
                                    int MasterWaring = (int) CanSignalProcessor.SignalGet(str, "MasterWarning");
                                    pulseLamp(iv_MasterWaring,(MasterWaring == 1));
//                                    iv_MasterWaring.setVisibility((MasterWaring == 1) ? View.VISIBLE : View.INVISIBLE);
                                    break;
                                }
                                case "189": {
                                    // 安全气囊
                                    int Airbag = (int) CanSignalProcessor.SignalGet(str, "Airbag");
                                    pulseLamp(iv_Airbag,(Airbag == 1));
//                                    iv_Airbag.setVisibility((Airbag == 1) ? View.VISIBLE : View.INVISIBLE);
                                    break;
                                }
                                */


                            }
                        }
                    });
                }
            }
        }.start();

    }
}