<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="loggedUser" type="src.main.java.com.epam.rodion.musicstore.entity.User"--%>
<%--@elvariable id="userAddress" type="src.main.java.com.epam.rodion.musicstore.entity.Address"--%>

<fmt:bundle basename="i18n">
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
</fmt:bundle>

<c:url var="edit_address_url" value="/do/edit/userAddress"/>
<c:url var="edit_userData_url" value="/do/edit/userData"/>

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
            <form role="form" action="${edit_address_url}" method="post">

                <div class="row">
                    <div class="col-9 p-0">
                        <p class="text-dark" style="font-size: 32px; padding-top: 15px; margin-bottom: 15px">
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
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${country_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" placeholder="${country_label}"
                                   name="country"
                                   value="${loggedUser.address.country}">
                            <c:if test="${wrongCountry.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${wrongCountryMessage}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${city_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" placeholder="${city_label}" name="city"
                                   value="${loggedUser.address.city}">
                            <c:if test="${wrongCity.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${wrongCityMessage}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${street_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" placeholder="${street_label}" name="street"
                                   value="${loggedUser.address.street}">
                            <c:if test="${wrongStreet.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${wrongStreetMessage}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${building_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" placeholder="${building_label}"
                                   name="buildingNumber" value="${loggedUser.address.buildingNumber}">
                            <c:if test="${wrongBuilding.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${wrongBuildingMessage}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${apartment_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" placeholder="${apartment_label}"
                                   name="apartmentNumber" value="${loggedUser.address.apartmentNumber}">
                            <c:if test="${wrongApartment.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${wrongApartmentMessage}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${post_index_label}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" placeholder="${post_index_label}" name="postIndex"
                                   value="${loggedUser.address.postIndex}">
                            <c:if test="${wrongPostIndex.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${wrongPostIndexMessage}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="row justify-content-end">
                    <div class="col-lg-3 col-md-5 col-sm-12 p-0">
                        <a class="btn btn-outline-primary" href="${edit_userData_url}" role="button" style="width: 175px;
                     margin: 5px">
                            ${profile_settings}
                        </a>
                    </div>

                    <div class="col-lg-2 col-md-5 col-sm-12 p-0 ">
                        <button value="submit" type="submit" class="btn btn-primary" style="width: 115px;
                        margin: 5px">${save}</button>
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