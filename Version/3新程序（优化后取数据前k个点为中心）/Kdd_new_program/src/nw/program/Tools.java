package nw.program;

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
    //求数据条数
    public static int GetNumber(String filepath) { //获取文件中数据条数
        int n=0;
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            /* 读入TXT文件 */
            File filename = new File(filepath); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            String line= br.readLine();
            while (line != null ) {
                n++;
                line= br.readLine();
            }
            br.close();     //关闭读取文件

        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
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
                }
                result[min_distance_k(dis)]++;
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
        double min = dis[0];
        int min_k = 0;
        for (int i = 1; i < dis.length; i++) {
            if (dis[i] < min) {
                min = dis[i];
                min_k = i;
            }
        }
        return min_k;
    }
    // 计算两点之间的距离 finished
    public static double distance(String point1[], String point2[]) {
        double sum_diff = 0,diff_value2; //每个坐标轴上两点差的平方
            for (int i = 0; i < point1.length-1; i++) {
                diff_value2 = (Double.parseDouble(point1[i]) - Double.parseDouble(point2[i]))*(Double.parseDouble(point1[i]) - Double.parseDouble(point2[i]));
                sum_diff += diff_value2;
            }
        return sum_diff;
    }

    public static int[] sort_all_fit(double[] distance_i_all) {
        int[] sort_result = new int[distance_i_all.length];
        double[] temp = new double[distance_i_all.length];
        int[] origin=new int[distance_i_all.length];
        for (int i = 0; i < sort_result.length; i++) {
            temp[i] = distance_i_all[i];
            origin[i]=i;
        }

        double[] sorted = sort(distance_i_all/*,origin*/);

        for (int i = 0; i < sorted.length; i++) {
            for (int j = 0; j < sorted.length; j++) {
                if (sorted[i] == temp[j]&&origin[j]!=-1) {
                    sort_result[i] = j;
                    origin[j]=-1;
                    break;
                }
            }
        }
        System.out.println();
//        /*for (int i=0;i<distance_i_all.length;i++){
//            System.out.println(sorted[i]);
//            System.out.println(temp[sort_result[i]]);
//            System.out.println(""+i+" "+sort_result[i]);
//        }*/
        return sort_result;
    }
    public static double[] sort(double[] distance_i_all/*,int[] oringin*/) {
        int k/*,kl*/;
        double temp;
        int i = 0;
        while (i < distance_i_all.length) {
            k = i;
            /*kl=oringin[i];*/
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
        /*for (int j=0;j<distance_i_all.length;j++){
            System.out.println(distance_i_all[j]);
        }*/
        return distance_i_all;
    }

    public static String[] Find_Central(int ki,int row,int column) { //11找到中心点并返回中心点位置(下标)
        String filepath = "classify[" + ki + "].txt";
        if (row == 0)
            return null;
        String[][] classify_k = Initia_data(filepath,row,column);
        int central;
        /*double[] distance_e_p*/ central = distance_of_betw_ech_point(classify_k);

//        int k=min_distance_k(distance_e_p);
        return classify_k[central/*k*/];
    }

    public static int/*double[]*/ distance_of_betw_ech_point(String[][] classify_k) {
//        double[] distance_e_p = new double[classify_k.length];
        double dist;
        double min=0;
        int minres=0;
        for (int i = 0; i < classify_k.length; i++) {
            dist=0;
            for (int j = 0; j < classify_k.length; j++) {
                if (i != j) {
                    dist/*distance_e_p[i]*/ += distance(classify_k[i], classify_k[j]); //求出所有点与其他所有点的距离
                } else {
                    dist/*distance_e_p[i]*/ += 0;
                }
            }
            if (i==0){
                min=dist;
            }else if(dist<min){
                min=dist;
                minres=i;
            }
        }
        return /*distance_e_p*/minres;
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

    public static double Fitness(int k, String[][] ki, int row, int column) { // 10求以k点为中心的簇的fitness的值
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

    public static double[] GetFitness(int k,String[][] central,int[] row,int column){
        double[] fitness=new double[k];
        for (int i=0;i<k;i++){
            fitness[i]= Fitness(i,central,row[i],column);
        }
        return fitness;
    }

//    public static int GetNormalNumber(String filepath) { //读取txt文本数据
//        int n=0;
//        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
//            /* 读入TXT文件 */
//            File filename = new File(filepath); // 要读取以上路径的input。txt文件
//            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
//
//            String line= br.readLine();
//            while (line != null ) {
//                if (line.contains("normal.")){
//                    n++;
//                }
//                line= br.readLine();
//            }
//            br.close();     //关闭读取文件
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return n;
//    }

    public static int[] AtoBn(int a, int b, int n) {//生成a到b之间的n个整数不包括b包括a
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            if (a < b) {
                result[i] = (int) (a + Math.abs(b - a) * Math.random());
                while (!difference(result, i, result[i])) {
                    result[i] = (int) (a + Math.abs(b - a) * Math.random());
                }
            } else {
                result[i] = (int) (b + Math.abs(b - a) * Math.random());
                while (!difference(result, i, result[i])) {
                    result[i] = (int) (b + Math.abs(b - a) * Math.random());
                }
            }
        }
        return result;
    }

    public static boolean difference(int[] num, int ci, int n) {
        boolean flag = true;
        for (int i = 0; i < ci; i++) {
            if (num[i] == n && num[i] != 1 && num[i] != 2 && num[i] != 3) {
                flag = false;
            }
        }
        return flag;
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

//   /* public static void main(String[] args) {
//        String filepath="coded_data.txt";
//        int row=GetNumber(filepath);
//        int column=GetColumnNumber(filepath);
//        double[][] scope=Scope(filepath,column);
//        for (int i=0;i<scope.length;i++){
//            for (int j=0;j<scope[i].length;j++){
//                System.out.print(scope[i][j]+" ");
//            }
//            System.out.println();
//        }
//        String[][] central=CreatingRandomCentral(scope,3);
//        for (int i=0;i<central.length;i++){
//            for (int j=0;j<central[i].length;j++){
//                System.out.print(central[i][j]+" ");
//            }
//            System.out.println();
//        }
//        int[] classify=classify(filepath,central);
//        central=GetCentral(3,classify,column);
//        for (int i=0;i<central.length;i++){
//            for (int j=0;j<central[i].length;j++){
//                System.out.print(central[i][j]+" ");
//            }
//            System.out.println();
//        }
//    }*/

   public static String[][] GetCentral(String datapath,int column,int n,int a) {
       String[][] central = new String[n+a][column];
       try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
           /* 读入TXT文件 */
           File filename = new File(datapath); // 要读取以上路径的input。txt文件
           InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
           BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

           String line = br.readLine();
           int r = 1,w = 1,i=0;
           while (line != null&&i<(n+a)) {

               if (line.contains("normal")&&r<=n){
                   central[i]=line.split(",");
                   r++;
                   i++;
               }
               if (!line.contains("normal")&&w<=a){
                   central[i]=line.split(",");
                   w++;
                   i++;
               }
               line = br.readLine();
           }
           br.close();     //关闭读取文件

       } catch (Exception e) {
           e.printStackTrace();
       }
       return central;
   }

    public static void Sort_from_far_to_near(String read_class_file, String write_class_file, String[] central_i, int row, int column) {
        Read_Write_data rwd = new Read_Write_data();
        String[][] classed = Initia_data(read_class_file, row, column);

        double[] distance_point = new double[classed.length];
        double median;
        for (int i = 0; i < classed.length; i++) {
            distance_point[i] = distance(classed[i], central_i);
        }
        double[] temp = new double[distance_point.length];
        for (int i = 0; i < distance_point.length; i++) {
            temp[i] = distance_point[i];
    }

        int[] distance_point_sorted = sort_all_fit(distance_point);
        if (distance_point.length%2==0){
            median=(temp[distance_point_sorted[distance_point.length/2-1]]+temp[distance_point_sorted[distance_point.length/2]])/2;
        }else {
            median=temp[distance_point_sorted[distance_point.length/2]];
        }

        System.out.println(write_class_file);
        for (int i = distance_point_sorted.length-1;i>=0; i--) {
            String line = ArrayToString(classed[distance_point_sorted[i]]);
            rwd.Write_line(write_class_file, line);
            rwd.Write_line("dist"+write_class_file,temp[distance_point_sorted[i]]/median/*+" "+distance_point[i]*/+"\n");
//            rwd.Write_line("disto"+write_class_file,temp[i]+"\n");
        }
       /* for (int i=0;i<central.length;i++){
            for (int j=0; j<classed.length;j++){
                System.out.print(distance_point_sorted[i]+" ");
            }
            System.out.println();
        }*/

    }

    public static boolean ArrayEquals(String[][] strarr1,String[][] strarr2){
        for (int i=0;i<strarr1.length;i++){
            for (int j=0;j<strarr1[i].length;j++){
                if (!strarr1[i][j].equals(strarr2[i][j])){
                    return false;
                }
            }
        }
        return true;
    }

    public static String[][] ArrayCopy(String[][] Stra){
        String[][] strb=new String[Stra.length][];
        for (int i=0;i<Stra.length;i++){
            strb[i]=new String[Stra[i].length];
            for (int j=0;j<Stra[i].length;j++){
                strb[i][j]=Stra[i][j];
            }
        }
        return strb;
    }

//    public static void main(String[] args) {
//        String[][] central=GetCentral("test_file.txt",41,3,2);
//        for (int i=0;i<central.length;i++){
//            for (int j=0;j<central[i].length;j++){
//                System.out.print(central[i][j]+" ");
//            }
//            System.out.println();
//        }
//    }
}
