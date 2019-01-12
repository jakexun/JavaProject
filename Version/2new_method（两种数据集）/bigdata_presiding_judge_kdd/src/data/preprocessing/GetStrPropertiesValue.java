package data.preprocessing;

import java.io.*;
import java.util.Properties;

public class GetStrPropertiesValue {
    public static void main(String[] args) {
        try {
            InputStream in_stream = new FileInputStream(new File("preprocessing.properties"));
            Properties pro = new Properties();
            pro.load(in_stream);

            /* 读入TXT文件 */
            String origin_path = pro.getProperty("origin_path").trim();

            int code_column_numb = Integer.parseInt(pro.getProperty("code_column_numb").trim());
            int[] code_column = new int[code_column_numb];
            String[] code_column_str = pro.getProperty("code_column").trim().split(",");
            for (int i=0;i<code_column_numb;i++){
                code_column[i] = Integer.parseInt(code_column_str[i])-1;
            }

            File filename = new File(origin_path);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader in = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            String line = in.readLine();
            String[] line_arr=line.split(",");
            String[][] feature=new String[code_column_numb][200];
            for (int i=0;i<code_column_numb;i++){
                feature[i][0]=line_arr[code_column[i]];
            }
            int[] start_i=new int[code_column_numb];
            for (int i=0;i<code_column_numb;i++){
                start_i[i]=0;
            }
            while (line != null) {
                line = in.readLine(); // 一次读入一行数据
                if (line != null){

                    line_arr=line.split(",");
                    String[] need_feature=new String[code_column_numb];
                    for (int i=0;i<code_column_numb;i++){
                        need_feature[i]=line_arr[code_column[i]];
                    }

                    for (int i=0;i<code_column_numb;i++){
                        int j = start_i[i];
                        while(j>=0&&!need_feature[i].equals(feature[i][j]))j--;
                        if (j<0){
                            feature[i][++start_i[i]]=need_feature[i];
                        }
                    }
                }
            }
            in.close();     //关闭读取文件
            File property_file = new File("property.txt");
            property_file.createNewFile();
            BufferedWriter property_out=new BufferedWriter(new FileWriter(property_file));
            for (int i=0;i<code_column_numb;i++){
                String fpro="";
                for (int j=0;feature[i][j]!=null;j++){
                    fpro+=feature[i][j]+" ";
                }
                property_out.write(fpro+"\n");
                property_out.flush();
            }
            property_out.close();
            int[] k=new int[code_column_numb];
            for (int i=0;i<k.length;i++)
                k[i]=0;
            for (int i=0;i<feature[0].length;i++){
                for (int j=0;j<code_column_numb;j++){
                    if (feature[j][i]!=null)
                        k[j]++;
                }
            }
            for (int i=0;i<k.length;i++)
                System.out.println(k[i]);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
