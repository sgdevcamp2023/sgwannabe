package main

import (
	"bytes"
	"crypto/md5"
	"fmt"
	"io"
	"log"
	"net/http"
	"net/url"
	"os"
	"path/filepath"
	"strings"
	"time"

	"github.com/joho/godotenv"
)

func main() {
    log.Println("starting Web Server")
	loadEnvironments()
    registerHandlers()
	listenServe()
}

func loadEnvironments() {
	godotenv.Load(".env")
}

func listenServe() {
	port := ":" + os.Getenv("PORT")
    err := http.ListenAndServe(port, nil)
    if err != nil {
        log.Fatalln(err)
    }
	log.Printf("The server is listening on http://localhost:%s", port)
}

func registerHandlers() {
	http.HandleFunc("/", handleMusicStaticFiles);
	http.HandleFunc("/upload", handleUploadMusic);
}

func handleMusicStaticFiles(w http.ResponseWriter, r *http.Request) {
	switch r.Method {
	case http.MethodGet:
		urlEncodedResourceName := strings.TrimPrefix(r.URL.Path, "/")
		resourceName, err := url.QueryUnescape(urlEncodedResourceName)
		if err != nil {
			log.Println(err)
			w.WriteHeader(http.StatusNotFound)
			return
		}

		audioFile, err := os.ReadFile("./resources/" + resourceName)
		if err != nil {
			log.Println(err)
			w.WriteHeader(http.StatusNotFound)
			return
		}

		w.Header().Set("Access-Control-Allow-Origin", "*")
		w.Header().Set("Content-Type", "audio/mpeg")
		
		data := []byte(time.Now().String())
		etag := fmt.Sprintf("%x", md5.Sum(data))
		w.Header().Set("Cache-Control", "public, max-age=600")
		w.Header().Set("ETag", etag)

		if match := w.Header().Get("If-None-Match"); match != "" {
			if strings.Contains(match, etag) {
				w.WriteHeader(http.StatusNotModified)
				break
			}
		}
		
		audioChunks := bytes.NewBuffer(audioFile)
		if _, err := audioChunks.WriteTo(w); err != nil {
			log.Println(err)
		}
		break
	}
}

func handleUploadMusic(w http.ResponseWriter, r *http.Request) {
	switch r.Method {
	case http.MethodPost:
		fmt.Println("File Upload Endpoint Hit")

		// Limit to 10 MB
		r.ParseMultipartForm(10 << 20)

		file, handler, err := r.FormFile("upload")
		if err != nil {
			fmt.Println("Error Retrieving the File")
			fmt.Println(err)
			return
		}	
		defer file.Close()

		fmt.Printf("Uploaded File: %+v\n", handler.Filename)
		fmt.Printf("File Size: %+v\n", handler.Size)
		fmt.Printf("MIME Header: %+v\n", handler.Header)

		tempFile, err := os.Create(filepath.Join("./resources", handler.Filename))
		if err != nil {
			fmt.Println(err)
		}
		defer tempFile.Close()
	
		fileBytes, err := io.ReadAll(file)
		if err != nil {
			fmt.Println(err)
		}
		
		tempFile.Write(fileBytes)
		fmt.Fprintf(w, "Successfully Uploaded File\n")
		break
	}
}
