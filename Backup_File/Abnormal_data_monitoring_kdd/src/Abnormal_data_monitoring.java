import java.io.File;

public class Abnormal_data_monitoring {
    public void monitoring_checking(String get_data_path,String filepath,int row,int column,int k,int iteration_times) {
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.#####");
        Red_Write_data rwd=new Red_Write_data();
        Tools t = new Tools();

       /* String get_data_path = "normalized_data.txt";
        String filepath = "test_data.txt";
        int row = 2000;
        int k = 5;
        int iteration_times = 10;
        int column = 44;//数据的列数，假想，可不可以把这个程序写成不管有多少列(能自动识别该计算哪一行)的数据？*/

        int St=iteration_times;
        int p=0;
        // 文件准备前的清理功能
        File duplicate_ready_data_delete = new File(filepath); //对上次测试数据的清楚
        duplicate_ready_data_delete.delete();

        for (int i =0;i<k;i++){//清楚上次分类的文件
            File classed_file_delete = new File("classify["+i+"].txt");
            classed_file_delete.delete();
            File classed_file_sorted_delete = new File("classify["+i+"]sorted.txt");
            classed_file_sorted_delete.delete();

            File classed_max_file_delete = new File("classify[max"+i+"].txt");
            classed_max_file_delete.delete();
            File classed_max_file_sorted_delete = new File("classify[max"+i+"]sorted.txt");
            classed_max_file_sorted_delete.delete();

            File classed_min_file_delete = new File("classify[min"+i+"].txt");
            classed_min_file_delete.delete();
            File classed_min_file_sorted_delete = new File("classify[min"+i+"]sorted.txt");
            classed_min_file_sorted_delete.delete();
        }

        File merge_data_delete = new File("mergeclass.txt"); //清楚上次合并的fitness大于平均值的类的数据
        merge_data_delete.delete();

        File fitness_data_delete = new File("fitness_infor.txt"); //清楚上次保存fitness值的点
        fitness_data_delete.delete();

        File central_data_delete = new File("central.txt"); //清楚上次中心点保存的信息
        central_data_delete.delete();

        rwd.Read_write(get_data_path,filepath,row);
        String[][] databf=t.Initia_data(filepath,row,column); //初始化最原始的数据

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
        int[] classify = t.classify(databf, chromosomeK);        //根据k个点将数据分为k个簇
        while (t.IncludeZero(classify)){
            for (int i =0;i<k;i++){//清楚上次分类的文件
                File classed_file_delete = new File("classify["+i+"].txt");
                classed_file_delete.delete();
            }
            System.out.println("\"\"\"\"");
            //要保证第一次取的k个点分出来的每个类中都有数据
            chromosomeK = t.Creating_chromosomeK(scope,k);
            classify = t.classify(databf, chromosomeK);
        }

        for (int i = 0;i<chromosomeK.length;i++){                //输出分类成功的k个随机点
            for (int j = 0;j<chromosomeK[0].length;j++){
                System.out.print(chromosomeK[i][j]+" ");
            }
            System.out.println();
        }
        for (int i = 0;i<classify.length;i++){     // 测试 测试分类效果
            System.out.print(classify[i]+" ");
        }


        double fitness_avg=0;
        while(p<St){
            String[][] data=t.Initia_data(filepath,row,column);//读取数据

            if (p!=0)
                classify = t.classify(databf, chromosomeK); //分类

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

            //fitness_avg = t.Numb_Array_Sum(fitness)/fitness.length; //求fitness的平均值

            //int mergeclass =0;
            int min=0,max=0;

            for (int i=0;i<k;i++){//将fitness值小于fitness平均值的簇合并为一个
                /*if (fitness[i]>fitness_avg){
                    mergeclass += classify[i];// #####修改为将fitness大于均值的类下标放在一个数组中: int merge_array[](数组长度如何确定？先确定数组长度，再定义数组)
                    String merge_file_name = "mergeclass.txt";
                    String class_file_name = "classify["+i+"].txt";
                    rwd.Read_write(class_file_name,merge_file_name,classify[i]);
                }       */                       // #####新思路，将
                if (fitness[min]>fitness[i])
                    min=i;
                if (fitness[max]<fitness[i])
                    max=i;
            }

            if(p==St-1){
                String class_iin_path = "classify["+min+"].txt";
                String class_min_path = "classify[min"+min+"].txt";
                rwd.Read_write(class_iin_path,class_min_path,classify[min]);
                System.out.println("检测出的min总数据条数："+classify[min]);

                File delete_file_min = new File(class_iin_path);
                delete_file_min.delete();

                String class_iax_path = "classify["+max+"].txt";
                String class_max_path = "classify[max"+max+"].txt";
                rwd.Read_write(class_iax_path,class_max_path,classify[max]);
                System.out.println("检测出的min总数据条数："+classify[max]);

                File delete_file_max = new File(class_iax_path);
                delete_file_max.delete();
            }
//            System.out.println();
            /*String[][] new_chso = t.Heredity(0.3,0.15,mergeclass,column,scope);//经过遗传变异产生新染色体
            row += new_chso.length;//将新产生的染色体数计入染色体总数中
*/
            /* for (int i = 0;i < new_chso.length; i++){
                for (int j = 0;j < new_chso[0].length;j++){  // 输出新产生的数据
                    System.out.print(new_chso[i][j]+" ");
                }
                System.out.println();
            }*/

           /* System.out.println("新产生了"+new_chso.length+"条数据！");*/
            System.out.println();
            System.out.println("-------------已经迭代了-------------"+(p+1)+"---------------次-------------");
            System.out.println();
            /*rwd.Write(filepath,new_chso);//将新染色体写入到源文件中*/

            if (p!=St-1){
                for (int i =0;i<k;i++){//清楚上次分类的文件
                    File classed_file_delete = new File("classify["+i+"].txt");
                    classed_file_delete.delete();

                    File classed_max_file_delete = new File("classify[max"+i+"].txt");
                    classed_max_file_delete.delete();

                    File classed_min_file_delete = new File("classify[min"+i+"].txt");
                    classed_min_file_delete.delete();

                    /*File classed_file_sorted_delete = new File("classify["+i+"]sorted.txt");
                    classed_file_sorted_delete.delete();

                    File classed_max_sorted_file_delete = new File("classify[max"+i+"]sorted.txt");
                    classed_max_sorted_file_delete.delete();

                    File classed_min_sorted_file_delete = new File("classify[min"+i+"]sorted.txt");
                    classed_min_sorted_file_delete.delete();*/
                }

//                File remerge_data_delete = new File("mergeclass.txt"); //清楚上次合并的fitness大于平均值的类的数据
//                remerge_data_delete.delete();
            }else{
                String fitness_infor = "";
                for (int i=0;i<classify.length;i++){
                    fitness_infor += df.format(fitness[i])+"\t";
                }
                rwd.Write_line("fitness_infor.txt",fitness_infor);
                for (int i=0;i<chromosomeK.length;i++){
                    rwd.Write_line("central.txt",t.ArrayToString(chromosomeK[i]));
                }

               /* for (int i =0;i<k;i++){//对分类后的文件进行排序
                    File classed_file = new File("classify["+i+"].txt");
                    if(classed_file.exists()){
                        String read_class_file = "classify["+i+"].txt";
                        String write_path = "classify["+i+"]sorted.txt";
                        t.sort_from_far_to_near(read_class_file,write_path,chromosomeK[i],classify[i],column);
                    }

                    File classed_max_file = new File("classify[max"+i+"].txt");
                    if(classed_max_file.exists()){
                        String read_max_class_file = "classify[max"+i+"].txt";
                        String write_max_path = "classify[max"+i+"]sorted.txt";
                        t.sort_from_far_to_near(read_max_class_file,write_max_path,chromosomeK[i],classify[i],column);
                    }

                    File classed_min_file_delete = new File("classify[min"+i+"].txt");
                    if(classed_min_file_delete.exists()){
                        String read_min_class_file = "classify[min"+i+"].txt";
                        String write_min_path = "classify[min"+i+"]sorted.txt";
                        t.sort_from_far_to_near(read_min_class_file,write_min_path,chromosomeK[i],classify[i],column);
                    }
                }*/

            }
            p++;
        }
        System.out.println(row);
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