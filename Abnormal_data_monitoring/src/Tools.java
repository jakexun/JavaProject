import java.util.Date;
import java.math.*;
public class Tools {
    public String[][] Initia_data(int row,int column){ //将读取的原始的字符串数据初始化转化成可计算的double类型的数据

        Red_Write_data rd = new Red_Write_data();
        String data_str[] = rd.Read("C:\\Myprofile\\family_normalization.txt", row);

        String [][]data = new String[data_str.length][column];

        for(int i = 0;i < data_str.length; i++){
            String[] line = data_str[i].split(",");
            for(int j=0;j < line.length; j++){
                data[i][j] = line[j];
            }
        }

        return data;
    }


    public double[][] scopecalcu(String[][] data){ //计算每一个数据项的范围
        int r = data.length;
        int c = data[0].length;
        double[][] scope = new double[2][c];
        for(int j = 1;j < c;j++){
            for(int i = 0; i < r;i++){
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
        return scope;
    }

    public static double distance(String point1[], String point2[]){//计算两点之间的距离 finished
        double sum_diff = 0, //所有坐标系上两点差的平方的和
                diff_value2; //每个坐标轴上两点差的平方
        for(int i = 1;i < point1.length;i++){
            diff_value2=(Double.parseDouble(point1[i])-Double.parseDouble(point2[i]))*(Double.parseDouble(point1[i])-Double.parseDouble(point2[i]));
            sum_diff += diff_value2;
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

    public static String ArrayToString(String[] s) { //功能函数，将double类型的数组转化成字符串
        String str= null,strtmp=""+s[0];
        for(int i=1;i < s.length;i++){
            str = strtmp+","+s[i];
            strtmp=str;
        }
        return str+"\n";
    }

    //对data中的点进行分类，分类完成后返回一个分完类的字符数组，数组下标表示类名
    public String[] classify(String[][] data,String[][] pointk){  // not finished
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
    public static double R_dom(double a, double b){ //随机生成a,到b之间的一个double类型的数
        if(a == b)
            return a;
        else if (a>b)
            return b + Math.abs(b-a)*Math.random();
        else
            return a + Math.abs(b-a)*Math.random();
    }

    public String[][] Creating_chromosomeK(double scope[][],int k){
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.####");
        String[][] chromosomeK = new String[k][scope[0].length];

        for (int j = 0;j < scope[0].length;j++){
            for (int i = 0; i < k; i++){
                if (j == 0){
                    chromosomeK[i][j] = "k" + i;
                }else{
                    chromosomeK[i][j] = String.valueOf(df.format(R_dom(scope[0][j],scope[1][j])));
                }
            }
        }

        return chromosomeK;
    }


    public static String[][] OnedimensToTwodimens(String class_kd1){
        String[] line = class_kd1.split("\n");
        String[] unit = line[0].split(",");
        String[][] class_kd2 = new String[line.length][unit.length];
        for (int i = 0;i<line.length;i++){
            class_kd2[i] = line[i].split(",");
        }
        return class_kd2;
    }

    public static double Numb_Array_Sum(double[] NumbArray){
        double sum = 0;
        for (int i = 0;i < NumbArray.length;i++){
            sum += NumbArray[i];
        }
        return sum;
    }

    public double Fitness(String class_kd1, String[][] ki ,int k){ // 求fitness的值
        //求类内距离
        String[][] class_kd2 = OnedimensToTwodimens(class_kd1);
        double[] distance_point = new double[class_kd2.length];
        for (int i=0; i<class_kd2.length;i++){
            distance_point[i]=distance(class_kd2[i],ki[k]);
        }
        double AvgDis = Numb_Array_Sum(distance_point)/distance_point.length; //类内距离

        //求类间距离
        double[] every_cla_intracla = new double[ki.length-1];
        for (int i = 0;i < ki.length;i++){
            if (i != k){
                every_cla_intracla[i] = AvgDis/distance(ki[i],ki[k]); //类内比内间距离
            }else
                every_cla_intracla[i] = 0;
        }
        return Numb_Array_Sum(every_cla_intracla);//求fitness:类内距离/类间距离之和
    }

    public static int FindCentral(String[][] data){ //找到中心点并返回中心点位置(下标)
        double[][] distance_e_p=new double[data.length][data.length];
        for (int i=0;i<data.length;i++){
            for (int j = 0;j<data.length;j++){
                if (i!=j){
                    distance_e_p[i][j] = distance(data[i],data[j]);
                }
            }
        }
        return 0;
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
}
