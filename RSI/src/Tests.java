/**
 * Created by SabaZerefa on 7/11/18.
 */

import java.util.ArrayList;

public class Tests {

    private static int n=9;
    public static void main(String[] args)
    {
        int[][] mtxsample = new int[n*(n-1)/2][n*(n-1)/2];
        for(int a=0; a<n*(n-1)/2; a++)
        {
            for(int b=0; b<n*(n-1)/2; b++)
            {
                System.out.print(mtxsample[a][b] + " ");
            }
            System.out.println();

        }
    }
    private static ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> input, int n) {
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();

        if (input.size() == 1) {
            output.add(input);
            return output;
        }

        for (int i = 0; i < input.size(); i++) {
            int fixpos = input.get(i);
            ArrayList<Integer> inputOther = new ArrayList<>();
            for (int j = 0; j < input.size(); j++) if (input.get(j) != fixpos) inputOther.add(input.get(j));
            ArrayList<ArrayList<Integer>> recRes = permute(inputOther, n);
            for (int k = 0; k < recRes.size(); k++) {
                ArrayList<Integer> midstep = new ArrayList<>();
                midstep.add(fixpos);
                midstep.addAll(recRes.get(k));
                output.add(midstep);
            }
        }
        return output;
        //you have a list 1 2 3 4 5 6 n
        // keep 1, then reorder 2 3 4 5 6 n
        //keep 1 for sure, or else keep a random other (take turns 2, 3, 4, 5)
        //at end, just add 5 to remaining and add that to the output
        //
    }

    private static ArrayList<ArrayList<Integer>> permute0(ArrayList<Integer> input, int n) {
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();

        int fixpos = input.get(0);
        ArrayList<Integer> inputOther = new ArrayList<>();
        for (int j = 0; j < input.size(); j++) if (input.get(j) != fixpos) inputOther.add(input.get(j));
        ArrayList<ArrayList<Integer>> recRes = permute(inputOther, n);
        for (int k = 0; k < recRes.size(); k++) {
            ArrayList<Integer> midstep = new ArrayList<>();
            midstep.add(fixpos);
            midstep.addAll(recRes.get(k));
            output.add(midstep);
        }

        return output;
    }


    private static int factorial (int n)
    {
        if(n==1) return 1;
        return n*factorial(n-1);
    }
}
