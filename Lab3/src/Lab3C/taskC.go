package main

import (
	"math/rand"
	"sync"
)

func container(slice []string, element string) bool {
	for i := 0; i < len(slice); i++ {
		if slice[i] == element {
			return true
		}
	}
	return false
}
func getRandomIngredients() []string {
	list := []string{"paper", "tobacco", "matches"}
	first := rand.Intn(3)
	second := rand.Intn(3)
	for first == second {
		second = rand.Intn(3)
	}
	print("Manager put : " + list[first] + " & " + list[second] + "\n")
	return []string{list[first], list[second]}
}
func smoker(ingredient string, ingredients []string, wg *sync.WaitGroup, cigaretteIsReady *bool) {
	if !container(ingredients, ingredient) && !*cigaretteIsReady {
		ingredients = append(ingredients, ingredient)
		print("collects: " + ingredient + "\n")
		print("Smokes a cigarette ... 🚬  💨 \n")
		ingredients = []string{}
		*cigaretteIsReady = true
	}
	wg.Done()
}
func main() {
	var ingredients []string
	var wg = sync.WaitGroup{}
	var mutex = sync.Mutex{}

	for i := 0; i < 3; i++ {
		cigaretteIsReady := false
		mutex.Lock()
		wg.Add(1)
		go func() {
			ingredients = getRandomIngredients()
			wg.Done()
		}()
		wg.Wait()
		wg.Add(3)
		go smoker("paper", ingredients, &wg, &cigaretteIsReady)
		go smoker("tobacco", ingredients, &wg, &cigaretteIsReady)
		go smoker("matches", ingredients, &wg, &cigaretteIsReady)
		wg.Wait()

		mutex.Unlock()
	}
}
