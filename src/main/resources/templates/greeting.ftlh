<#import "parts/main.ftlh" as m>
<#import "parts/header.ftlh" as h>
<#import "parts/footer.ftlh" as f>
<@m.head_part>

    <@h.nav_bar></@h.nav_bar>

    <div class="greeting-container">
        <div class="greeting-label">
            <span>Сложнейший выбор сделает лишь сильнейший</span>
        </div>
        <div class="selector">
            <a href="#am" class="">Американская классика</a>
            <span>|</span>
            <a href="#sc" class="">Спорткары</a>
            <span>|</span>
            <a href="#super" class="">Экзотические суперкары</a>
        </div>
    </div>

    <div class="cards-container">
        <div class="car-container" id="am">
            <div class="car-label">
                <span>Американская классика</span>
            </div>
            <div class="american-muscle-cards cards">
                <#list american_muscle as am>
                    <div class="card">
                        <div class="content">
                            <div class="front">
                                <img src="/static/img/${am.iconName}.jpg" alt="" width="320" height="195">
                                <div class="child-card">
                                    <span class="car-name">${am.carName}</span>
                                    <span class="price">${am.priceForHour} руб/час</span>
                                </div>
                            </div>
                            <div class="back">
                                <div class="child-card">
                            <span class="description">
                                ${am.description}
                            </span>
                                    <a href="/car/${am.id}" class="button">Посмотреть</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
        <div class="main-description">
            <span class="main-description">
                Всё остальное, что появилось в 70-х, устарело, а классические масл-кары только похорошели. С каждым годом их таинственное обаяние лишь усиливается - как у хорошего вина. Заберитесь в эту винтажную машину, оцените самоуверенность и техничность, которая появляется только со временем, и вам больше никогда не захочется тратить время на свежую модель.
            </span>
        </div>
    </div>

    <div class="cards-container sport-container">
        <div class="car-container" id="sc">
            <div class="car-label sportcar-label">
                <span>Современные спорткары</span>
            </div>
            <div class="sportcar-cards cards">
                <#list sportcar as sc>
                    <div class="card sport-card">
                        <div class="content">
                            <div class="sportcar-front front">
                                <img src="/static/img/${sc.iconName}.jpg" alt="" width="320" height="195">
                                <div class="child-card">
                                    <span class="car-name">${sc.carName}</span>
                                    <span class="price">${sc.priceForHour} руб/час</span>
                                </div>
                            </div>
                            <div class="sportcar-back back">
                                <div class="child-card">
                            <span class="description">
                                ${sc.description}
                            </span>
                                    <a href="/car/${sc.id}" class="sportcar-button button">Посмотреть</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
        <div class="main-description">
            Современные спорткары быстры, удобны, нафаршированы страхующей электроникой по самую крышу и не пытаются вас
            убить при любом удобном случае. Это классно и… скучно. По крайней мере, для особой прослойки петролхедов.
            Они выбирают абсолютное вовлечение в процесс управления, чистый драйв и ослепительно яркие эмоции за
            штурвалом полугоночных монстров для дорог общего пользования.
        </div>
    </div>

    <div class="cards-container">
        <div class="car-container" id="super">
            <div class="car-label supercar-label">
                <span>Экзотические суперкары</span>
            </div>
            <div class="supercar-cards cards">
                <#list supercar as super>
                    <div class="card">
                        <div class="content">
                            <div class="supercar-front front">
                                <img src="/static/img/${super.iconName}.jpg" alt="" width="320" height="195">
                                <div class="child-card">
                                    <span class="car-name">${super.carName}</span>
                                    <span class="price">${super.priceForHour} руб/час</span>
                                </div>
                            </div>
                            <div class="supercar-back back">
                                <div class="child-card">
                            <span class="description">
                                ${super.description}
                            </span>
                                    <a href="/car/${super.id}" class="supercar-button button">Посмотреть</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
        <div class="main-description">
            Супер дорогие, а также супер редкие и очень дорогие автомобили привлекают внимание всех без исключения
            граждан, которым нравятся вообще автомобили. Сегодня мы предлагаем Вам познакомиться с "королями
            автоспринта" и с невероятно дорогими монстрами скорости. Мы специально отобрали для вас самых быстрых редких
            и самых дорогих суперкаров.
        </div>
    </div>

    <@f.footer></@f.footer>
    <script>

        //Скрипт для вращения карточек
        let content = document.getElementsByClassName('content')
        console.log(content)

        let flag = [];
        for (let i = 0; i < content.length; i++) {
            flag.push(false)
        }
        for (let i = 0; i < content.length; i++) {
            content[i].onclick = function () {
                if (!flag[i]) {
                    content[i].style.transform = 'rotateY( 180deg )'
                    content[i].style.transition = 'transform 1s'
                    flag[i] = true
                } else {
                    content[i].style.transform = 'rotateY( 0deg )'
                    flag[i] = false
                }
            }
        }
    </script>
</@m.head_part>