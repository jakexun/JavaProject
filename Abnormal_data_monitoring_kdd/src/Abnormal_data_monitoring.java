public class Abnormal_data_monitoring {
    public static void main(String[] args) {
        Red_Write_data wrd=new Red_Write_data();
        Tools t = new Tools();
        int row = 1000;
        int k = 3;
        int St = 7;
        String filepath = "test_data.txt";
        int p=0;
        int column = 44;//数据的列数
        String[][] databf=t.Initia_data(filepath,row,column); //将字符串长度改为44，其中43存标记，44标识新数据
        /*for (int i = 0;i<databf.length;i++){
            for (int j = 0;j<databf[0].length;j++){     // 测试 列修改后Initia_data是否正常
                System.out.print(databf[i][j]+" ");
            }
            System.out.println();
        }*/
        double[][] scope = t.scopecalcu(databf);
        /*for (int i = 0;i<scope.length;i++){
            for (int j = 0;j<scope[0].length;j++){      // 测试 列修改后scopecalcu是否正常
                System.out.print(scope[i][j]+" ");
            }
            System.out.println();
        }*/





        String[][] chromosomeK = t.Creating_chromosomeK(scope,k);//随机给的k个点
        for (int i = 0;i<chromosomeK.length;i++){
            for (int j = 0;j<chromosomeK[0].length;j++){
                System.out.print(chromosomeK[i][j]+" ");
            }
            System.out.println();
        }
        double fitness_avg=0;
        /*while(p<St){*/
            String[][] data=t.Initia_data(filepath,row,column);//读取数据
            int[] classify = t.classify(data, chromosomeK);//根据k个点将数据分为k个簇

            for (int i = 0;i<classify.length;i++){     // 测试 测试分类效果
                System.out.print(classify[i]+" ");
            }

            double[] fitness = new double[k];
            for (int i=0;i<chromosomeK.length;i++){
                chromosomeK[i]=t.Find_Central(i,classify[i],column);//找每个类的中心,i表示类别，classify[i]表示i类的数据条数，column表示数据的列数
                if (classify[i]==0)
                    System.out.println("第"+i+"类没有分到数据，无中心点！");
                else{
                    System.out.println("第"+i+"类的中心点为：\n");
                    for (int j=0;j<chromosomeK[i].length;j++){
                        System.out.print(chromosomeK[i][j]+" ");
                    }
                }
                System.out.println();
                if (classify[i]==0)
                    System.out.println("第"+i+"类没有分到数据，fitness按0计算");
                else{
                    fitness[i] = t.Fitness(i,chromosomeK,classify[i],column);//计算每个簇的fitness值
                    System.out.println("第"+i+"类的fitness值为："+fitness[i]);
                }
            }
            for (int i = 1;i<fitness.length;i++){
            }
            fitness_avg = t.Numb_Array_Sum(fitness)/fitness.length;//求fitness的平均值
            String mergeclass = "";
            int min=0,max=0;
            /*for (int i=0;i<k;i++){//将fitness值小于fitness平均值的簇合并为一个
                if (fitness[i]>fitness_avg){
                    mergeclass += classify[i];// #####修改为将fitness大于均值的类下标放在一个数组中: int merge_array[](数组长度如何确定？先确定数组长度，再定义数组)
                }
                if (fitness[min]>fitness[i])
                    min=i;
                if (fitness[max]<fitness[i])
                    max=i;

            }
            if(p==St-1){
                wrd.Write_line("result_min.txt",classify[min]);
                String[][] sss=t.OnedimensToTwodimens(classify[min]);
                System.out.println("检测出的min总数据条数："+sss.length);
                wrd.Write_line("result_max.txt",classify[max]);
                String[][] vvv=t.OnedimensToTwodimens(classify[max]);
                System.out.println("检测出的max总数据条数："+vvv.length);

            }

            System.out.println(mergeclass);//输出合并后的簇（异常应该就在当中）
            System.out.println();
            System.out.println();
           String[][] new_chso = t.Heredity(mergeclass,0.3,0.15,scope);//经过遗传变异产生新染色体
           row += (int)(mergeclass.split("\n").length*0.3);//将新产生的染色体数计入染色体总数中
            for (int i = 0;i < new_chso.length; i++){
                for (int j = 0;j < new_chso[0].length;j++){
                    System.out.print(new_chso[i][j]+" ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("--------------------------"+p+"----------------------------");
            System.out.println();
            wrd.Write("test_data.txt",new_chso);//将新染色体写入到源文件中
            p++;*/
       /* }
        System.out.println(row);*/
    }
}
//无穷与非数值（NaN）
//Infinity无群大
/* for (int i = 0;i<chromosomeK.length;i++){
    for (int j = 0;j<chromosomeK[0].length;j++){
        System.out.print(chromosomeK[i][j]+" ");
    }
    System.out.println();
}*/