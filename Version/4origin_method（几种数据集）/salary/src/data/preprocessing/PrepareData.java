package data.preprocessing;

import java.io.*;
import java.sql.BatchUpdateException;
import java.util.Properties;

public class PrepareData {
    public static void main(String[] args) {
        try {
            InputStream in_stream=new FileInputStream(new File("Data_Cleaning.properties"));
            Properties pro=new Properties();
            pro.load(in_stream);
            String normalized_data=pro.getProperty("normalized_path").trim();
            String prepare_data=pro.getProperty("prepare_data").trim();
            double abnormal_rate=Double.parseDouble(pro.getProperty("abnormal_rate").trim());
            int data_number=Integer.parseInt(pro.getProperty("data_number").trim());
            int abnormal_number=(int)(data_number*abnormal_rate);

            File file_nd=new File(normalized_data);
            InputStreamReader in_nd=new InputStreamReader(new FileInputStream(file_nd));
            BufferedReader in_reader_nd=new BufferedReader(in_nd);

            File file_pd=new File(prepare_data);
            OutputStreamWriter out_pd=new OutputStreamWriter(new FileOutputStream(file_pd));
            BufferedWriter out_writer_pd=new BufferedWriter(out_pd);

            String line=in_reader_nd.readLine();
            int i=0,w=0;
            while (i<data_number){
                if (line.contains("abnormal")&&w<abnormal_number){
                    out_writer_pd.write(line+"\n");
                    w++;
                    i++;
                }else if (line.contains("normal")&&!line.contains("abnormal")){
                    out_writer_pd.write(line+"\n");
                    i++;
                }
                line=in_reader_nd.readLine();
            }
            System.out.println("abnormal_number="+abnormal_number+" 1="+i+" w="+w);
            in_reader_nd.close();
            out_writer_pd.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
