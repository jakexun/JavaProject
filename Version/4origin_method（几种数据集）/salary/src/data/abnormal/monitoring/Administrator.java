package data.abnormal.monitoring;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Administrator {
    public static void main(String[] args) {
        try{
            InputStream inStream = new FileInputStream(new File("function.properties"));
            Properties pro = new Properties();
            pro.load(inStream);
            Abnormal_data_monitoring adm = new Abnormal_data_monitoring();
            String get_data_path = pro.getProperty("get_data_path").trim();
            String filepath = pro.getProperty("filepath").trim();
            int row = Integer.parseInt(pro.getProperty("row").trim());
            int k = Integer.parseInt(pro.getProperty("k").trim());
            int iteration_times = Integer.parseInt(pro.getProperty("iteration_times").trim());
            int column = Integer.parseInt(pro.getProperty("column").trim());
            int times = Integer.parseInt(pro.getProperty("times").trim());
            String fitness_file = pro.getProperty("fitness_file").trim();/*1*/
            Statistical_all statistica = new Statistical_all();
            for (int i = 0;i<times;i++){
                adm.monitoring_checking(get_data_path,filepath,row,column,k,iteration_times,i+1);
                statistica.Statisticing(filepath,"test"+(i+1)+ "\\"+fitness_file,row,column,k,i+1);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
