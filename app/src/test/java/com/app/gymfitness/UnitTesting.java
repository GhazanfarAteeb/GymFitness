package com.app.gymfitness;

import static org.junit.Assert.assertEquals;
import com.app.gymfitness.utils.MyUtils;

import org.junit.Test;
public class UnitTesting {

    @Test
    public void trueTestValidateFieldTime(){
        boolean expected = true;
        boolean result = MyUtils.validateFieldTIme("10:24","12:10");
        assertEquals(expected, result);
    }

    @Test
    public void trueTestIsDataEmpty() {
        boolean expected = true;
        boolean result = MyUtils.checkData("This is a test case");
        assertEquals(expected,result);
    }

    @Test
    public void trueTestInstructorId() {
        boolean expected = true;
        boolean result = MyUtils.checkInstructorId(1,2);
        assertEquals(expected,result);
    }

    @Test
    public void falseTestValidateFieldTime(){
        boolean expected = false;
        boolean result = MyUtils.validateFieldTIme("12:10","10:24");
        assertEquals(expected, result);
    }

    @Test
    public void falseTestIsDataEmpty() {
        boolean expected = false;
        boolean result = MyUtils.checkData("");
        assertEquals(expected,result);
    }

    @Test
    public void falseTestInstructorId() {
        boolean expected = false;
        boolean result = MyUtils.checkInstructorId(1,1);
        assertEquals(expected,result);
    }
}
