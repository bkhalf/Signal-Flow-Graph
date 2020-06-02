package sample;

import java.awt.Point;
import java.util.ArrayList;

public class SFG {
    int [][] arr;
    int lenght;
    public ArrayList<ArrayList<Integer>> loops;
    public ArrayList<ArrayList<Integer>> paths;
    public int NTL=0;
    int lastNode;
    public SFG(int n) {
        lenght=n;
        arr=new int [n][n];
        loops = new ArrayList<ArrayList<Integer>> ();
        paths= new ArrayList<ArrayList<Integer>> ();

    }
    public void setLastNode(int last){
        this.lastNode=last;
    }
    public void addEdge(int from,int to,int weight) {
        from--;
        to--;
        arr[from][to]=weight;
    }
    public void forwardPaths (int dist) {
        dist--;
        for(int i=0;i<lenght;i++) {
            if(arr[0][i]!=0) {
                ArrayList<Integer> path=new ArrayList<Integer>();
                boolean [] visited=new boolean[lenght];
                path.add(0);
                visited[0]=true;
                findTheWay(i,path,visited,dist);
            }
        }
        for(int i=0;i<paths.size();i++) {
            int k=arr[paths.get(i).get(0)][paths.get(i).get(1)];
            for(int j=1;j<paths.get(i).size()-1;j++) {
                k*=arr[paths.get(i).get(j)][paths.get(i).get(j+1)];
            }
            paths.get(i).add(k);
        }
    }
    private void findTheWay(int i, ArrayList<Integer> path, boolean[] visited, int dist) {
        path.add(i);
        visited[i]=true;
        if(i==dist) {
            paths.add(path);
            return ;
        }
        for(int j=0;j<lenght;j++) {
            if(arr[i][j]!=0 && !visited[j]&&i!=j) {
                ArrayList<Integer> path2=new ArrayList<Integer>();
                boolean [] visited2=new boolean[lenght];

                for(int m=0;m<path.size();m++)
                    path2.add(path.get(m));
                for(int m=0;m<visited.length;m++)
                    visited2[m]=visited[m];

                findTheWay(j,path2,visited2,dist);
            }
        }
    }

    public void cycles () {
        for(int i=0;i<arr.length;i++) {
            for(int j=0;j<arr.length;j++) {
                if(arr[i][j]!=0 && i!=j) {
                    ArrayList<Integer> l=new ArrayList<>();
                    boolean []visited = new boolean [lenght];
                    visited[i]=true;
                    l.add(i);
                    if(findOut(j,l,visited)) {
                        loops.add(l);
                    }
                }
            }
        }

        fixing(loops);
        for(int i=0;i<arr.length;i++) {
            for(int j=0;j<arr.length;j++) {
                if(i==j&&arr[i][j]!=0) {
                    ArrayList <Integer> t= new ArrayList<Integer>();
                    t.add(i);
                    t.add(arr[i][j]);
                    loops.add(t);
                }
            }
        }
    }

    private void fixing(ArrayList<ArrayList<Integer>> lists) {
        int k=0;
        int temp=0;

        for(int i=0;i<lists.size();i++) {
            k=arr[lists.get(i).get(0)][lists.get(i).get(1)];
            temp=lists.get(i).get(0);
            for(int j=1;j<lists.get(i).size()-1;j++) {
                k*=arr[lists.get(i).get(j)][lists.get(i).get(j+1)];
                temp*=lists.get(i).get(j);
            }
            lists.get(i).remove(lists.get(i).size()-1);
            lists.get(i).add( k);
            lists.get(i).add( temp);
        }
        int i=0;
        boolean loop1=i<lists.size()-1;
        ArrayList<Integer> iList;
        ArrayList<Integer> jList;
        while(loop1) {
            int j=i+1;
            boolean loop2=j<lists.size();
            iList=lists.get(i);
            while(loop2) {
                jList=lists.get(j);
                if(Math.abs(iList.get(iList.size()-1)-jList.get(jList.size()-1))<.00000001&&Math.abs(iList.get(iList.size()-2)-jList.get(jList.size()-2))<.00000001)
                {
                    lists.remove(j);
                    j--;
                }
                j++;
                loop2=j<lists.size();
            }
            i++;
            loop1=i<lists.size()-1;
        }
        for(i=0;i<lists.size();i++) {
            lists.get(i).remove(lists.get(i).size()-1);
        }

    }

