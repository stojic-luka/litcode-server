class Solution():
    def sum_numbers(self, num1, num2):
        print("Hello world")
        return num1 + num2

args = [[1, 3], [3, 5], [7, 9]]
expected = [4, 8, 16]
f = open('user.out', 'a')
try:
	for i in range(len(args)):
		value = Solution().sum_numbers(args[i][0], args[i][1])
		if not isinstance(value, int): raise TypeError(f'{str(value)} is not valid value for the expected return type int')
		if value != expected[i]: raise ValueError(f'Expected value of {str(expected[i])}, but got {str(value)}')
		f.write(f'{str(value)}\n')
except Exception as e:
	raise e
