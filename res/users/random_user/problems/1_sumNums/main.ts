class Solution {
    sumNumbers(num1: number, num2: number): number {
        
        return num1 + num2;
    }
}

import { appendFile } from 'fs';
const args: number[][] = [[1, 3], [3, 5], [7, 9]];
const expected: number[] = [4, 8, 16];
for (let i: number = 0; i < args.length; i++) {
	try {
		const value: number = new Solution().sumNumbers(args[i][0], args[i][1]);
		if (typeof value !== 'number') throw new TypeError(`${value} is not a valid value for the expected return type int`);
		if (value !== expected[i]) throw new Error(`Expected value of ${expected[i]}, but got ${value}`);
		appendFile('user.out', value.toString() + '\n', (err) => {
			if (err) throw err;
		});
	} catch (e) {
		throw e;
	}
}
