import java.util.Scanner;
import java.util.ArrayList;

public class Edge0 {

    private static int n=8;
    private int a;
    private int b;

    private static ArrayList<Edge0> edgeList = new ArrayList<Edge0>(0);

    public static void main(String[] args)
    {
        ArrayList<ArrayList<ArrayList<Integer>>> testing = generateAllCycles();

        for(int a=0; a<testing.size(); a++)
        {
            System.out.println("Length is " + (a+3));
            System.out.println(testing.get(1).size());
            for(int r=0; r<testing.get(a).size(); r++) System.out.print(testing.get(a).get(r) + " ");
            System.out.println(testing.size());
            System.out.println();
        }


        Scanner kboard = new Scanner(System.in);
        System.out.print("What is the n of the given K_n ");
        n=kboard.nextInt();
        int[][] signedCrossings=new int[n*(n-1)/2][n*(n-1)/2];
        for(int i=1; i<=n; i++) for(int j=i+1; j<=n; j++) {
            edgeList.add(new Edge0(i, j));
        }
        for(int i=0; i<edgeList.size(); i++) {
            System.out.println(edgeList.get(i).a + "," + edgeList.get(i).b);
        }
        System.out.println("How many crossings?");
        int crossno = kboard.nextInt();
        for(int i=0; i<crossno; i++)
        {
            System.out.println("Which edges, with the edge that lies over being listed first, cross?");
            System.out.println("use a comma to pair vertices when denoting edges, and use a space to separate edges ");
            String cross = kboard.next() + " " + kboard.next();//BECAUSE IT IS DONE BY SPECIFIC CHARACTER, DOES NOT WORK PAST K9
            System.out.println(cross.length());
            int w= (int) cross.charAt(0)-48;
            int x= (int) cross.charAt(2)-48;
            int y= (int) cross.charAt(4)-48;
            int z= (int) cross.charAt(6)-48;
            System.out.println(w + " " + x);
            if(x<w)
            {
                w+=x;
                x=w-x;
                w=w-x;
            }
            if(z<y)
            {
                y+=z;
                z=y-z;
                y=y-z;
            }
            System.out.println(w + " " + x);
            System.out.println(y + " " + z);
            Edge0 firstedge = new Edge0(w,x);
            Edge0 secondedge = new Edge0(y,z);
            if(w<y&&x<z)
            {
                signedCrossings[firstedge.IndexOf()][secondedge.IndexOf()]=-1;
                signedCrossings[secondedge.IndexOf()][firstedge.IndexOf()]=-1;
            }
            if(w>y&&x>z)
            {
                signedCrossings[firstedge.IndexOf()][secondedge.IndexOf()]=1;
                signedCrossings[secondedge.IndexOf()][firstedge.IndexOf()]=1;
            }
        }
        kboard.close();
        for(int a=0; a<n*(n-1)/2; a++ ) {
            for (int b = 0; b < n * (n - 1) / 2; b++)
                System.out.print(signedCrossings[a][b] + " ");
            System.out.println();
        }
        ArrayList<Integer> linklist = new ArrayList<>();
        linklist.addAll(linkNumGenerate(signedCrossings,generateAllCycles()));
        System.out.println(linklist.size());
        for(int a=0; a<linklist.size(); a++)
            if(linklist.get(a)!=0)
            {
                System.out.print(linklist.get(a)+ " ");
            }

    }


    public Edge0(int a, int b) {
        this.a = a;
        this.b = b;
    }

