#include <fstream>
#include <iostream>
#include <string>
#include <vector>

class Solution {
public:
    int sumNumbers(int num1, int num2) {
        
        return num1 + num2;
    }
};

int main() {
	std::ofstream f("user.out", std::ios::app);
	std::vector<std::vector<int>> args = {{1, 3}, {3, 5}, {7, 9}};
	std::vector<int> expected = {4, 8, 16};
	try {
		for (int i = 0; i < args.size(); ++i) {
			int value = Solution().sumNumbers(args[i][0], args[i][1]);
			if (typeid(value) != typeid(int))
				throw std::invalid_argument(std::to_string(value) + " is not valid value for the expected return type int");
			if (value != expected[i])
				throw std::runtime_error("Expected value of " + std::to_string(expected[i]) + ", but got " + std::to_string(value));
			f << value << std::endl;
		}
	} catch (std::exception &e) {
		throw e;
	}
	return 0;
}
