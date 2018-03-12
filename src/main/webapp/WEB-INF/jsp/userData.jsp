<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="loggedUser" type="src.main.java.com.epam.rodion.musicstore.entity.User"--%>


<fmt:bundle basename="i18n">
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
    <fmt:message key="common.changePassword" var="change_password"/>

</fmt:bundle>


<c:url var="edit_user_url" value="/do/edit/userData"/>
<c:url var="edit_address_url" value="/do/edit/userAddress"/>
<c:url var="edit_password_url" value="/do/edit/password?userId=${loggedUser.id}"/>

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
        <div class="col-7">
            <form role="form" action="${edit_user_url}" method="post" c>

                <div class="row">
                    <div class="col-9 p-0">
                        <p class="text-dark" style="font-size: 32px; padding-top: 15px; margin-bottom: 15px">
                            ${personal_data}
                        </p>
                    </div>
                    <div class="col-3 p-0">
                    </div>
                </div>

                <div class="row">
                    <div class="col-12 p-0" style="height: 10px">
                    </div>
                </div>


                <div class="form-group">
                    <input hidden name="userId" value="${loggedUser.id}">
                    <input hidden name="roleId" value="${loggedUser.role.id}">

                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${first_name_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" placeholder="${first_name_label}"
                                   value="${loggedUser.firstName}" name="firstName">
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
                                   value="${loggedUser.lastName}">
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
                            <select class="form-control custom-select " id="gender" name="gender">
                                <option selected name="gender" value="${loggedUser.gender}">
                                    <c:if test="${loggedUser.gender.equals('male')}">
                                        ${male}
                                    </c:if>
                                    <c:if test="${loggedUser.gender.equals('female')}">
                                        ${female}
                                    </c:if>
                                </option>
                                <c:if test="${loggedUser.gender.equals('male')}">
                                    <option value="female">${female}</option>
                                </c:if>
                                <c:if test="${loggedUser.gender.equals('female')}">
                                    <option value="male">${male}</option>
                                </c:if>


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
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${phone_number_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control"
                                   name="phoneNumber" value="${loggedUser.phoneNumber}">
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

                    <div class="btn-group">
                        <a class="btn btn-outline-primary" href="${edit_password_url}" role="button" >
                            ${change_password}
                        </a>
                        <a class="btn btn-outline-primary" href="${edit_address_url}" role="button" ">
                            ${edit_address_label}
                        </a>


                        <button value="submit" type="submit" class="btn btn-primary" >${save}</button>



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