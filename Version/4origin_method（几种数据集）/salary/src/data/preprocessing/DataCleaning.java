package data.preprocessing;

import java.io.*;
import java.util.Properties;

public class DataCleaning {
    public static void main(String[] args) {
        try {
            InputStream instream = new FileInputStream(new File("Data_Cleaning.properties"));
            Properties pro = new Properties();
            pro.load(instream);

            /* 读入TXT文件 */
            String originpath = pro.getProperty("origin_path").trim();
            File filename = new File(originpath);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader in = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = null;
            line = in.readLine();
            String[] line_arr=line.split(", ");
            String[][] feature=new String[8][50];
            feature[0][0]=line_arr[1];
            feature[1][0]=line_arr[3];
            feature[2][0]=line_arr[5];
            feature[3][0]=line_arr[6];
            feature[4][0]=line_arr[7];
            feature[5][0]=line_arr[8];
            feature[6][0]=line_arr[9];
            feature[7][0]=line_arr[13];
            int[] start_i=new int[8];
            int[] need_i={1,3,5,6,7,8,9,13};
            for (int i=0;i<8;i++){
                start_i[i]=0;
            }

            File cleanedpath = new File(pro.getProperty("cleaned_path").trim()); // 相对路径，如果没有则要建立一个新的output。txt文件
            cleanedpath.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(cleanedpath));
            while (line != null) {
                if (!line.contains("?")){
                    out.write(ArraytoString(line.split(", "))+"\n");
                    out.flush(); // 把缓存区内容压入文件
                }
                line = in.readLine(); // 一次读入一行数据
                if (line != null&&!line.contains("?")){
                    line_arr=line.split(", ");
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
                        int j = start_i[i];
                        while(j>=0&&!need_feature[i].equals(feature[i][j]))j--;
                        if (j<0){
                            feature[i][++start_i[i]]=need_feature[i];
                        }
                    }
                }
            }
            in.close();     //关闭读取文件
            out.close(); // 关闭关闭写入文件
            File property_file = new File("property.txt");
            property_file.createNewFile();
            BufferedWriter property_out=new BufferedWriter(new FileWriter(property_file));
            for (int i=0;i<8;i++){
                String fpro="";
                for (int j=0;feature[i][j]!=null;j++){
                    fpro+=feature[i][j]+" ";
                }
                property_out.write(fpro+"\n");
                property_out.flush();
            }
            property_out.close();
            int[] k=new int[8];
            for (int i=0;i<k.length;i++)
                k[i]=0;
            for (int i=0;i<feature[0].length;i++){
                for (int j=0;j<8;j++){
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
