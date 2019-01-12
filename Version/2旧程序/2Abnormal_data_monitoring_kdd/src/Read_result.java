import java.io.*;

public class Read_result {
    public int number(String filepath) {
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
}