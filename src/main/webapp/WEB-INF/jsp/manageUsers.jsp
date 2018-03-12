<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<fmt:bundle basename="i18n">
    <fmt:message key="common.firstName" var="first_name"/>
    <fmt:message key="common.lastName" var="last_name"/>
    <fmt:message key="common.lastName" var="last_name"/>
    <fmt:message key="common.phoneNumber" var="phone_number"/>
    <fmt:message key="common.cash" var="cash"/>
    <fmt:message key="user.gender" var="gender"/>
    <fmt:message key="user.userID" var="user_id"/>
    <fmt:message key="common.delete" var="delete"/>
    <fmt:message key="common.edit" var="edit"/>


</fmt:bundle>

<%--@elvariable id="types" type="java.util.List"--%>
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
        <div class="col-10">
            <table class="table table-hover">

                <thead class="thead-dark">
                <tr>
                    <th scope="col">${user_id}</th>
                    <th scope="col">${first_name}</th>
                    <th scope="col">${last_name}</th>
                    <th scope="col">${phone_number}</th>
                    <th scope="col">${cash}</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.phoneNumber}</td>
                        <td>${user.cash}</td>
                        <td>
                            <a class="btn btn-default"
                               href="<c:url value="/do/manage/deleteUser?userId=${user.id}"/>"
                            >${delete}</a>
                        </td>

                        <td>
                            <a class="btn btn-default"
                               href="<c:url value="/do/manage/editUser?userId=${user.id}"/>"
                            >${edit}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>
    </div>





    <div class="row justify-content-center">
        <div class="col-12 p-0">
            <my:footer/>
        </div>
    </div>
</div>