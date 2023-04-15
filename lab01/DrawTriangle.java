public class DrawTriangle {
    public static String getStars(int stacks) {
        String stars = "";
        for (int i=0; i < stacks; i++) {
            stars+="*";
        }
        return stars;
    }

    public static void drawTriangles(int n) {
        if(n < 1) {
            return;
        }

        int stacks = 1;

        while (stacks < n+1) {
            String stars = getStars(stacks);

            System.out.println(stars);
            stacks+=1;
        }
    }
    public static void main(String[] args) {
        int nums = 1;
        drawTriangles(nums);
    }
}