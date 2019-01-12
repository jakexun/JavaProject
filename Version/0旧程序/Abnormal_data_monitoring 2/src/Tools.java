import java.util.Date;
import java.math.*;
public class Tools {
    public String[][] Initia_data(String filepath,int row,int column){ //1 将读取的原始的字符串数据初始化转化成可计算的double类型的数据

        Red_Write_data rd = new Red_Write_data();
        String data_str[] = rd.Read(filepath, row);

        String [][]data = new String[data_str.length][column];

        for(int i = 0;i < data_str.length; i++){
            String[] line = data_str[i].split(",");
            for(int j=0;j < line.length; j++){
                data[i][j] = line[j];
            }
        }

        return data;
    }


    public double[][] scopecalcu(String[][] data){ //2 计算每一个数据项的范围
        int r = data.length;
        int c = data[0].length;
        double[][] scope = new double[2][c];
        for(int j = 0;j < c;j++){
            for(int i = 0; i < r;i++){
                if (j!=1&&j!=2&&j!=3&&j!=41&&j!=42){
                    if(i == 0){
                        scope[0][j] = Double.parseDouble(data[i][j]); //初始化scope[0]
                        scope[1][j] = Double.parseDouble(data[i][j]);   //初始化scope[1]
                    }else {
                            if(scope[0][j] > Double.parseDouble(data[i][j])){
                                scope[0][j] = Double.parseDouble(data[i][j]); //对比寻找最小值
                            }
                            if(scope[1][j] < Double.parseDouble(data[i][j])){
                                scope[1][j] = Double.parseDouble(data[i][j]); //对比寻找最大值
                            }
                        }
                }
            }
        }
        return scope;
    }

    public static double distance(String point1[], String point2[]){// 3计算两点之间的距离 finished
        double sum_diff = 0, //所有坐标系上两点差的平方的和
                diff_value2; //每个坐标轴上两点差的平方
        /*System.out.println("point1.length"+point1.length);
        System.out.println("point2.length"+point2.length);
        for (int i = 0;i<point1.length;i++){
                System.out.print(point1[i]+" ");
        }*/
        if(point1.length==43&&point2.length==43)
            for(int i = 0;i < point1.length;i++){
                if (i!=1&&i!=2&&i!=3&&i!=41&&i!=42) {
                    diff_value2 = (Double.parseDouble(point1[i]) - Double.parseDouble(point2[i])) * (Double.parseDouble(point1[i]) - Double.parseDouble(point2[i]));
                    sum_diff += diff_value2;
                }
            }
        return Math.sqrt(sum_diff);
    }

    public static int min_distance_k(double[] dis){ //最近距离
        double min = dis[0];
        int min_k = 0;
        for(int i=1;i<dis.length;i++){
            if(dis[i]<min){
                min = dis[i];
                min_k = i;
            }
        }
        return min_k;
    }

    public static String ArrayToString(String[] s) { //4功能函数，将double类型的数组转化成字符串
        String str= null,strtmp=""+s[0];
        for(int i=1;i < s.length;i++){
            str = strtmp+","+s[i];
            strtmp=str;
        }
        return str+"\n";
    }

    //对data中的点进行分类，分类完成后返回一个分完类的字符数组，数组下标表示类名
    public String[] classify(String[][] data,String[][] pointk){  // 5not finished
        int k = pointk.length; //k的值
        int n = data.length; //表示点的个数
        double[] dis = new double[k]; //min_dis表示一个点与ki的距离
        int ki; //表示哪个中心(0~k)
        String[] str =new String[k]; //分类后的状态
        for(int i=0;i<str.length;i++){
            str[i]="";
        }
        for(int i=0;i < n;i++){
            for(int j =0;j < k;j++){
                dis[j] = distance(data[i],pointk[j]);
            }
            String st = str[min_distance_k(dis)];
            str[min_distance_k(dis)] = st + ArrayToString(data[i]);
        }
        return str;
    }
    public static double R_dom(double a, double b){ //6随机生成a,到b之间的一个double类型的数
        if(a == b)
            return a;
        else if (a>b)
            return b + Math.abs(b-a)*Math.random();
        else
            return a + Math.abs(b-a)*Math.random();
    }

    public String[][] Creating_chromosomeK(double scope[][],int k){ //7
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.#####");
        String[][] chromosomeK = new String[k][scope[0].length];

        for (int j = 0;j < scope[0].length;j++){
            for (int i = 0; i < k; i++){
                    chromosomeK[i][j] = String.valueOf(df.format(R_dom(scope[0][j],scope[1][j])));
            }
        }

        return chromosomeK;
    }


    public static String[][] OnedimensToTwodimens(String class_kd1){ //8一维数组转二维数组
        String[] line = class_kd1.split("\n");
        String[] unit = line[0].split(",");
        String[][] class_kd2 = new String[line.length][unit.length];
        for (int i = 0;i<line.length;i++){
            class_kd2[i] = line[i].split(",");
        }
        return class_kd2;
    }

