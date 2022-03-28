<#import "parts/main.ftlh" as m>
<#import "parts/header.ftlh" as h>
<@m.head_part>
    <@h.nav_bar></@h.nav_bar>
    <section class="admin-container">
        <form action="/admin/" method="get" class="filter-users">
            <input type="text" name="findingName" placeholder="Search by username">
            <button type="submit">Search</button>
        </form>
        <div class="users-container">
            <#list users as us>
                <div class="user">
                    <span>Имя/фамилия: ${us.firstName!"User"} ${us.lastName!"User"}</span>
                    <span>Username: ${us.username}</span>
                    <span>Email: ${us.email}</span>
                    <span>Бюджет: ${us.balance}</span>
                    <span>Роли:
                        <#list us.roles as role>
                            <span>${role}</span>
                        </#list>
                    </span>
                </div>
            <#else>
                <span>No users</span>
            </#list>
        </div>

        <div class="application-main-info">
            <span class="label">Основная статистика за пол года</span>
            <span>Самая популярная машина: <a href="/car/${mostPopularCar.id}">${mostPopularCar.carName}</a></span>
            <span>Количество аренд: ${allApplicationForAHalfOfYear?size}</span>
            <span>Прибыль с аренд: ${allSumForAHalfOfYear}</span>
            <#list cars as car>
                <div style="display: none">
                    <input class="car_name_plot" type="hidden" value="${car.carName}">
                    <input class="car_number_of_rents" type="hidden" value="${car.numberOfRents}">
                </div>
            </#list>
            <canvas id="myChart" style="width:100%; max-width:600px; margin-top: 50px"></canvas>
        </div>
    </section>

    <script>

        let carName = document.getElementsByClassName("car_name_plot");
        let rentNumbers = document.getElementsByClassName("car_number_of_rents");
        let xValues = [];
        let yValues = [];

        Array.prototype.forEach.call(carName, car => xValues.push(car.getAttribute("value")))
        Array.prototype.forEach.call(rentNumbers, num => yValues.push(num.getAttribute("value")))
        let barColors = "#000444";

        new Chart("myChart", {
            type: "bar",
            data: {
                labels: xValues,
                datasets: [{
                    backgroundColor: barColors,
                    data: yValues
                }]
            },
            options: {
                responsive: true,
                barSpacing: 2,
                legend: {display: false},
                title: {
                    display: true,
                    text: "График аренд автомобилей"
                },
                scales: {
                    yAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: "Количество аренд"
                        },
                        display: true,
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                },
            }
        });
    </script>
</@m.head_part>