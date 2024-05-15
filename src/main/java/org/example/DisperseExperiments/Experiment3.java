package org.example.DisperseExperiments;

import java.util.Scanner;

import static java.lang.Math.sqrt;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/14
 */
public class Experiment3 {

    private final int[] factor;
    private int len;
    private final int n;

    public Experiment3(int n) {
        factor = new int[(int) (sqrt(n) * 2) + 1];//感觉因子个数不会超过n的平方根的两倍，但是数组末尾会有空位
        this.n = n;
    }

    private void init(int n) {
        int index = 0;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                factor[index++] = i;
            }
        }
        len = index;
    }

    private void isCover() {
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (factor[j] % factor[i] == 0) {
                    boolean flag = true;
                    for (int k = 0; k < len; k++) {
                        if (factor[k] == factor[i] || factor[k] == factor[j]) continue;
                        if (factor[j] % factor[k] == 0 && factor[k] % factor[i] == 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag)
                        System.out.println("<" + factor[i] + "," + factor[j] + ">");
                }
            }
        }
    }

    private void isComplementedLattice() {
        boolean[] flag = new boolean[len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (i == j) continue;
                if (factor[j] == 0 || factor[i] == 0) break;
                var _gcd = gcd(factor[i], factor[j]);
                var _lcm = factor[i] * factor[j] / _gcd;
                if (_gcd == 1 && _lcm == n) {
                    flag[i] = true;
                    break;
                }
            }
        }
        for (int i = 0; i < len; i++) {
            if (!flag[i]) {
                System.out.println("不是有补格");
                return;
//                System.out.println(factor[i]);
            }
        }
        System.out.println("是有补格");
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    public static void main(String[] args) {
        int n;
        System.out.println("请输入一个正整数：");
        var s = new Scanner(System.in);
        n = s.nextInt();
        var e = new Experiment3(n);
        e.init(n);
        e.isCover();
        e.isComplementedLattice();
    }
}
