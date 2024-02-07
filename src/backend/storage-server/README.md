## 스토리지 서버

음원 파일을 업로드하고 다운로드 받을 수 있는 서버입니다.

## 실행 방법

### 로컬 실행 without Docker

```bash
# 바로 실행
$ go run main.go

# 빌드 실행
$ go build main.go
$ go ./main
```

### 도커 실행

```bash
$ docker-compose down && docker-compose up -d
```
