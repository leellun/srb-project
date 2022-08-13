package com.newland.srb.common.utils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Author: leell
 * Date: 2022/8/10 19:55:52
 */
public class RandomUtils {
    private static final Random random=new Random();
    private static final DecimalFormat fourdf=new DecimalFormat("0000");
    private static final DecimalFormat sixdf=new DecimalFormat("000000");
    public static String getFourBitRandom(){
        return fourdf.format(random.nextInt(10000));
    }
}
