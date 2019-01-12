import java.math.*;
public class Test {
    public static void main(String[] args) {
        String str1 = "asd";
        String str2 = "dwd";
        str1=str2;
        str2 = null;
        System.out.println(str1);
      /*  System.out.println(Math.random());
        for (int i = 0;i<100;i++)
            System.out.println(Math.random());*/
        /*int[] num = AtoBn(50,100,1);
        sort(num);
        for (int i = 0;i<1;i++){
            System.out.println(num[i]);
        }*/
        /*System.out.println((int)(3+Math.abs(10-3)*Math.random()));*/
        /*String str1 = "123";
        String str2 = "abc";
        str1 =Mergeclass(str1,str2);
        System.out.println(str1);*/
        /*int[] d=new int[3];
        System.out.println(d[2]);
        System.out.println("before");*/
       /* for (int i=0;i<distance_i_all.length;i++){
            System.out.print(distance_i_all[i]+" ");
            if(i%10==0&&i!=0){
                System.out.println();
            }
        }*/
     /*   String[][] central_k = new String[k][];
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
        }*/
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
    }

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
}
