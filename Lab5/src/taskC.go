package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var BOUND = 5
var seed = rand.NewSource(time.Now().UnixNano())
var random = rand.New(seed)

type Arrays struct {
	arrayList [][]int
	sync.WaitGroup
}

func creatArrays(n, size int) *Arrays {
	return &Arrays{
		arrayList: initializationArrays(n, size),
	}
}
func initializationArrays(n, size int) [][]int {
	arrayList := make([][]int, n, size)
	for i := 0; i < n; i++ {
		arrayList[i] = generateArray(size)
	}
	return arrayList
}
func generateArray(size int) []int {
	array := make([]int, size)
	for i := 0; i < size; i++ {
		array[i] = random.Intn(BOUND)
	}
	return array
}
func printArray(list *Arrays) {
	for _, i := range list.arrayList {
		fmt.Println(i)
	}
	fmt.Println()
}

func changeArray(list *Arrays, group *sync.WaitGroup, index, size int) {

	elem := random.Intn(size)
	operation := random.Intn(2)
	switch operation {
	case 0:
		if list.arrayList[index][elem] < BOUND {
			list.arrayList[index][elem]++
		}
	case 1:
		if list.arrayList[index][elem] > BOUND*(-1) {
			list.arrayList[index][elem]--
		}
	}
	group.Done()
}
func checkSumOfArray(list *Arrays, n int) bool {
	sum := make([]int, n)
	for i := 0; i < n; i++ {
		counter := 0
		for _, j := range list.arrayList[i] {
			counter += j
		}
		sum[i] = counter
	}
	if checkSum(sum) {
		return true
	} else {
		return false
	}

}
func checkSum(arr []int) bool {
	fmt.Println("Sum: ", arr)
	for i := range arr {
		if arr[0] != arr[i] {
			return false
		}
	}
	return true
}

func main() {
	const N = 3
	const SIZE = 5

	arr := creatArrays(N, SIZE)
	group := new(sync.WaitGroup)
	stop := false
	for !stop {
		group.Add(N)
		for i := 0; i < N; i++ {
			go changeArray(arr, group, i, SIZE)
		}
		group.Wait()
		if checkSumOfArray(arr, N) {
			stop = true
			fmt.Println("Array sum are equal")
		}
		printArray(arr)
	}
}
