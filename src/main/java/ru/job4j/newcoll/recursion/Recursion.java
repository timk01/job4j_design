package ru.job4j.newcoll.recursion;


public class Recursion {

    public int loop(int summary, int index) {
        for (int i = index; i > 0; i--) {
            summary += i;
        }
        return summary;
    }

    /**
     * пример с рекурсией, который по мне и читается хуже, и просто хуже - потому что жрет память и не безопасен.
     * ну и потом это по-сути тот же цикл, просто его мы отдали... рекурсии
     */
    public int sum(int summary, int index) {
        if (index > 0) {
            summary += index;
            index--;
            summary = sum(summary, index);
        }
        return summary;
    }

    /**
     * 1! = 1
     * 2! = 2
     * 3! = 6
     * 4! = 24
     * 5! = 120
     *
     * @param f
     * @return
     */

    public long factorialLoop(int f) {
        long result = 1L;
        if (f > 0) {
            for (int i = 1; i <= f; i++) {
                result = result * i;
            }
        }
        return result;
    }

    public long factorialRecursion(long index) {
        if (index == 0) {
            return 1;
        }

        long result = 1;
        if (index > 0) {
            result = index * factorialRecursion(index - 1);
        }

        return result;
    }

    /**
     * f(0)=0, а f(1)=1.
     * f(n)=f(n-1) + f(n-2)
     * 1, 1, 2, 3, 5, 8, 13, 21
     *
     * @param n
     * @return
     */

    public long fibonacciLoopClassic(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n should be non-negative");
        }

        if (n == 0) {
            return 0;
        }

        long result = 1L;
        long f1 = 1L;
        long f2 = 1L;

        for (int i = 2; i < n; i++) {
            result = f2 + f1;
            f1 = f2;
            f2 = result;
        }

        return result;
    }

    public long fibonacciLoop(int n) {
        long result = 0L;
        if (n == 1 || n == 2) {
            result = 1L;
        } else if (n > 2) {
            long f1 = 1L;
            long f2 = 1L;
            for (int i = 0; i < (n - 2); i++) {
                result = f2 + f1;
                f1 = f2;
                f2 = result;
            }
        }
        return result;
    }

    public long fibonacciRecursion(int n) {
        if (n == 0) {
            return 0L;
        }

        if (n == 1 || n == 2) {
            return 1L;
        }

        long result = 0L;
        if (n > 2) {
            result += fibonacciRecursion(n - 1) + fibonacciRecursion(n - 2);
        }
        return result;
    }

    public static void main(String[] args) {
        Recursion recursion = new Recursion();
        int result = (int) recursion.fibonacciRecursion(4);
        //int result = (int) recursion.factorialRecursion(3);
        System.out.println("result = " + result);
    }
}