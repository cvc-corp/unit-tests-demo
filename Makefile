start-stack: package
	docker-compose up --build -d
stop-stack:
	docker-compose down
package:
	mvn package
rebuild-app:
	docker-compose stop -t 1 $(APP)
	mvn clean
	mvn package
	docker-compose build $(APP)
	docker-compose create $(APP)
	docker-compose start $(APP)