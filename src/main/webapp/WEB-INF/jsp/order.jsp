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
    <fmt:message key="user.personalData" var="personal_data"/>
    <fmt:message key="common.companyname" var="companyName"/>
    <fmt:message key="common.firstName" var="first_name_label"/>
    <fmt:message key="common.lastName" var="last_name_label"/>
    <fmt:message key="common.phoneNumber" var="phone_number_label"/>
    <fmt:message key="error.emailUsed" var="email_used_message"/>
    <fmt:message key="common.email" var="email_label"/>
    <fmt:message key="common.password" var="password_label"/>
    <fmt:message key="user.save" var="save"/>
    <fmt:message key="error.email" var="email_error_message"/>
    <fmt:message key="user.gender" var="gender"/>
    <fmt:message key="user.female" var="female"/>
    <fmt:message key="user.male" var="male"/>
    <fmt:message key="error.firstName" var="firstName_error_message"/>
    <fmt:message key="error.lastName" var="lastName_error_message"/>
    <fmt:message key="error.phoneNumber" var="phoneNumer_error_message"/>
    <fmt:message key="error.password" var="password_error_message"/>
    <fmt:message key="error.emptyFirstName" var="empty_first_name"/>
    <fmt:message key="error.emptyLastName" var="empty_last_name"/>
    <fmt:message key="error.emptyPhoneNumber" var="empty_phone_number"/>
    <fmt:message key="error.wrongFirstName" var="wrong_first_name"/>
    <fmt:message key="error.wrongLastName" var="wrong_last_name"/>
    <fmt:message key="error.wrongPhoneNumber" var="wrong_phone_number"/>
    <fmt:message key="error.emptyGender" var="empty_gender"/>
    <fmt:message key="error.emptyPassword" var="empty_password"/>
    <fmt:message key="error.wrongPassword" var="wrong_password"/>
    <fmt:message key="error.emptyEmail" var="empty_email"/>
    <fmt:message key="error.wrongEmail" var="wrong_email"/>
    <fmt:message key="common.editAddress" var="edit_address_label"/>
    <fmt:message key="common.address" var="address_label"/>
    <fmt:message key="common.country" var="country_label"/>
    <fmt:message key="common.city" var="city_label"/>
    <fmt:message key="common.street" var="street_label"/>
    <fmt:message key="common.building" var="building_label"/>
    <fmt:message key="common.apartmentNumber" var="apartment_label"/>
    <fmt:message key="common.postIndex" var="post_index_label"/>
    <fmt:message key="user.save" var="save"/>
    <fmt:message key="error.wrongCountry" var="wrongCountryMessage"/>
    <fmt:message key="error.wrongCity" var="wrongCityMessage"/>
    <fmt:message key="error.wrongStreet" var="wrongStreetMessage"/>
    <fmt:message key="error.wrongApartment" var="wrongApartmentMessage"/>
    <fmt:message key="error.wrongBuilding" var="wrongBuildingMessage"/>
    <fmt:message key="error.wrongPostIndex" var="wrongPostIndexMessage"/>
    <fmt:message key="user.profileSettings" var="profile_settings"/>
    <fmt:message key="common.balance" var="balance"/>
    <fmt:message key="user.female" var="female"/>
    <fmt:message key="user.male" var="male"/>
    <fmt:message key="common.role" var="role"/>
    <fmt:message key="common.edit" var="edit"/>
    <fmt:message key="product.quantity" var="quantity"/>
</fmt:bundle>

