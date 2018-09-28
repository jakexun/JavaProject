import java.io.*;

public class Red_Write_data {
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

    /*
     * file是文件的地址
     * 文件中追加的内容
     *
     * */
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
                line = "";
                System.out.println("已经处理了"+((i+1)/100)+"00条数据！");
            }
        }
        if (i%100!=0)
            Write_line(file,line);
    }

    public static void Write_line(String file,String line){ //当前类中使用的函数工具
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(line);
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
}
