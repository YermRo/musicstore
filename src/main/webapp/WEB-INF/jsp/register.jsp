<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:bundle basename="i18n">
    <fmt:message key="common.companyname" var="companyName"/>
    <fmt:message key="common.firstName" var="first_name_label"/>
    <fmt:message key="common.lastName" var="last_name_label"/>
    <fmt:message key="common.phoneNumber" var="phone_number_label"/>
    <fmt:message key="error.emailUsed" var="email_used_message"/>
    <fmt:message key="common.email" var="email_label"/>
    <fmt:message key="common.password" var="password_label"/>
    <fmt:message key="register" var="register_label"/>
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
</fmt:bundle>

<c:url var="register_url" value="/do/register"/>


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
            <form role="form" action="${register_url}" method="post" c>

                <div class="row">
                    <div class="col-9 p-0">
                        <p class="text-dark" style="font-size: 32px; padding-top: 15px; margin-bottom: 15px">
                            ${register_label}
                        </p>
                    </div>
                    <div class="col-3 p-0">
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${first_name_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" placeholder="${first_name_label}" name="firstName"
                                   value="${firstName}">
                            <c:if test="${emptyFirstName.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${empty_first_name}</p>
                            </c:if>
                            <c:if test="${wrongFirstName.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_first_name}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${last_name_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" placeholder="${last_name_label}" name="lastName"
                                   value="${lastName}">
                            <c:if test="${emptyLastName.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${empty_last_name}</p>
                            </c:if>
                            <c:if test="${wrongLastName.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_last_name}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group " style="font-size: 14px">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${gender}</p>
                        </div>
                        <div class="col-9 p-0">
                            <select class="form-control custom-select" id="gender" name="gender">
                                <option selected name="gender" value="gender">${gender}</option>
                                <option value="male">${male}</option>
                                <option value="female">${female}</option>
                            </select>
                            <c:if test="${emptyGender.equals('true')}">
                                <p class="text-danger " style="font-size: 14px; margin-top:1px">${empty_gender}</p>
                            </c:if>
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${email_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" placeholder="${email_label}" name="email"
                                   value="${email}">
                            <c:if test="${wrongEmail.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_email}</p>
                            </c:if>
                            <c:if test="${emailUsed.equals('used')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${email_used_message}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${password_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="password" class="form-control" placeholder="${password_label}" name="password"
                                   value="${password}">
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
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${phone_number_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" placeholder="${phone_number_label}"
                                   name="phoneNumber"
                                   value="${phoneNumber}">
                            <c:if test="${emptyPhoneNumber.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${empty_phone_number}</p>
                            </c:if>
                            <c:if test="${wrongPhoneNumber.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_phone_number}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="row justify-content-end">
                    <div class="col-lg-2 col-md-5 col-sm-12 p-0 ">
                        <button value="submit" type="submit" class="btn btn-primary" style="width: 115px;
                        margin: 5px">
                            ${register_label}
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

