# Инструкция подключения БД и запуска SUT:

1. Склонировать проект из репозитория командой ```git clone ```
1. Открыть склонированный проект в IDEA
1. Для запуска контейнеров с MySql, PostgresSQL, и Node.js в терминале ввести команду ```docker-compose up -d --
   force-recreate```
1. Запуск SUT:
- для MySql в терминале ввести команду:

```java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar```
  
- для PostgreSQL в терминале ввести команду:
  
  ```java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar```

5. Запуск тестов (Allure):

- для запуска на MySql в терминале ввести команду:

  ```gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app allureReport```

- для запуска на PostgreSQL в терминале ввести команду:
  
  ```gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app allureReport```

6. Для получения отчета Allure ввести команду: ```gradlew allureServe```

7. После прогона тестов завершить работу приложения Ctrl+C, остановить контейнеры командой:
```docker-compose down```


