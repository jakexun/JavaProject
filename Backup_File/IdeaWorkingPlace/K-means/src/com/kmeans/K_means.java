package com.kmeans;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.kmeans.Tools.*;

public class K_means {
    public static double[][] K_means(int k,String filepath,int column) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Clean(k);
        Read_Write_data rwd=new Read_Write_data();
        double[][] scope=Scope(filepath,column);
        String[][] central=CreatingRandomCentral(scope,k);
        int[] classify=classify(filepath,central);
        while (IncludeZero(classify)){
            Clean(k);
            System.out.println("有0数据类！");
            central=CreatingRandomCentral(scope,k);
            classify=classify(filepath,central);
        }
        int iter=1;
        int[] beforclassify=new int[k];
        for (int i=0;i<k;i++){
            beforclassify[i]=classify[i];
        }
        while (!Arrayequals(classify,beforclassify)||iter==1){
            System.out.println();
            if (iter!=1){
                for (int i=0;i<k;i++){
                    beforclassify[i]=classify[i];
                }
            }
            System.out.println("第"+(iter-1)+"次分类完成！第"+iter+"次求中心点开始："+df.format(new Date()));// new Date()为获取当前系统时间
            central=GetCentral(k,classify,column);
            for (int i=0;i<central.length;i++){
                String line=ArrayToString(central[i]);
                System.out.print(line);
            }
            System.out.println();
            Clean(k);
            System.out.println("第"+iter+"次求中心点完成！第"+iter+"次分类开始："+df.format(new Date()));// new Date()为获取当前系统时间
            classify=classify(filepath,central);
            iter++;
        }
        File central_file=new File("central.txt");
        central_file.delete();
//        central=GetCentral(k,classify,column);
        Clean(k);
        classify(filepath,central);
//        GetCentral(k,classify,column);
        String centralfile="central.txt";
        for (int i=0;i<central.length;i++){
            String line=ArrayToString(central[i]);
//            System.out.print(line);
            rwd.Write_line(centralfile,line);
        }
        System.out.println();
       return scope;
    }

}
