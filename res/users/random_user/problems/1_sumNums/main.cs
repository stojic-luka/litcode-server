using System;
using System.IO;
public class Program {

public class Solution {
    public int sumNumbers(int num1, int num2) {
        Console.WriteLine("Hello world!");
        return num1 + num2;
    }
}

	public static void Main() {
		int[,] args = new int[,] {{1, 3}, {3, 5}, {7, 9}};
		int[] expected = {4, 8, 16};
		Solution solution = new Solution();
		try {
			using (StreamWriter writer = new StreamWriter("user.out"))
				for (int i = 0; i < args.GetLength(0); i++) {
					int value = solution.sumNumbers(args[i,0], args[i,1]);
					if (value != expected[i])
						throw new ArgumentException(String.Format("Expected value of {{0}}, but got {{1}}", expected[i], value));
					writer.Write(value + "\n");
				}
		} catch (Exception e) {
			throw e;
		}
	}
}
