<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="user" type="src.main.java.com.epam.rodion.musicstore.entity.User"--%>
<%--@elvariable id="address" type="src.main.java.com.epam.rodion.musicstore.entity.Address"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>


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
</fmt:bundle>


<c:url var="edit_user_url" value="/do/edit/user"/>


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

    <div class="row justify-content-end">
        <div class="col-4 p-0">
            <a class="btn btn-default"
               href="<c:url value="/do/manage/editBalance?userId=${user.id}"/>"
            >${balance}</a>
        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <form role="form" action="${change_user_url}" method="post">
                <div class="row justify-content-center">
                    <div class="col-6">
                        <div class="row justify-content-center">
                            <div class="col-10">

                                <div class="row">
                                    <div class="col-9 p-0">
                                        <p class="text-dark"
                                           style="font-size: 32px; padding-top: 15px; margin-bottom: 15px">
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
                                    <input hidden name="userId" value="${user.id}">
                                    <input hidden name="roleId" value="${user.role.id}">

                                    <div class="row">
                                        <div class="col-3 p-0">
                                            <p class="text-secondary"
                                               style="font-size: 17px; margin:1px">${first_name_label}</p>
                                        </div>
                                        <div class="col-9 p-0">
                                            <input type="text" class="form-control" placeholder="${first_name_label}"
                                                   value="${user.firstName}" name="firstName">
                                            <c:if test="${emptyFirstName.equals('true')}">
                                                <p class="text-danger"
                                                   style="font-size: 14px; margin:1px">${empty_first_name}</p>
                                            </c:if>
                                            <c:if test="${wrongFirstName.equals('true')}">
                                                <p class="text-danger"
                                                   style="font-size: 14px; margin:1px">${wrong_first_name}</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-3 p-0">
                                            <p class="text-secondary"
                                               style="font-size: 17px; margin:1px">${last_name_label}</p>
                                        </div>
                                        <div class="col-9 p-0">
                                            <input type="text" class="form-control" placeholder="${last_name_label}"
                                                   name="lastName"
                                                   value="${user.lastName}">
                                            <c:if test="${emptyLastName.equals('true')}">
                                                <p class="text-danger"
                                                   style="font-size: 14px; margin:1px">${empty_last_name}</p>
                                            </c:if>
                                            <c:if test="${wrongLastName.equals('true')}">
                                                <p class="text-danger"
                                                   style="font-size: 14px; margin:1px">${wrong_last_name}</p>
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
                                                <option selected name="gender" value="${user.gender}">
                                                    <c:if test="${user.gender.equals('male')}">
                                                        ${male}
                                                    </c:if>
                                                    <c:if test="${user.gender.equals('female')}">
                                                        ${female}
                                                    </c:if>
                                                </option>
                                                <c:if test="${user.gender.equals('male')}">
                                                    <option value="female">${female}</option>
                                                </c:if>
                                                <c:if test="${user.gender.equals('female')}">
                                                    <option value="male">${male}</option>
                                                </c:if>


                                            </select>
                                            <c:if test="${emptyGender.equals('true')}">
                                                <p class="text-danger "
                                                   style="font-size: 14px; margin-top:1px">${empty_gender}</p>
                                            </c:if>
                                        </div>

                                    </div>
                                </div>




                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-3 p-0">
                                            <p class="text-secondary"
                                               style="font-size: 17px; margin:1px">${phone_number_label}</p>
                                        </div>
                                        <div class="col-9 p-0">
                                            <input type="text" class="form-control"
                                                   name="phoneNumber" value="${user.phoneNumber}">
                                            <c:if test="${emptyPhoneNumber.equals('true')}">
                                                <p class="text-danger"
                                                   style="font-size: 14px; margin:1px">${empty_phone_number}</p>
                                            </c:if>
                                            <c:if test="${wrongPhoneNumber.equals('true')}">
                                                <p class="text-danger"
                                                   style="font-size: 14px; margin:1px">${wrong_phone_number}</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-6">
                        <div class="row justify-content-center">
                            <div class="col-11">
                                <div class="row">
                                    <div class="col-9 p-0">
                                        <p class="text-dark"
                                           style="font-size: 32px; padding-top: 15px; margin-bottom: 15px">
                                            ${address_label}
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
                                    <div class="row">
                                        <div class="col-3 p-0">
                                            <p class="text-secondary"
                                               style="font-size: 17px; margin:1px">${country_label}</p>
                                        </div>
                                        <div class="col-9 p-0">
                                            <input type="text" class="form-control" placeholder="${country_label}"
                                                   name="country"
                                                   value="${user.address.country}">
                                            <c:if test="${wrongCountry.equals('true')}">
                                                <p class="text-danger"
                                                   style="font-size: 14px; margin:1px">${wrongCountryMessage}</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-3 p-0">
                                            <p class="text-secondary"
                                               style="font-size: 17px; margin:1px">${city_label}</p>
                                        </div>
                                        <div class="col-9 p-0">
                                            <input type="text" class="form-control" placeholder="${city_label}"
                                                   name="city"
                                                   value="${user.address.city}">
                                            <c:if test="${wrongCity.equals('true')}">
                                                <p class="text-danger"
                                                   style="font-size: 14px; margin:1px">${wrongCityMessage}</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-3 p-0">
                                            <p class="text-secondary"
                                               style="font-size: 17px; margin:1px">${street_label}</p>
                                        </div>
                                        <div class="col-9 p-0">
                                            <input type="text" class="form-control" placeholder="${street_label}"
                                                   name="street"
                                                   value="${user.address.street}">
                                            <c:if test="${wrongStreet.equals('true')}">
                                                <p class="text-danger"
                                                   style="font-size: 14px; margin:1px">${wrongStreetMessage}</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-3 p-0">
                                            <p class="text-secondary"
                                               style="font-size: 17px; margin:1px">${building_label}</p>
                                        </div>
                                        <div class="col-9 p-0">
                                            <input type="text" class="form-control" placeholder="${building_label}"
                                                   name="buildingNumber" value="${user.address.buildingNumber}">
                                            <c:if test="${wrongBuilding.equals('true')}">
                                                <p class="text-danger"
                                                   style="font-size: 14px; margin:1px">${wrongBuildingMessage}</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-3 p-0">
                                            <p class="text-secondary"
                                               style="font-size: 17px; margin:1px">${apartment_label}</p>
                                        </div>
                                        <div class="col-9 p-0">
                                            <input type="text" class="form-control" placeholder="${apartment_label}"
                                                   name="apartmentNumber" value="${user.address.apartmentNumber}">
                                            <c:if test="${wrongApartment.equals('true')}">
                                                <p class="text-danger"
                                                   style="font-size: 14px; margin:1px">${wrongApartmentMessage}</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-3 p-0">
                                            <p class="text-secondary"
                                               style="font-size: 17px; margin:1px">${post_index_label}</p>
                                        </div>
                                        <div class="col-9 p-0">
                                            <input type="text" class="form-control" placeholder="${post_index_label}"
                                                   name="postIndex"
                                                   value="${user.address.postIndex}">
                                            <c:if test="${wrongPostIndex.equals('true')}">
                                                <p class="text-danger"
                                                   style="font-size: 14px; margin:1px">${wrongPostIndexMessage}</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>

                                <div class="row justify-content-end">
                                    <div class="col-lg-4 col-md-6 col-sm-12 p-0 ">
                                        <button value="submit" type="submit" class="btn btn-primary" style="width: 115px;
                        ">${save}</button>
                                    </div>
                                </div>
                            </div>
                        </div>
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
