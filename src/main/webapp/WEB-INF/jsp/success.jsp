<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:bundle basename="i18n">
    <fmt:message key="common.successfullyRegistered" var="successRegistered"/>
    <fmt:message key="common.signInInfo" var="afterRegisterInfo"/>
    <fmt:message key="common.afterUserDataChangeMessage" var="successUserDataChange"/>
    <fmt:message key="common.afterAddressChangeMessage" var="successAddressChange"/>
    <fmt:message key="product.afterProductAddMessage" var="successProductAdd"/>
    <fmt:message key="common.afterProductEditMessage" var="successProductEdit"/>
    <fmt:message key="common.afterProductDeleteMessage" var="successProductDelete"/>
    <fmt:message key="common.afterUserDeleteMessage" var="successUserDelete"/>
    <fmt:message key="error.userDelete" var="errorUserDelete"/>
    <fmt:message key="error.loginError" var="login_error"/>
    <fmt:message key="error.wrongNameOrPassword" var="wrongLoginData"/>
    <fmt:message key="common.successBuy" var="succes_buy"/>
    <fmt:message key="common.afterBuyMessage" var="afterBuyMessage"/>
    <fmt:message key="error.neededCash" var="need_cash"/>
    <fmt:message key="error.notEnoughMoney" var="not_enough_money"/>

</fmt:bundle>


<div class="container-fluid ">
    <div class="row">
        <div class="col-12 p-0">
            <my:navbar pagetitle="Music Planet"/>
        </div>
    </div>
    <div class="row text-center justify-content-center" style="margin-left: 400px; margin-right: 400px;
margin-top: 30px; ">
        <div class="col-12">

            <c:if test="${afterRegisterFlag.equals('true')}">
                <img src="<c:url value="/images/forPageBody/tick.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${successRegistered}</p>
                <p class="text-secondary" style="font-size: 15px; padding: 1px; ">${afterRegisterInfo}</p>
            </c:if>
            <c:if test="${afterUserDataChangeFlag.equals('true')}">
                <img src="<c:url value="/images/forPageBody/tick.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${successUserDataChange}</p>
            </c:if>
            <c:if test="${afterAddressChangeFlag.equals('true')}">
                <img src="<c:url value="/images/forPageBody/tick.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${successAddressChange}</p>
            </c:if>
            <c:if test="${afterProductAddFlag.equals('true')}">
                <img src="<c:url value="/images/forPageBody/tick.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${successProductAdd}</p>
            </c:if>
            <c:if test="${afterProductEditFlag.equals('true')}">
                <img src="<c:url value="/images/forPageBody/tick.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${successProductEdit}</p>
            </c:if>
            <c:if test="${afterProductDeleteFlag.equals('true')}">
                <img src="<c:url value="/images/forPageBody/tick.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${successProductDelete}</p>
            </c:if>
            <c:if test="${afterUserDeleteFlag.equals('true')}">
                <img src="<c:url value="/images/forPageBody/tick.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${successUserDelete}</p>
            </c:if>
            <c:if test="${userDeleteError.equals('true')}">
                <img src="<c:url value="/images/forPageBody/cross.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${errorUserDelete}</p>
            </c:if>
            <c:if test="${balanceError.equals('true')}">
                <img src="<c:url value="/images/forPageBody/cross.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${need_cash}</p>
            </c:if>

            <c:if test="${loginError.equals('true')}">
                <img src="<c:url value="/images/forPageBody/cross.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${login_error}</p>
                <p class="text-secondary" style="font-size: 15px; padding: 1px; ">${wrongLoginData}</p>
            </c:if>

            <c:if test="${notEnoughMoneyError.equals('true')}">
                <img src="<c:url value="/images/forPageBody/cross.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${not_enough_money}</p>

            </c:if>

            <c:if test="${successBuy.equals('true')}">
                <img src="<c:url value="/images/forPageBody/tick.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${succes_buy}</p>
                <p class="text-secondary" style="font-size: 15px; padding: 1px; ">${afterBuyMessage}</p>
            </c:if>

        </div>
    </div>
    <div class="row" style="height: 310px">
        <div class="col-12 ">
        </div>
    </div>


    <div class="row">
        <div class="col-12 p-0">
            <my:footer/>
        </div>
    </div>
</div>

