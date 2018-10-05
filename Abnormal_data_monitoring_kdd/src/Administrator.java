import java.io.File;
public class Administrator {
    public static void main(String[] args) {
        int times=300;//times是你要进行数据异常检测的次数
        for (int i = 0;i<times;i++){
            File file_test_data = new File("test_data.txt");
            File file_result1 = new File("result_min.txt");
            File file_result2 = new File("result_max.txt");
            file_test_data.delete();
            file_result1.delete();
            file_result2.delete();
            ReadWrite rw = new ReadWrite();
            rw.main(null);
            Abnormal_data_monitoring adm = new Abnormal_data_monitoring();
            adm.main(null);
            Statistical_f_min s1 =new Statistical_f_min();
            s1.main(null);
            Statistical_f_max s2 =new Statistical_f_max();
            s2.main(null);
        }
    }
}
/*System.out.println(Double.parseDouble("3.1415"));*/
        /*double[] all_avg = new double[42];
        double[] variance = new double[42];
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.#####");
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            *//* 读入TXT文件 *//*
            File filename = new File("Finished_cleaning.txt"); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            String line = br.readLine();
            int ii=0;
            int i = 1;
            while (ii<2) {
                while (line != null) {
                    String data[] = line.split(","); // 一次读入一行数据
                    if (ii==0)
                        for (int j = 0; j < data.length; j++) {
                            if (j != 1 && j != 2 && j != 3 && j != 41) {
                                all_avg[j] += Double.parseDouble(data[j]);
                            }
                        }
                    else if(ii==1){
                        for (int j = 0; j < variance.length; j++) {
                            if (j != 1 && j != 2 && j != 3 && j != 41) {
                                variance[j] += (Double.parseDouble(data[j])-all_avg[j])*(Double.parseDouble(data[j])-all_avg[j]);
                            }
                        }
                    }
                    line = br.readLine();
                    i++;
                }
                br.close();     //关闭读取文件
                if (ii==0)
                    for (int j = 0; j < all_avg.length; j++) {
                        all_avg[j] /= i-1;
                    }
                else if(ii==1)
                    for (int j = 0; j < variance.length; j++) {
                        if (j != 1 && j != 2 && j != 3 && j != 41) {
                            variance[j] /= i-1;
                        }
                    }
                System.out.println(i);
                i=1;
                ii++;
            }
            for (int j = 0;j<all_avg.length;j++){
                System.out.print(all_avg[j]+" ");
            }
            System.out.println();
            for (int j = 0;j<variance.length;j++){
                System.out.print(variance[j]+" ");
            }
        } catch (Exception e) {
            e.printStackTrace();*/




















    /*public static void main(String[] args) {
        String str1 = "asd";
        String str2 = "dwd";
        str1=str2;
        str2 = null;
        System.out.println(str1);
      *//*  System.out.println(Math.random());
        for (int i = 0;i<100;i++)
            System.out.println(Math.random());*//*
        *//*int[] num = AtoBn(50,100,1);
        sort(num);
        for (int i = 0;i<1;i++){
            System.out.println(num[i]);
        }*//*
        *//*System.out.println((int)(3+Math.abs(10-3)*Math.random()));*//*
        *//*String str1 = "123";
        String str2 = "abc";
        str1 =Mergeclass(str1,str2);
        System.out.println(str1);*//*
        *//*int[] d=new int[3];
        System.out.println(d[2]);
        System.out.println("before");*//*
       *//* for (int i=0;i<distance_i_all.length;i++){
            System.out.print(distance_i_all[i]+" ");
            if(i%10==0&&i!=0){
                System.out.println();
            }
        }*//*
     *//*   String[][] central_k = new String[k][];
        for (int i=0;i<central_k.length;i++){
            central_k[i]=t.Find_Central(classify,i);
            System.out.println("第"+i+"类为：\n"+classify[i]);
            System.out.println("第"+i+"类的中心为：");
            for (int j=0;j<central_k[i].length;j++){
                System.out.print(central_k[i][j]+" ");
            }
            System.out.println();
            System.out.println("第"+i+"类的fitness值为：");
            System.out.println(t.Fitness(classify[i],chromosomeK,i));
            System.out.println();
        }
        for (int i = 0; i < k;i++){
            System.out.println("第"+i+"类！");
            System.out.println(classify[i]);
            System.out.println("第"+i+"类的中心点是：");
            System.out.print(central_k[i]);
            System.out.println();
            System.out.println("第"+i+"类的fitness值为："+t.Fitness(classify[i],central_k,i));
        }
        for (int i = 0;i < chromosomeK.length; i++){
            for (int j = 0;j < chromosomeK[0].length;j++){
                System.out.print(chromosomeK[i][j]+" ");
            }
            System.out.println();
        }*//*
    }*/
    /*public static void main(String[] args) {
        int x ;
        int y ;
        System.out.println(4);
        for (int i=75;i<85;i++) {
            y = (600 - 7 * i) / 3;
            x = (8 * i - 600) / 6;
            if (15 * x + 9 * y + i == 300 && x + y + i == 100)
                System.out.println("cock=  " + x + ",hen= " + y + ",chick= " + i );
        }
    }
    public static String Mergeclass(String class1,String calss2){
        class1 += calss2;
        return class1;
    }
    public static boolean difference(int[] num,int ci,int n){
        boolean flag = true;
        for (int i=0;i<ci;i++){
            if(num[i]==n){
                flag = false;
            }
        }
        return flag;
    }

    public static int[] AtoBn(int a ,int b,int n){//生成a到b之间的n个整数不包括b包括a
        int[] result = new int[n];
        for (int i=0;i<n;i++){
            if (a<b){

                result[i]=(int)(a+Math.abs(b-a)*Math.random());
                while (!difference(result,i,result[i])){
                    result[i]=(int)(a+Math.abs(b-a)*Math.random());
                }
            }else{
                result[i]=(int)(b+Math.abs(b-a)*Math.random());
                while (!difference(result,i,result[i])){
                    result[i]=(int)(b+Math.abs(b-a)*Math.random());
                }
            }
        }
        return result;
    }
    public static int[] sort(int[] distance_i_all){//没用
        int k;
        int temp;
        int i=0;
        while(i<distance_i_all.length){
            k=i;
            for (int j = i+1;j<distance_i_all.length;j++){
                if(distance_i_all[k]<=distance_i_all[j]){
                    k = j;
                }
            }
            temp = distance_i_all[i];
            distance_i_all[i] = distance_i_all[k];
            distance_i_all[k] =temp;
            i++;
        }
        return distance_i_all;
    }*/

    //计算每个点距离其他点的距离之和
      /*double[][] distance_e_p = distance_of_betw_ech_point(classify_k);
      //生成一个按平均距离排序的String二维数组逆序
      double[] distance_i_all = new double[distance_e_p.length];
      for (int i = 0; i < distance_e_p.length;i++){
          for (int j = 0; j< distance_e_p.length;j++){
              distance_i_all[i] += distance_e_p[i][j];
          }
      }
      for (int i=0;i<distance_i_all.length;i++){
            System.out.print(distance_i_all[i]+" ");
            if(i%10==0&&i!=0){
                System.out.println();
            }
        }
      System.out.println();
      System.out.println();
      int sorted_list[] = sort_all_dis(distance_i_all);
      for (int i=0;i<distance_i_all.length;i++){
         System.out.print(distance_i_all[sorted_list[i]]+" ");
          if(i%10==0&&i!=0){
              System.out.println();
          }
      }
      //选取%30的优良基因，%60的普通基因，定义距离之和越大的基因越优良*/

//}
