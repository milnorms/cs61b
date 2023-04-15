public class BreakContinue {

    public static int replaceNum(int[]arr, int num, int curPos) {
        int sum = 0;
        for (int i = 0; i < num+1; i++) {
//            System.out.println("cur pos: " + curPos);
            // break out if last index
            if (curPos >= arr.length) {break;}
            sum += arr[curPos];
//            System.out.println("CUR NUM: "+arr[curPos]);
//            System.out.println("SUM: " + sum);
            curPos += 1;
        }
//        System.out.println("**FINAL SUM: " + sum);
        return sum;
    }
    public static void windowPosSum(int[] a, int n) {
        for (int i = 0; i < a.length; i++) {
            // iterate up to the 2nd to last index
            // check if negative, then continue
            if (a[i] < 0 ) {
                continue;
            }

            a[i] = replaceNum(a, n, i);

        }
    }
    public static void main(String[] args) {
//       int[] a = {1, 2, -3, 4, 5, 4};
        int[] a = {1, -1, -1, 10, 5, -1};
        int n = 2;
        windowPosSum(a, n);
        // Should print 4, 8, -3, 13, 9, 4
        System.out.println(java.util.Arrays.toString(a));
    }
}