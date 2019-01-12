package data.process;

import nw.program.Read_Write_data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Statistical {
    public static void Main(int k,String filepath,String result,int runtimes) {
        Read_Write_data rwd=new Read_Write_data();
        System.out.println(Statistic(filepath));
        rwd.Write_line(result,Statistic(filepath)+"\n");
        String destinationFloderUrl="test"+(runtimes+1);
        File destinationDir=new File(destinationFloderUrl);
        destinationDir.mkdir();
        for (int i=0;i<k;i++){
            String maxfile="classify[max"+i+"].txt";
            String filemo="classify["+i+"].txt";
            String minfile="classify[min"+i+"].txt";
            File filea=new File(maxfile);
            File filem=new File(filemo);
            File filei=new File(minfile);

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


    public static void main(String[] args) {
        Main(5,"test_file.txt","result.txt",0);
    }
}