    public static double Numb_Array_Sum(double[] NumbArray){//9
        double sum = 0;
        for (int i = 0;i < NumbArray.length;i++){
            sum += NumbArray[i];
        }
        return sum;
    }

    public static double[][] distance_of_betw_ech_point(String[][] classify_k){
        double[][] distance_e_p=new double[classify_k.length][classify_k.length];
        for (int i=0;i<classify_k.length;i++){
            for (int j = 0;j<classify_k.length;j++){
                if (i!=j){
                    distance_e_p[i][j] = distance(classify_k[i],classify_k[j]); //求出所有点与其他所有点的距离
                }else{
                    distance_e_p[i][j] = 0;
                }
            }
        }
        return distance_e_p;
    }

    public double Fitness(String class_kd1, String[][] ki ,int k){ // 10求以k点为中心的簇的fitness的值
        //求类内距离
        String[][] class_kd2 = OnedimensToTwodimens(class_kd1);
        double[] distance_point = new double[class_kd2.length];
        for (int i=0; i<class_kd2.length;i++){
            distance_point[i]=distance(class_kd2[i],ki[k]);
        }
        double AvgDis = Numb_Array_Sum(distance_point)/distance_point.length; //类内距离

        //求类间距离
        System.out.println("半径："+AvgDis);
        double[] every_cla_intracla = new double[ki.length];
        for (int i = 0;i < ki.length;i++){
            if (i != k){
                every_cla_intracla[i] = AvgDis/distance(ki[i],ki[k]); //类内比内间距离
            }else
                every_cla_intracla[i] = 0;
        }
        for (int i=0;i<every_cla_intracla.length;i++){
            System.out.print(every_cla_intracla[i]+"   ");
        }
        System.out.println();
        return Numb_Array_Sum(every_cla_intracla);//求fitness:类内距离/类间距离之和
    }

    public String[] Find_Central(String[] classify,int ki){ //11找到中心点并返回中心点位置(下标)
        String[][] classify_k =OnedimensToTwodimens(classify[ki]);
        /*for (int i = 0;i < classify_k.length; i++){
            for (int j = 0;j < classify_k[0].length;j++){
                System.out.print(classify_k[i][j]+" ");
            }
            System.out.println();
        }*/
        double[][] distance_e_p=distance_of_betw_ech_point(classify_k);

        /*for (int i = 0; i < distance_e_p.length;i++){ //测试
            for (int j = 0; j < distance_e_p.length;j++){ // 寻找与其他点距离之和最短的点
                System.out.print(distance_e_p[i][j]+" ");
            }
            System.out.println();
        }*/

        double min=0;
        int k=0;
        for (int i = 0; i < distance_e_p.length;i++){
            double sum=0;
            for (int j = 0; j < distance_e_p.length;j++){ // 寻找与其他点距离之和最短的点
                sum += distance_e_p[i][j];
            }
            if(i == 0){
//                System.out.println(sum); //测试
                min = sum;
            }else if(sum < min){
                min = sum;
                k = i;
//                System.out.println(sum); //测试
            }
        }
        /*for (int i = 0 ;i<classify_k[k].length;i++){
            System.out.print(classify_k[k][i]+" ");
        }
        System.out.println();*/
        return classify_k[k];
    }
    /*public static void main(String[] args) {
        String[][] data=Initia_data(399000,56);
        double[][] scope = scopecalcu(data);
        String[][] chromosomeK = Creating_chromosomeK(scope,3);
        for (int i = 0;i < chromosomeK.length; i++){
            for (int j = 0;j < chromosomeK[0].length;j++){
                System.out.print(chromosomeK[i][j]+" ");
            }
            System.out.println();
        }
    }*/
  /*  public static void main(String[] args) {
        String[][] data=Initia_data(399000,56);
      *//*  Red_Write_data rwd = new Red_Write_data();  // start
        Date start = new Date();
        rwd.Write("C:\\Myprofile\\opt.txt",data);
        Date end = new Date();
        System.out.println(end.getSeconds()-start.getSeconds()); // end*//*
       double[][] scope = scopecalcu(data);   //start
        for (int i=1;i<scope[0].length;i++){
                System.out.println(scope[0][i]+"~"+scope[1][i]);
        }  //end
       *//*for(int i = 0;i<data.length;i++){  // 测试Initia_data传回数组的正确性start
           for (int j = 0;j<data[0].length;j++){
               System.out.print(data[i][j]+" ");
           }
           System.out.println();
       } // end*//*


    }*/
  /* public static void main(String[] args) { // just test function ，test classify function
          String[][] data=Initia_data(1000,56);

          String[][] ki = {{"{8FA610BB-21EF-486C-9C1D-FB56F2550F8D}","0.4","0","0","0","0","1","0.0011","1","1","1","0.2718","0.2033","0.0242","0.1069","0.0000","0.0841","0.0545","1","0.5013","0.3275","0.0000","0.0000","0.0000","0.0100","0.0177","0.0305","0.0006","0.0000","0.0010","0.0009","0.0000","0.0000","0.0000","0.0337","0.2500","0.2308","0.3000","0.0000","0.0000","0.0000","0.1905","0.2857","0.0000","0.0000","0.1333","0.0000","0.0000","0.3333","0.2222","0.1538","0.3333","0.0000","0.0000","1","0"},
                  {"{8FA610BB-21EF-486C-9C1D-FB56F2550F8D}","0.5","0","0","0","0","1","0.0011","1","1","1","0.2518","0.2033","0.0242","0.1069","0.0000","0.0841","0.0545","1","0.5013","0.3275","0.0000","0.0000","0.0000","0.0000","0.0177","0.0305","0.0006","0.0000","0.0010","0.0009","0.0000","0.0000","0.0000","0.0307","0.2500","0.2308","0.3000","0.0000","0.0000","0.0000","0.1905","0.2157","0.0000","0.0000","0.1333","0.0000","0.0000","0.3333","0.2222","0.1538","0.3333","0.0000","0.0000","1","0"},
                  {"{8FA610BB-21EF-486C-9C1D-FB56F2550F8D}","0.5","0","0","0","0","1","0.0011","1","1","1","0.2718","0.2033","0.0242","0.1069","0.0000","0.0041","0.0545","1","0.5713","0.3275","0.0000","0.0000","0.0000","0.0000","0.0177","0.0205","0.0006","0.0000","0.0010","0.0009","0.0000","0.0000","0.0000","0.0337","0.2500","0.2008","0.3000","0.0000","0.0000","0.0000","0.1905","0.2857","0.0000","0.0000","0.1333","0.0000","0.0000","0.3033","0.2222","0.1538","0.3333","0.0000","0.0000","1","0"},
                        };
        Tools cl = new Tools();
        String[] str = cl.classify(data, ki);

        for(int i=0;i<str.length;i++){
            System.out.println("第"+i+"类");
            System.out.println(str[i]);

        }
    }*/

