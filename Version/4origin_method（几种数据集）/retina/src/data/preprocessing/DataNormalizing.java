package data.preprocessing;

import java.io.*;
import java.util.Properties;

public class DataNormalizing {
    public static void main(String[] args) throws Exception{
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.#########");
        int correct=0,error=0;
        try {
            InputStream instream=new FileInputStream(new File("Data_Cleaning.properties"));
            Properties pro=new Properties();
            pro.load(instream);
            String filepath=pro.getProperty("coded_path").trim();
            double[][] scope=ScopeCaculor(filepath);

            InputStreamReader oreader = new InputStreamReader(new FileInputStream(new File(filepath)));
            BufferedReader normalizedin = new BufferedReader(oreader);

            File normalizedpath = new File(pro.getProperty("normalized_path").trim()); // 相对路径，如果没有则要建立一个新的output。txt文件
            normalizedpath.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(normalizedpath));
            double[] normalized_data=new double[6];
            for (int i=0;i<6;i++){
                normalized_data[i]=0;
            }
            String line=normalizedin.readLine();
            while (line!=null){
                String[] line_arr=line.split(",");
                normalized_data[0]=Double.parseDouble(line_arr[0])/(scope[1][0]-scope[0][0]);
                normalized_data[1]=Double.parseDouble(line_arr[2])/(scope[1][2]-scope[0][2]);
                normalized_data[2]=Double.parseDouble(line_arr[4])/(scope[1][4]-scope[0][4]);
                normalized_data[3]=Double.parseDouble(line_arr[10])/(scope[1][10]-scope[0][10]);
                normalized_data[4]=Double.parseDouble(line_arr[11])/(scope[1][11]-scope[0][11]);
                normalized_data[5]=Double.parseDouble(line_arr[12])/(scope[1][12]-scope[0][12]);
                line_arr[0]=df.format(normalized_data[0]);
                line_arr[2]=df.format(normalized_data[1]);
                line_arr[4]=df.format(normalized_data[2]);
                line_arr[10]=df.format(normalized_data[3]);
                line_arr[11]=df.format(normalized_data[4]);
                line_arr[12]=df.format(normalized_data[5]);
                if (line_arr[14].equals(">50K")){
                    line_arr[14]="abnormal";
                    error++;
                }else{
                    line_arr[14]="normal";
                    correct++;
                }
                out.write(ArraytoString(line_arr)+"\n");
                line=normalizedin.readLine();
            }
            normalizedin.close();
            out.close();
            System.out.println("abnormal="+error+" normal="+correct);
            /*for (int i=0;i<scope[0].length;i++){
                System.out.print(scope[0][i]+" ");
            }
            System.out.println();
            for (int i=0;i<scope[1].length;i++){
                System.out.print(scope[1][i]+" ");
            }
            System.out.println();*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static double[][] ScopeCaculor(String filepath)throws Exception{
        double[][] scope=new double[2][14];
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(filepath)));
            BufferedReader br=new BufferedReader(reader);
            String line=br.readLine();
            String[] line_data=line.split(",");
            for (int i=0;i<line_data.length-1;i++){
               scope[0][i]=scope[1][i]= Double.parseDouble(line_data[i]);
            }
            while (line!=null){
                line_data=line.split(",");
                for (int i=0;i<line_data.length-1;i++){
                    if (scope[0][i]>Double.parseDouble(line_data[i])){
                        scope[0][i]=Double.parseDouble(line_data[i]);
                    }
                    if (scope[1][i]<Double.parseDouble(line_data[i])){
                        scope[1][i]=Double.parseDouble(line_data[i]);
                    }
                }
                line=br.readLine();
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return scope;
    }
    public static String ArraytoString(String[] array){
        String str="";
        for (int i=0;i<array.length;i++){
            if(i!=array.length-1){
                str+=array[i]+",";
            }else {
                str+=array[i];
            }
        }
        return str;
    }
}
