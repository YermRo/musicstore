<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<fmt:bundle basename="i18n">
    <fmt:message key="user.save" var="save"/>
    <fmt:message key="error.balanceValue" var="error"/>
    <fmt:message key="common.balance" var="balance"/>
</fmt:bundle>

<%--@elvariable id="types" type="java.util.List"--%>
<%--@elvariable id="user" type="src.main.java.com.epam.rodion.musicstore.entity.User"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>


<c:url var="set_user_cash" value="/do/manage/balance"/>
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
        <div class="col-10">

            <div class="row">
                <div class="col-12 p-0" style="height: 40px">
                    <p class="text-secondary" style="font-size: 23px; margin:1px">${user.firstName} ${user.lastName}</p>
                </div>
            </div>


            <div class="row">
                <div class="col-12 p-0" style="height: 40px">
                </div>
            </div>

            <form role="form" action="${set_user_cash}" method="post" c>
                <input hidden name="userId" value="${user.id}">
                <div class="form-group">
                    <div class="row">


                        <div class="col-4 p-0">
                            <input type="text" class="form-control" placeholder="${balance}" name="balance"
                                   value="${user.cash.amount.intValue()}">
                            <c:if test="${emptyBalance.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${balance_error}</p>
                            </c:if>
                        </div>

                        <div class="col-4">
                            <button value="submit" type="submit" class="btn btn-primary" style="width: 115px;
                        ">${save}</button>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-12 p-0">
                        <fmt:formatNumber var="formattedBalance" type="currency" currencyCode="KZT"
                                          maxFractionDigits="0" value="${user.cash.amount}"/>
                        <p class="text-secondary" style="font-size: 17px; margin:1px">${balance} : ${formattedBalance}</p>
                    </div>
                </div>

            </form>


        </div>
    </div>


    <div class="row">
        <div class="col-12 p-0" style="height: 300px">
        </div>
    </div>

    <div class="row justify-content-center">
        <div class="col-12 p-0">
            <my:footer/>
        </div>
    </div>

</div>