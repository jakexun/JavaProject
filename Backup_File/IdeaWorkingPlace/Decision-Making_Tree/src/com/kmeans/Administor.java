package com.kmeans;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static com.kmeans.Tools.GetColumnNumber;

public class Administor{
    public static void main(String[] args) {
        try {
            InputStream inStream = new FileInputStream(new File("Configuration.properties"));
            Properties pro = new Properties();
            pro.load(inStream);

            String TFilepath = pro.getProperty("TFilepath").trim();
            String GDpath = pro.getProperty("GDpath").trim();

            int K = Integer.parseInt(pro.getProperty("K").trim());
            int Runtimes = Integer.parseInt(pro.getProperty("Runtimes").trim());
            int Row = Integer.parseInt(pro.getProperty("Row").trim());

            Read_Write_data rwd=new Read_Write_data();
            K_means km=new K_means();

            for (int i=0;i<Runtimes;i++){

                File file=new File(TFilepath);
                file.delete();

                rwd.Read_write(GDpath,TFilepath,Row);
                int column=GetColumnNumber(TFilepath);
                km.K_means(K,TFilepath,column);//kmeans¾ÛÀà
                /*File centralfile=new File("central.txt");
                centralfile.delete();*/
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
