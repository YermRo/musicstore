<?xml version="1.0" encoding="UTF-8"?>

<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>


<fmt:bundle basename="i18n">
    <fmt:message key="common.product.name" var="name"/>
    <fmt:message key="common.product.type" var="type"/>
    <fmt:message key="common.product.price" var="price"/>
    <fmt:message key="common.product.description" var="description"/>
    <fmt:message key="common.delete" var="delete"/>
    <fmt:message key="product.productId" var="id"/>
    <fmt:message key="product.newProduct" var="add"/>
    <fmt:message key="common.edit" var="edit"/>
    <fmt:message key="common.delete" var="delete"/>


</fmt:bundle>


<%--@elvariable id="product" type="src.main.java.com.epam.rodion.musicstore.entity.Product"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>
<%--@elvariable id="loggedUser" type="src.main.java.com.epam.rodion.musicstore.entity.User"--%>


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

    <div class="row justify-content-end">
        <div class="col-4 p-0">
            <a class="btn btn-default"
               href="<c:url value="/do/manage/addProduct"/>"
            >${add}</a>
        </div>
    </div>

    <div class="row justify-content-center">
        <div class="col-8">
            <table class="table table-hover">

                <thead class="thead-dark">
                <tr>
                    <th scope="col">${id}</th>
                    <th scope="col">${name}</th>
                    <th scope="col">${type}</th>
                    <th scope="col">${price}</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${products}" var="product">
                    <fmt:formatNumber var="formattedPrice" type="currency" currencyCode="KZT"
                                      maxFractionDigits="0" value="${product.price.amount}"/>
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.type.getName(pageContext.response.locale)}</td>
                        <td>${formattedPrice}</td>

                        <td>
                            <a class="btn btn-default"
                               href="<c:url value="/do/manage/deleteProduct?id=${product.id}"/>"
                            >${delete}</a>
                        </td>

                        <td>
                            <a class="btn btn-default"
                               href="<c:url value="/do/manage/editProduct?id=${product.id}"/>"
                            >${edit}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>
    </div>


    <div class="row justify-content-center" style="margin-top: 30px">
        <div class="col-12 p-0">
            <my:pagination url="/do/manage/products" pagesCount="${pagesCount}"/>
        </div>
    </div>


    <div class="row justify-content-center">
        <div class="col-12 p-0">
            <my:footer/>
        </div>
    </div>
</div>