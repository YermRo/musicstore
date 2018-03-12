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
    <fmt:message key="cart.total" var="total"/>
    <fmt:message key="cart.totalPrice" var="total_price"/>
    <fmt:message key="cart.clear" var="clear"/>
    <fmt:message key="cart.buy" var="buy"/>
    <fmt:message key="cart.empty" var="empty_cart"/>
    <fmt:message key="error.incorrectAmount" var="incorrect_amount"/>
    <fmt:message key="error.neededAmount" var="needed_amount"/>


</fmt:bundle>

<%--@elvariable id="items" type="java.util.List"--%>
<%--@elvariable id="types" type="src.main.java.com.epam.rodion.musicstore.entity.ProductType"--%>
<%--@elvariable id="item" type="src.main.java.com.epam.rodion.musicstore.entity.StorageItem"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>
<%--@elvariable id="errorMap" type="java.util.Map;"--%>

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
            <c:choose>
            <c:when test="${empty cart.orderingItems}">
                <div class="row text-center justify-content-center" style= " margin-top: 30px; ">
                    <div class="col-12">
                        <img src="<c:url value="/images/forPageBody/cart.png"/>" height="60px" style="margin-top: 10px">
                        <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${empty_cart}</p>
                    </div>
                    <div class="col-12" style="height: 230px;">

                    </div>
                </div>
            </c:when>
            <c:otherwise>
            <table class="table table-hover">

                <thead class="thead-dark">
                <tr>
                    <th></th>
                    <th>${name}</th>
                    <th>${quantity}</th>
                    <th>${price}</th>
                    <th>${total}</th>
                    <th></th>
                </tr>
                </thead>

                <form action="<c:url value="/do/cart/recount"/>" method="post">

                <tbody>
                <c:forEach items="${cart.orderingItems}" var="item" varStatus="itemRow">


                    <tr>
                        <td>
                            <img style="max-height: 60px; max-width: 60px; margin-top: 15px"
                                 src="<c:url value="/img/${item.product.id}"/>">
                        </td>
                        <td>${item.product.name} </td>
                        <td>
                            <div class="input-group">
                                <input class="form-control" type="number" min="1" max="9999" value="${item.amount}"
                                       style="width: 100px"
                                       name="item${itemRow.index}" onchange="this.form.submit()">
                                <c:if test="${errorMap.get(itemRow.index).equals('true')}">
                                    <p class="text-danger"
                                       style="height: 20px;font-size: 12px;">${incorrect_amount}</p>
                                </c:if>
                                <c:if test="${errorMap.get(itemRow.index).equals('itemAmountError')}">
                                    <p class="text-danger"
                                       style="height: 20px;font-size: 12px;">${needed_amount}: ${item.amount}</p>
                                </c:if>
                            </div>
                        </td>

                        <fmt:formatNumber var="formattedProductPrice" type="currency" currencyCode="KZT"
                                          maxFractionDigits="0"
                                          value="${item.product.price.amount}"/>
                        <td>${formattedProductPrice}</td>
                        <fmt:formatNumber var="formattedItemPrice" type="currency" currencyCode="KZT"
                                          maxFractionDigits="0"
                                          value="${item.price.amount}"/>
                        <td>${formattedItemPrice}</td>
                        <td>
                            <a class="btn btn-default"
                               href="<c:url value="/do/orderingItem/delete?item=${itemRow.index}"/>"
                            >${delete}
                            </a>
                        </td>


                    </tr>
                </c:forEach>
                </tbody>
                </form>
            </table>
        </div>
    </div>

    <div class="row justify-content-center">
        <div class="col-8 p-0">
            <div class="row justify-content-end">
                <div class="col-5 p-0">
                    <fmt:formatNumber var="formattedCartPrice" type="currency" currencyCode="KZT"
                                      maxFractionDigits="0"
                                      value="${cart.price.amount}"/>
                    <p class="text-dark font-weight-bold" style="font-size: 20px;">
                            ${total_price} ${formattedCartPrice}
                    </p>
                </div>
            </div>
        </div>
    </div>


    <div class="row justify-content-center">
        <div class="col-8">
            <div class="row justify-content-end">
                <div class="col-3">
                    <div class="btn-group">
                        <a class="btn btn-dark btn-sm" href="<c:url value="/do/cart/clear"/>" role="button">
                                ${clear}
                        </a>
                        <a class="btn  btn-dark btn-sm" href="<c:url value="/do/cart/buy"/>" role="button"
                           style="background-color: #F16143; border-color: #F16143; color: #212529;">
                                ${buy}
                        </a>
                    </div>
                </div>
            </div>


        </div>

        </c:otherwise>
        </c:choose>
    </div>


    <div class="row justify-content-center">
        <div class="col-12 p-0">
            <my:footer/>
        </div>
    </div>
</div>


