## Сервис по аренде эксклюзивных автомобилей CARRENT

### Описание

Серверная часть построена с исполдьзованием Java Spring и POSTGRESQL. Взаимодействие с базой данных происходит при
помощи JPA. База данных основана на классах из пакета Entity. JPA строит таблицы на основе полей классов, название
таблиц (если не указано, берется из названия классов).

    Так же, при регистрации, на указанную почту придет ссылка, для активации аккаунта, после перехода по ней, вы сможете войти
    под своим username.

Взаимодействие с базой данных строится на методах из интерфейсов в пакете "repos"

Пример интерфейса для взаимодействия с заявками аренды

```java
public interface ApplicationRepo extends JpaRepository<Application, Long> {

    List<Application> findAllByClient(User user);

    List<Application> findAllByActive(boolean active);

    Application findAllByClientAndActive(User user, boolean active);

    Application findAllByCarAndActive(Car car, boolean active);
}

```

Основные таблицы приведены ниже

```postgresql
create table application
(
    id          bigint  not null
        primary key,
    active      boolean not null,
    total_price integer,
    car_id      bigint
        constraint fkst8xgdau7igvp6og3ure9vwru
            references car,
    client_id   bigint
        constraint fkio87oqddw52ei5k2tm4dv57qt
            references main_user,
    end_time    timestamp,
    start_time  timestamp
);

alter table application
    owner to postgres;

create table car
(
    id          bigint  not null
        primary key,
    car_name    varchar(255),
    category    text,
    description text,
    in_rent     boolean not null,
    price       integer,
    icon_name   varchar(255)
);

alter table car
    owner to postgres;

create table main_user
(
    id             bigint  not null
        primary key,
    password       varchar(255),
    first_name     varchar(255),
    last_name      varchar(255),
    active         boolean not null,
    username       varchar(255),
    currentbalance integer
);

create table review
(
    id        bigint not null
        primary key,
    text      varchar(255),
    author_id bigint
        constraint fk4s8hhauku0pqs8yiabvbed216
            references main_user,
    car_id    bigint
        constraint fkoie88l9xdqjv41ym8c1s7valq
            references car
);

create table user_role
(
    user_id bigint not null
        constraint user_fk
            references main_user,
    roles   varchar(255)
);
```

В приложении так же настроен Scheduled сервис, который каждые 15 проверяет статус аренд на их актуальность

```java

@Service
public class ScheduledService {

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    CarRepo carRepo;

    @Scheduled(cron = "0 0/15 * * * *")
    public void checkRent() {
        System.out.println("Scheduled запущен...");
        List<Application> applicationList = applicationRepo.findAllByActive(true);

        for (Application application : applicationList) {
            if (ChronoUnit.MINUTES.between(application.getStartTime(), LocalDateTime.now()) >= 60) {
                application.setActive(false);
                application.getCar().setInRent(false);
                applicationRepo.save(application);
                carRepo.save(application.getCar());
                System.out.println("Application " + application.getId() + " - закрыта");
            }
        }

    }
}
```

Клиентская часть написана на языке разметки freemarker (.ftlh). Страницы адаптированы под компьютеры и телефоны. Дизайн
проектировался с максимальной понятностью и легковесностью.



