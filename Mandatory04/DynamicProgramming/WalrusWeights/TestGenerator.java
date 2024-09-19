package WalrusWeights;

import java.util.Random;

public class TestGenerator {

    public static void main(String[] args) {
        Random random = new Random();
        int n = 100; // Number of plates
        System.out.println(n);
        for (int i = 0; i < n; i++) {
            // Generate random plate weight between 1 and 1000
            int weight = random.nextInt(1000) + 1;
            System.out.println(weight);
        }
    }
}
