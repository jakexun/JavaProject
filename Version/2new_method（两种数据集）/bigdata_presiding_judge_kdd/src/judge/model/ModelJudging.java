package judge.model;

import java.io.*;
import java.util.Properties;

import static judge.model.ModelTraing.GetData;
import static judge.model.ModelTraing.GetFeature;
import static judge.model.ModelTraing.IfContain;

public class ModelJudging {
    public static void main(String[] args) {
        try {
            InputStream in_stream=new FileInputStream(new File("configuration.properties"));
            Properties pro=new Properties();
            pro.load(in_stream);

            String to_identify_data=pro.getProperty("to_identify_data").trim();
            InputStreamReader in_tid=new InputStreamReader(new FileInputStream(new File(to_identify_data)));
            BufferedReader in_reader_tid=new BufferedReader(in_tid);

            String correct_model=pro.getProperty("correct_model").trim();
//            File file_c=new File(correct_model);
//            InputStreamReader in_c=new InputStreamReader(new FileInputStream(file_c));
//            BufferedReader in_reader_c=new BufferedReader(in_c);

            String wrong_model=pro.getProperty("wrong_model").trim();
//            File file_w=new File(wrong_model);
//            InputStreamReader in_w=new InputStreamReader(new FileInputStream(file_w));
//            BufferedReader in_reader_w=new BufferedReader(in_w);

            String fuzzy_model=pro.getProperty("fuzzy_model").trim();
//            File file_fm=new File(fuzzy_model);
//            InputStreamReader in_fm=new InputStreamReader(new FileInputStream(file_fm));
//            BufferedReader in_reader_fm=new BufferedReader(in_fm);

            String[] correct_model_data=GetData(correct_model);
            String[] wrong_model_data=GetData(wrong_model);
            String[] fuzzy_model_data=GetData(fuzzy_model);

            String normal_data=pro.getProperty("normal_data").trim();
            File file_nd=new File(normal_data);
            OutputStreamWriter out_nd=new OutputStreamWriter(new FileOutputStream(file_nd));
            BufferedWriter out_writer_nd=new BufferedWriter(out_nd);

            String abnormal_data=pro.getProperty("abnormal_data").trim();
            File file_ad=new File(abnormal_data);
            OutputStreamWriter out_ad=new OutputStreamWriter(new FileOutputStream(file_ad));
            BufferedWriter out_writer_ad=new BufferedWriter(out_ad);

            String fail_identify_data=pro.getProperty("fail_identify_data").trim();
            File file_fid=new File(fail_identify_data);
            OutputStreamWriter out_fid=new OutputStreamWriter(new FileOutputStream(file_fid));
            BufferedWriter out_writer_fid=new BufferedWriter(out_fid);

            String new_feature=pro.getProperty("new_feature").trim();
            File file_nf=new File(new_feature);
            OutputStreamWriter out_nf=new OutputStreamWriter(new FileOutputStream(file_nf));
            BufferedWriter out_writer_nf=new BufferedWriter(out_nf);

            String line=in_reader_tid.readLine();
            while (line!=null){
                String feature=GetFeature(line);
                if (!IfContain(fuzzy_model_data,feature)){
                    if (IfContain(correct_model_data,feature)){
                        out_writer_nd.write(line+"\n");
                    }else if (IfContain(wrong_model_data,feature)){
                        out_writer_ad.write(line+"\n");
                    }else {
                        out_writer_nf.write(line+"\n");
                    }
                }else {
                    out_writer_fid.write(line+"\n");
                }
                line=in_reader_tid.readLine();
            }

            out_writer_nd.close();
            out_writer_ad.close();
            out_writer_fid.close();
            out_writer_nf.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
