<#macro head_part>
    <!doctype html>
    <html lang="en">
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="/static/css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Rubik:ital,wght@0,400;1,300&display=swap" rel="stylesheet">
        <title>CarRent</title>
    </head>
    <body>
    <#nested>
    <script>
        $('a[href*="#"]').click(function(event){
            $('html, body').animate({
                scrollTop: $( $.attr(this, 'href') ).offset().top
            }, 500);
            event.preventDefault();
        });
    </script>
    </body>
    </html>
</#macro>