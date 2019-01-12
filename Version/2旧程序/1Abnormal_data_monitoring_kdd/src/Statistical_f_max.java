import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class Statistical_f_max {
    public static void main(String[] args) {
    try {
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");
        Tools t = new Tools();
        String filepath = "test_data.txt";
        String[][] databf = t.Initia_data(filepath, 3000, 43);
        int p = 0;
        int n = 0;
        for (int i = 0; i < databf.length; i++) {
            if (databf[i][41].equals("normal.")) {
                p += 1;
            } else {
                n += 1;
            }
        }
        int p_n = p + n;
        System.out.println("正确的数据有" + p + "条!");
        System.out.println("错误的数据有" + n + "条!");
        String filenew = "result_max.txt";
        int tn = 0;
        Read_result Rr =new Read_result();
        int number =Rr.number(filenew);
        String[][] datatd = t.Initia_data(filenew, number, 43);
        int nd=0;
        int fn=0;
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < datatd.length; i++) {
            if (!datatd[i][42].equals("{mixture}")&&!(datatd[i][42].equals("null")&&datatd[i][41].equals("normal."))) {
                tn += 1;
            }
            if (datatd[i][42].equals("{mixture}")){
                nd+=1;
            }
            if ((datatd[i][42].equals("null")&&datatd[i][41].equals("normal."))){
                fn+=1;
            }
        }
        int tp = p - fn;
        int fp = n - tn;
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("run_result_max.txt", true)));
        double precition = tp*1.0/(tp+fn),recall = tp*1.0/p;
//        String title = "真异常\t新数据\t假异常\t准确率\t错误率\t敏感度\t特效性\t精度\t精度和召回率的调和均值";
        String line = ""+tn+"\t\t"+nd+"\t\t"+fn+"\t\t"+df.format((tp+tn)*1.0/(p+n)*100)+"%\t"+df.format((fp+fn)*1.0/(p+n)*100)+"%\t"+df.format(recall*100)+"%\t"+df.format(tn*1.0/n*100)+"%\t"+
                df.format(precition*100)+"%\t"+df.format((2*precition*recall)/(precition+recall)*100)+"%";
//        out.write(title+"\n");
        out.write(line+"\n");
        out.close();
        System.out.println("检测出真异常的数据有" + tn + "条!");
        System.out.println("其中新产生的数据有" + nd + "条!");
        System.out.println("其中假异常的数据有" + fn + "条!");
//        System.out.println("其中检测准确率为："+(tn/((tn+fn)*1.0))*100+"%");
//        System.out.println("其中检测错误率为："+(1-(tn/((tn+fn)*1.0)))*100+"%");
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}