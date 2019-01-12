package nwun.ml.dmt;

import static nwun.ml.dmt.Tools.GetRowNumber;
import static nwun.ml.dmt.Tools.Read_Data;

/**
 * @author ÕÅÀñÖÇ
 */
public class DecisionMakingTreeModel {
    public static void ModelTrain(String[][] train_data,int[] data_list,double beratio,int n){
            String[] feature=GetFeature(train_data,data_list,n);
            int i=0;
            int feature_length=Getlength(feature);

            while(feature[i]!=null){

            }
    }
    public static double MolelTesting(String test_data,int[] data_list,int wn){//n´ú±í
    return 0.2;
    }
    public static int[] GetPropertyFeature(String[][] train_data,int[] data_list){
        int t=0,f=0;
        for (int i=1;i<train_data[0].length-1;i++){
            String[] feature=GetFeature(train_data,data_list,i);
            for (int p=0;feature[p]!=null;p++)
                System.out.print(feature[p]+" ");
            System.out.println();
            int length=Getlength(feature);
            int[] result=new int[2];
            for (int l=0;l<length;l++){
                t=0; f=0;
                for (int j=0;j<data_list.length;j++){
                    if (data_list[j]!=-1&&train_data[data_list[j]][train_data[0].length-1].equals("·ñ")&&train_data[data_list[j]][i].equals(feature[l]))
                        f++;
                    else if (data_list[j]!=-1&&train_data[data_list[j]][train_data[0].length-1].equals("ÊÇ")&&train_data[data_list[j]][i].equals(feature[l]))
                        t++;
                }
                System.out.println("ture="+t+"false="+f);
                if (t==0&&f!=0){
                    result[0]=i;
                    result[1]=l;
                    return result;
                }
            }
        }
        return null;
    }
    public static int Getlength(String[] feature){
        int length=0;
        while(length<feature.length&&feature[length]!=null) length++;
        return length;
    }
    public static String[] GetFeature(String[][] train_data,int[] data_list,int column){
        String[] feature=new String[train_data.length];
        int flag=-1;
        for (int i=0;i<train_data.length;i++){
            if (data_list[i]!=-1&&!IsContain(feature,flag,train_data[data_list[i]][column])||data_list[i]!=-1&&flag==-1){
                feature[++flag]=train_data[data_list[i]][column];
            }
        }
        return feature;
    }
    public static boolean IsContain(String[] feature,int end,String strfeature){
        while (end>=0){
            if (feature[end].equals(strfeature))
                return true;
            end--;
        }
        return false;
    }
    public static void main(String[] args) {
        String[][] train_data=Read_Data("watermelon_data_train.txt");
        int[] data_list=new int[GetRowNumber("watermelon_data_train.txt")];
        for (int i=0;i<data_list.length;i++){
            /*if(train_data[i][1].equals("Ç³°×")||train_data[i][2].equals("Ó²Í¦"))
            data_list[i]=-1;
            else */data_list[i]=i;
        }
        String[] feature=GetFeature(train_data,data_list,5);
        System.out.println(GetPropertyFeature(train_data,data_list));
        /*String[] feature=GetFeature(train_data,data_list,5);
        for (int i=0;feature[i]!=null;i++)
            System.out.println(feature[i]);*/
        /*System.out.println(GetPropertyFeature(train_data,data_list));*/
    }
}
