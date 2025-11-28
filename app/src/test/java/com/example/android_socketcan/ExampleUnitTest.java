package com.example.android_socketcan;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testCanValueIsCorrect(){
        float VehicleSpeed_1 = (float) CanSignalProcessor.SignalGet("BC00000000000000", "VehicleSpeed");
        System.out.println(VehicleSpeed_1);
    }
}