package com.kmeans;
import java.text.DecimalFormat;
public class CreateData {
    public static void CreateData(int number,String filename) {
        DecimalFormat jtd=new DecimalFormat("#.##");
        Read_Write_data rwd=new Read_Write_data();
        String x,y;
        for (int i=0;i<number;i++){
            x=jtd.format(R_dom(-10,10));
            y=jtd.format(R_dom(-10,10));
            rwd.Write_line(filename,x+","+y+"\n");
        }
    }

    public static void main(String[] args) {
        CreateData(10000,"K-means.txt");
    }

    public static double R_dom(double a, double b) {
        if (a == b)
            return a;
        else if (a > b) {
            return b + Math.abs(b - a) * Math.random();
        } else {
            return a + Math.abs(b - a) * Math.random();
        }
    }
}