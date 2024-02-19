# 차트 서버
차트 서버는 음원 순위를 관리합니다.  
음원 순위를 집계하고 매 정각의 음원 순위를 보관합니다.  
사용자에게 가장 최근에 집계한 음원 순위를 보여줍니다.

## 실행 방법

### 로컬 실행
```bash
$ ./gradlew build
$ docker-compose -f docker-compose.yml down && docker-compose -f docker-compose.yml up -d 
$ java -jar chart-api/build/libs/XXX.jar
```

### 도커 실행

```bash
$ docker-compose down && docker-compose up -d
```
