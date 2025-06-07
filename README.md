## The project describes an example of Active directory and oauth2, gateway

Where kerbefos is used for ad, the problem is in combining kerberos and Ad, since there is no grand type PASSWORD.

Account describe authorization server, front and service, Gateway back taken from the example https://github.com/spring-projects/spring-authorization-server/tree/main/samples.

Launch launch the docker-compose.yml database with commpand docker compose up mariadb. 

Now you need to configure hosts, put 10.10.0.11 gdev.test, where your ip machine 10.10.0.11

Now copy the config to nginx nginx_example_microservices.conf, for Linux /etc/nginx/site-enabled/ and change it

AFter run with spring boot plugin account, gateway, service-back1.

Front - install angular: npm install -g @angular/cli, install packages with command: npm install, run front with command: ng serve --host 0.0.0.0 --disable-host-check



We check, open port 4200 in the browser and click login, then enter test1 and password password, after authorization we should enter and enter messages Message 1, Message 2, Message 3

This is only development configuration For AD cadmin 121407Banan@

For add click on AD button , it will send POST request with /login?AD=true

For user login use can use login: user1 and pass: password