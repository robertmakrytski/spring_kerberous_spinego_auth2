## Проект описывает пример Active directory и oauth2

Где для ад используется kerbefos , проблема в совмещение  kerberos и Ad, так как нету grand type PASSWORD.

Аккаунт описывать authorization server, front и сервис, Gateway бэк взяли из примера https://github.com/spring-projects/spring-authorization-server/tree/main/samples. 

Запуск запускаем базу docker-compose.yml, account, gateway, service-back1, front - его запускаем npm install, ng serve

Проверяем, открыаем 4200 порт в браузере и нажимем логин, дальше вводим test1 и пароль password, после авторизации мы должно ввойти и ведить сообщения 	Message 1, 	Message 2, 	Message 3
