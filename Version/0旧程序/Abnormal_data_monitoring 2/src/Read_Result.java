import java.io.*;

public class Read_Result {
    public static void main(String args[]) {
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  

            /* 读入TXT文件 */
            String pathname = "C:\\Myprofile\\corrected"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input。txt文件  
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line = null;
            line = br.readLine();
            int i = 1;

            while (line != null && i<= 100) {
                System.out.println(line);

                line = br.readLine(); // 一次读入一行数据
                i++;
            }
            br.close();     //关闭读取文件
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}  //共42项，2,3,4,42为字符串