    private int IndexOf()
    {
        for(int c=0; c<n*(n-1)/2; c++)
            if((edgeList.get(c).a==a&&edgeList.get(c).b==b)||(edgeList.get(c).a==b&&edgeList.get(c).b==a)) return c;
        return -1;
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
        if(input.size() == 1) {
            output.add(input);
            return output;
        }

        for(int i=0; i<input.size(); i++)
        {
            int fixpos=input.get(i);
            ArrayList<Integer> inputOther = new ArrayList<>();
            for(int j=0; j<input.size(); j++) if(input.get(j)!=fixpos) inputOther.add(input.get(j));
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

    private static ArrayList<ArrayList<Integer>> permute0(ArrayList<Integer> input) {
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();

        int fixpos = input.get(0);
        ArrayList<Integer> inputOther = new ArrayList<>();
        for (int j = 0; j < input.size(); j++) if (input.get(j) != fixpos) inputOther.add(input.get(j));
        ArrayList<ArrayList<Integer>> recRes = permute(inputOther);
        for (int k = 0; k < recRes.size(); k++) {
            ArrayList<Integer> midstep = new ArrayList<>();
            midstep.add(fixpos);
            midstep.addAll(recRes.get(k));
            output.add(midstep);
        }
        return output;
    }

    private static ArrayList<ArrayList<ArrayList<Integer>>> generateAllCycles()
    {
        ArrayList<ArrayList<ArrayList<Integer>>> output = new ArrayList<>();
        for(int a=3; a<=n-3; a++)
        {
            ArrayList<ArrayList<Integer>> midstep = new ArrayList<>();
            ArrayList<ArrayList<Integer>> combos = generateCycles(a, range(n));
            for(int b=0; b<combos.size(); b++) midstep.addAll(cycleCheck(permute0(combos.get(b))));
            output.add(midstep);
        }
        return output;
    }

    private static ArrayList<ArrayList<Integer>> cycleCheck(ArrayList<ArrayList<Integer>> input)
    {
        for(int a=0; a<input.size(); a++) for(int b=a+1; b<input.size(); b++) if(cycleCheck0(input.get(a), input.get(b))) input.remove(b);
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

    private static int factorial (int n)
    {
        if(n==1) return 1;
        else return n*factorial(n-1);
    }

    private static boolean isDisjoint(ArrayList<Integer> input0, ArrayList<Integer> input1)
    {
        for(int a=0; a<input0.size(); a++)
            for(int b=0; b<input1.size(); b++) if(input0.get(a)==input1.get(b)) return false;
        return true;
    }

    private static ArrayList<Integer> linkNumGenerate(int[][] crossingMtx, ArrayList<ArrayList<ArrayList<Integer>>> cycles)
    {
        ArrayList<Integer> linkNo = new ArrayList<>();
        for(int a=3; a<Math.floor(n/2)+1; a++)
        {
            for(int b=a; b<n-a+1; b++)
            {
                for(int i=0; i<cycles.get(a-3).size(); i++)
                {
                    for(int j=0; j<cycles.get(b-3).size(); j++)
                        if(isDisjoint(cycles.get(a-3).get(i), cycles.get(b-3).get(j)))
                        {
                            linkNo.add(linkingNumber(crossingMtx, cycles.get(a-3).get(i),cycles.get(b-3).get(j)));
                        }

                }
            }
        }
        return linkNo;
    }

    private static int linkingNumber(int[][] crossingMtx, ArrayList<Integer> cycle1, ArrayList<Integer> cycle2)
    {
        int sum=0;
        for(int i=0; i<cycle1.size(); i++)
        {
            for(int j=0; j<cycle2.size(); j++)
            {
                Edge0 firstedge = new Edge0(cycle1.get( (i%cycle1.size())),cycle1.get(((i+1)%cycle1.size())));
                Edge0 secondedge = new Edge0(cycle2.get( (j%cycle2.size())),cycle2.get(((j+1)%cycle2.size())));
                sum+=crossingMtx[firstedge.IndexOf()][secondedge.IndexOf()]*sign(cycle1.get((i+1)%cycle1.size())-cycle1.get((i+1)%cycle1.size()));
            }
        }
        if(sum>0) System.out.println(cycle1 + " " + cycle2);
        return sum;
    }

    private static int sign(int x)
    {
        if(x>0) return 1;
        if(x<0) return -1;
        else return 0;
    }

    private static ArrayList<Integer> range(int n)
    {
        ArrayList<Integer> output = new ArrayList<>();
        for(int a=1; a<n+1; a++) output.add(a);
        return output;
    }


}
