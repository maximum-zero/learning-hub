## 🐳 3-1. Docker 실습: Nginx 웹 서버 및 DB 컨테이너 구축

이 문서는 Notion **[3-1. Docker 실습: Nginx 웹 서버 및 DB 컨테이너 구축](https://www.notion.so/3-1-Docker-Nginx-DB-2c255588581980688682f59db6d4e5c3)** 섹션의 핵심 명령어를 정리합니다.

### 1. Dockerfile을 이용한 커스텀 Nginx 웹 서버 이미지 생성

```bash
# Nginx 이미지 다운로드
docker pull nginx:1.25.0-alpine

# Docker 이미지 빌드
docker build -t myweb:1.0 .

# 생성한 Docker 이미지로 컨테이너 실행 (8081 포트)
docker run -d -p 8001:80  --name=webserver1 myweb:1.0

# 실행 확인
curl localhost:8001

```

### 2. MariaDB 이미지를 이용한 Docker 컨테이너 실행

```bash
# MariaDB 컨테이너 실행 및 포트 바인딩 (이미지가 없으면 자동으로 pull 함)
docker run --name mariadb -e MYSQL_ROOT_PASSWORD=pass123# -d \
-e MARIADB_DATABASE=item -p 3306:3306 mariadb:10.2

# 컨테이너 내부 셸(Shell)로 접근
docker exec -it mariadb bash

# MariaDB 접속 및 인증
mysql -u root -p

# Table 생성 및 Data 삽입
use item;
CREATE TABLE Projectes (id int(11) NOT NULL, name varchar(255) DEFAULT NULL, code varchar(255) DEFAULT NULL, PRIMARY KEY (id));
INSERT INTO Projectes (id, name, code) VALUES (1, 'DevOps', 'D0180');

# 외부 도구(DBeaver 등)에서 접속하기 위해 필요한 Container IP 주소 확인
docker inspect mariadb | grep -i IPAddress

```
