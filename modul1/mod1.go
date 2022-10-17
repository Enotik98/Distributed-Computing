package main

import (
	"fmt"
	expRand "golang.org/x/exp/rand"
	"math"
	//"math"
	"math/rand"
	"time"
)

const TickDuration time.Duration = 100 * time.Millisecond

const AutoLambda float64 = 0.2

type Car struct {
	id       int
	waitTime time.Duration
	reserve  time.Duration
}

func (c Car) Process(parking chan int, stop chan struct{}) {
	endOfwaitTime := make(chan struct{})

	go func() {
		defer close(endOfwaitTime)
		time.Sleep(time.Millisecond * c.waitTime)
	}()

	select {
	case place := <-parking:
		fmt.Printf("%d take place %d\n", c.id, place)
		time.Sleep(time.Millisecond * c.reserve)
		parking <- place
		fmt.Printf("%d leave place %d\n", c.id, place)
	case <-endOfwaitTime:
		fmt.Printf("%d leave, end of waitTime\n", c.id)
	case <-stop:
		fmt.Printf("parking is closed, %d leaves\n", c.id)
	}
}

func CreateTraffic(lambda float64, stop chan struct{}) <-chan Car {
	traffic := make(chan Car)
	go func() {
		defer close(traffic)
		for {
			select {
			case <-stop:
				break
			default:
				traffic <- CreateCar()
				newDuration := int64(math.RoundToEven(expRand.ExpFloat64() / lambda))
				time.Sleep(time.Duration(newDuration) * time.Millisecond)
			}
		}
	}()
	return traffic
}

func PlaceLot(parking chan int, stop chan struct{}, count int) {
	for i := 1; i <= count; i++ {
		select {
		case <-stop:
			break
		default:
			parking <- i
			fmt.Printf("create parking place %d\n", i)
		}
	}
}

func CreateCar() Car {
	rand.Seed(time.Now().UTC().UnixNano())

	id := rand.Intn(100)
	wait := time.Duration(rand.Int63n(50) + 1)
	reserve := time.Duration(rand.Int63n(50) + 1)
	result := Car{id: id, waitTime: wait, reserve: reserve}
	fmt.Printf("car %d, wait %d, reserve %d\n", result.id, result.waitTime, result.reserve)
	return result
}
func main() {
	parking := make(chan int)
	stopTime := make(chan struct{})
	cars := CreateTraffic(AutoLambda, stopTime)
	go PlaceLot(parking, stopTime, 3)
	for i := 0; i < 25; i++ {
		select {
		case car := <-cars:
			go car.Process(parking, stopTime)
		}
	}
}
