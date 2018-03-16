package com.my.test;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void af() throws Exception {
        Date date=new Date();
        long time = date.getTime();
        System.out.println(time);

        Date date2=new Date(time);
        System.out.println(date2.getTime());
    }
    @Test
    public void asfd() throws Exception {

    }
}