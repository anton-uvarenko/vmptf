package main

import (
	"encoding/json"
	"net/http"
)

func main() {
	server := http.Server{
		Addr: ":8080",
	}

	http.HandleFunc("/data", func(w http.ResponseWriter, r *http.Request) {
		type Data struct {
			Name  string
			Age   int
			Hobby string
		}
		data := Data{
			Name:  "Anton",
			Age:   19,
			Hobby: "play chess",
		}

		w.Header().Set("Access-Control-Allow-Origin", "*")
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(data)
	})

	server.ListenAndServe()
}
