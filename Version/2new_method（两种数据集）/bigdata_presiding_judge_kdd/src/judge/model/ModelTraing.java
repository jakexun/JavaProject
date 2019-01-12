package judge.model;

import java.io.*;
import java.util.Properties;

public class ModelTraing {
    public static void main(String[] args){
        try {
            InputStream in_stream=new FileInputStream(new File("configuration.properties"));
            Properties pro=new Properties();
            pro.load(in_stream);

            String train_data=pro.getProperty("train_data").trim();
            InputStreamReader in_t=new InputStreamReader(new FileInputStream(new File(train_data)));
            BufferedReader in_reader_t=new BufferedReader(in_t);

            String fuzzy_data=pro.getProperty("fuzzy_data").trim();
            OutputStreamWriter out_f=new OutputStreamWriter(new FileOutputStream(new File(fuzzy_data)));
            BufferedWriter out_writer_f=new BufferedWriter(out_f);

            String correct_model=pro.getProperty("correct_model").trim();
            File file_c=new File(correct_model);
            OutputStreamWriter out_c=new OutputStreamWriter(new FileOutputStream(file_c));
            BufferedWriter out_writer_c=new BufferedWriter(out_c);
            InputStreamReader in_c=new InputStreamReader(new FileInputStream(file_c));
            BufferedReader in_reader_c=new BufferedReader(in_c);
            in_reader_c.mark(( int )file_c.length() + 1);

            String wrong_model=pro.getProperty("wrong_model").trim();
            File file_w=new File(wrong_model);
            OutputStreamWriter out_w=new OutputStreamWriter(new FileOutputStream(file_w));
            BufferedWriter out_writer_w=new BufferedWriter(out_w);
            InputStreamReader in_w=new InputStreamReader(new FileInputStream(file_w));
            BufferedReader in_reader_w=new BufferedReader(in_w);
            in_reader_w.mark(( int )file_w.length() + 1);

            String fuzzy_model=pro.getProperty("fuzzy_model").trim();
            File file_fm=new File(fuzzy_model);
            OutputStreamWriter out_fm=new OutputStreamWriter(new FileOutputStream(file_fm));
            BufferedWriter out_writer_fm=new BufferedWriter(out_fm);
            InputStreamReader in_fm=new InputStreamReader(new FileInputStream(new File(fuzzy_model)));
            BufferedReader in_reader_fm=new BufferedReader(in_fm);
            in_reader_fm.mark(( int )file_fm.length() + 1);

            String line=in_reader_t.readLine();
            while (line!=null){
                String feature=GetFeature(line);
                //2...
                String[] c_data=GetData(correct_model);
                String[] w_data=GetData(wrong_model);
                String[] f_data=GetData(fuzzy_model);
                if (!IfContain(c_data,feature)&&line.split(",")[line.split(",").length-1].equals("normal.")){
                    out_writer_c.write(feature+"\n");
                    out_writer_c.flush();
                    in_reader_c.mark(( int )file_c.length() + 1);
                }else if (!IfContain(w_data,feature)&&!line.split(",")[line.split(",").length-1].equals("normal.")){
                    out_writer_w.write(feature+"\n");
                    out_writer_w.flush();
                    in_reader_w.mark(( int )file_w.length() + 1);
                }else if (IfContain(c_data,feature)&&IfContain(w_data,feature)){
                    out_writer_f.write(line+"\n");
                    //1...
                    if (!IfContain(f_data,feature)){
                        out_writer_fm.write(feature+"\n");
                        out_writer_fm.flush();
                        in_reader_fm.mark(( int )file_w.length() + 1);
                    }
                }
                in_reader_c.reset();
                in_reader_w.reset();
                in_reader_fm.reset();

                line=in_reader_t.readLine();
            }
            in_reader_c.close();
            in_reader_w.close();
            in_reader_t.close();
            in_reader_fm.close();
            in_reader_t.close();
            out_writer_c.close();
            out_writer_w.close();
            out_writer_f.close();
            out_writer_fm.close();
            CorrectModel(correct_model,fuzzy_model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int LineNumber(String file_name){
        int number=0;
        try {
            InputStreamReader in=new InputStreamReader(new FileInputStream(new File(file_name)));
            BufferedReader in_reader=new BufferedReader(in);
            String line=in_reader.readLine();
            while(line!=null){
                number++;
                line=in_reader.readLine();
            }
            in_reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return number;
    }

    public static String GetFeature(String line){
        String[] row_data=line.split(",");
        String feature="";
        for (int i=1;i<row_data.length-1;i++){
            if (Double.parseDouble(row_data[i])-Double.parseDouble(row_data[i-1])>0){
                feature+=1+",";
            }else if (Double.parseDouble(row_data[i])-Double.parseDouble(row_data[i-1])==0){
                feature+=0+",";
            }else {
                feature+=-1+",";
            }
        }
        return feature;
    }

    public static boolean IfContain(String[] strarray,String str){
        boolean flag=false;
        for (int i=0;i<strarray.length;i++){
            if (strarray[i].equals(str)){
                flag=true;
                break;
            }
        }
        return flag;
    }

    public static String[] GetData(String filepath){
        int number=LineNumber(filepath);
        String[] data=new String[number];
        try {
            InputStreamReader in=new InputStreamReader(new FileInputStream(new File(filepath)));
            BufferedReader reader=new BufferedReader(in);
            for (int i=0;i<number;i++){
                data[i]=reader.readLine();
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public static void ClearRedundant(String filepath){
        String[] data=GetData(filepath);
        int pointer=data.length-1;
        try {
            OutputStreamWriter out=new OutputStreamWriter(new FileOutputStream(new File(filepath)));
            BufferedWriter writer=new BufferedWriter(out);
            while(pointer>=0){
                boolean flag=false;
                for (int i=pointer+1;i<data.length;i++){
                    if (data[pointer].equals(data[i])&&data[pointer]!=null){
                        flag=true;
                        data[i]=null;
                    }
                }
                if (!flag&&data[pointer]!=null){
                    writer.write(data[pointer]+"\n");
                }
                pointer--;
            }
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String[] Read(String filepath,int n) { //读取txt文本数据
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

    public static void CorrectModel(String correctmodel,String fuzzymodel){
        int correct_number=LineNumber(correctmodel);
        int fuzzy_number=LineNumber(correctmodel);
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

            String[] Clines = Read(correctmodel,correct_number);
            String[] Flines = Read(fuzzymodel,fuzzy_number);
            File file=new File(correctmodel);
            file.delete();
            /* 写入Txt文件 */
            File writename = new File(correctmodel); // 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            for (int i=0;i<correct_number;i++){
                boolean flag=false;
                for (int j=0;j<fuzzy_number;j++){
                    flag=Clines[i].equals(Flines[j]);
                }
                if (!flag){
                    out.write(Clines[i]+"\n");
                }
            }
           out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//1...
/*
                    if (in_reader_fm.readLine()==null){
                        out_writer_fm.write(feature+"\n");
                        out_writer_fm.flush();
                        in_reader_fm.mark(( int )file_w.length() + 1);
                    }*/
//2...
  /*if (in_reader_c.readLine()==null&&line.split(",")[line.split(",").length-1].equals("normal.")){
                    out_writer_c.write(feature+"\n");
                    out_writer_c.flush();
                    in_reader_c.mark(( int )file_c.length() + 1);
                    System.out.println("1,,,,"+feature);
                }
                in_reader_c.reset();

                if (in_reader_w.readLine()==null&&!line.split(",")[line.split(",").length-1].equals("normal.")){
                    out_writer_w.write(feature+"\n");
                    out_writer_w.flush();
                    in_reader_w.mark(( int )file_w.length() + 1);
                    System.out.println("2,,,,"+feature);
                }
                in_reader_w.reset();*/
