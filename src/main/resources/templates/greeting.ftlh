<#import "parts/main.ftlh" as m>
<@m.head_part>
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
                                    <button>Посмотреть</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>

    <div class="cards-container">
        <div class="car-container" id="sc">
            <div class="car-label sportcar-label">
                <span>Современные спорткары</span>
            </div>
            <div class="sportcar-cards cards">
                <#list sportcar as sc>
                    <div class="card">
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
                                    <button class="sportcar-button">Посмотреть</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
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
                                    <button class="supercar-button">Посмотреть</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
    <script>
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