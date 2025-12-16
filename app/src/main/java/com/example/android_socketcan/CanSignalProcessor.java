package com.example.android_socketcan;

import android.util.Log;

import java.util.HashMap;

public class CanSignalProcessor {

    private static final HashMap<String, HashMap<String, String>> msgDics = new HashMap<>();

    static {
        addSignalDefinitions();
    }

    private static void addSignalDefinitions() {
        // 添加所有信号定义

        // SG_ TurnLightSt : 8|2@1+ (1,0) [0|2] ""  HU,GW
        HashMap<String, String> msgDic1 = new HashMap<>();
        msgDic1.put("startBit", "8");
        msgDic1.put("signalSize", "2");
        msgDic1.put("byteOrder", "1");
        msgDic1.put("factor", "1.0");
        msgDic1.put("offset", "0.0");
        msgDics.put("TurnLightSt", msgDic1);

        // SG_ VehicleBodyLockSt : 7|1@1+ (1,0) [0|1] ""  TBOX,HU,GW
        HashMap<String, String> msgDic2 = new HashMap<>();
        msgDic2.put("startBit", "48");
        msgDic2.put("signalSize", "1");
        msgDic2.put("byteOrder", "1");
        msgDic2.put("factor", "1.0");
        msgDic2.put("offset", "0.0");
        msgDics.put("VehicleBodyLockSt", msgDic2);

        // SG_ BeamHeadlightSt : 0|2@1+ (1,0) [0|3] ""  HU,GW
        HashMap<String, String> msgDic3 = new HashMap<>();
        msgDic3.put("startBit", "0");
        msgDic3.put("signalSize", "2");
        msgDic3.put("byteOrder", "1");
        msgDic3.put("factor", "1.0");
        msgDic3.put("offset", "0.0");
        msgDics.put("BeamHeadlightSt", msgDic3);

        // SG_ WindshieldWiperSt : 4|2@1+ (1,0) [0|3] ""  HU,GW
        HashMap<String, String> msgDic4 = new HashMap<>();
        msgDic4.put("startBit", "0");
        msgDic4.put("signalSize", "2");
        msgDic4.put("byteOrder", "1");
        msgDic4.put("factor", "1.0");
        msgDic4.put("offset", "0.0");
        msgDics.put("WindshieldWiperSt", msgDic4);

        // SG_ VehicleSpeed : 0|16@1- (1,-128) [-128|240] "km/h"  GW,ADAS,HU
        HashMap<String, String> msgDic5 = new HashMap<>();
        msgDic5.put("startBit", "0");
        msgDic5.put("signalSize", "16");
        msgDic5.put("byteOrder", "1");
        msgDic5.put("factor", "1.0");
        msgDic5.put("offset", "-128.0");
        msgDics.put("VehicleSpeed", msgDic5);

        // SG_ EngineSpeed : 16|16@1+ (1,0) [0|10000] "rpm"  HU,GW
        HashMap<String, String> msgDic6 = new HashMap<>();
        msgDic6.put("startBit", "16");
        msgDic6.put("signalSize", "16");
        msgDic6.put("byteOrder", "1");
        msgDic6.put("factor", "1.0");
        msgDic6.put("offset", "0.0");
        msgDics.put("EngineSpeed", msgDic6);

        // SG_ Gear : 0|4@1- (1,-1) [-1|7] ""  HU,PC,GW
        HashMap<String, String> msgDic7 = new HashMap<>();
        msgDic7.put("startBit", "0");
        msgDic7.put("signalSize", "4");
        msgDic7.put("byteOrder", "1");
        msgDic7.put("factor", "1.0");
        msgDic7.put("offset", "-1.0");
        msgDics.put("Gear", msgDic7);

        // SG_ AEBLvl : 0|2@1+ (1,0) [0|3] ""  HU,GW
        HashMap<String, String> msgDic8 = new HashMap<>();
        msgDic8.put("startBit", "0");
        msgDic8.put("signalSize", "2");
        msgDic8.put("byteOrder", "1");
        msgDic8.put("factor", "1.0");
        msgDic8.put("offset", "0.0");
        msgDics.put("AEBLvl", msgDic8);

       /*
        // 0x180 ABSWarn
        HashMap<String, String> dic_ABSWarn = new HashMap<>();
        dic_ABSWarn.put("startBit", "0");
        dic_ABSWarn.put("signalSize", "8");
        dic_ABSWarn.put("byteOrder","1");
        dic_ABSWarn.put("factor", "1.0");
        dic_ABSWarn.put("offset", "0.0");
        msgDics.put("ABSWarn", dic_ABSWarn);

        // 0x181 BrakingSysErr
        HashMap<String, String> dic_BrakingSysErr = new HashMap<>();
        dic_BrakingSysErr.put("startBit", "0");
        dic_BrakingSysErr.put("signalSize", "8");
        dic_BrakingSysErr.put("byteOrder", "1");
        dic_BrakingSysErr.put("factor", "1.0");
        dic_BrakingSysErr.put("offset", "0.0");
        msgDics.put("BrakingSysErr", dic_BrakingSysErr);

        // 0x182 ChargingSysErr
        HashMap<String, String> dic_ChargingSysErr = new HashMap<>();
        dic_ChargingSysErr.put("startBit", "0");
        dic_ChargingSysErr.put("signalSize", "8");
        dic_ChargingSysErr.put("byteOrder", "1");
        dic_ChargingSysErr.put("factor", "1.0");
        dic_ChargingSysErr.put("offset", "0.0");
        msgDics.put("ChargingSysErr", dic_ChargingSysErr);

        // 0x183 EngineErr
        HashMap<String, String> dic_EngineErr = new HashMap<>();
        dic_EngineErr.put("startBit", "0");
        dic_EngineErr.put("signalSize", "8");
        dic_EngineErr.put("byteOrder", "1");
        dic_EngineErr.put("factor", "1.0");
        dic_EngineErr.put("offset", "0.0");
        msgDics.put("EngineErr", dic_EngineErr);

        //0x184 ESPWarn
        HashMap<String, String> dic_ESPWarn = new HashMap<>();
        dic_ESPWarn.put("startBit", "0");
        dic_ESPWarn.put("signalSize", "8");
        dic_ESPWarn.put("byteOrder", "1");
        dic_ESPWarn.put("factor", "1.0");
        dic_ESPWarn.put("offset", "0.0");
        msgDics.put("ESPWarn", dic_ESPWarn);

        // 0x185 EngineOverheat
        HashMap<String, String> dic_EngineOverheat = new HashMap<>();
        dic_EngineOverheat.put("startBit", "0");
        dic_EngineOverheat.put("signalSize", "8");
        dic_EngineOverheat.put("byteOrder", "1");
        dic_EngineOverheat.put("factor", "1.0");
        dic_EngineOverheat.put("offset", "0.0");
        msgDics.put("EngineOverheat", dic_EngineOverheat);

        // 0x186 LowEngineOil
        HashMap<String, String> dic_LowEngineOil = new HashMap<>();
        dic_LowEngineOil.put("startBit", "0");
        dic_LowEngineOil.put("signalSize", "8");
        dic_LowEngineOil.put("byteOrder", "1");
        dic_LowEngineOil.put("factor", "1.0");
        dic_LowEngineOil.put("offset", "0.0");
        msgDics.put("LowEngineOil", dic_LowEngineOil);

        // 0x187 TirePressure
        HashMap<String, String> dic_TirePressure = new HashMap<>();
        dic_TirePressure.put("startBit", "0");
        dic_TirePressure.put("signalSize", "8");
        dic_TirePressure.put("byteOrder", "1");
        dic_TirePressure.put("factor", "1.0");
        dic_TirePressure.put("offset", "0.0");
        msgDics.put("TirePressure", dic_TirePressure);

        // 0x188 MasterWarning
        HashMap<String, String> dic_MasterWarning = new HashMap<>();
        dic_MasterWarning.put("startBit", "0");
        dic_MasterWarning.put("signalSize", "8");
        dic_MasterWarning.put("byteOrder", "1");
        dic_MasterWarning.put("factor", "1.0");
        dic_MasterWarning.put("offset", "0.0");
        msgDics.put("MasterWarning", dic_MasterWarning);

        // 0x189 Airbag
        HashMap<String, String> dic_Airbag = new HashMap<>();
        dic_Airbag.put("startBit", "0");
        dic_Airbag.put("signalSize", "8");
        dic_Airbag.put("byteOrder", "1");
        dic_Airbag.put("factor", "1.0");
        dic_Airbag.put("offset", "0.0");
        msgDics.put("Airbag", dic_Airbag);
       */



        //new 0x180 ABS[0,8] esp[16,24] tirePressure[28,32]
        //abs
        HashMap<String, String> dic_ABSWarn = new HashMap<>();
        dic_ABSWarn.put("startBit", "0");
        dic_ABSWarn.put("signalSize", "8");
        dic_ABSWarn.put("byteOrder","1");
        dic_ABSWarn.put("factor", "1.0");
        dic_ABSWarn.put("offset", "0.0");
        msgDics.put("ABSWarn", dic_ABSWarn);
        //esp
        HashMap<String, String> dic_EngineErr = new HashMap<>();
        dic_EngineErr.put("startBit", "16");
        dic_EngineErr.put("signalSize", "8");
        dic_EngineErr.put("byteOrder", "1");
        dic_EngineErr.put("factor", "1.0");
        dic_EngineErr.put("offset", "0.0");
        msgDics.put("EngineErr", dic_EngineErr);
        //tirePressure
        HashMap<String, String> dic_TirePressure = new HashMap<>();
        dic_TirePressure.put("startBit", "32");
        dic_TirePressure.put("signalSize", "8");
        dic_TirePressure.put("byteOrder", "1");
        dic_TirePressure.put("factor", "1.0");
        dic_TirePressure.put("offset", "0.0");
        msgDics.put("TirePressure", dic_TirePressure);

        //new 0x181 EngineOverheat
        HashMap<String, String> dic_EngineOverheat = new HashMap<>();
        dic_EngineOverheat.put("startBit", "16");
        dic_EngineOverheat.put("signalSize", "16");
        dic_EngineOverheat.put("byteOrder", "1");
        dic_EngineOverheat.put("factor", "1.0");
        dic_EngineOverheat.put("offset", "0.0");
        msgDics.put("EngineTemperature", dic_EngineOverheat);

        //new 0x182 LowEngineOil
        HashMap<String, String> dic_LowEngineOil = new HashMap<>();
        dic_LowEngineOil.put("startBit", "48");
        dic_LowEngineOil.put("signalSize", "8");
        dic_LowEngineOil.put("byteOrder", "1");
        dic_LowEngineOil.put("factor", "1.0");
        dic_LowEngineOil.put("offset", "0.0");
        msgDics.put("LowEngineOil", dic_LowEngineOil);

        //brake_system_status
        HashMap<String, String> brake_system_status = new HashMap<>();
        brake_system_status.put("startBit", "8");
        brake_system_status.put("signalSize", "8");
        brake_system_status.put("byteOrder", "1");
        brake_system_status.put("factor", "1.0");
        brake_system_status.put("offset", "0.0");
        msgDics.put("BrakeSystemStatus", brake_system_status);

        //hand_brake
        HashMap<String, String> hand_brake = new HashMap<>();
        hand_brake.put("startBit", "24");
        hand_brake.put("signalSize", "8");
        hand_brake.put("byteOrder", "1");
        hand_brake.put("factor", "1.0");
        hand_brake.put("offset", "0.0");
        msgDics.put("HandBrake", hand_brake);

        //BatteryVoltage
        HashMap<String, String> Battery_Voltage = new HashMap<>();
        Battery_Voltage.put("startBit", "16");
        Battery_Voltage.put("signalSize", "8");
        Battery_Voltage.put("byteOrder", "1");
        Battery_Voltage.put("factor", "1.0");
        Battery_Voltage.put("offset", "0.0");
        msgDics.put("BatteryVoltage", Battery_Voltage);
        // EleGeneratorPower
        HashMap<String, String> Ele_Generator_Power = new HashMap<>();
        Ele_Generator_Power.put("startBit", "32");
        Ele_Generator_Power.put("signalSize", "4");
        Ele_Generator_Power.put("byteOrder", "1");
        Ele_Generator_Power.put("factor", "1.0");
        Ele_Generator_Power.put("offset", "0.0");
        msgDics.put("EleGeneratorPower", Ele_Generator_Power);
        //SteeringAngle
        HashMap<String, String> Steering_Angle = new HashMap<>();
        Steering_Angle.put("startBit", "16");
        Steering_Angle.put("signalSize", "8");
        Steering_Angle.put("byteOrder", "1");
        Steering_Angle.put("factor", "1.0");
        Steering_Angle.put("offset", "0.0");
        msgDics.put("SteeringAngle", Steering_Angle);
        // 0x186 Airbag
        HashMap<String, String> dic_Airbag = new HashMap<>();
        dic_Airbag.put("startBit", "0");
        dic_Airbag.put("signalSize", "8");
        dic_Airbag.put("byteOrder", "1");
        dic_Airbag.put("factor", "1.0");
        dic_Airbag.put("offset", "0.0");
        msgDics.put("Airbag", dic_Airbag);


    }

