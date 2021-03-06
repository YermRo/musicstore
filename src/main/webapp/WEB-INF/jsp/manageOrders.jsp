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
    <fmt:message key="cart.orderDate" var="created"/>
    <fmt:message key="cart.status" var="status"/>
    <fmt:message key="cart.customer" var="customer"/>
    <fmt:message key="common.details" var="details"/>


</fmt:bundle>

<%--@elvariable id="orders" type="java.util.List"--%>
<%--@elvariable id="types" type="src.main.java.com.epam.rodion.musicstore.entity.ProductType"--%>
<%--@elvariable id="product" type="src.main.java.com.epam.rodion.musicstore.entity.Product"--%>
<%--@elvariable id="order" type="src.main.java.com.epam.rodion.musicstore.entity.Order"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>


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



    <div class="row justify-content-center">
        <div class="col-8">
            <table class="table table-hover">

                <thead class="thead-dark">
                <tr>
                    <th>${created}</th>
                    <th>${customer}</th>
                    <th>${description}</th>
                    <th>${price}</th>
                    <th>${status}</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${orders}" var="order">
                    <fmt:formatNumber var="formattedPrice" type="currency" currencyCode="KZT"
                                      maxFractionDigits="0" value="${product.price.amount}"/>
                    <tr>
                        <td>${order.formattedCreationTime}</td>
                        <td>${order.user.firstName}</td>
                        <td>${order.getDescription(pageContext.response.locale)}</td>
                        <fmt:formatNumber var="formattedPrice" type="currency" currencyCode="KZT"
                                          maxFractionDigits="0"
                                          value="${order.price.amount}"/>
                        <td>${formattedPrice}</td>
                        <td>
                            <div class="form-group input-group" style="margin-bottom: 0px">
                                <form action="<c:url value="/do/edit/orderStatus"/>" method="post"
                                      style="margin-bottom: 0px">
                                    <input hidden name="orderId" value="${order.id}">
                                    <select class="form-control custom-select" id="status" name="statusId"
                                            onchange="this.form.submit()" style="width: 150px">
                                        <c:forEach items="${statuses}" var="status">
                                            <option value="${status.id}"<c:if
                                                    test="${order.status.equals(status)}"> selected
                                            </c:if>>${status.getName(pageContext.response.locale)}</option>
                                        </c:forEach>
                                    </select>
                                </form>
                            </div>
                        </td>
                        <td style="width: 100px"><a class="btn btn-default"
                                                    href="<c:url value="/do/order?orderId=${order.id}"/>"
                        >${details}</a>

                        </td>
                        <td>
                            <a class="btn btn-default"
                               href="<c:url value="/do/manage/deleteOrder?userId=${order.id}"/>"
                            >${delete}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>
    </div>


    <div class="row justify-content-center" style="margin-top: 30px">
        <div class="col-12 p-0">
            <my:pagination url="/do/manage/orders" pagesCount="${pagesCount}"/>
        </div>
    </div>


    <div class="row justify-content-center">
        <div class="col-12 p-0">
            <my:footer/>
        </div>
    </div>
</div>