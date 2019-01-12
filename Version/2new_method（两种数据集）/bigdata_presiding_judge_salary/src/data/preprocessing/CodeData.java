package data.preprocessing;

import java.io.*;
import java.util.Properties;

public class CodeData {
    public static void main(String[] args){
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");
        try {
            InputStream instream=new FileInputStream(new File("preprocessing.properties"));
            Properties pro=new Properties();
            pro.load(instream);

            String propertyfile_path=pro.getProperty("property_path").trim();
            int code_column_numb = Integer.parseInt(pro.getProperty("code_column_numb").trim());
            int[] code_column = new int[code_column_numb];
            String[] code_column_str = pro.getProperty("code_column").trim().split(",");
            for (int i=0;i<code_column_numb;i++){
                code_column[i] = Integer.parseInt(code_column_str[i])-1;
            }

            File properties=new File(propertyfile_path);
            InputStreamReader reader=new InputStreamReader(new FileInputStream(properties));
            BufferedReader in = new BufferedReader(reader);
            String line = in.readLine();
            String[][] property=new String[code_column_numb][];
            for (int i=0;i<code_column_numb;i++){
                property[i]=line.split(" ");
                line=in.readLine();
            }
            in.close();

            String origin_path = pro.getProperty("origin_path").trim();
            InputStreamReader o_reader = new InputStreamReader(new FileInputStream(new File(origin_path)));
            BufferedReader coded_in = new BufferedReader(o_reader);

            String coded_path = pro.getProperty("coded_path").trim();
            BufferedWriter coded_out = new BufferedWriter(new FileWriter(coded_path));

            String lineo=coded_in.readLine();
            while(lineo!=null){
                String[] line_arr = lineo.split(",");
                String[] need_feature=new String[code_column_numb];
                for (int i=0;i<code_column_numb;i++){
                    need_feature[i]=line_arr[code_column[i]];
                }
                for (int i=0;i<code_column_numb;i++){
                    for (int j=0;j<property[i].length;j++){
                        if (need_feature[i].equals(property[i][j])){
                            need_feature[i]=""+df.format((j+1)*1.0/100);
                        }
                    }
                }
                for (int i=0;i<code_column_numb;i++){
                    line_arr[code_column[i]]=need_feature[i];
                }
                coded_out.write(ArrayToString(line_arr));
                coded_out.flush();
                lineo=coded_in.readLine();
            }
            coded_in.close();
            coded_out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String ArrayToString(String[] array){
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
