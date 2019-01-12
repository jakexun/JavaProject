package data.process;
import java.util.Scanner;

public class Main {

    private static int n,c;
    private static int[] x,bestx,w;
    private static int r,cw,bestw;
    public static int times,times1;

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        while (true){
            bestw = 0;
            cw = 0;
            r = 0;

            n = input.nextInt();
            c = input.nextInt();

            w = new int[n+1];
            x = new int[n+1];
            bestx = new int[n+1];

            for(int i=1; i<=n; i++)
                w[i] = input.nextInt();

            for(int i=1; i<=n; i++)
                r += w[i];

            backtrack(1);

            output();
        }
    }

    private static void backtrack(int t){
        if(t > n){
            record();/*
            times1++;*/
        }else{
            times++;
            for(int i=0; i<=1; i++){
                x[t] = i;
                if(constraint(t) && bound(t)){
                    change(t);
                    backtrack(t+1);
                    restore(t);
                }
            }
        }
    }

    private static void record(){
        if(cw > bestw){
            bestw = cw;
            for(int i=1; i<=n; i++)
                bestx[i] = x[i];
        }
    }

    private static boolean constraint(int t){
        if(x[t]==0 || x[t]==1 && cw+w[t]<=c) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean bound(int t){
        if(x[t]==1 || x[t]==0 && cw+r-w[t]>bestw){
            return true;
        }
        else {
            return false;
        }
    }

    private static void change(int t){
        if(x[t] == 1) {
            cw+=w[t];
        }
        r -= w[t];
    }

    private static void restore(int t){
        if(x[t] == 1) {
            cw-=w[t];
        }
        r += w[t];
    }

    private static void output(){
        System.out.println(bestw+" "+times+" "+times1);
        /*for(int i=1; i<=n; i++)
            System.out.print(bestx[i]+" ");*/
    }
}
