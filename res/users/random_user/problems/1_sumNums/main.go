package main
import (
	"fmt"
	"os"
	"strconv"
)
func sumNumbers(num1 int, num2 int) int {
    fmt.Println("Hello world")
    return num1 + num2
}


func main() {
	args := [][]int{{1, 3}, {3, 5}, {7, 9}}
	expected := []int{4, 8, 16}
	file, err := os.OpenFile("user.out", os.O_APPEND|os.O_CREATE|os.O_WRONLY, 0644)
	if err != nil {
		panic(err)
	}
	defer file.Close()
	for i := 0; i < len(args); i++ {
		value := sumNumbers(args[i][0], args[i][1])
		if value != expected[i] {
			panic(fmt.Sprintf("Expected value of %v, but got %v", expected[i], value))
		}
		_, err := file.WriteString(strconv.Itoa(value) + "\n")
		if err != nil {
			panic(err)
		}
	}
}
