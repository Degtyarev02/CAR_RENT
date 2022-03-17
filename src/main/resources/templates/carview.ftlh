<#import "parts/main.ftlh" as m>
<#import "parts/header.ftlh" as h>
<@m.head_part>
    <@h.nav_bar></@h.nav_bar>

    <section class="main-car-container">
        <div class="main-car-block">
            <div class="car-pic">
                <img src="/static/img/${car.iconName}.jpg" alt="">
            </div>

            <div class="info-block">
                <span>${car.carName}</span>
                <span>${car.priceForHour} руб/час</span>
                <div class="buttons-container">
                    <button>Арендовать</button>
                    <button>Оставить отзыв</button>
                </div>
            </div>
        </div>
    </section>

    <section class="review-and-description">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home" type="button"
                        role="tab" aria-controls="home" aria-selected="true">Описание
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile" type="button"
                        role="tab" aria-controls="profile" aria-selected="false">Отзывы
                </button>
            </li>
        </ul>

        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
            <span class="description">
                ${car.description}
            </span>
            </div>

            <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                <section class="reviews-container">
                    <div class="reviews">
                        <div class="slider-reviews">
                            <div class="swiper-wrapper">
                                <#list car.reviews as review>
                                    <div class="swiper-slide">
                                        <div class="rev_header">
                                            <img class="avatar" src="/static/img/bird.png" alt="">
                                            <div class="rev_name">${review.author.firstName} ${review.author.lastName}</div>
                                        </div>
                                        <div class="rev_footer">
                                            <p>${review.text}</p>
                                        </div>
                                    </div>
                                <#else>
                                    <span>Пока что нет отзывов :(</span>
                                </#list>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>

    </section>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
    <script>
        jQuery(function () {
            let j = jQuery;
            let addInput = '#qty';
            let n = 1;
            let startValue = 1050;
            document.getElementById("price").textContent = startValue + " ₽";
            j(addInput).val(n + ' шт.');

            j('.plus').on('click', function () {
                j(addInput).val(++n + ' шт.');
                document.getElementById("price").textContent = startValue * n + " ₽";
            })

            j('.min').on('click', function () {
                if (n > 0) {
                    j(addInput).val(--n + ' шт.');
                    document.getElementById("price").textContent = startValue * n + " ₽";
                }
            });
        });


        let swiperSlider = new Swiper(".slider-reviews", {
            slidesPerView: 2,
            spaceBetween: 16,
        });
    </script>
</@m.head_part>