package com.kmeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Tools {
    public static String[][] Initia_data(String filepath,int row,int column) { //1 将读取的原始的字符串数据初始化转化成可计算的double类型的数据
        Read_Write_data rd = new Read_Write_data();

        String data_str[] = rd.Read(filepath, row);

        String[][] data = new String[data_str.length][column];

        for (int i = 0; i < data_str.length; i++) {
            String[] line = data_str[i].split(",");
            for (int j = 0; j < line.length; j++) {
                data[i][j] = line[j];
            }
        }

        return data;
    }

    //求属性的域
    public static double[][] Scope(String filepath,int column){
        double[][] scope=new double[2][column];
        try {
            File filename = new File(filepath);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);

            String line = br.readLine();
            double[] data=StringToDouble(line);

            for (int i=0;i<column;i++){
                scope[0][i]=data[i];
                scope[1][i]=data[i];
            }

            line=br.readLine();
            while (line != null ) {
                data=StringToDouble(line);
                for (int j=0;j<column;j++){
                    if (scope[0][j]<data[j]){
                        scope[0][j]=data[j];
                    }
                    if (scope[1][j]>data[j]){
                        scope[1][j]=data[j];
                    }
                }
                line=br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scope;
    }

    //获取有多少列
    public static int GetColumnNumber(String filepath){
        int CN=0;
        try {
            File filename = new File(filepath);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);

            CN = br.readLine().split(",").length;

            br.close();     //关闭读取文件

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CN;
    }

    //将一行数据转换成double数组类型
    public static double[] StringToDouble(String line){
        String[] str=line.split(",");
        double[] data=new double[str.length];
        for (int i=0;i<data.length-1;i++){
            data[i]=Double.parseDouble(str[i]);
        }
        return data;
    }

    //生成k个中心点
    public static String[][] CreatingRandomCentral(double scope[][], int k) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#####");
        String[][] chromosomeK = new String[k][scope[0].length];
        for (int j = 0; j < scope[0].length; j++) {
            for (int i = 0; i < k; i++) {
                chromosomeK[i][j] = String.valueOf(df.format(R_dom(scope[0][j], scope[1][j])));
            }
        }

        return chromosomeK;
    }

    //随机生成a,到b之间的一个double类型的数
    public static double R_dom(double a, double b) {
        if (a == b)
            return a;
        else if (a > b) {
            return b + Math.abs(b - a) * Math.random();
        } else {
            return a + Math.abs(b - a) * Math.random();
        }
    }

    //对数据分类
    public static int[] classify(String datafile, String[][] pointk) {  // 5not finished
        int k = pointk.length; //k的值
        int[] result=new int[k];
        try {
            File data=new File(datafile);
            InputStreamReader in_d=new InputStreamReader(new FileInputStream(data));
            BufferedReader in_d_b=new BufferedReader(in_d);
            String line=in_d_b.readLine();
            Read_Write_data rwd = new Read_Write_data();
            double[] dis = new double[k]; //min_dis表示一个点与ki的距离
            String[] str = new String[k]; //分类后的类文件名String数组
            //起文件名
            for (int i = 0; i < str.length; i++) {
                str[i] = "classify[" + i + "].txt";
            }
            while (line!=null) {
                String[] line_array=line.split(",");
                for (int j = 0; j < k; j++) {
                    dis[j] = distance(line_array, pointk[j]);
//                    System.out.print(dis[j]+" ");
                }
//                System.out.println();
                result[min_distance_k(dis)]++;
//                System.out.println(dis[min_distance_k(dis)]);
                rwd.Write_line(str[min_distance_k(dis)], line+"\n");
                line=in_d_b.readLine();
            }
            in_d_b.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    //将一个字符串数组转化为一个以\n结尾的字符串
    public static String ArrayToString(String[] s) {
        String str = null, strtmp = "" + s[0];
        for (int i = 1; i < s.length; i++) {
            str = strtmp + "," + s[i];
            strtmp = str;
        }
        return str + "\n";
    }

    //最近距离
    public static int min_distance_k(double[] dis) {
        double min = 99999;
        int min_k = 99999;
        for (int i = 0; i < dis.length; i++) {
            if (dis[i] <= min) {
                min = dis[i];
                min_k = i;
            }
        }
        return min_k;
    }

    // 计算两点之间的距离 finished
    public static double distance(String point1[], String point2[]) {
        double sum_diff = 0,diff_value2; //每个坐标轴上两点差的平方
            for (int i = 0; i < point1.length; i++) {
                diff_value2 = Math.pow((Double.parseDouble(point1[i]) - Double.parseDouble(point2[i])),2)/**(Double.parseDouble(point1[i]) - Double.parseDouble(point2[i]))*/;
                sum_diff += diff_value2;
            }
        return Math.sqrt(sum_diff);
    }

//    public static int[] sort_all_fit(double[] distance_i_all) {
//        int[] sort_result = new int[distance_i_all.length];
//        double[] temp = new double[distance_i_all.length];
//        int[] origin=new int[distance_i_all.length];
//        for (int i = 0; i < sort_result.length; i++) {
//            temp[i] = distance_i_all[i];
//            origin[i]=i;
//        }
//
//        double[] sorted = sort(distance_i_all/*,origin*/);
//
//        for (int i = 0; i < sorted.length; i++) {
//            for (int j = 0; j < sorted.length; j++) {
//                if (sorted[i] == temp[j]&&origin[j]!=-1) {
//                    sort_result[i] = j;
//                    origin[j]=-1;
//                    break;
//                }
//            }
//        }
//        System.out.println();
//        return sort_result;
//    }

    public static double[] sort(double[] distance_i_all) {
        int k;
        double temp;
        int i = 0;
        while (i < distance_i_all.length) {
            k = i;
            for (int j = i + 1; j < distance_i_all.length; j++) {
                if (distance_i_all[k] <= distance_i_all[j]) {
                    k = j;
                }
            }
            temp = distance_i_all[i];
            distance_i_all[i] = distance_i_all[k];
            distance_i_all[k] = temp;
            i++;
        }
        return distance_i_all;
    }

    public static String[] Find_Central(int ki,int row,int column) { //11找到中心点并返回中心点位置(下标)
        String filepath = "classify[" + ki + "].txt";
        if (row == 0)
            return null;
        String[][] classify_k = Initia_data(filepath,row,column);

        double[] distance_e_p = distance_of_betw_ech_point(classify_k);

        int k=min_distance_k(distance_e_p);
        return classify_k[k];
    }

    public static double[] distance_of_betw_ech_point(String[][] classify_k) {
        double[] distance_e_p = new double[classify_k.length];
        for (int i = 0; i < classify_k.length; i++) {
            for (int j = 0; j < classify_k.length; j++) {
                if (i != j) {
                    distance_e_p[i] += distance(classify_k[i], classify_k[j]); //求出所有点与其他所有点的距离
                } else {
                    distance_e_p[i] += 0;
                }
            }
        }
        return distance_e_p;
    }

    public static String[][] GetCentral(int k,int[] classify,int column){
        String[][] central=new String[k][];
        for (int i=0;i<k;i++){
            central[i]=Find_Central(i,classify[i],column);
        }
        return central;
    }

    public static boolean IncludeZero(int[] Array) {
        for (int i = 0; i < Array.length; i++) {
            if (Array[i] == 0) {
                return true;
            }
        }
        return false;
    }

    public static void Clean(int k){
        for (int i =0;i<k;i++){//清楚上次分类的文件
            File classed_file_delete = new File("classify["+i+"].txt");
            classed_file_delete.delete();

            File classed_max_file_delete = new File("classify[max"+i+"].txt");
            classed_max_file_delete.delete();

            File classed_min_file_delete = new File("classify[min"+i+"].txt");
            classed_min_file_delete.delete();
        }
    }

    public static double Fitness(int k, String[][] ki, int row, int column) {
        // 10求以k点为中心的簇的fitness的值
        //求类内距离
        if (row == 0)
            return 0;
        String filepath = "classify[" + k + "].txt";
        String[][] class_kd2 = Initia_data(filepath,row,column);
        double[] distance_point = new double[class_kd2.length];
        for (int i = 0; i < class_kd2.length; i++) {
            distance_point[i] = distance(class_kd2[i], ki[k]);
        }
        double AvgDis = Numb_Array_Sum(distance_point) / distance_point.length;

        //求类间距离
        System.out.println("半径：" + AvgDis);
        double[] every_cla_intracla = new double[ki.length];
        for (int i = 0; i < ki.length; i++) {
            if (i != k) {
                every_cla_intracla[i] = AvgDis / distance(ki[i], ki[k]); //类内比内间距离
            } else
                every_cla_intracla[i] = 0;
        }

        return Numb_Array_Sum(every_cla_intracla);//求fitness:类内距离/类间距离之和
    }

    public static double Numb_Array_Sum(double[] NumbArray) {//9
        double sum = 0;
        for (int i = 0; i < NumbArray.length; i++) {
            sum += NumbArray[i];
        }
        return sum;
    }

    public static boolean Arrayequals(int[] array1,int[] array2){
        boolean flag=false;
        for (int i=0;i<array1.length;i++){
            flag=false;
            for (int j=0;j<array2.length;j++){
                if (array1[i]==array2[j]){
                    flag=true;
                    break;
                }
            }
            if (flag==true){
                continue;
            }else break;
        }
        return flag;
    }

}
