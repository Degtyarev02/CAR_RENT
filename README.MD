## Сервис по аренде эксклюзивных автомобилей DiamondRent

### Описание

Архитектуру приложения можно разделить на две части: серверную и клиентскую. Серверная часть, в основе которой лежит
Spring Boot. Серверная часть связана с сервером Tomcat и общается с базой данных postgres. Взаимодействие с базой данных
происходит при помощи JPA. База данных основана на классах из пакета Entity. JPA строит таблицы на основе полей классов,
название таблиц (если не указано, берется из названия классов). Серверная часть содержит основные сущности, сервисы,
методы интерфейса Jpa, для общения с базой данных и контроллеры, для общения с клиентской частью. Клиентская часть
написана на языке разметки freemarker и состоит из отдельных страниц, общих частей и ресурсов (
картинки, js-код, css файлы). Клиентская часть связана с серверной частью HTTPClient, который посылает запросы и
принимает ответы от сервера.

    Документация JavaDoc для классов хранится по пути CAR_RENT/target/site/apidocs/com/example/CAR_RENT

    Скрипты для sql по пути src/main/resources/sql_dumps/*

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

### Навигация по сервису

Для того, чтобы начать пользоваться сервисом, необходимо создать учетную запись. На странице регистрации, введите имя
пользователя, электронную почту и пароль. Если все данные введены верно, то Вас перенаправит на страницу логина.
Перейдите на ранее указанную почту и найдите письмо от сервиса. Перейдите по ссылке внутри, чтобы активировать аккаунт.
Далее, на странице авторизации, введите свои учетные данные и перейдите на главную страницу. Ознакомьтесь с
представленными моделями и перейдите в профиль, чтобы пополнить баланс. Теперь вы можете арендовать выбранный
автомобиль. После успешной оплаты, начнется аренда длительностью в 60 мин. Отслеживать текущие аренды Вы сможете на
странице профиля. Аренда автоматически завершится спустя час.


