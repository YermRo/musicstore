<%@ tag pageEncoding="UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%@attribute name="pagetitle" required="true" %>
<%@attribute name="footer" fragment="true" %>


<%--@elvariable id="loggedUser" type="src.main.java.com.epam.rodion.musicstore.entity.User"--%>
<%--@elvariable id="cart" type="src.main.java.com.epam.rodion.musicstore.entity.Order"--%>
<%--@elvariable id="type" type="src.main.java.com.epam.rodion.musicstore.entity.ProductType"--%>

<fmt:requestEncoding value="UTF-8"/>
<fmt:bundle basename="i18n">

    <fmt:message key="profile.myprofile" var="myprofile"/>
    <fmt:message key="common.products" var="products"/>

    <fmt:message key="logout" var="logout"/>
    <fmt:message key="home" var="home"/>
    <fmt:message key="about" var="about"/>
    <fmt:message key="welcome.myAccount" var="my_account"/>
    <fmt:message key="common.email" var="email_label"/>
    <fmt:message key="common.password" var="password_label"/>
    <fmt:message key="welcome.signIn" var="sign_in"/>
    <fmt:message key="welcome.register" var="registration_label"/>
    <fmt:message key="common.search" var="search_label"/>
    <fmt:message key="common.language" var="language"/>
    <fmt:message key="common.instruments" var="instruments"/>
    <fmt:message key="user.profileSettings" var="profile_settings"/>
    <fmt:message key="user.myOrders" var="my_orders"/>
    <fmt:message key="user.logOut" var="logout"/>
    <fmt:message key="common.signIn" var="sign_in_sign_up"/>
    <fmt:message key="common.management" var="management"/>
    <fmt:message key="common.userManagement" var="user_management"/>
    <fmt:message key="common.storageManagement" var="storage_management"/>
    <fmt:message key="common.orderManagement" var="order_management"/>
    <fmt:message key="common.productManagement" var="product_management"/>

</fmt:bundle>


<c:url value="/do/locale?locale=en" var="en_locale_url"/>
<c:url value="/do/locale?locale=ru" var="ru_locale_url"/>
<c:url var="login_url" value="/do/login"/>
<c:url var="register_url" value="/do/register"/>
<c:url var="search_url" value="/do/search"/>
<c:url var="switch_lang_url" value="/do/switchlang"/>
<c:url var="catalog" value="/do/catalog"/>


<head>
    <title>${pagetitle}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="/css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="SHORTCUT ICON" href="/images/forPageBody/icon.png" type="image/png">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css"
          integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
</head>

<body>
<nav class="navbar navbar-expand-lg   navbar-dark bg-dark py-0">
    <a class="navbar-brand" href="<c:url value="/do/home"/>">
        <img src="<c:url value="/images/forPageBody/logo.png"/>" height="60px">
    </a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label=Navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">

            <li class="nav-item active">
                <a class="nav-link" href="<c:url value="/do/home"/>">${home}</a>
            </li>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> ${instruments}
                </a>
                <div class="dropdown-menu" aria-labelledby="Categories">
                    <c:forEach items="${productTypes}" var="type">
                        <a class="dropdown-item"
                           href="<c:url value="/do/catalog?type=${type.id}"/>">${type.getName(pageContext.response.locale)}</a>
                    </c:forEach>
                </div>
            </li>




            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <c:if test="${!(loggedUser.role.name == 'user'  || loggedUser.role.name == 'admin')}">
                        ${sign_in_sign_up}
                    </c:if>
                    <c:if test="${loggedUser.role.name == 'user' || loggedUser.role.name == 'admin'}">
                        ${my_account}
                    </c:if>
                </a>
                <div class="dropdown-menu">
                    <c:if test="${!(loggedUser.role.name == 'user'  || loggedUser.role.name == 'admin')}">
                        <form class="px-4 py-3" role="form" action="${login_url}" method="post">
                            <div class="form-group">
                                <label for="exampleDropdownFormEmail1">${email_label}</label>
                                <input type="text" name="email" class="form-control" id="exampleDropdownFormEmail1"
                                       placeholder="email@example.com">
                            </div>
                            <div class="form-group">
                                <label for="exampleDropdownFormPassword1">${password_label}</label>
                                <input type="password" name="password" class="form-control"
                                       id="exampleDropdownFormPassword1"
                                       placeholder="••••">
                            </div>
                            <button type="submit" class="btn btn-primary">${sign_in}</button>
                        </form>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="<c:url value="/do/register"/>">${registration_label}</a>
                    </c:if>

                    <c:if test="${loggedUser.role.name == 'user' || loggedUser.role.name == 'admin'}">
                        <a class="dropdown-item" href="<c:url value="/do/edit/userData"/>">${profile_settings}</a>
                        <a class="dropdown-item" href="<c:url value="/do/logout"/>">${logout}</a>
                    </c:if>


                </div>
            </li>
            <c:if test="${loggedUser.role.name == 'user'}">
                <li class="nav-item ">
                    <a class="nav-link" href="<c:url value="/do/myOrders"/>">${my_orders}</a>
                </li>
            </c:if>

            <c:if test="${loggedUser.role.name == 'admin'}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ${management}
                    </a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="<c:url value="/do/manage/users"/>">${user_management}</a>
                        <a class="dropdown-item" href="<c:url value="/do/manage/products"/>">${product_management}</a>
                        <a class="dropdown-item" href="<c:url value="/do/manage/orders"/>">${order_management}</a>
                    </div>
                </li>
            </c:if>

            <c:if test="${loggedUser.role.name == 'user' || loggedUser.role.name == 'admin'}">
                <li class="nav-item active">
                    <a class="nav-link" href="<c:url value="/do/cart"/>" style="padding-left: 50px">
                        <fmt:formatNumber var="formattedProductPrice" type="currency" currencyCode="KZT"
                                          maxFractionDigits="0"
                                          value="${loggedUser.cash.amount}"/>

                        <span style="color: #F16143; font-size: 18px;">
                                ${formattedProductPrice}
                        </span>
                    </a>

                </li>

                <li class="nav-item active">
                    <a class="nav-link" href="<c:url value="/do/cart"/>" style="padding-left: 50px">
                        <img
                                style="height: 28px"
                                src="/images/forPageBody/cart.png">
                        <span style="color: #F16143; font-size: 18px;">
                                ${cart.orderingItems.size()}
                        </span>
                    </a>

                </li>
            </c:if>

        </ul>

        <form class="form-inline my-2 ,y-lg-0" role="form" action="${switch_lang_url}" method="post"
        style="margin-right: 100px">
            <div class="collapse navbar-collapse my-2 ,y-lg-0" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> ${language}
                        </a>
                        <div class="dropdown-menu" aria-labelledby="Languages">
                            <a class="dropdown-item language" href="<c:url value="/do/locale?locale=en"/>">
                                <img src="/images/forPageBody/usa.png"
                                     height="25px" align="left">&nbspEnglish</a>
                            <a class="dropdown-item language" href="<c:url value="/do/locale?locale=ru"/>">
                                <img src="/images/forPageBody/rus.png"
                                     height="25px" align="left"> &nbspРусский</a>
                        </div>
                    </li>
                </ul>
            </div>
        </form>

        <c:if test="${loggedUser.role.name.equals('user') || loggedUser.role.name.equals('admin')}">
        <div class="nav-item active">
            <a class="nav-link" href="<c:url value="/do/logout"/>" style="padding-left: 50px">
                <img
                        style="height: 28px"
                        src="/images/forPageBody/logout.png">

            </a>
        </div>
        </c:if>
    </div>
</nav>


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