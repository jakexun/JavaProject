package data.process;

import nw.program.Read_Write_data;
import nw.program.Tools;

import java.io.*;
import java.util.Properties;

import static nw.program.Tools.GetColumnNumber;
import static nw.program.Tools.GetNumber;
import static nw.program.Tools.Initia_data;

public class Statistical {
    public static void Main(int k,String filepath,String result,/*int runtimes,*/String[][] central,int[] classify,int column/*,int time*/) {
        Read_Write_data rwd=new Read_Write_data();
        System.out.println(Statistic(filepath));
        rwd.Write_line(result,Statistic(filepath)+"\n");
        Tools t=new Tools();
        for (int i =0;i<k;i++){//对分类后的文件进行排序
            File classed_file = new File("classify["+i+"].txt");
            if(classed_file.exists()){
                String read_class_file = "classify["+i+"].txt";
                String write_path = "sortedclassify["+i+"].txt";
                t.Sort_from_far_to_near(read_class_file,write_path,central[i],classify[i],column);
            }

            File classed_max_file = new File("classify[max"+i+"].txt");
            if(classed_max_file.exists()){
                String read_max_class_file = "classify[max"+i+"].txt";
                String write_max_path = "sortedclassify[max"+i+"].txt";
                t.Sort_from_far_to_near(read_max_class_file,write_max_path,central[i],classify[i],column);
            }

            File classed_min_file_delete = new File("classify[min"+i+"].txt");
            if(classed_min_file_delete.exists()){
                String read_min_class_file = "classify[min"+i+"].txt";
                String write_min_path = "sortedclassify[min"+i+"].txt";
                t.Sort_from_far_to_near(read_min_class_file,write_min_path,central[i],classify[i],column);
            }
        }

        rwd.Write_line(result,"\n");
    }
    public static String Statistic(String filepath){
        String result="";
        try {
            InputStreamReader in=new InputStreamReader(new FileInputStream(new File(filepath)));
            BufferedReader reader=new BufferedReader(in);

            int normal=0,abnormal=0,all=0,new_data=0;
            String line=reader.readLine();
            while(line!=null){
                if (line.contains("normal")){
                    normal++;
                }else if (!line.contains("{mixture}")){
                    abnormal++;
                }else {
                    new_data++;
                }
                all++;
                line=reader.readLine();
            }
            reader.close();
            result+=" all="+all+" normal="+normal+" abnormal="+abnormal+" newdata="+new_data;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    private static void RemoveFile(String fileName,String destinationFloderUrl)
    {
        File file = new File(fileName);
        File destFloder = new File(destinationFloderUrl);

        //检查源文件是否合法
            String destinationFile = destinationFloderUrl+"/"+file.getName();
            if(file.renameTo(new File(destinationFile)))
            {
                System.out.println(fileName+"文件移动成功！");
            }
    }
    public static void Move(int k,int runtime){
        String destinationFloderUrl="test"+(runtime+1);
        String destinationFloderUrll="test"+(runtime+1);
        File destinationDir=new File(destinationFloderUrl);
        destinationDir.mkdir();
        Read_Write_data rwd =new Read_Write_data();
        String result="results.txt";
        String central="central.txt";
        RemoveFile(central,destinationFloderUrl);
        String maxfile;
        String filemo;
        String minfile;
        File filea;
        File filem;
        File filei;
        for (int i=0;i<k;i++){
            maxfile="classify[max"+i+"].txt";
            filemo="classify["+i+"].txt";
            minfile="classify[min"+i+"].txt";

            filea=new File(maxfile);
            filem=new File(filemo);
            filei=new File(minfile);
            if (filea.exists()){
                rwd.Write_line(result,maxfile+" "+Statistic(maxfile)+"\n");
                RemoveFile(maxfile,destinationFloderUrl);
            }
            if (filem.exists()){
                rwd.Write_line(result,filemo+" "+Statistic(filemo)+"\n");
                RemoveFile(filemo,destinationFloderUrl);
            }
            if (filei.exists()){
                rwd.Write_line(result,minfile+" "+Statistic(minfile)+"\n");
                RemoveFile(minfile,destinationFloderUrl);
            }
        }
        for (int i=0;i<k;i++){
            maxfile="sortedclassify[max"+i+"].txt";
            filemo="sortedclassify["+i+"].txt";
            minfile="sortedclassify[min"+i+"].txt";

            filea=new File(maxfile);
            filem=new File(filemo);
            filei=new File(minfile);
            if (filea.exists()){
                rwd.Write_line(result,maxfile+" "+Statistic(maxfile)+"\n");
                RemoveFile(maxfile,destinationFloderUrll);
                RemoveFile("dist"+maxfile,destinationFloderUrll);

            }
            if (filem.exists()){
                rwd.Write_line(result,filemo+" "+Statistic(filemo)+"\n");
                RemoveFile(filemo,destinationFloderUrll);
                RemoveFile("dist"+filemo,destinationFloderUrll);

            }
            if (filei.exists()){
                rwd.Write_line(result,minfile+" "+Statistic(minfile)+"\n");
                RemoveFile(minfile,destinationFloderUrll);
                RemoveFile("dist"+minfile,destinationFloderUrll);

            }
        }
    }
    public static void Control(/*String centralfile*/) {
        try{/*
        int datavolume=GetNumber(centralfile);
        int volumn=GetColumnNumber(centralfile);*/
        InputStream inStream = new FileInputStream(new File("Configuration.properties"));
        Properties pro = new Properties();
        pro.load(inStream);
        int k = Integer.parseInt(pro.getProperty("K").trim());
        String filepath = pro.getProperty("TFilepath").trim();
        int row=GetNumber("central.txt");
        int column=GetColumnNumber("central.txt");
        String[][] central=Initia_data("central.txt",row,column);
        int[] classify=new int[k];
        String maxfile;
        String filemo;
        String minfile;
        File filea;
        File filem;
        File filei;
        for (int i=0;i<k;i++){
            maxfile="classify[max"+i+"].txt";
            filemo="classify["+i+"].txt";
            minfile="classify[min"+i+"].txt";
            filea=new File(maxfile);
            filem=new File(filemo);
            filei=new File(minfile);
            if (filea.exists()){
                classify[i]=GetNumber(maxfile);
            }
            if (filem.exists()){
                classify[i]=GetNumber(filemo);
            }
            if (filei.exists()){
                classify[i]=GetNumber(minfile);
            }
        }
        Main(k,filepath,"results.txt",/*0,*/central,classify, column/*,1*/);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void Statistic(/*String[] args*/int k,int runtime) {
        Control();
        Move(k,runtime);
    }
}
