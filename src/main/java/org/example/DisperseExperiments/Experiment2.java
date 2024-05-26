package org.example.DisperseExperiments;

import java.util.Random;
import java.util.Scanner;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/7
 */
@SuppressWarnings("MismatchedReadAndWriteOfArray")
public class Experiment2 {

    private int n;
    private int[][] relationMatrix;

    private void init() {
        var s = new Scanner(System.in);
        System.out.println("请输入集合X的元素个数（不超过5个元素）：");
        n = s.nextInt();
        int[] x = new int[n + 1];
        relationMatrix = new int[n + 1][n + 1];
        System.out.println("请输入集合X中的元素：");
        for (int i = 1; i <= n; i++) {
            x[i] = s.nextInt();
        }
        var r = new Random();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                relationMatrix[i][j] = r.nextInt() % 2 == 0 ? 0 : 1;
                System.out.print(relationMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean isReflexive() {
        for (int i = 1; i <= n; i++) {
            if (relationMatrix[i][i] != 1) {
                System.out.println("不满足自反性，因为第" + i + "行第" + i + "列应该为1");
                return false;
            }
        }
        return true;
    }

    private boolean isIrreflexive() {
        for (int i = 1; i <= n; i++) {
            if (relationMatrix[i][i] != 0) {
                System.out.println("不满足反自反性，因为第" + i + "行第" + i + "列应该为0");
                return false;
            }
        }
        return true;
    }

    private boolean isSymmetrical() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j && relationMatrix[i][j] != relationMatrix[j][i]) {
                    System.out.println("不满足对称性，因为第" + i + "行第" + j + "列和第" + j + "行第" + i + "列不同时为1");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAsymmetrical() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j && relationMatrix[i][j] == relationMatrix[j][i]) {
                    System.out.println("不满足反对称性，因为第" + i + "行第" + j + "列和第" + j + "行第" + i + "列不能同时为1");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isTransitive() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= n; k++) {
                    if (relationMatrix[i][j] == 1 && relationMatrix[j][k] == 1 && relationMatrix[i][k] != 1) {
                        System.out.println("不满足传递性，因为第" + i + "行第" + j + "列和第" + j + "行第" + k + "列为" + relationMatrix[i][j] + "，但第" + i + "行第" + k + "列不为" + relationMatrix[i][j]);
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Experiment2 experiment2 = new Experiment2();
        experiment2.init();
        if (experiment2.isReflexive())
            System.out.println("满足自反性");
        if (experiment2.isIrreflexive())
            System.out.println("满足反自反性");
        if (experiment2.isSymmetrical())
            System.out.println("满足对称性");
        if (experiment2.isAsymmetrical())
            System.out.println("满足反对称性");
        if (experiment2.isTransitive())
            System.out.println("满足传递性");
    }
}
