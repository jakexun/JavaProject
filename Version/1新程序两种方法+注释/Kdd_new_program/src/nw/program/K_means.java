package nw.program;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import static nw.program.Tools.*;

public class K_means {
    public static double[][] K_means(int k,int iterations,String filepath,int column,int n,int a) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        int mergeclass =0;
        Clean(k);
        Read_Write_data rwd=new Read_Write_data();
        double[][] scope=Scope(filepath,column);
//        String[][] central=GetCentral(filepath,column,n,a);
        String[][] central=CreatingRandomCentral(scope,k);
        int[] classify=classify(filepath,central);
        while (IncludeZero(classify)){
            Clean(k);
            for (int i =0;i<k;i++){//清楚上次分类的文件
                File classed_file_delete = new File("classify["+i+"].txt");
                classed_file_delete.delete();
            }
            System.out.println("\"\"\"\"");
            //要保证第一次取的k个点分出来的每个类中都有数据
            central=CreatingRandomCentral(scope,k);
            classify=classify(filepath,central);
        }
        int iter=1;
        int[] beforclassify=new int[k];
        for (int i=0;i<k;i++){
            beforclassify[i]=classify[i];
        }
        String[][] becentral=ArrayCopy(central);
        while (!Arrayequals(classify,beforclassify)&&!ArrayEquals(becentral,central)||iter==1){
            System.out.println();
            if (iter!=1){
                for (int i=0;i<k;i++){
                    beforclassify[i]=classify[i];
                }
            }
            System.out.println("第"+(iter-1)+"次分类完成！第"+iter+"次求中心点开始："+df.format(new Date()));// new Date()为获取当前系统时间
            becentral=ArrayCopy(central);
            central=GetCentral(k,classify,column);
            Clean(k);
            System.out.println("第"+iter+"次求中心点完成！第"+iter+"次分类开始："+df.format(new Date()));// new Date()为获取当前系统时间
            classify=classify(filepath,central);
            iter++;
        }

        String centralfile="central.txt";
        for (int i=0;i<central.length;i++){
            String line=ArrayToString(central[i]);
            rwd.Write_line(centralfile,line);
        }

        rwd.Write_line("iterations_infor.txt",iter+" ");
        double[] fitness=GetFitness(k,central,classify,column);

        double fitness_avg = Numb_Array_Sum(fitness)/fitness.length; //求fitness的平均值

        int min=0,max=0;

        for (int i=0;i<k;i++){//将fitness值大于fitness平均值的簇合并为一个
            if (fitness[i]>fitness_avg){
                mergeclass += classify[i];
                String merge_file_name = "mergeclass.txt";
                String class_file_name = "classify["+i+"].txt";
                rwd.Read_write(class_file_name,merge_file_name,classify[i]);
            }                              // #####新思路，将
            if (fitness[min]>fitness[i])
                min=i;
            if (fitness[max]<fitness[i])
                max=i;
        }

        String class_iin_path = "classify["+min+"].txt";
        String class_min_path = "classify[min"+min+"].txt";
        rwd.Read_write(class_iin_path,class_min_path,classify[min]);
        System.out.println("检测出的min总数据条数："+classify[min]);

        File delete_file_min = new File(class_iin_path);
        delete_file_min.delete();

        String class_iax_path = "classify["+max+"].txt";
        String class_max_path = "classify[max"+max+"].txt";
        rwd.Read_write(class_iax_path,class_max_path,classify[max]);
        System.out.println("检测出的max总数据条数："+classify[max]);

        File delete_file_max = new File(class_iax_path);
        delete_file_max.delete();
       return scope;
    }

}
/*
        for (int i=0;i<central.length;i++){
            for (int j=0;j<central[i].length;j++){
                System.out.print(central[i][j]+" ");
            }
            System.out.println();
        }
*/