FROM golang:1.21.4 AS builder

ENV GO111MODULE=on \
    CGO_ENABLED=0 \
    GOOS=linux \
    GOARCH=amd64

WORKDIR /build

COPY go.mod go.sum main.go .env ./

RUN go mod download && go mod verify

RUN go build -o main .

WORKDIR /dist

RUN cp /build/main .

RUN chmod a+x .

RUN mkdir resources

FROM scratch

COPY --from=builder /dist/ .

ENTRYPOINT ["/main"]
