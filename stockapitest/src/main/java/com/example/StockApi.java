package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by simonchung on 26/11/15.
 */
public class StockApi {
    public static String STOCK_API = "http://hq.sinajs.cn/list=";
    public static int URI_STOCK_NAME = 0;
    private static int URI_STOCK_CURRENT_PRICE = 3;
    private static int URI_STOCK_MAX_PRICE = 4;
    private static int URI_STOCK_MIN_PRICE = 5;


    //    private static void getStockInfo(Stock stock) {
//        URL oracle = null;
//        BufferedReader in = null;
//        String inputLine;
//        try {
//            oracle = new URL(STOCK_API + stock.getNum());
//            in = new BufferedReader(new InputStreamReader(oracle.openStream()));
//            inputLine = in.readLine();
//            System.out.println(inputLine);
//            stock.setName(getStockName(inputLine, URI_STOCK_NAME));
//            String str = getStockPrice(inputLine, URI_STOCK_CURRENT_PRICE);
//            stock.setCurrent(Float.parseFloat(str));
//            str = getStockPrice(inputLine, URI_STOCK_MAX_PRICE);
//            stock.setMax(Float.parseFloat(str));
//            str = getStockPrice(inputLine, URI_STOCK_MIN_PRICE);
//            stock.setMin(Float.parseFloat(str));
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    static String getStockName(String str, int index) {
        return findMatchString(str, "\"", ",", index);
    }
    static String getStockPrice(String str,int index) {
        return findMatchString(str, ",", ",", index);
    }
    static String findMatchString(String str,String start, String end, int index)
    {
        Pattern p = Pattern.compile(start + "(.*?)" + end);
        Matcher m = p.matcher(str);
        for(int i = 0; m.find() ; i++) {
            if (i == index) {
                return m.group(1);
            }
        }
        return null;
    }
    static void getStockInfo(Stock stock) {
        URL oracle = null;
        BufferedReader in = null;
        String inputLine;
        String stockNum = null;
        try {
            if (stock.getNum().charAt(0) == '6') {
                stockNum = new String("sh" + stock.getNum());
            } else if(stock.getNum().charAt(0) == '0') {
                stockNum = new String("sz" + stock.getNum());
            } else {
                return;
            }
            oracle = new URL(STOCK_API + stockNum);
            in = new BufferedReader(new InputStreamReader(oracle.openStream(), "GB2312"));
            inputLine = in.readLine();
            System.out.println(inputLine);
            Pattern p = Pattern.compile(",");
            String list[] = p.split(inputLine);
            stock.setName(getStockName(inputLine, URI_STOCK_NAME));
            stock.setCurrent(Float.parseFloat(list[URI_STOCK_CURRENT_PRICE]));
            stock.setMax(Float.parseFloat(list[URI_STOCK_MAX_PRICE]));
            stock.setMin(Float.parseFloat(list[URI_STOCK_MIN_PRICE]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
