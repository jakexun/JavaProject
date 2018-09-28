public class Abnormal_data_monitoring {
    public static void main(String[] args) {
        Tools t = new Tools();
        int k = 3;
        String[][] data=t.Initia_data(399000,56);
        double[][] scope = t.scopecalcu(data);
        String[][] chromosomeK = t.Creating_chromosomeK(scope,k);
        String[] classify = t.classify(data, chromosomeK);
        for (int i = 0; i < k;i++){
            System.out.println("第"+i+"类！");
            System.out.println(classify[i]);
        }
       /* for (int i = 0;i < chromosomeK.length; i++){
            for (int j = 0;j < chromosomeK[0].length;j++){
                System.out.print(chromosomeK[i][j]+" ");
            }
            System.out.println();
        }*/
    }
}
