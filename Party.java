/*
 * A. Party
time limit per test3 seconds
memory limit per test256 megabytes
A company has n employees numbered from 1 to n. Each employee either has no immediate manager or exactly one immediate manager, who is another employee with a different number. An employee A is said to be the superior of another employee B if at least one of the following is true:

Employee A is the immediate manager of employee B
Employee B has an immediate manager employee C such that employee A is the superior of employee C.
The company will not have a managerial cycle. That is, there will not exist an employee who is the superior of his/her own immediate manager.

Today the company is going to arrange a party. This involves dividing all n employees into several groups: every employee must belong to exactly one group. Furthermore, within any single group, there must not be two employees A and B such that A is the superior of B.

What is the minimum number of groups that must be formed?

Input
The first line contains integer n (1 ≤ n ≤ 2000) — the number of employees.

The next n lines contain the integers pi (1 ≤ pi ≤ n or pi = -1). Every pi denotes the immediate manager for the i-th employee. If pi is -1, that means that the i-th employee does not have an immediate manager.

It is guaranteed, that no employee will be the immediate manager of him/herself (pi ≠ i). Also, there will be no managerial cycles.

Output
Print a single integer denoting the minimum number of groups that will be formed in the party.

Examples
InputCopy
5
-1
1
2
1
-1
OutputCopy
3
Note
For the first example, three groups are sufficient, for example:

Employee 1
Employees 2 and 4
Employees 3 and 5

*/

import java.util.*;


public final class Party{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        if (n == 0) {
            System.out.println(0);
            return;
        }


        int[] man = new int[n+1];
        for (int i = 1; i <=n; i++) {
            man[i] = sc.nextInt();
        }

        int maxDepth = 0;
        for (int i = 1; i <= n; i++) {
            maxDepth = Math.max(maxDepth, findDepth(i, man));
        }
        System.out.println(maxDepth);
    }

    private static int findDepth(int employee, int[] man){
        int dep = 1;
        while(man[employee]!=-1){
            employee = man[employee];
            dep++;
        }
        return dep;
    }
}