package data.preprocessing;

import java.io.*;
import java.util.Properties;

public class test {
    public static void main(String[] args) {
        try {
            InputStream in_stream=new FileInputStream(new File("configuration.properties"));
            Properties pro=new Properties();
            pro.load(in_stream);

            String train_data=pro.getProperty("train_data").trim();
            File file=new File(train_data);
            InputStreamReader in_t=new InputStreamReader(new FileInputStream(file));
            BufferedReader in_reader_t=new BufferedReader(in_t);
//            in_reader_t .mark( ( int )file.length() + 1 );
            int i=1;
            while (i<10){
                System.out.println(in_reader_t.readLine());
                in_reader_t.reset();
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