    public static double SignalGet(String dataFrame, String signal) {

        //data field
        String dataStr = "";
        for (int i = 0; i < dataFrame.length(); i+=2) {
            String tmp = Integer.toBinaryString(Integer.parseInt(dataFrame.substring(i , i + 2), 16));
            tmp = String.format("%8s", tmp).replace(' ', '0');
//            Log.i("CAN Tmp val","tmp:"+tmp);
            for (int j = 0; j < 8; j++) {
                dataStr += tmp.charAt(7 - j);
            }
        }

//        StringBuilder dataStr = new StringBuilder();
//        for (int i = 0; i < dataFrame.length(); i += 2) {
//            String hexByte = dataFrame.substring(i, i + 2);
//            String binaryByte = String.format("%8s", Integer.toBinaryString(Integer.parseInt(hexByte, 16))).replace(' ', '0');
//            String reversedBinary = new StringBuilder(binaryByte).reverse().toString();
//            dataStr.append(reversedBinary);
//        }

        int startBit = Integer.parseInt(msgDics.get(signal).get("startBit"));
        int signalSize = Integer.parseInt(msgDics.get(signal).get("signalSize"));
        int byteOrder = Integer.parseInt(msgDics.get(signal).get("byteOrder"));
        double factor = Double.parseDouble(msgDics.get(signal).get("factor"));
        double offset = Double.parseDouble(msgDics.get(signal).get("offset"));
//        Log.e("CAN信息",dataStr.toString());
        String signalStr = "";

        // Intel
        if (byteOrder == 1) {
            for (int i = 0; i < signalSize; i++) {
                signalStr += dataStr.charAt(startBit + signalSize - 1 - i);
            }
        }
        // Motorola
        else if (byteOrder == 0) {
            signalStr = "";
            int byteCount = 0;
            for (int i = 0; i < signalSize; i++) {
                signalStr += dataStr.charAt(startBit - i + byteCount * 8);
                if ((startBit - i + byteCount * 16) % 8 == 0) {
                    byteCount += 2;
                }
            }
        }
        // 计算物理值
        System.out.println(signalStr);
        double trueValue = Integer.parseInt(signalStr, 2) * factor + offset;
        System.out.println(trueValue);
//        Log.e("Print",String.valueOf(trueValue));
        return trueValue;
    }
}
