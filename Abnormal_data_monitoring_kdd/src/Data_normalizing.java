import java.io.*;

public class Data_normalizing {
    public static void main(String[] args) {
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.######");
            Tools t =new Tools();
            String[][] databf=t.Initia_data("normalized_data.txt",77219,42);
            double[][] scope = t.scopecalcu(databf);

            String pathname = "Finished_cleaning.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = null;
            line = br.readLine();
            for (int i = 0;i<scope.length;i++){
                for (int j = 0;j<scope[0].length;j++){
                    System.out.print(scope[i][j]+" ");
                }
                System.out.println();
            }
             /*写入Txt文件 */
            File writename = new File("normalized_data.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));

            for (int i = 0;i<scope[0].length;i++){
                    System.out.print(scope[1][i]-scope[0][i]+" ");
            }
            System.out.println();
            while (line != null) {
                String[] data = line.split(",");
                for (int j = 0; j < data.length; j++) {
                    if (j != 1 && j != 2 && j != 3 && j != 41 && (scope[1][j]-scope[0][j])!=0) {
                        data[j] = ""+df.format(((Double.parseDouble(data[j])-scope[0][j])/(scope[1][j]-scope[0][j])));
                    }
                }
                String linesed ="";
                for (int j = 0; j < data.length; j++) {
                    if (j!=data.length-1)
                        linesed += data[j]+",";
                    else
                        linesed += data[j];
                }
                out.write(linesed+"\n"); // \r\n即为换行
                System.out.println(linesed);
                out.flush(); // 把缓存区内容压入文件
                line = br.readLine(); // 一次读入一行数据
            }
            br.close();     //关闭读取文件
            out.close(); // 关闭关闭写入文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
