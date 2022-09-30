package main

import (
	"math/rand"
	"sync"
	"time"
)

type City struct {
	index      int
	deleted    bool
	routePrice int
}

func editRoute(list [][]City, cityState []City, rwLock *sync.RWMutex) {
	for true {
		rand.Seed(time.Now().UnixNano())
		randFrom := rand.Intn(len(list))

		rwLock.Lock()

		if len(list[randFrom]) != 0 && !cityState[randFrom].deleted {
			action := rand.Intn(2) == 0

			randToIndex := rand.Intn(len(list[randFrom]))
			cityTo := list[randFrom][randToIndex]

			cityTo.deleted = action
			randTo := cityTo.index

			if action {
				println("Route:", randFrom, " <-=-> ", randTo, "was deleted")
			} else {
				println("Route:", randFrom, " <-=-> ", randTo, "was added")
			}

			for i := 0; i < len(list[randTo]); i++ {
				if list[randTo][i].index == randFrom {
					cityFrom := list[randTo][i]
					cityFrom.deleted = action
					break
				}
			}
		}
		rwLock.Unlock()
		time.Sleep(3 * time.Second)
	}
}
func changePrice(list [][]City, rwLock *sync.RWMutex) {
	for true {
		rand.Seed(time.Now().UnixNano())
		randFrom := rand.Intn(len(list))

		rwLock.Lock()

		if len(list[randFrom]) != 0 {

			randToIndex := rand.Intn(len(list[randFrom]))
			cityTo := list[randFrom][randToIndex]

			newPrice := 1 + rand.Intn(100)
			randTo := cityTo.index
			cityTo.routePrice = newPrice

			println("Price", randFrom, " <-=-> ", randTo, "was changed to", newPrice)

			for i := 0; i < len(list[randTo]); i++ {
				if list[randTo][i].index == randFrom {
					cityFrom := list[randTo][i]
					cityFrom.routePrice = newPrice
					break
				}
			}
		}
		rwLock.Unlock()
		time.Sleep(2 * time.Second)
	}
}
func driver(list [][]City, cityState []City, rwLock *sync.RWMutex) {
	for true {
		rand.Seed(time.Now().UnixNano())
		var randFrom int
		var randTo int
		rwLock.RLock()

		for {
			randFrom = rand.Intn(len(list))
			randTo = rand.Intn(len(list))

			if randFrom != randTo && !cityState[randFrom].deleted && !cityState[randTo].deleted {
				break
			}
		}

		visited := make([]bool, len(cityState))
		price := driverHelper(list, cityState, &cityState[randFrom], &cityState[randTo], visited)
		println("Price from", randFrom, "to", randTo, " = ", price)
		rwLock.RUnlock()
		time.Sleep(2 * time.Second)
	}
}

func driverHelper(list [][]City, cityState []City, cur *City, dest *City, visited []bool) int {
	if cur.index == dest.index {
		return 0
	}
	visited[cur.index] = true

	for i := 0; i < len(list[cur.index]); i++ {
		neighbour := list[cur.index][i]
		if !visited[neighbour.index] {
			neighbourPrice := driverHelper(list, cityState, &neighbour, dest, visited)
			if neighbourPrice != -1 {
				return neighbourPrice + neighbour.routePrice
			}
		}
	}

	return -1
}

func main() {
	var ReadWriteLock sync.RWMutex

	list := [][]City{
		{City{1, false, 17}, City{2, false, 8}},
		{City{0, false, 17}, City{2, false, 19},
			City{4, false, 5}},
		{City{0, false, 8}, City{1, false, 19},
			City{3, false, 3}, City{4, false, 35}},
		{City{2, false, 3}, City{4, false, 27}},
		{City{1, false, 5}, City{2, false, 35},
			City{3, false, 27}},
	}
	cityState := []City{
		{0, false, 0},
		{1, false, 0},
		{2, false, 0},
		{3, false, 0},
		{4, false, 0},
	}

	go changePrice(list, &ReadWriteLock)
	go editRoute(list, cityState, &ReadWriteLock)
	go driver(list, cityState, &ReadWriteLock)

	time.Sleep(1 * time.Hour)
}
