public class Tongji {
    public static void main(String[] args) {
        Tools t = new Tools();
        String filepath = "output.txt";
        String[][] databf = t.Initia_data(filepath, 3000, 43);
        int correct = 0;
        int wrong = 0;
        for (int i = 0; i < databf.length; i++) {
            if (databf[i][41].equals("normal.")) {
                correct += 1;
            } else {
                wrong += 1;
            }
        }
        System.out.println("正确的数据有" + correct + "条!");
        System.out.println("错误的数据有" + wrong + "条!");
        String filenew = "result2.txt";
        int check_result = 0;
        String[][] datatd = t.Initia_data(filenew, 513, 43);
        int new_data=0;
        int right=0;
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < datatd.length; i++) {
            if (!datatd[i][42].equals("{mixture}")&&!(datatd[i][42].equals("null")&&datatd[i][41].equals("normal."))) {
                check_result += 1;
            }
            if (datatd[i][42].equals("{mixture}")){
                new_data+=1;
            }
            if ((datatd[i][42].equals("null")&&datatd[i][41].equals("normal."))){
                right+=1;
            }
        }
        System.out.println("检测出真异常的数据有" + check_result + "条!");
        System.out.println("其中新产生的数据有" + new_data + "条!");
        System.out.println("其中假异常的数据有" + right + "条!");
        System.out.println("其中检测准确率为："+(check_result/((check_result+right)*1.0))*100+"%");
    }
}