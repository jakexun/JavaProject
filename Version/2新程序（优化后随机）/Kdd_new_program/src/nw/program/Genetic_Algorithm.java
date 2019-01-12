package nw.program;

import data.process.Statistical;

import static nw.program.Tools.*;

public class Genetic_Algorithm {
    public static String[][] Genetic_Algorithm(double[][] scope,double genaticrate,double variation,int column){
        // 将一维数组转化为二维数组
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#####");
        String filepath = "mergeclass.txt";
        int row=GetNumber(filepath);
        String[][] classify_k = Initia_data(filepath,row,column);

        int new_data_number=(int)(row*genaticrate);
        int normal_number=GetNormalNumber(filepath);
        if (normal_number<new_data_number){
            new_data_number=normal_number;
        }

        String[][] new_chso = new String[new_data_number][column];

        for (int i = 0,j=0; i < classify_k.length;) {
            if(!classify_k[i][column - 1].equals("normal.")&&j<new_data_number){
                new_chso[j] = classify_k[i];
                new_chso[j][classify_k[0].length - 1] = "{mixture}";
                i++;
                j++;
            }else if (j>=new_data_number){
                break;
            }else i++;
        }

        for (int i = 0; i < new_data_number-1; i += 2) { // 遗传
            int[] rdm = AtoBn(0, column-2, 1);
            String temp;
            for (int j = rdm[0]; j < new_chso[0].length; j++) {
                    temp = new_chso[i][j];
                    new_chso[i][j] = new_chso[i + 1][j];
                    new_chso[i + 1][j] = temp;
            }
        }

        //变异模块
        /*int[] variat_id = AtoBn(0, new_data_number, (int) (new_data_number * variation));
        for (int i = 0; i < variat_id.length; i++) { // 变异
            int[] rdm = AtoBn(0, column-2, 1);
            new_chso[variat_id[i]][rdm[0]] = "" + df.format(R_dom(scope[0][rdm[0]], scope[1][rdm[0]]));
        }*/
        return new_chso;
    }

}