    private boolean findOut(int j, ArrayList<Integer> l, boolean[] visited) {
        visited[j]=true;
        l.add(j);
        int i=j;
        for(j=0;j<arr.length;j++) {
            if(arr[i][j]!=0&&visited[j]&&i!=j) {
                if(j==l.get(0)) {
                    visited[j]=true;
                    l.add(j);
                    return true;
                }
            }else if(arr[i][j]!=0&&!visited[j]&&i!=j){
                for(int k=0;k<visited.length;k++) {
                    if(arr[j][k]!=0 && j!=k&&!visited[k]) {
                        ArrayList<Integer> l2=new ArrayList<>();
                        boolean []visited2 = new boolean [lenght];

                        for(int m=0;m<l.size();m++)
                            l2.add(l.get(m));
                        for(int m=0;m<visited.length;m++)
                            visited2[m]=visited[m];
                        l2.add(j);
                        visited2[j]=true;
                        if(findOut(k,l2,visited2)) {
                            loops.add(l2);
                        }
                    }else if(arr[j][k]!=0 && j!=k&&visited[k]) {
                        if(k==l.get(0)) {
                            visited[j]=true;
                            l.add(j);
                            visited[k]=true;
                            l.add(k);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public Point nonTouchingLoops(ArrayList<ArrayList<Integer>> loops) {
        int k=0;
        int l=0;
        for(int i=0;i<loops.size()-1;i++) {
            l+=loops.get(i).get(loops.get(i).size()-1);
            for(int j=i+1;j<loops.size();j++) {
                if(!intersection(loops.get(i), loops.get(j))) {
                    k+=loops.get(i).get(loops.get(i).size()-1)*loops.get(j).get(loops.get(j).size()-1);
                }
            }
        }
        l+=loops.get(loops.size()-1).get(loops.get(loops.size()-1).size()-1);
        Point h=new Point();
        h.x=l;
        h.y=k;
        return h;


    }

    public boolean intersection(ArrayList<Integer> list1, ArrayList<Integer> list2) {

        for (int i=0;i<list1.size()-1;i++) {
            if(list2.contains(list1.get(i))) {
                if(list2.get(list2.size()-1)!=list1.get(i))
                    return true;
            }
        }

        return false;
    }

    public void pathsDelta() {
        for(int i=0;i<paths.size();i++) {
            ArrayList<ArrayList<Integer>> pLoops= new ArrayList<ArrayList<Integer>>();
            for(int j=0;j<loops.size();j++) {
                if(!intersection(paths.get(i), loops.get(j))) {
                    pLoops.add(loops.get(j));
                }
            }
            if(pLoops.size()!=0) {
                Point p=nonTouchingLoops(pLoops);
                paths.get(i).add(1-p.x+p.y);
            }else {
                paths.get(i).add(1);
            }


        }
    }
    public double calc() {
        cycles();
        forwardPaths(lastNode);
        pathsDelta();
        double ans=0;
        for(int i=0;i<paths.size();i++) {
            ans+=paths.get(i).get(paths.get(i).size()-1)*paths.get(i).get(paths.get(i).size()-2);
        }
        Point p=nonTouchingLoops(loops);
        ans/=(1-p.x+p.y);
        System.out.println("Loops =  "+loops);
        System.out.println("Note : the last element in every list is the loop gain");
        System.out.println("paths =  "+paths);
        System.out.println("Note :  the element precedes the last element in every list is the path gain");
        System.out.println("Note : the last element in every list is the path delta");
        System.out.println("ans =  "+ans);
        return ans;
    }

    public void print () {
        for(int i=0;i<arr.length;i++) {
            for(int j=0;j<arr.length;j++) {
                System.out.print(arr[i][j]+"      ");
            }
            System.out.println();
        }
    }
}
