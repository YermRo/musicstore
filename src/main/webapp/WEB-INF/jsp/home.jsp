<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:bundle basename="i18n">
    <fmt:message key="marketing.h-1" var="h1"/>
    <fmt:message key="marketing.h-2" var="h2"/>
    <fmt:message key="marketing.h-3" var="h3"/>
    <fmt:message key="marketing.h-4" var="h4"/>
    <fmt:message key="marketing.h-5" var="h5"/>
    <fmt:message key="marketing.msg-1" var="msg1"/>
    <fmt:message key="marketing.msg-2" var="msg2"/>
    <fmt:message key="marketing.msg-3" var="msg3"/>
    <fmt:message key="marketing.msg-4" var="msg4"/>
    <fmt:message key="marketing.msg-5" var="msg5"/>
</fmt:bundle>
<%--@elvariable id="pagesCount" type="java.lang.Integer"--%>
<%--@elvariable id="loggedUser" type="com.epam.igor.electronicsshop.entity.User"--%>
<%--@elvariable id="products" type="java.util.List"--%>
<div class="container-fluid">
    <div class="row">
        <div class="col-12 p-0">
            <my:navbar pagetitle="Music Planet"/>
        </div>
    </div>
    <div class="row">
        <div class="col-12 p-0">
            <my:carousel/>
        </div>
    </div>

    <div class="marketing">
        <div class="row text-center justify-content-around">
            <div class="col-xs-12 col-lg-4">
                <img src="/images/forPageBody/usluga-1.png" alt="" width="90px">
                <h3>${h1}</h3>
                <hr align="center">
                <p>${msg1}
                </p>

            </div>

            <div class="col-xs-12 col-lg-4">
                <img src="/images/forPageBody/usluga-2.png" alt="" width="90px">
                <h3>${h2}</h3>
                <hr align="center">
                <p>
                    ${msg2}
                </p>
            </div>
            <div class="col-xs-12 col-lg-4">
                <img src="/images/forPageBody/usluga-3.png" alt="" width="90px">
                <h3>${h3}</h3>
                <hr align="center">
                <p>
                    ${msg3}
                </p>
            </div>

            <div class="col-xs-12 col-lg-4 p-10">
                <img src="/images/forPageBody/usluga-4.png" alt="" width="90px">
                <h3>${h4}</h3>
                <hr align="center">
                <p>${msg4}
                </p>
            </div>
            <div class="col-xs-12 col-lg-4 p-10">
                <img src="/images/forPageBody/usluga-5.png" alt="" width="90px">
                <h3>${h5}</h3>
                <hr align="center">
                <p>
                    ${msg5}
                </p>
            </div>
        </div>


    </div>
    <div class="row">
        <div class="col-12 p-0">
            <my:footer/>
        </div>
    </div>
</div>






