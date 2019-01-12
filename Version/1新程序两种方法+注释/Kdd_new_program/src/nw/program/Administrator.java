package nw.program;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static nw.program.Tools.GetColumnNumber;
import static nw.program.Tools.Scope;
import data.process.Statistical;

public class Administrator {
    public static void main(String[] args) {
        try {
            Statistical st=new Statistical();
            InputStream inStream = new FileInputStream(new File("Configuration.properties"));
            Properties pro = new Properties();
            pro.load(inStream);

            String TFilepath = pro.getProperty("TFilepath").trim();
            String GDpath = pro.getProperty("GDpath").trim();

            int K = Integer.parseInt(pro.getProperty("K").trim());
            int KMiterations = Integer.parseInt(pro.getProperty("KMiterations").trim());
            int Runtimes = Integer.parseInt(pro.getProperty("Runtimes").trim());
            int GAtimes = Integer.parseInt(pro.getProperty("GAtimes").trim());
            int Row = Integer.parseInt(pro.getProperty("Row").trim());
            int n=Integer.parseInt(pro.getProperty("N").trim());
            int a=Integer.parseInt(pro.getProperty("A").trim());

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
                //double[][] scope=Scope(TFilepath,column);
                double[][] scope=km.K_means(K,KMiterations,TFilepath,column,n,a);//kmeans聚类
                for (int j=0;j<GAtimes;j++){
//                    String maxfile;
//                    String filemo;
                    String minfile;
//                    File filea;
//                    File filem;
                    File filei;
                    for (int ki=0;ki<K;ki++){//遗传变异模块
//                       /* maxfile="classify[max"+ki+"].txt";
//                        filemo="classify["+ki+"].txt";*/
                        minfile="classify[min"+ki+"].txt";

//                        /*filea=new File(maxfile);
//                        filem=new File(filemo);*/
                        filei=new File(minfile);
//                      if (filea.exists()){
//                            ga.Genetic_Algorithm(scope,1,0.1,column,maxfile,TFilepath);//遗传变异算法
//                      }
//                      if (filem.exists()){
//                            ga.Genetic_Algorithm(scope,1,0.1,column,filemo,TFilepath);//遗传变异算法
//                      }
                      if (filei.exists()){
                            ga.Genetic_Algorithm(scope,1,0.1,column,minfile,TFilepath);//遗传变异算法
                        }
                    }
//                    String[][] new_data=ga.Genetic_Algorithm(scope,1,0.1,column,);//遗传变异算法
//                    rwd.Write(TFilepath,new_data);
                }
                File centralfile=new File("central.txt");
                centralfile.delete();
                km.K_means(K,KMiterations,TFilepath,column,n,a);//遗传变异后再聚类
                st.Statistic(K,i);//统计结果
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
