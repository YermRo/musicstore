<%@ tag pageEncoding="UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<%@attribute name="footer" fragment="true" %>


<%--@elvariable id="loggedUser" type="src.main.java.com.epam.rodion.musicstore.entity.User"--%>
<%--@elvariable id="type" type="src.main.java.com.epam.rodion.musicstore.entity.ProductType"--%>


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
    <fmt:message key="common.cart" var="cart"/>
    <fmt:message key="common.cart" var="cart"/>
    <fmt:message key="common.address" var="address"/>
    <fmt:message key="home" var="home"/>
    <fmt:message key="user.myOrders" var="my_orders"/>
    <fmt:message key="common.account" var="account"/>
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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="/css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css"
          integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
</head>

<body>

<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark side-bar-border py-0" style="width: 70%;
padding: 0px">


    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label=Navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent" >
        <ul class="navbar-nav mr-auto flex-column">

            <li class="nav-item">
                <div class="row" style="height: 70px;">

                </div>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/do/home"/>" >
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
                    ${home}
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/do/myOrders"/>">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file"><path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path><polyline points="13 2 13 9 20 9"></polyline></svg>
                    ${my_orders}
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/do/cart"/>">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-cart"><circle cx="9" cy="21" r="1"></circle><circle cx="20" cy="21" r="1"></circle><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path></svg>
                    ${cart}
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/do/edit/userData"/>">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
                    ${account}
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/do/edit/userAddress"/>">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-layers"><polygon points="12 2 2 7 12 12 22 7 12 2"></polygon><polyline points="2 17 12 22 22 17"></polyline><polyline points="2 12 12 17 22 12"></polyline></svg>
                    ${address}
                </a>
            </li>

            <li class="nav-item">
                <div class="row" style="height: 70px;">

                </div>
            </li>

        </ul>
    </div>
</nav>


</body>