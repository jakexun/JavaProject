public class Abnormal_data_monitoring {
    public static void main(String[] args) {
        Red_Write_data wrd=new Red_Write_data();
        Tools t = new Tools();
        int row = 500;
        int k = 3;
        int St = 10;
        String filepath = "C:\\Myprofile\\output.txt";
//        int p=0;
        double m=0;
        String[][] databf=t.Initia_data(filepath,row,56);
        double[][] scopebf = t.scopecalcu(databf);
        String[][] chromosomeK = t.Creating_chromosomeK(scopebf,k);//随机给的k个点
        double fitness_avg=0;
        while(m-fitness_avg>0.08||m==0){
            m=0;
            String[][] data=t.Initia_data(filepath,row,56);//读取数据
            double[][] scope = t.scopecalcu(data);
            String[] classify = t.classify(data, chromosomeK);//根据k个点将数据分为k个簇
    //        t.Heredity(classify[0]);
            double[] fitness = new double[k];
            for (int i=0;i<chromosomeK.length;i++){
                chromosomeK[i]=t.Find_Central(classify,i);//找每个类的中心
             /*   System.out.println("第"+i+"类的中心点为：\n");
                for (int j=0;j<central_k[i].length;j++){
                    System.out.print(central_k[i][j]+" ");
                }*/
              /*  System.out.println();
                System.out.println("第"+i+"类的fitness值为：");*/
                fitness[i] = t.Fitness(classify[i],chromosomeK,i);//计算每个簇的fitness值
            }
            m=fitness[0];
            for (int i = 1;i<fitness.length;i++){
                if(m<fitness[i]){
                    m=fitness[i];
                }
            }
            fitness_avg = t.Numb_Array_Sum(fitness)/fitness.length;//求fitness的平均值
            String mergeclass = "";
            for (int i=0;i<k;i++){//将fitness值小于fitness平均值的簇合并为一个
                if (fitness[i]>fitness_avg){
                    mergeclass +=classify[i];
                }
            }
           /* if(m-fitness_avg>1)
                wrd.Write_line("C:\\Myprofile\\result.txt",mergeclass);*/
            System.out.println(mergeclass);//输出合并后的簇（异常应该就在当中）
            System.out.println();
            System.out.println();
           String[][] new_chso = t.Heredity(mergeclass,0.3,0.1,scope);//经过遗传变异产生新染色体
           row += (int)(mergeclass.split("\n").length*0.3);//将新产生的染色体数计入染色体总数中
            for (int i = 0;i < new_chso.length; i++){
                for (int j = 0;j < new_chso[0].length;j++){
                    System.out.print(new_chso[i][j]+" ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
            wrd.Write("C:\\Myprofile\\output.txt",new_chso);//将新染色体写入到源文件中
           /* p++;*/
        }
        System.out.println(row);
    }
}
