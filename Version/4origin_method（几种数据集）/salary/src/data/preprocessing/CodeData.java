package data.preprocessing;

import java.io.*;
import java.util.Properties;

public class CodeData {
    public static void main(String[] args)throws Exception{
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");
        try {
            InputStream instream=new FileInputStream(new File("Data_Cleaning.properties"));
            Properties pro=new Properties();
            pro.load(instream);

            String propertyfile_path=pro.getProperty("property_path").trim();
            File properties=new File(propertyfile_path);
            InputStreamReader reader=new InputStreamReader(new FileInputStream(properties));
            BufferedReader in = new BufferedReader(reader);
            String line = in.readLine();
            String[][] property=new String[8][];
            for (int i=0;i<8;i++){
                property[i]=line.split(" ");
                line=in.readLine();
            }
            in.close();

            String originpath = pro.getProperty("cleaned_path").trim();
            InputStreamReader oreader = new InputStreamReader(new FileInputStream(new File(originpath)));
            BufferedReader codedin = new BufferedReader(oreader);

            String codedpath = pro.getProperty("coded_path").trim();
            BufferedWriter codedout = new BufferedWriter(new FileWriter(codedpath));

            String lineo=codedin.readLine();
            while(lineo!=null){
                String[] line_arr = lineo.split(",");
                String[] need_feature=new String[8];
                need_feature[0]=line_arr[1];
                need_feature[1]=line_arr[3];
                need_feature[2]=line_arr[5];
                need_feature[3]=line_arr[6];
                need_feature[4]=line_arr[7];
                need_feature[5]=line_arr[8];
                need_feature[6]=line_arr[9];
                need_feature[7]=line_arr[13];
                for (int i=0;i<8;i++){
                    for (int j=0;j<property[i].length;j++){
                        if (need_feature[i].equals(property[i][j])){
                            need_feature[i]=""+df.format((j+1)*1.0/100);
                        }
                    }
                }
                line_arr[1]=need_feature[0];
                line_arr[3]=need_feature[1];
                line_arr[5]=need_feature[2];
                line_arr[6]=need_feature[3];
                line_arr[7]=need_feature[4];
                line_arr[8]=need_feature[5];
                line_arr[9]=need_feature[6];
                line_arr[13]=need_feature[7];
                codedout.write(ArraytoString(line_arr));
                codedout.flush();
                lineo=codedin.readLine();
            }
            codedin.close();
            codedout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String ArraytoString(String[] array){
        String str="";
        for (int i=0;i<array.length;i++){
            if (i!=array.length-1){
                str+=array[i]+",";
            } else {
                str+=array[i]+"\n";
            }
        }
        return str;
    }
}
/*for (int i=0;i<8;i++){
                    for (int j=0;j<property[i].length;j++){
                        System.out.print(property[i][j]+" ");
                    }
                    System.out.println();
                }
            for (int i=0;i<8;i++)
                System.out.print(need_feature[i]);
            System.out.println();
            System.out.println("----------------------------");
                for (int i=0;i<8;i++){
                    System.out.println(need_feature[i]);
                    for (int j=0;j<property[i].length;j++){
                        System.out.print("]]]"+property[i][j]+" "+property[i][j].equals(need_feature[i])+" ");
                        if (need_feature[i].equals(property[i][j])){
                            need_feature[i]=""+(j+1);
                        }
                    }
                    System.out.println();
                }*/