public class new_test {
    public static void main(String[] args) {
        Tools t = new Tools();
        int row = 1000;
        String filepath = "C:\\Myprofile\\output.txt";
        String[][] databf=t.Initia_data(filepath,row,43);
       /* for (int i = 0;i<databf.length;i++){
            for (int j = 0;j<databf[0].length;j++){
                System.out.print(databf[i][j]+" ");
            }
            System.out.println();
        }*/
        double[][] scopebf = t.scopecalcu(databf);
        for (int i = 0;i<scopebf.length;i++){
            for (int j = 0;j<scopebf[0].length;j++){
                System.out.print(scopebf[i][j]+" ");
            }
            System.out.println();
        }
        String[][] chromosomeK = t.Creating_chromosomeK(scopebf,3);//随机给的k个点
        /*for (int i = 0;i<chromosomeK.length;i++){
            for (int j = 0;j<chromosomeK[0].length;j++){
                System.out.print(chromosomeK[i][j]+" ");
            }
            System.out.println();
        }*/
        String[] classify = t.classify(databf, chromosomeK);//根据k个点将数据分为k个簇
    }
}
