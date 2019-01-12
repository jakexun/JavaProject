package od.program;

import java.io.File;

public class Statistical_all {
    public static void Statisticing(String origin_file,String fitness_file,int row,int column,int k) {
        Red_Write_data rwd = new Red_Write_data();
        /*String origin_file = "test_data.txt";
        String fitness_file = "fitness_infor.txt";
        int row=2000;
        int column = 44;
        int k=5;*/
        int[] sored_fitness = rwd.read_fitness(fitness_file);

        for (int i=0;i<k;i++){ // 对每个类进行统计
            if (i==0) {
                String file_max_name = "classify[max" + sored_fitness[0] + "].txt";
//                System.out.println(file_max_name);
                File file_min = new File(file_max_name);
                if (file_min.exists()) {
                    /*rwd.Statictical(origin_file, row, column, file_max_name, "classify["+sored_fitness[0]+"]_statistical"+"max"+".txt");*/
                    rwd.Statictical(origin_file, row, column, file_max_name, "classify[max]_statistical.txt");
                }
            }
            if (i==k-1) {
                String file_min_name = "classify[min"+sored_fitness[k-1]+"].txt";
//                System.out.println(file_min_name);
                File file_max = new File(file_min_name);
                if (file_max.exists()){
                    /*rwd.Statictical(origin_file,row,column,file_min_name,"classify["+sored_fitness[k-1]+"]_statistical"+"min"+".txt");*/
                    rwd.Statictical(origin_file,row,column,file_min_name,"classify[min]_statistical.txt");
                }
            }
            if(i!=0&&i!=k-1){
                String normal_filename = "classify["+sored_fitness[i]+"].txt";
//                System.out.println(normal_filename);
                File file_normal = new File(normal_filename);
                if(file_normal.exists()){
                    rwd.Statictical(origin_file,row,column,normal_filename,"classify[can'tretrospect]_statistical"+i+".txt");
                }
            }
        }
    }
}