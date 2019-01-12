package data.preprocessing;

import java.io.*;
import java.util.Properties;

public class StatisticResult {
    public static void main(String[] args) {
        try{
            InputStream in_stream=new FileInputStream(new File("configuration.properties"));
            Properties pro=new Properties();
            pro.load(in_stream);

            String result=pro.getProperty("result").trim();
            OutputStreamWriter in_r=new OutputStreamWriter(new FileOutputStream(new File(result)));
            BufferedWriter out_writer_r=new BufferedWriter(in_r);

            String to_identify_data=pro.getProperty("to_identify_data").trim();
            String normal_data=pro.getProperty("normal_data").trim();
            String abnormal_data=pro.getProperty("abnormal_data").trim();
            String fail_identify_data=pro.getProperty("fail_identify_data").trim();
            String new_feature=pro.getProperty("new_feature").trim();

            String[] result_data=new String[5];
            result_data[0]="测试数据信息："+Statistic(to_identify_data);
            result_data[1]="normal_data信息："+Statistic(normal_data);
            result_data[2]="abnormal_data信息："+Statistic(abnormal_data);
            result_data[3]="fail_identify_data信息："+Statistic(fail_identify_data);
            result_data[4]="new_feature信息："+Statistic(new_feature);

            for (int i=0;i<result_data.length;i++){
                out_writer_r.write(result_data[i]+"\n");
            }
            out_writer_r.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String Statistic(String filepath){
        String result="";
        try {
            InputStreamReader in=new InputStreamReader(new FileInputStream(new File(filepath)));
            BufferedReader reader=new BufferedReader(in);

            int normal=0,abnormal=0,all=0;
            String line=reader.readLine();
            while(line!=null){
                if (line.contains("normal")){
                    normal++;
                }else if (!line.contains("mixture")){
                    abnormal++;
                }
                all++;
                line=reader.readLine();
            }
            result+="all="+all+",normal="+normal+",abnormal="+abnormal;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