  public static double[] sort(double[] distance_i_all){//没用
      int k;
      double temp;
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
  }

  public static int[] sort_all_dis(double[] distance_i_all){//排序有错误
      int[] sort_result = new int[distance_i_all.length];
      double[] sorted = sort(distance_i_all);
      for (int i=0;i<sorted.length;i++){
          for (int j=0;j<sorted.length;j++){
            if(sorted[i]==distance_i_all[j]){
                sort_result[i]=j;
                break;
            }
          }
      }
      return sort_result;
  }
    public static String Mergeclass(String class1,String calss2){//合并两个字符串
        class1 += calss2;
        return class1;
    }
    public static boolean difference(int[] num,int ci,int n){
        boolean flag = true;
        for (int i=0;i<ci;i++){
            if(num[i]==n&&num[i]!=1&&num[i]!=2&&num[i]!=3){
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
  public static String[][] Heredity(String classify,double genaticrate,double variatrate,double[][] scope){
      // 将一维数组转化为二维数组
      java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.#####");
      String[][] classify_k = OnedimensToTwodimens(classify);
      int[] cross_id = AtoBn(0,classify_k.length,(int)(classify_k.length*genaticrate));

     /* for (int i = 0;i<cross_id.length;i++){
          System.out.println(cross_id[i]);
      }
*/
      String[][] new_chso = new String[cross_id.length][classify_k[0].length];

      for (int i=0;i<new_chso.length;i++){
              new_chso[i]=classify_k[cross_id[i]];
          new_chso[i][classify_k[0].length-1]="{mixture}";
      }
      for (int i = 0;i<new_chso.length;i++){
            for (int j = 0;j<new_chso[0].length;j++){
                System.out.print(new_chso[i][j]+" ");
            }
            System.out.println();
        }
      for (int i = 0;i<cross_id.length-1;i+=2){ // 遗传
          int[] rdm = AtoBn(2,42,1);
          String temp;
          for (int j=rdm[0];j<new_chso[0].length;j++){
              if (i!=1&&i!=2&&i!=3&&i!=41&&i!=42){
              temp = new_chso[i][j];
              new_chso[i][j] = new_chso[i+1][j];
              new_chso[i+1][j] = temp;
            }
         }
      }
      for (int i = 0;i<new_chso.length;i++){
          for (int j = 0;j<new_chso[0].length;j++){
              System.out.print(new_chso[i][j]+" ");
          }
          System.out.println();
      }
     /* for (int i = 0;i < new_chso.length; i++){
          for (int j = 0;j < new_chso[0].length;j++){
              System.out.print(new_chso[i][j]+" ");
          }
          System.out.println();
      }*/
      int[] variat_id = AtoBn(0,new_chso.length,(int)(new_chso.length*genaticrate));
      for (int i = 0;i<variat_id.length;i++){ // 变异
          int[] rdm = AtoBn(2,41,1);
          new_chso[variat_id[i]][rdm[0]] = ""+df.format(R_dom(scope[0][rdm[0]],scope[1][rdm[0]]));
      }
      return new_chso;
  }

}
