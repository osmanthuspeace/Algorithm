package org.example.Strings;

/**
 * author: osmanthuspeace
 * createTime: 2024/6/6
 */
//低位优先的字符串排序(Least Significant Digit)
//对于长度不一样的字符串，需要对字符串进行填充，使其长度一致，一般是在短字符串的前面填'\0'，或者使用MSD
public class MyLSD {
    /**
     * @param a 待排序数组
     * @param W 每一个字符串的长度
     */
    public static void sort(String[] a, int W) {
        int N = a.length;
        int R = 256;//默认字符的种类不超过256种
        var aux = new String[N];

        //从最低位开始，每次根据第d个字符排序
        //必须倒序，因为排序低位时不会影响到高位的排序结果，最高位的权重是最大的
        for (int d = W - 1; d >= 0; d--) {
            int[] count = new int[R + 1];
            for (String s : a) {
                count[s.charAt(d) + 1]++;//+1的目的是在下一步时可以将频率转换为索引
                /*
                    比如s.charAt[d]是0的话，count[1]++
                    若不加1，count[0]++，则在下一步的时候count[1]=count[0]+(s.charAt[]等于1的个数)，明显多出来了，理论上(s.charAt[]等于1)的字符就是从索引为count[0]开始
                 */
            }
            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }
            for (String s : a) {
                aux[count[s.charAt(d)]++] = s;
            }
            System.arraycopy(aux, 0, a, 0, N);
        }
    }

}
