package nw.program;

import data.process.Statistical;
import od.program.Abnormal_data_monitoring;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static nw.program.Tools.GetColumnNumber;

public class Administrator {
    public static void main(String[] args) {
        try {
            Statistical st=new Statistical();
            InputStream inStream = new FileInputStream(new File("Configuration.properties"));
            Properties pro = new Properties();
            pro.load(inStream);

            Abnormal_data_monitoring adm = new Abnormal_data_monitoring();

            String TFilepath = pro.getProperty("TFilepath").trim();
            String GDpath = pro.getProperty("GDpath").trim();

            int K = Integer.parseInt(pro.getProperty("K").trim());
            int KMiterations = Integer.parseInt(pro.getProperty("KMiterations").trim());
            int Runtimes = Integer.parseInt(pro.getProperty("Runtimes").trim());
            int GAtimes = Integer.parseInt(pro.getProperty("GAtimes").trim());
            int Row = Integer.parseInt(pro.getProperty("Row").trim());


            Read_Write_data rwd=new Read_Write_data();
            Genetic_Algorithm ga=new Genetic_Algorithm();
            K_means km=new K_means();

            for (int i=0;i<Runtimes;i++){
                File mergeclass_delete = new File("mergeclass.txt");
                mergeclass_delete.delete();


                File file=new File(TFilepath);
                file.delete();

                rwd.Read_write(GDpath,TFilepath,Row);
                int column=GetColumnNumber(TFilepath);

                double[][] scope=km.K_means(K,KMiterations,TFilepath,column);

                for (int j=0;j<GAtimes;j++){
                    String[][] new_data=ga.Genetic_Algorithm(scope,0.8,0.1,column);
                    rwd.Write(TFilepath,new_data);
                }
                km.K_means(K,KMiterations,TFilepath,column);
                st.Main(K,TFilepath,"result.txt",i);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
