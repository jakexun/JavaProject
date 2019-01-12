package data.process;

import java.io.*;

public class Remove_duplicate {
    public static void main(String[] args) {
        try {
            String pathname = "classify[1].txt"; //读取的需要清理的文件
            File filename = new File(pathname); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = null;
            line = br.readLine();
            int i = 1;
            String[] lines = new String[77291];
            /* 写入Txt文件 */
            File writename = new File("cleaned_classify[1].txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));

            while (line != null && i<77291) {
                lines[i-1]=line;
                //System.out.println(line);
                int j = i-2;
                for (;j>=0;j--){
                    if (lines[i-1].equals(lines[j])){
                        break;
                    }
                }
                if (j==-1){
                    out.write(line+"\n"); // \r\n即为换行
                    out.flush(); // 把缓存区内容压入文件
                }
                line = br.readLine(); // 一次读入一行数据
                i++;
            }
            br.close();     //关闭读取文件

            out.close(); // 关闭关闭写入文件

            /* *//* 写入Txt文件 *//*
            File writename = new File("C:\\Myprofile\\output.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write("我会写入文件啦\r\n"); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件  */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
