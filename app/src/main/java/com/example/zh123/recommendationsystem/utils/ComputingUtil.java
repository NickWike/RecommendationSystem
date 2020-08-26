package com.example.zh123.recommendationsystem.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by zh123 on 20-3-20.
 */

public class ComputingUtil {
    public static String StringNumberAdd(String n1,String n2){
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal decimalN1 = new BigDecimal(n1);
        BigDecimal decimalN2 = new BigDecimal(n2);
        BigDecimal addNumber = decimalN1.add(decimalN2);
        return df.format(addNumber);
    }

    public static String StringNumberSubtract(String n1,String n2){
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal decimalN1 = new BigDecimal(n1);
        BigDecimal decimalN2 = new BigDecimal(n2);
        BigDecimal addNumber = decimalN1.subtract(decimalN2);
        return df.format(addNumber);
    }

    public static String StringNumberMultiply(String n1,int n2){
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal decimalN1 = new BigDecimal(n1);
        BigDecimal decimalN2 = new BigDecimal(n2);
        BigDecimal addNumber = decimalN1.multiply(decimalN2);
        return df.format(addNumber);
    }
}
