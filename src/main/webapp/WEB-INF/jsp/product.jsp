<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:requestEncoding value="UTF-8"/>
<fmt:bundle basename="i18n">
    <fmt:message key="common.product.name" var="name"/>
    <fmt:message key="common.product.type" var="type"/>
    <fmt:message key="common.product.price" var="price"/>
    <fmt:message key="common.product.description" var="description"/>
    <fmt:message key="common.delete" var="delete"/>
    <fmt:message key="product.productId" var="id"/>
    <fmt:message key="product.newProduct" var="add"/>
    <fmt:message key="product.addToCart" var="add_to_cart"/>
    <fmt:message key="product.view" var="view"/>
    <fmt:message key="common.edit" var="edit"/>
    <fmt:message key="common.delete" var="delete"/>
    <fmt:message key="product.inStock" var="in_stock"/>
    <fmt:message key="error.needLogin" var="need_login"/>
    <fmt:message key="error.productNotAvailable" var="not_available"/>

</fmt:bundle>

<%--@elvariable id="types" type="java.util.List"--%>
<%--@elvariable id="products" type="java.util.List"--%>
<%--@elvariable id="types" type="src.main.java.com.epam.rodion.musicstore.entity.ProductType"--%>
<%--@elvariable id="item" type="src.main.java.com.epam.rodion.musicstore.entity.StorageItem"--%>
<%--@elvariable id="product" type="src.main.java.com.epam.rodion.musicstore.entity.Product"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>
<%--@elvariable id="items" type="java.util.List"--%>


<c:set var="localeCode" value="${pageContext.response.locale}"/>
<div class="container-fluid">

    <div class="row">
        <div class="col-12 p-0">
            <my:navbar pagetitle="Music Planet"/>
        </div>
    </div>

    <div class="row">
        <div class="col-12 p-0" style="height: 40px">
        </div>
    </div>


    <div class="row">
        <div class="col-2" style="padding: 0px;">
            <my:sidebar/>
        </div>

        <div class="col-7" style="padding: 0px">
            <div class="row" style="height: 415px">
                <div class="col-6" style="padding: 0px" >
                    <img class="" style="position: absolute; top: 0; right: 0; bottom: 0; left: 0;
                     margin: auto; max-height: 415px; max-width: 322px; margin-top: 15px"
                         src="<c:url value="/img/${item.product.id}"/>">
                </div>
                <div class="col-6">
                    <p class="font-weight-bold" style="font-size: 23px; margin-right: 17px;
                                        margin-left: 22px; margin-top: 20px;">
                        ${item.product.name}
                    </p>
                    <p class="font-weight-bold font-italic" style="font-size: 21px; margin-right: 17px;
                                        color: #F16143; margin-left: 22px;">
                        ₸ ${item.product.price.amount.intValue()}
                    </p>
                    <p class=" text-secondary " style="font-size: 18px; margin-right: 17px;
                                        ; margin-left: 22px">
                        ${in_stock} ${item.amount}
                    </p>

                    <div class="row justify-content-start">
                        <div class="col-12" style="padding-left: 36px; padding-top: 80px">
                            <c:if test="${(loggedUser.role.name.equals('user')
                                        || loggedUser.role.name.equals('admin'))}">
                                <c:if test="${!(itemAmountError.equals('true'))}">
                                    <form action="<c:url value="/do/addToCart"/>" method="post" style="margin: 0px">
                                        <input type="hidden" name="product" value="${item.product.id}">
                                        <input type="hidden" name="amount" value="1">
                                        <button class="btn  btn-dark btn-sm" value="submit" type="submit"
                                                style="background-color: #F16143; border-color: #F16143;
                                                    color: #212529;">
                                            <span class="text-dark font-italic"
                                                  style="font-size: 18px; margin-top: 10px">${add_to_cart}</span>
                                        </button>
                                    </form>
                                </c:if>

                                <c:if test="${itemAmountError.equals('true')}">
                                    <p class="text-danger">${not_available}</p>
                                </c:if>
                            </c:if>
                            <c:if test="${!((loggedUser.role.name.equals('user')
                                        || loggedUser.role.name.equals('admin')))}">
                                <p class="text-secondary" style=" margin-top: 10px">${need_login}</p>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>


            <div class="row" style="padding-right: 40px; padding-top: 40px">
                <div class="col">

                    <c:if test="${pageContext.response.locale.toString().equals('ru_RU') ||
                    pageContext.response.locale.toString().equals('ru')}">
                        <p class="text-dark" style="font-size: 14px; margin:1px">${item.product.ruDescription}</p>
                    </c:if>
                    <c:if test="${pageContext.response.locale.toString().equals('en_EN') ||
                    pageContext.response.locale.toString().equals('en')}">
                        <p style="white-space: pre-wrap">
                                ${item.product.enDescription}
                        </p>
                    </c:if>

                </div>
            </div>
        </div>

        <div class="col-3">


            <c:forEach items="${items}" var="item">
                <div class="row">
                    <div class="col-md-10">

                        <div class="card mb-3 box-shadow" style="border-color: #343A40 ; border-width: 2px">
                            <div class="row justify-content-center" style="height: 255px">
                                <a href="<c:url value="/do/product?id=${item.product.id}"/>"><img
                                        style="max-height: 240px; max-width: 240px; margin-top: 15px"
                                        src="<c:url value="/img/${item.product.id}"/>"></a>
                            </div>
                            <div class="card-body" style="padding-top: 2px">

                                <div class="row" style="height:75px">
                                    <a href="<c:url value="/do/product?id=${item.product.id}"/>"
                                       class="card-link text-dark">
                                        <p class="card-text" style="margin-right: 17px; margin-left: 17px">
                                                ${item.product.name}
                                        </p>
                                    </a>
                                </div>


                                <div class="row" style="height: 35px">
                                    <a href="<c:url value="/do/product?id=${item.product.id}"/>"
                                       class="card-link text-dark">
                                        <p class="font-weight-bold" style="font-size: 20px; margin-right: 17px;
                                        color: #F16143; margin-left: 22px">
                                            ₸ ${item.product.price.amount.intValue()}
                                        </p>
                                    </a>
                                </div>

                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <a class="btn btn-dark btn-sm"
                                           href="<c:url value="/do/product?id=${item.id}"/>"
                                           role="button">
                                                ${view}
                                        </a>

                                    </div>
                                    <small class="text-muted">${in_stock} ${item.amount}</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>


        </div>
    </div>


    <div class="row justify-content-center">
        <div class="col-12 p-0">
            <my:footer/>
        </div>
    </div>
</div>