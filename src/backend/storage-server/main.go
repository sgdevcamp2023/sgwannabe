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
	"strconv"
	"strings"

	"github.com/joho/godotenv"
	"go.uber.org/zap"
)

func main() {
	loadEnvironments()
	configureLogger()

	logger.Info("starting Web Server")

	registerHandlers()
	listenServe()
}

func loadEnvironments() {
	godotenv.Load(".env")
}

type ReleaseType int

const (
	DEVELOP ReleaseType = iota
	RELEASE
	PRODUCTION
)

var releaseLookup = map[string]ReleaseType{
	"":           DEVELOP,
	"develop":    DEVELOP,
	"release":    RELEASE,
	"production": PRODUCTION,
}

var logger *zap.Logger

func configureLogger() {
	environment := releaseLookup[os.Getenv("ENV")]
	switch environment {
	case DEVELOP:
		logger = zap.Must(zap.NewDevelopment())
		break
	case RELEASE:
		logger = zap.Must(zap.NewDevelopment())
		break
	case PRODUCTION:
		logger = zap.Must(zap.NewProduction())
	}

	defer logger.Sync()
}

func listenServe() {
	port := ":" + os.Getenv("PORT")
	err := http.ListenAndServe(port, nil)
	if err != nil {
		log.Fatalln(err)
	}
	logger.Info("The server is listening on http://localhost:" + port)
}

func registerHandlers() {
	http.HandleFunc("/", handleMusicStaticFiles)
	http.HandleFunc("/upload", handleUploadMusic)
}

func handleMusicStaticFiles(w http.ResponseWriter, r *http.Request) {
	switch r.Method {
	case http.MethodGet:
		urlEncodedResourceName := strings.TrimPrefix(r.URL.Path, "/")
		resourceName, err := url.QueryUnescape(urlEncodedResourceName)
		if err != nil {
			logger.Error(err.Error())
			w.WriteHeader(http.StatusNotFound)
			return
		}

		file, err := os.ReadFile("./resources/" + resourceName)
		if err != nil {
			logger.Error(err.Error())
			w.WriteHeader(http.StatusNotFound)
			return
		}

		mimeType := http.DetectContentType(file)

		w.Header().Set("Access-Control-Allow-Origin", "*")
		w.Header().Set("Content-Type", mimeType)

		etag := fmt.Sprintf("%x", md5.Sum([]byte(resourceName)))
		w.Header().Set("Cache-Control", "public, max-age=600")
		w.Header().Set("ETag", etag)

		if match := w.Header().Get("If-None-Match"); match != "" {
			if strings.Contains(match, etag) {
				w.WriteHeader(http.StatusNotModified)
				logger.Info("access hit file : " + resourceName)
				break
			}
		}

		audioChunks := bytes.NewBuffer(file)
		if _, err := audioChunks.WriteTo(w); err != nil {
			logger.Warn(err.Error())
		}
		w.Header().Set("Content-Length", strconv.Itoa(len(audioChunks.Bytes())))

		logger.Info("access miss file : " + resourceName)
		break
	}
}

func handleUploadMusic(w http.ResponseWriter, r *http.Request) {
	switch r.Method {
	case http.MethodPost:
		logger.Info("File Upload Endpoint Hit")

		// Limit to 10 MB
		r.ParseMultipartForm(10 << 20)

		file, handler, err := r.FormFile("upload")
		if err != nil {
			logger.Error(err.Error())
			return
		}
		defer file.Close()

		logger.Sugar().Infof("Uploaded File: %+v", handler.Filename)
		logger.Sugar().Infof("File Size: %+v", handler.Size)
		logger.Sugar().Infof("MIME Header: %+v", handler.Header)

		tempFile, err := os.Create(filepath.Join("./resources", handler.Filename))
		if err != nil {
			logger.Warn(err.Error())
		}
		defer tempFile.Close()

		fileBytes, err := io.ReadAll(file)
		if err != nil {
			logger.Warn(err.Error())
		}

		tempFile.Write(fileBytes)
		logger.Info("Successfully Uploaded File")
		break
	}
}
