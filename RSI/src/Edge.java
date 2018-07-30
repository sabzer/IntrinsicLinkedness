/**
 * Created by SabaZerefa on 7/11/18.
 */


import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.StringTokenizer;

public class Edge
{
    private int a;
    private int b;

    private static int n=9;
    private static int linkno4=0;

    private static int[][] crossingMtx;

    private static ArrayList<Edge> edgeList = new ArrayList<>();
    private static ArrayList<ArrayList<ArrayList<Integer>>> allCycles = new ArrayList<>();
    private static ArrayList<Integer> linkNo = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        Scanner kboard = new Scanner(System.in);

        System.out.print("What is the n of the desired K_n ");
        n = kboard.nextInt();
        for(int i=1; i<=n; i++)
            for(int j=i+1; j<=n; j++)
                edgeList.add(new Edge(i,j));

        crossingMtx = new int[n*(n-1)/2][n*(n-1)/2];
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));

        for(int i=0; i<n*(n-1)/2; i++)
        {
           StringTokenizer tk = new StringTokenizer(br.readLine());
           for (int j = 0; j < n * (n - 1) / 2; j++)
           {
               String x = tk.nextToken();
               if(x.equals("1")) crossingMtx[i][j]=1;
               if(x.equals("0")) crossingMtx[i][j]=0;
               else crossingMtx[i][j]=-1;
           }
        }

        printMtx();

        setUp();

        FileWriter fileWriter = new FileWriter("input.txt");
        PrintWriter out = new PrintWriter(fileWriter);

        for(int a=0; a<n*(n-1)/2; a++) {
            for (int b = 0; b < n * (n - 1) / 2; b++)
                out.write(crossingMtx[a][b] + " ");
            out.write("\n");
        }
        out.close();

        printMtx();

        allCycles = generateAllCycles();


        linkNo.addAll(linkNumGenerate(allCycles));

        System.out.println("Number of 4 cycles is " + linkno4);

    }

    public Edge(int a, int b)
    {
        this.a=a;
        this.b=b;
    }

    private static void setUp()
    {
        Scanner kboard = new Scanner(System.in);

        System.out.print("How many (different) crossings? ");
        int crossno = kboard.nextInt();
        for(int i=0; i<crossno; i++)
        {
            System.out.println("Which edges, with the edge that lies on top listed first, cross?");
            System.out.print("Use a comma to pair vertices when denoting edges, and use a space to separate different edges ");
            String cross = kboard.next() + " " + kboard.next();

            System.out.print("Sign of crossing ");
            String sgn = kboard.next();
            char sign = sgn.charAt(0);

            System.out.print("Value of crossing ");
            String vl = kboard.next();
            int val = vl.charAt(0)-48;

            int crossingSign=0;
            if((int) sign == 43) crossingSign = 1;
            else if ((int) sign == 45) crossingSign=-1;
            else crossingSign=0;


            int w = (int) cross.charAt(0)-48;
            int x = (int) cross.charAt(2)-48;
            int y = (int) cross.charAt(4)-48;
            int z = (int) cross.charAt(6)-48;

            if(x < w)
            {
                w += x;
                x = w-x;
                w = w-x;
            }
            if(z < y)
            {
                y += z;
                z = y-z;
                y = y-z;
            }

            Edge firstedge = new Edge(w,x);
            Edge secondedge = new Edge(y,z);

            crossingMtx[firstedge.IndexOf()][secondedge.IndexOf()] = crossingSign*(val);
            crossingMtx[secondedge.IndexOf()][firstedge.IndexOf()] = crossingSign*(val);
        }

        kboard.close();
    }

    public int IndexOf()
    {
        for(int c=0; c<n*(n-1)/2; c++)
            if((edgeList.get(c).a==a && edgeList.get(c).b==b) || (edgeList.get(c).a==b && edgeList.get(c).b==a)) return c;
        return -1;
    }

    private static void printMtx()
    {
        for(int a=0; a<crossingMtx.length; a++) {
            for (int b = 0; b < crossingMtx.length; b++)
                System.out.print(crossingMtx[a][b] + " ");
            System.out.println();
        }
    }

    private static ArrayList<ArrayList<Integer>> generateCycles(int cycleLength, ArrayList<Integer> options)//Permutations minus last
    {
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();

        if(cycleLength==1)
        {
            for (int i = 0; i < options.size(); i++)
            {
                ArrayList<Integer> wasteOfSpace = new ArrayList<>();
                wasteOfSpace.add(options.get(i));
                output.add(wasteOfSpace);
            }
            return output;
        }

        cycleLength--;

        for(int i=0; i<options.size()-cycleLength; i++)
        {
            ArrayList<Integer> optionsParam = new ArrayList<>();
            for(int c = i+1; c < options.size(); c++)
                optionsParam.add(options.get(c));

            ArrayList<ArrayList<Integer>> recRes = generateCycles(cycleLength, optionsParam);

            for(int j = 0; j < recRes.size(); j++) {
                ArrayList<Integer> outOther = new ArrayList<>();
                outOther.add(options.get(i));

                ArrayList<Integer> addTo = recRes.get(j); //add the beginning of this line to here
                for(int k = 0; k < addTo.size(); k++)
                    outOther.add(addTo.get(k));

                output.add(outOther);
            }
        }
        return output;
    }

    private static ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> input)
    {
        ArrayList<ArrayList<Integer>> output= new ArrayList<>();

        if(input.size() == 1)
        {
            output.add(input);
            return output;
        }

        for(int i=0; i<input.size(); i++)
        {
            int fixpos=input.get(i);
            ArrayList<Integer> inputOther = new ArrayList<>();

            for(int j=0; j<input.size(); j++)
                if(input.get(j)!=fixpos)
                    inputOther.add(input.get(j));

            ArrayList<ArrayList<Integer>> recRes = permute(inputOther);

            for(int k=0; k<recRes.size(); k++)
            {
                ArrayList<Integer> midstep = new ArrayList<>();
                midstep.add(fixpos);
                midstep.addAll(recRes.get(k));
                output.add(midstep);
            }
        }
        return output;
    }

    private static ArrayList<ArrayList<Integer>> permutedCycles(ArrayList<Integer> input) {
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();

        int fixpos = input.get(0);
        ArrayList<Integer> inputOther = new ArrayList<>();


        for (int j = 0; j < input.size(); j++) if (input.get(j) != fixpos) inputOther.add(input.get(j));

        ArrayList<ArrayList<Integer>> recRes = permute(inputOther);

        for (int k = 0; k < recRes.size(); k++)
        {
            ArrayList<Integer> midstep = new ArrayList<>();
            midstep.add(fixpos);
            midstep.addAll(recRes.get(k));
            output.add(midstep);
        }

        return output;
    }

    private static ArrayList<ArrayList<Integer>> cycleCheck(ArrayList<ArrayList<Integer>> input)
    {
        for(int a=0; a<input.size(); a++)
            for(int b=a+1; b<input.size(); b++)
                if(cycleCheck0(input.get(a), input.get(b)))
                    input.remove(b);

        return input;
    }

    private static boolean cycleCheck0(ArrayList<Integer> input0, ArrayList<Integer> input1)
    {
        ArrayList<Integer> backwards = new ArrayList<>();

        for(int a=0; a<input1.size(); a++) backwards.add(input1.get(input1.size()-1-a));

        for(int a=0; a<input0.size()-1; a++) if(!(Math.abs(a-backwards.indexOf(input0.get(a)))==Math.abs(a+1-backwards.indexOf(input0.get(a+1))))
                && !(Math.abs(a-backwards.indexOf(input0.get(a)))==input0.size()-Math.abs(a+1-backwards.indexOf(input0.get(a+1))))) return false;

        return true;
    }

    private static ArrayList<ArrayList<ArrayList<Integer>>> generateAllCycles()
    {
        ArrayList<ArrayList<ArrayList<Integer>>> output = new ArrayList<>();

        for(int a=3; a<=n-3; a++)
        {
            ArrayList<ArrayList<Integer>> midstep = new ArrayList<>();
            ArrayList<ArrayList<Integer>> combos = generateCycles(a, range(n));
            for(int b=0; b<combos.size(); b++) midstep.addAll(cycleCheck(permutedCycles(combos.get(b))));
            output.add(midstep);
        }

        return output;
    }

    private static ArrayList<Integer> linkNumGenerate(ArrayList<ArrayList<ArrayList<Integer>>> cycles)
    {
        ArrayList<Integer> linkNo = new ArrayList<>();
        for(int a=3; a<Math.floor(n/2)+1; a++)
            for(int b=a; b<n-a+1; b++)
                for(int i=0; i<cycles.get(a-3).size(); i++)
                {
                    if(b==a)
                    {
                        for (int j = i; j < cycles.get(b - 3).size(); j++)
                            if (isDisjoint(cycles.get(a - 3).get(i), cycles.get(b - 3).get(j)))
                                linkNo.add(linkingNumber(cycles.get(a - 3).get(i), cycles.get(b - 3).get(j)));
                    }
                    else
                    {
                        for (int j = 0; j < cycles.get(b - 3).size(); j++)
                            if (isDisjoint(cycles.get(a - 3).get(i), cycles.get(b - 3).get(j)))
                                linkNo.add(linkingNumber(cycles.get(a - 3).get(i), cycles.get(b - 3).get(j)));
                    }
                }
        return linkNo;
    }

    private static int linkingNumber(ArrayList<Integer> cycle1, ArrayList<Integer> cycle2)
    {
        int sum=0;

        for(int i=0; i<cycle1.size(); i++)
        {
             for(int j=0; j<cycle2.size(); j++)
                 if(isDisjoint(cycle1, cycle2))
                 {
                     Edge firstedge = new Edge(cycle1.get((i%cycle1.size())),cycle1.get(((i+1)%cycle1.size())));
                     Edge secondedge = new Edge(cycle2.get((j%cycle2.size())),cycle2.get(((j+1)%cycle2.size())));

                     sum+=crossingMtx[firstedge.IndexOf()][secondedge.IndexOf()]
                             *sign(firstedge.b-firstedge.a)
                             *sign(secondedge.b-secondedge.a);
                 }
        }

        if(Math.abs(sum)==4)
        {
            printCycles(cycle1,cycle2);
            System.out.println(" with lk no " + sum + " /// ");
            linkno4++;
        }

        return sum;
    }

    private static ArrayList<Integer> range(int n)
    {
        ArrayList<Integer> output = new ArrayList<>();
        for(int a=1; a<n+1; a++) output.add(a);
        return output;
    }

    private static boolean isDisjoint(ArrayList<Integer> input0, ArrayList<Integer> input1)
    {
        for(int a=0; a<input0.size(); a++)
            for(int b=0; b<input1.size(); b++)
                if(input0.get(a)==input1.get(b)) return false;
        return true;
    }

    private static int sign(int x)
    {
        if(x>0) return 1;
        if(x<0) return -1;
        else return 0;
    }

    private static void printCycles(ArrayList<Integer> a, ArrayList<Integer> b)
    {
        for(int c=0; c<a.size(); c++)
            System.out.print(" " + a.get(c));

        System.out.print("//");

        for(int d=0; d<b.size(); d++)
            System.out.print(" " + b.get(d));

        System.out.println();
    }

    private static void signCheck(ArrayList<Integer> cycle)
    {
        for(int i=0; i<cycle.size(); i++)
            System.out.print(sign(cycle.get((i+1)%cycle.size())-cycle.get((i)%cycle.size()))+ " " + i + " ");
    }

}