/*  Vasya has found a strange device. On the front panel of a device there are: a red button, a blue button and a display showing some positive integer. 
After clicking the red button, device multiplies the displayed number by two. After clicking the blue button, device subtracts one from the number on the display. If at some point the number stops being positive, the device breaks down. 
The display can show arbitrarily large numbers. Initially, the display shows number n.

Bob wants to get number m on the display. What minimum number of clicks he has to make in order to achieve this result?

Input
The first and the only line of the input contains two distinct integers n and m (1 ≤ n, m ≤ 104), separated by a space .

Output
Print a single number — 
the minimum number of times one needs to push the button required to get the number m out of number n.

Examples
InputCopy
4 6
OutputCopy
2
InputCopy
10 1
OutputCopy
9
Note
In the first example you need to push the blue button once, and then push the red button once.

In the second example, doubling the number is unnecessary, so we need to push the blue button nine times.*/

// import java.util.*;
// import java.io.*;

// class TwoButtons{
//     public static void main(String[] args) {
//         Scanner in = new Scanner(System.in);

//         long a = in.nextLong();
//         long b = in.nextLong();

//         long ans = 0;
//         while(b>a){
//             if(b%2!=0){
//                 b +=1;
//             }else{
//                 b /= 2;
//             }
//             ans ++;
//         }
//         ans += (a-b);
//         System.out.println(ans);
//     }
// }

// using graph

import java.util.*;
import java.io.*;
 
public final class TwoButtons{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        
        int n = in.nextInt();
        int m = in.nextInt();
        
        System.out.println(minSteps(n,m));
    }
    
    public static int minSteps(int n, int m){
        Queue<int[]> q = new LinkedList<>();
        Set<Integer> vis = new HashSet<>();
        
        q.offer(new int[]{n,0});
        vis.add(n);
        
        while(!q.isEmpty()){
            int[] curr = q.poll();
            int number = curr[0];
            int steps = curr[1];
            
            if(number == m) return steps;
            if(number*2 <= 2*m && !vis.contains(number*2)){
                q.offer(new int[]{number*2, steps+1});
                vis.add(number*2);
            }
            
            if(number-1 > 0 && !vis.contains(number-1)){
                q.offer(new int[]{number-1, steps+1});
                vis.add(number-1);
            }
        }
        return -1;
    }
}