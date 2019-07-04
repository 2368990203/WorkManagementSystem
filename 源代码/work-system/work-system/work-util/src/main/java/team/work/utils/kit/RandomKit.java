package team.work.utils.kit;

import java.util.Random;

public class RandomKit {

    /**
     * htc
     *
     * @param n 题库中某题型的题目数
     * @param k 要从中抽取多少题
     * @return 在[0, n)范围中随机抽取到k个不重复数字
     */
    public static int[] getRandomNum(int n, int k) {
        //生成0~n之间的数组
        int[] x = new int[n];
        int[] back = new int[k];
        for (int i = 0; i < n; i++) {
            x[i] = i;
        }
        //开始随机 k 个不重复数出来
        for (int i = 0; i < k; i++) {
            Random random = new Random();
            // t : i 至 n 的随机数
            // 目的：不再随机出已置换出去 的数 的下标
            int t = random.nextInt(n - i) + i;
            // 交换x[i]，x[t]的值
            int temp = x[i];
            x[i] = x[t];
            x[t] = temp;
            //此时输出x[i]的值，就是第i 个随机出的数。
           // System.out.println("随机数== " + x[i]);
            back[i] = x[i];
        }
        return back;
    }

    public static void main(String[] args) {
        getRandomNum(10,10);
    }
}
