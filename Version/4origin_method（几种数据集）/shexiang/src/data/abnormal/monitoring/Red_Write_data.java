package data.abnormal.monitoring;

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

    public static void Read_write(String readpath,String writepath,int row) {
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

            /* 读入TXT文件 */
            File filename = new File(readpath); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = null;
            line = br.readLine();
            int i = 1;

            /* 写入Txt文件 */
            File writename = new File(writepath); // 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writepath, true)));

            while (line != null && i<=row) {
//                System.out.println(line);
                out.write(line+"\n"); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
                line = br.readLine(); // 一次读入一行数据
                i++;
            }

            br.close();     //关闭读取文件

            out.close(); // 关闭关闭写入文件
            System.out.println("Read_write写入"+row+"条数据！");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    // 读取数据进行统计
    public static void Statictical(String origin_file,int row,int column,String statictical_file,String result_file,int times) {
        try {
            java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");
            int p = 0;
            int n = 0;
            Red_Write_data rwd = new Red_Write_data();
            Tools t = new Tools();

            if (times==1){
                String[][] databf = t.Initia_data(origin_file, row, column);
                for (int i = 0; i < databf.length; i++) {
                    if (databf[i][column-1].equals("g")) {
                        p += 1;
                    } else {
                        n += 1;
                    }
                }
                BufferedWriter write= new BufferedWriter(new OutputStreamWriter(new FileOutputStream("result_infor.txt", false)));
                write.write(""+p+" "+n);
                write.close();
            }else {
                BufferedReader read= new BufferedReader(new InputStreamReader(new FileInputStream(new File("result_infor.txt"))));
                String[] str=read.readLine().split(" ");
                p=Integer.parseInt(str[0]);
                n=Integer.parseInt(str[1]);
                System.out.println("正确的数据有" + p + "条!");
                System.out.println("错误的数据有" + n + "条!");
                read.close();
            }

            int number =rwd.Read_result_number(statictical_file);
            String[][] datatd = t.Initia_data(statictical_file, number, column);
            int tn = 0;
            int nd=0;
            int fn=0;
            System.out.println("---------------------------------------------------------");
            int sum=0;
            for (int i = 0; i < datatd.length; i++) {
                if (datatd[i][column-1].equals("b")) {
                    tn += 1;
                    sum++;
                }
                if (datatd[i][column-1].equals("{mixture}")){
                    nd+=1;
                }
                if (datatd[i][column-1].equals("g")){
                    fn+=1;
                    sum++;
                }
            }
            int tp = p - fn;
            int fp = n - tn;
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(result_file, true)));
            double precition = tp*1.0/(tp+fn),recall = tp*1.0/p;
            if (times==1){
                String experiment_infor="测试数据量"+row+"条，正常数据"+p+"条，异常数据"+n+"条。";
                String title = "结果总数"+",真异常"+",新数据"+",假异常"+",查准率"+"，召回率";
                out.write(experiment_infor+"\n");
                out.write(title+"\n");
            }
            String line = ""+sum+","+tn+","+nd+","+fn+","+df.format(tn*1.0/(tn+fn)*100)+"%"+","+df.format(tn*1.0/(tn+fp)*100)+"%"/*+","+df.format(recall*100)+"%"+","+df.format(tn*1.0/n*100)+"%"+","+df.format(precition*100)+"%"+","+df.format((2*precition*recall)/(precition+recall)*100)+"%"*/;
            out.write(line+"\n");
            out.close();
            System.out.println(statictical_file+"统计完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
