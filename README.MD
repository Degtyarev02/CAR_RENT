## Сервис по аренде эксклюзивных автомобилей CARRENT

### Описание

Серверная часть построена с исполдьзованием Java Spring и POSTGRESQL. Взаимодействие с базой данных происходит при
помощи JPA. База данных основана на классах из пакета Entity. JPA строит таблицы на основе полей классов, название
таблиц (если не указано, берется из названия классов).

    Документация JavaDoc для классов хранится по пути CAR_RENT/target/site/apidocs/com/example/CAR_RENT

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

В приложении настроен Scheduled сервис, который каждые 15 проверяет статус аренд на их актуальность

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

Ниже приведены скриншоты клиентской части

![Login page](https://s1.hostingkartinok.com/uploads/images/2022/03/b49bb37b0436b7018764722c959eec83.png)
![Main page](https://s1.hostingkartinok.com/uploads/images/2022/03/3e13d4fd905488078aa597db3115f8d3.png)
![Main page](https://s1.hostingkartinok.com/uploads/images/2022/03/7eca2167e63988ccb47bde3717215f07.png)
![Car page](https://s1.hostingkartinok.com/uploads/images/2022/03/29cdc79e7c40b112fa4dc3d7d9930d80.png)
![Profile page](https://s1.hostingkartinok.com/uploads/images/2022/03/29cdc79e7c40b112fa4dc3d7d9930d80.png)
