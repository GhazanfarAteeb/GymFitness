package com.app.gymfitness;

import static org.junit.Assert.assertEquals;
import com.app.gymfitness.utils.MyUtils;

import org.junit.Test;
public class UnitTesting {

    @Test
    public void testValidateFieldTime(){
        boolean expected = true;
        boolean result = MyUtils.validateFieldTIme("10:24","12:10");
        assertEquals(expected, result);
    }



}
