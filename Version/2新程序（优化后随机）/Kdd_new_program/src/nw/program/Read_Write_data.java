package nw.program;

import java.io.*;

public class Read_Write_data {
    public  String[] Read(String filepath,int n) { //读取txt文本数据
        String line[] = new String[n];
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            /* 读入TXT文件 */
            File filename = new File(filepath); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            line[0] = br.readLine();
            int i = 1;
            while (line != null && i < n) {

                line[i] = br.readLine(); // 一次读入一行数据
                i++;

            }
            br.close();     //关闭读取文件

        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }

    public void Write(String file,String[][] new_data){ //将新产生的数据写入文件
        String line="";
        int i;
        for (i = 0;i < new_data.length;i++){
            for (int j = 0;j < new_data[0].length;j++){
                if (j!=new_data[0].length-1)
                    line += new_data[i][j]+",";
                else
                    line += new_data[i][j];
            }
            line += "\n";
            if ((i+1)%100==0){
                Write_line(file,line);
            }
            if (i%100!=0){
                Write_line(file,line);
            }
            line = "";
        }
        System.out.println("写入"+(i+1)+"条数据！");
    }

    public static void Write_line(String file,String line){ //当前类中使用的函数工具
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(line);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void Read_write(String readpath,String writepath,int row) {
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

            /* 读入TXT文件 */
            File filename = new File(readpath); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = br.readLine();
            int i = 1;

            /* 写入Txt文件 */
            File writename = new File(writepath); // 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writepath, true)));

            while (line != null && i<=row) {
                out.write(line+"\n"); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
                line = br.readLine(); // 一次读入一行数据
                i++;
            }

            br.close();     //关闭读取文件
            out.close(); // 关闭关闭写入文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Read_write("normalized_data.txt","test_data_abnormal_reduced.txt",10000);
    }

    public int Read_result_number(String filepath) {
        int i = 0;
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            File filename = new File(filepath); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = null;
            line = br.readLine();
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                i++;
            }
            br.close();     //关闭读取文件
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(i);
        return i;
    }

    public static int[] read_fitness(String filepath){
        Tools t = new Tools();
        int[] wrong = {};
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            File filename = new File(filepath); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String[] line = br.readLine().split("\t");
            double[] fitness = new double[line.length];
            for (int i=0;i<fitness.length;i++){
                fitness[i] = Double.parseDouble(line[i]);
            }
            int[] result = t.sort_all_fit(fitness);
            br.close();     //关闭读取文件

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wrong;
    }

}
