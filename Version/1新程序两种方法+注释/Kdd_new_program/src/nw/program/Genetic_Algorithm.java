package nw.program;

import data.process.Statistical;

import static nw.program.Tools.*;

public class Genetic_Algorithm {
    public static String[][] Genetic(double genaticrate,int column,String filepath){
        // 将一维数组转化为二维数组
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#######");
//        String filepath = "glass.txt";
        int row=GetNumber(filepath);
        String[][] classify_k = Initia_data(filepath,row,column);

        int new_data_number=(int)(row*genaticrate);
//        /*int normal_number=GetNormalNumber(filepath);
//        if (normal_number<new_data_number){
//            new_data_number=normal_number;
//        }
//*/
        String[][] new_chso = new String[new_data_number][column];
        //遗传变异数据准备模块
        for (int i = 0,j=0; i < classify_k.length;) {
            /*if(classify_k[i][column - 1].equals("normal.")&&j<new_data_number){*/
                new_chso[j] = classify_k[i];
                new_chso[j][classify_k[0].length - 1] = "-1"/*"{mixture}"*/;
                i++;
                j++;
            /*}else if (j>=new_data_number){
                break;
            }else i++;*/
        }
        //遗传模块
        for (int i = 0; i < new_data_number-1; i += 2) { // 遗传
            int[] rdm = AtoBn(0, column-2, 1);
//            String temp;
            for (int j = rdm[0]; j < new_chso[0].length; j++) {
//                    temp = new_chso[i][j];
                    new_chso[i][j] = new_chso[i + 1][j]=""+df.format((Double.parseDouble(new_chso[i][j])+Double.parseDouble(new_chso[i + 1][j]))/2);
//                    new_chso[i + 1][j] = temp;
            }
        }

//        //变异模块
//       /* int[] variat_id = AtoBn(0, new_data_number, (int) (new_data_number * variation));
//        for (int i = 0; i < variat_id.length; i++) { // 变异
//            int[] rdm = AtoBn(0, column-2, 1);
//            new_chso[variat_id[i]][rdm[0]] = "" + df.format(R_dom(scope[0][rdm[0]], scope[1][rdm[0]]));
//        }*/
        String[][] new_data = new String[new_data_number/2][column];
        for (int i=0,j=0;i<new_data_number/2;i++,j+=2){
            new_data[i]=new_chso[j];
        }
        return new_data;
    }

    public static String[][] Variation(double[][] scope,double variation,int column,String filepath){
    java.text.DecimalFormat df = new java.text.DecimalFormat("#.#######");

    int row=GetNumber(filepath);
    String[][] classify_k = Initia_data(filepath,row,column);
    int new_data_number=(int)(row*variation);
    int[] varia=AtoBn(0,row-1,new_data_number);
    //System.out.println("qqq"+classify_k.length);
//    for (int i=0;i<new_data_number;i++) {
//        System.out.print(varia[i]+" ");
//    }
    String[][] new_data=new String[new_data_number][column];
    for (int i=0;i<new_data_number;i++){
        new_data[i] = classify_k[varia[i]];
        new_data[i][classify_k[0].length - 1] = "-2"/*"{mixture}"*/;
    }
    for (int i = 0; i < new_data_number; i++) { // 变异
        int[] rdm = AtoBn(0, column-2, 1);
        new_data[i][rdm[0]] = "" + df.format(R_dom(scope[0][rdm[0]], scope[1][rdm[0]]));
    }
    return new_data;
}

    public void Genetic_Algorithm(double[][] scope,double genaticrate,double variation,int column,String filepath,String originfile){
        Read_Write_data rwd=new Read_Write_data();
        String[][] ge_new_data=Genetic(genaticrate,column,filepath);
        rwd.Write(originfile,ge_new_data);
        String[][] va_new_data=Variation(scope,variation,column,filepath);
        rwd.Write(originfile,va_new_data);
    }

}
