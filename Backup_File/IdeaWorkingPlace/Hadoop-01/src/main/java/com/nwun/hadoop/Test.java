package com.nwun.hadoop;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Test {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
//        conf.set("fs.defaultFS", "hdfs://master:3020/");
        FileSystem fs=FileSystem.get(new URI("hdfs://master:8020"),conf,"root");
        fs.mkdirs(new Path("/test/ttt"));
        fs.close();
        System.out.println("over!");
    }
    //HDFS的API操作
    @org.junit.Test
    public void testCopyFromLocalFile() throws URISyntaxException, IOException, InterruptedException {
        // 1 获取fs对象
        Configuration conf = new Configuration();
        String user="root";
        FileSystem fs=FileSystem.get(new URI("hdfs://master:8020"),conf,user);
        // 2 执行上传操作
        fs.copyFromLocalFile(new Path("d:/test.txt"),new Path("/test.txt"));
        // 3 关闭资源
    }
    @org.junit.Test
    public void testCopyToLocal() throws URISyntaxException, IOException, InterruptedException {
        // 1 获取文件系统
        Configuration conf=new Configuration();
        String user="root";
        FileSystem fs=FileSystem.get(new URI("hdfs://master:8020"),conf,user);
        // 2 执行下载操作
        // boolean delSrc 指是否将源文件删除
        // Path src 指要下载的文件路径
        // Path dist 指将文件下载到的路径
        // boolean useRawLocalFileSystem 是否开启文件校验
        fs.copyToLocalFile(false, new Path("/test.txt"), new Path("d:/zhu.txt"),true);
        // 3 关闭资源
        fs.close();
    }
    @org.junit.Test
    public void testDelete() throws URISyntaxException, IOException, InterruptedException {
        //1 获取文件系统
        Configuration conf=new Configuration();
        String user="root";
        FileSystem fs=FileSystem.get(new URI("hdfs://master:8020"), conf, user);
        //2 修改文件名称
        fs.rename(new Path("/test.txt"),new Path("/regular.txt"));
        fs.close();
    }
    @org.junit.Test
    public void testListFiles() throws IOException, URISyntaxException, InterruptedException {
        //1 获取文件系统
        Configuration conf=new Configuration();
        String user="root";
        FileSystem fs=FileSystem.get(new URI("hdfs://master:8020"),conf,user);

        //2获取文件详情
        RemoteIterator<LocatedFileStatus> listFiles=fs.listFiles(new Path("/"),true);
        while(listFiles.hasNext()){
            LocatedFileStatus status=listFiles.next();
            //输出详情
            //文件名称
            System.out.println(status.getPath().getName());
            //长度
            System.out.println(status.getLen());
            //权限
            System.out.println(status.getPermission());
            //z组
            System.out.println(status.getGroup());

            //获取存储快的信息
            BlockLocation[] blockLocations=status.getBlockLocations();

            for (BlockLocation blockLocation:blockLocations){
                //获取块存储的主机节点
                String[] hosts=blockLocation.getHosts();

                for (String host: hosts){
                    System.out.println(host);
                }
            }
            System.out.println("-----My will-----");
        }
        fs.close();
    }
    @org.junit.Test
    public void testListStatus() throws URISyntaxException, IOException, InterruptedException {
        //1 获取文件配置信息
        Configuration conf=new Configuration();

        String user="root";
        FileSystem fs = FileSystem.get(new URI("hdfs://master:8020"), conf, user);
        //2 判断是文件还是文件夹
        FileStatus[] listStatus=fs.listStatus(new Path("/"));

        for (FileStatus fileStatus:listStatus) {
            // 如果是文件
            if (fileStatus.isFile()) {
                System.out.println("f." + fileStatus.getPath().getName());
            } else {
                System.out.println("d."+ fileStatus.getPath().getName());
            }
        }
        fs.close();
    }
    //hdfs的I/O流操作
    @org.junit.Test
    public void putFileToHDFS() throws URISyntaxException, IOException, InterruptedException {
        //1 获取文件系统
        Configuration conf=new Configuration();
        String user="root";
        FileSystem fs = FileSystem.get(new URI("hdfs://master:8020"), conf, user);

        //2 创建输入流
        FileInputStream fis=new FileInputStream(new File("d:/test.txt"));

        //3 获取输出流
        FSDataOutputStream fos=fs.create(new Path("/hello.txt"));

        //4 流对拷
        IOUtils.copyBytes(fis,fos,conf);
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
    }
    @org.junit.Test
    public void getFileFromHDFS() throws URISyntaxException, IOException, InterruptedException {
        //1 获取文件系统
        Configuration conf=new Configuration();
        String user="root";
        FileSystem fs = FileSystem.get(new URI("hdfs://master:8020"), conf, user);
        //2 获取输入流
        FSDataInputStream fis=fs.open(new Path("/hello.txt"));
        //3 获取输出流
        FileOutputStream fos=new FileOutputStream(new File("d:/hello.txt"));
        //4 流的对拷
        IOUtils.copyBytes(fis,fos,conf);
        //5 关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);

    }
    //定位文件读取
    @org.junit.Test
    public void readFileSeek1() throws URISyntaxException, IOException, InterruptedException {
        //1 读取文件系统
        Configuration conf=new Configuration();
        String user="root";
        FileSystem fs = FileSystem.get(new URI("hdfs://master:8020"), conf, user);
        //2 获取输入流
        FSDataInputStream fis=fs.open(new Path("/hadoop-2.9.1-src.tar.gz"));
        //3 创建输出流
        FileOutputStream fos=new FileOutputStream(new File("d:/hadoop-2.9.1-src.tar.gz"));
        //4 流的拷贝
        byte[] buf=new byte[1024];
        for (int i=0;i<1024*128;i++){
            fis.read(buf);
            fos.write(buf);
        }
        //5 关闭所有资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
    }
    @org.junit.Test
    public void readFileSeek2() throws URISyntaxException, IOException, InterruptedException {
        //1 获取文件系统
        Configuration conf=new Configuration();
        String user="root";
        FileSystem fs = FileSystem.get(new URI("hdfs://master:8020"), conf, user);
        //2 创建输入流
        FSDataInputStream fis=fs.open(new Path("/hadoop-2.9.1-src.tar.gz"));
        //3 创建输出流
        FileOutputStream fos=new FileOutputStream(new File("d:/hadoop-2.9.1-src.tar.gz2"));
        //5 流的对拷
        IOUtils.copyBytes(fis,fos,conf);
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
    }
}
