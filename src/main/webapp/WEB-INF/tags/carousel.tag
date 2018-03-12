<%@ tag pageEncoding="UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:bundle basename="i18n">


    <fmt:message key="firstCarouselHeader" var="first_header"/>
    <fmt:message key="secondCarouselHeader" var="second_header"/>
    <fmt:message key="thirdCarouselHeader" var="third_header"/>
    <fmt:message key="fourthCarouselHeader" var="fourth_header"/>
    <fmt:message key="fifthCarouselHeader" var="fifth_header"/>
    <fmt:message key="firstCarouselMsg" var="first_msg"/>
    <fmt:message key="secondCarouselMsg" var="second_msg"/>
    <fmt:message key="thirdCarouselMsg" var="third_msg"/>
    <fmt:message key="fourthCarouselMsg" var="fourth_msg"/>
    <fmt:message key="fifthCarouselMsg" var="fifth_msg"/>
</fmt:bundle>


<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="">
    <link rel="SHORTCUT ICON" href="/images/forPageBody/icon.png" type="image/png">
    <title></title>
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700&amp;subset=cyrillic" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="https://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">


</head>

<body>

            <div class="container-fluid p-0">
                <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="4"></li>
                    </ol>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img class="d-block w-100" src="/images/forPageBody/0.jpg" alt="First slide">
                            <div class="carousel-caption d-none d-md-block">
                                <h3>${first_header}</h3>
                                <p>${first_msg}</p>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img class="d-block w-100" src="/images/forPageBody/1.jpg" alt="Second slide">
                            <div class="carousel-caption d-none d-md-block">
                                <h3>${second_header}</h3>
                                <p><h5>${second_msg}</h5></p>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img class="d-block w-100" src="/images/forPageBody/2.jpg" alt="Third slide">
                            <div class="carousel-caption d-none d-md-block">
                                <h3>${third_header}</h3>
                                <p>${third_msg}</p>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img class="d-block w-100" src="/images/forPageBody/3.jpg" alt="Third slide">
                            <div class="carousel-caption d-none d-md-block">
                                <h3>${fourth_header}</h3>
                                <p>${fourth_msg}</p>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img class="d-block w-100" src="/images/forPageBody/4.jpg" alt="Third slide">
                            <div class="carousel-caption d-none d-md-block">
                                <h3>${fifth_header}</h3>
                                <p>${fifth_msg}</p>
                            </div>
                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"
        integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4"
        crossorigin="anonymous"></script>

</body>