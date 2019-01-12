package nwun.ml.dmt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author ’≈¿Ò÷«
 */
public class Tools {
    public static String[][] Read_Data(String filepath) {
        int row=GetRowNumber(filepath),column=GetColumnNumber(filepath);
        String[][] data = new String[row][column];
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath))));
            String line=reader.readLine();
            for (int i = 0; i < row; i++) {
                data[i] = line.split(",");
                line=reader.readLine();
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
    public static int GetRowNumber(String filepath) {
        int n=0;
        try {
            File filename = new File(filepath);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);

            String line= br.readLine();
            while (line != null ) {
                n++;
                line= br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }
    public static int GetColumnNumber(String filepath){
        int CN=0;
        try {
            File filename = new File(filepath);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);

            CN = br.readLine().split(",").length;

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CN;
    }

//    public static void main(String[] args) {
//        String[][] data=Read_Data("watermelon_data_train.txt");
//        for (int i=0;i<data.length;i++){
//            for (int j=0;j<data[0].length;j++){
//                System.out.print(data[i][j]+" ");
//            }
//            System.out.println();
//        }
//    }
}
