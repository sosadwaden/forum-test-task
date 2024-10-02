## Forum test task
Тестовое задание для Greenatom, 2024 г.
## Техническое задание
Требуется создать простейший движок форума/доски объявлений (только backend). 
Суть задачи: 
Есть топики (темы), в каждом топике может быть одно или более сообщений. 
Движок должен обеспечивать хранение в БД (IMDB) и CRUD операции с топиками (темами) и сообщениями в топиках.
Топик должен содержать заголовок (название темы). Топик не может быть пустым, т.е. должен содержать как минимум одно сообщение.
Сообщение должно содержать имя (ник) автора, текст сообщения, дату создания. 
Сообщение обязательно должно относиться к одному из топиков.
Необходимо реализовать клиентский REST-API позволяющий пользователю:
1.   Получать список топиков </br>
2.   Получать сообщения в указанном топике </br>
3.   Создать топик (с первым сообщением в нем) </br>
4.   Создать сообщение в указанном топике </br>
5.   Отредактировать свое сообщение </br>
6.   Удалить свое сообщение </br>

### Требования

Язык – Java (Spring или Spring Boot - по желанию) </br>
Автоматизация сборки – Maven (Gradle) </br>
Хранилище – in-memory DB (скрипт по наполнению тестовыми данными приветствуется) **P.S. Была использована H2 Database** </br> 

### Задания на дополнительный бал
+ Реализовать пагинацию для топиков и сообщений </br>
+ Реализовать аутентификацию пользователей (т.е. для доступа к сервису необходимо сперва залогиниться) </br>
+ Реализовать REST-API администратора. Администратор может редактировать и удалять любые сообщения и топики. </br>

## Как работает API
1. Регистрация и аутентификация (/api/auth) производится при помощи JWT (Bearer токена). При помощи к нему даётся доступ к ресурсам /api/v1/
2. Пользователь с ролью user имеет доступ только к клиентскому API, для пользователей с ролью админа ещё дополнительно и API админов.
3. Если пользователь не аутентифицирован, то доступа к ресурсам /api/v1 он не имеет.
## Документация
Документация для API доступна по ссылке http://localhost:7070/swagger-ui/index.html
![api_doc](https://github.com/sosadwaden/forum-test-task/assets/106944660/788deef1-49da-45e6-b07d-feb069f82371)
