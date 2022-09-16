package main

import (
	"fmt"
	"math/rand"
	"time"
)

func printArr(arr []int) {
	fmt.Printf("[ ")
	for i := 0; i < len(arr); i++ {
		fmt.Printf("%d ", arr[i])
	}
	fmt.Printf(" ]\n")
}

func fight(fighters []int, final chan int) int {
	printArr(fighters)
	if len(fighters) == 1 {
		final <- fighters[0]
		return fighters[0]
	}
	var winners []int
	for i := 0; i < len(fighters); i += 2 {
		if fighters[i] > fighters[i+1] {
			winners = append(winners, fighters[i])
		} else {
			winners = append(winners, fighters[i+1])
		}
	}
	return fight(winners, final)
}

func main() {
	var participation []int
	Amount := 32
	s1 := rand.NewSource(time.Now().UnixNano())
	r1 := rand.New(s1)
	for i := 0; i < Amount; i++ {
		participation = append(participation, r1.Intn(500))
	}
	final := make(chan int, 2)
	go fight(participation[:Amount/2], final)
	go fight(participation[Amount/2:], final)
	leftWinner := <-final
	rightWinner := <-final
	if rightWinner > leftWinner {
		fmt.Printf("Winner is %d", rightWinner)
	} else {
		fmt.Printf("Winner is %d", leftWinner)
	}

}
