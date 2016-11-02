package com.example;

import com.example.StockApi;

public class StockApiTest {


    public static void main(String args[])
    {
        Stock stock = new Stock("601988");
        StockApi.getStockInfo(stock);
        System.out.println("stock:" + stock.getName() + "\n"
                + "stock num    :" + stock.getNum() + "\n"
                + "current price:" + stock.getCurrent() + "\n");
    }
}
