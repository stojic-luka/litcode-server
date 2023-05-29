import java.io.BufferedWriter;
import java.lang.IllegalArgumentException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Main {
public class Solution {
    public int sumNumbers(int num1, int num2) {
        
        return num1 + num2;
    }
}

	public static void main(String[] args) throws java.io.IOException {
		int[][] argss = {{1, 3}, {3, 5}, {7, 9}};
		int[] expected = {4, 8, 16};
		Main mainClass = new Main();
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("user.out"))) {
			for (int i = 0; i < argss.length; i++) {
				int value = mainClass.new Solution().sumNumbers(argss[i][0], argss[i][1]);
				if (value != expected[i])
					throw new IllegalArgumentException(String.format("Expected value of {%d}, but got {%d}", expected[i], value));
				writer.write(String.valueOf(value) + "\n");
			}
		}
	}
}