<%--@elvariable id="orderingItems" type="java.util.List"--%>
<%--@elvariable id="order" type="src.main.java.com.epam.rodion.musicstore.entity.Order"--%>
<%--@elvariable id="item" type="src.main.java.com.epam.rodion.musicstore.entity.StorageItem"--%>
<%--@elvariable id="user" type="src.main.java.com.epam.rodion.musicstore.entity.User"--%>
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
        <div class="col-4 p-0">

            <table class="table table-hover table-bordered">

                <thead class="thead-dark" style>
                <tr>
                    <th colspan="2" style="padding: 0px">${personal_data}</th>
                </tr>
                </thead>
                <tbody style="">
                <tr>
                    <td class="font-weight-bold" style="padding: 0px">${first_name_label}</td>
                    <td style="padding: 0px">${user.firstName}</td>
                </tr>
                <tr>
                    <td class="font-weight-bold" style="padding: 0px">${last_name_label}</td>
                    <td style="padding: 0px">${user.lastName}</td>
                </tr>
                <tr>
                    <td class="font-weight-bold" style="padding: 0px">${gender}</td>
                    <td style="padding: 0px">
                        <c:if test="${user.gender.equals('male')}">
                            ${male}
                        </c:if>
                        <c:if test="${user.gender.equals('female')}">
                            ${female}
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td class="font-weight-bold" style="padding: 0px">${role}</td>
                    <td style="padding: 0px">${user.role.getName(pageContext.response.locale)}</td>
                </tr>
                <tr>
                    <td class="font-weight-bold" style="padding: 0px">${email_label}</td>
                    <td style="padding: 0px">${user.email}</td>
                </tr>
                <tr>
                    <td class="font-weight-bold" style="padding: 0px">${phone_number_label}</td>
                    <td style="padding: 0px">${user.phoneNumber}</td>
                </tr>

                </tbody>
            </table>
        </div>
        <div class="col-4 p-0">
            <table class="table table-hover table-bordered">

                <thead class="thead-dark">
                <tr>
                    <th colspan="2" style="padding: 0px">${address_label}</th>

                </tr>
                </thead>

                <tbody>

                <tr>
                    <td class="font-weight-bold" style="padding: 0px">${country_label}</td>
                    <td style="padding: 0px">${user.address.country}</td>
                </tr>
                <tr>
                    <td class="font-weight-bold" style="padding: 0px">${city_label}</td>
                    <td style="padding: 0px">${user.address.city}</td>
                </tr>
                <tr>
                    <td class="font-weight-bold" style="padding: 0px">${street_label}</td>
                    <td style="padding: 0px">${user.address.street}</td>
                </tr>
                <tr>
                    <td class="font-weight-bold" style="padding: 0px">${building_label}</td>
                    <td style="padding: 0px">${user.address.buildingNumber}</td>
                </tr>
                <tr>
                    <td class="font-weight-bold" style="padding: 0px">${apartment_label}</td>
                    <td style="padding: 0px">${user.address.apartmentNumber}</td>
                </tr>
                <tr>
                    <td class="font-weight-bold" style="padding: 0px">${post_index_label}</td>
                    <td style="padding: 0px">${user.address.postIndex}</td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
</div>


<div class="row justify-content-center">
    <div class="col-8">

        <table class="table table-hover">

            <thead class="thead-dark">
            <tr>
                <th></th>
                <th>${name}</th>
                <th>${quantity}</th>
                <th>${price}</th>
                <th>${total}</th>

            </tr>
            </thead>

            <tbody>
            <c:forEach items="${order.orderingItems}" var="item" varStatus="itemRow">
                <tr>
                    <td>
                        <img style="height: 60px; margin-top: 15px" src="<c:url value="/img/${item.product.id}"/>">
                    </td>
                    <td>${item.product.name}</td>
                    <td> ${item.amount}</td>

                    <fmt:formatNumber var="formattedProductPrice" type="currency" currencyCode="KZT"
                                      maxFractionDigits="0"
                                      value="${item.product.price.amount}"/>
                    <td>${formattedProductPrice}</td>
                    <fmt:formatNumber var="formattedItemPrice" type="currency" currencyCode="KZT"
                                      maxFractionDigits="0"
                                      value="${item.price.amount}"/>
                    <td>${formattedItemPrice}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="row justify-content-center">
    <div class="col-8 p-0">
        <div class="row justify-content-end">
            <div class="col-5 p-0">
                <fmt:formatNumber var="formattedCartPrice" type="currency" currencyCode="KZT"
                                  maxFractionDigits="0"
                                  value="${order.price.amount}"/>
                <p class="text-dark font-weight-bold" style="font-size: 20px;">
                    ${total_price} ${formattedCartPrice}
                </p>
            </div>
        </div>
    </div>
</div>




<div class="row justify-content-center">
    <div class="col-12 p-0">
        <my:footer/>
    </div>
</div>
</div>
