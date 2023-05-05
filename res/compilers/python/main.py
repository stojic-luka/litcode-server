import sys

def sum_numbers(nums, target):
	nums_len = len(nums)
	for i in range(nums_len):
		for j in range(nums_len):
			if (i != j):
				if (nums[i] + nums[j] == target):
					return [i, j]

if __name__ == "__main__":
	passed_args = sys.argv[1:]
	print(passed_args)
	# print(sum_numbers(passed_args[0], passed_args[1]))