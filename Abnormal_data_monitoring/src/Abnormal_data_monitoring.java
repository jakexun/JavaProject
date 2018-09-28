public class Abnormal_data_monitoring {
    public static void main(String[] args) {
        Tools t = new Tools();
        int k = 3;
        String[][] data=t.Initia_data(100,56);
        double[][] scope = t.scopecalcu(data);
        String[][] chromosomeK = t.Creating_chromosomeK(scope,k);//随机给的k个点
        String[] classify = t.classify(data, chromosomeK);
        String[][] classify_0 = t.OnedimensToTwodimens(classify[0]);//这里需要根据实际情况自己加
        String[][] classify_1 = t.OnedimensToTwodimens(classify[1]);
        String[][] classify_2 = t.OnedimensToTwodimens(classify[2]);

        String[][] central_k = new String[k][];
        central_k[0] = classify_0[t.Find_Central(classify_0)];
        central_k[1] = classify_1[t.Find_Central(classify_1)];
        central_k[2] = classify_2[t.Find_Central(classify_2)];
        for (int i = 0; i < k;i++){
            System.out.println("第"+i+"类！");
            System.out.println(classify[i]);
            switch (i){
                case 0:System.out.println("第"+i+"类的中心点是：");
                    for (int j = 0;j<classify_0[0].length;j++){
                             System.out.print(central_k[0][j]+" ");
                    }
                    System.out.println();
                continue;
                case 1:System.out.println("第"+i+"类的中心点是：");
                    for (int j = 0;j<classify_1[0].length;j++){
                         System.out.print(central_k[1][j]+" ");
                    }
                    System.out.println();
                continue;
                case 2:System.out.println("第"+i+"类的中心点是：");
                    for (int j = 0;j<classify_2[0].length;j++){
                            System.out.print(central_k[2][j]+" ");
                    }
                    System.out.println();
                continue;
            }
        }
       /* for (int i = 0;i < chromosomeK.length; i++){
            for (int j = 0;j < chromosomeK[0].length;j++){
                System.out.print(chromosomeK[i][j]+" ");
            }
            System.out.println();
        }*/
    }
}
