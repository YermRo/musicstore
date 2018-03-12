<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="loggedUser" type="com.epam.igor.electronicsshop.entity.User"--%>
<%--@elvariable id="role" type="com.epam.igor.electronicsshop.entity.Role"--%>

<fmt:bundle basename="i18n">
    <fmt:message key="common.password" var="password_label"/>
    <fmt:message key="error.emptyPassword" var="empty_password"/>
    <fmt:message key="error.wrongPassword" var="wrong_password"/>
    <fmt:message key="error.passwordsDontMatch" var="not_same_password"/>
    <fmt:message key="common.oldPassword" var="old_password"/>
    <fmt:message key="common.newPassword" var="new_password"/>
    <fmt:message key="common.changePassword" var="change_password"/>
    <fmt:message key="common.repeatPassword" var="repeat_password"/>
    <fmt:message key="user.save" var="save"/>
</fmt:bundle>

<c:url var="edit_password_url" value="/do/edit/password"/>


<div class="container-fluid p-0">
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
        <div class="col-7">
            <form role="form" action="${edit_password_url}" method="post" c>
                <input hidden name="userId" value="${loggedUser.id}">
                <input hidden name="changePasswordForm" value="true">
                <div class="row">
                    <div class="col-9 p-0">
                        <p class="text-dark" style="font-size: 32px; padding-top: 15px; margin-bottom: 15px">
                            ${change_password}
                        </p>
                    </div>
                    <div class="col-3 p-0">
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${old_password}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="password" class="form-control" placeholder="${old_password}" name="oldPassword">
                        </div>
                        <c:if test="${wrongOldPassword.equals('true')}">
                            <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_password}</p>
                        </c:if>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${new_password}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="password" class="form-control" placeholder="${new_password}" name="newPassword"
                                   >
                            <c:if test="${wrongPassword.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_password}</p>
                            </c:if>
                            <c:if test="${emptyPassword.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${empty_password}</p>
                            </c:if>
                        </div>
                    </div>
                </div>



                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${repeat_password}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="password" class="form-control" placeholder="${repeat_password}"
                                   name="repeatedPassword"
                                   value="${password}">
                            <c:if test="${differentPasswordError.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${not_same_password}</p>
                            </c:if>

                        </div>
                    </div>
                </div>



                <div class="row justify-content-end">
                    <div class="col-lg-2 col-md-5 col-sm-12 p-0 ">
                        <button value="submit" type="submit" class="btn btn-primary" style="width: 115px;
                        margin: 5px">
                            ${save}
                        </button>
                    </div>
                </div>

            </form>
        </div>
    </div>

</div>
<div class="row">
    <div class="col-12 p-0">
        <my:footer/>
    </div>
</div>
</div>