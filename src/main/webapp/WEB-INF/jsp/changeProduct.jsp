<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:bundle basename="i18n">
    <fmt:message key="common.addProduct" var="add_product"/>
    <fmt:message key="product.productName" var="product_name"/>
    <fmt:message key="product.productPrice" var="price"/>
    <fmt:message key="product.productType" var="type"/>
    <fmt:message key="product.description" var="description"/>
    <fmt:message key="product.descriptionLanguageMessage" var="select_description_message"/>
    <fmt:message key="user.save" var="save"/>
    <fmt:message key="common.image" var="image"/>
    <fmt:message key="error.image" var="imageErrorMessage"/>
    <fmt:message key="error.money" var="moneyErrorMessage"/>
    <fmt:message key="product.productData" var="product_data"/>
    <fmt:message key="product.productData" var="product_data"/>

</fmt:bundle>

<%--@elvariable id="types" type="java.util.List"--%>
<%--@elvariable id="types" type="src.main.java.com.epam.rodion.musicstore.entity.ProductType"--%>
<%--@elvariable id="product" type="src.main.java.com.epam.rodion.musicstore.entity.Product"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>
<c:url var="changeProduct_url" value="/do/edit/product"/>
<c:set var="localeCode" value="${pageContext.response.locale}" />

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
            <form role="form" action="${changeProduct_url}" method="post" enctype="multipart/form-data" c>
                <input hidden name="id" value="${product.id}">
                <div class="row">
                    <div class="col-9 p-0">
                        <p class="text-dark" style="font-size: 32px; padding-top: 15px; margin-bottom: 15px">
                            ${product_data}
                        </p>
                    </div>
                    <div class="col-3 p-0">
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${product_name}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" placeholder="${product_name}" name="productName"
                                   value="${product.name}">
                            <c:if test="${emptyFirstName.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${empty_first_name}</p>
                            </c:if>
                            <c:if test="${wrongFirstName.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_first_name}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group " style="font-size: 14px">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${type}</p>
                        </div>
                        <div class="col-9 p-0">
                            <select class="form-control" id="type" name="typeId">
                                <c:forEach items="${types}" var="type">
                                    <option value="${type.id}"
                                            <c:if test="${product.type.equals(type)}">selected</c:if>
                                    >${type.getName(pageContext.response.locale)}
                                    </option>
                                </c:forEach>
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
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${price}</p>
                        </div>
                        <div class="col-9 p-0">
                            <input type="text" class="form-control" name="productPrice"
                                   value="${product.price.amount.intValue()}">
                            <c:if test="${moneyError.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${moneyErrorMessage}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary" style="font-size: 17px; margin:1px">${description}</p>
                        </div>
                        <div class="col-9 p-0">
                            <div class="form-group input-group">
                                <textarea style=" resize: none; width: 400px; height:200px;" class="form-control"
                                          id="description"
                                          name="productDescription"
                                ><c:if test="${locale.equals('en')}">${product.enDescription}</c:if>
                                     <c:if test="${locale.equals('ru')}">${product.ruDescription}</c:if></textarea>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="form-group " style="font-size: 14px">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary"
                               style="font-size: 17px; margin:1px">${select_description_message}</p>
                        </div>
                        <div class="col-9 p-0">



                            <select class="form-control custom-select " id="lang" name="descriptionLanguage">
                                <c:if test="${pageContext.response.locale.toString().equals('en_EN')}">

                                    <option selected name="descriprionLanguage" value="en">
                                        English
                                    </option>
                                    <option value="ru">Русский</option>

                                </c:if>

                                <c:if test="${pageContext.response.locale.toString().equals('ru_RU')}">

                                        <option selected name="descriprionLanguage" value="ru">
                                            Русский
                                        </option>
                                        <option value="en">English</option>

                                </c:if>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="form-group " style="font-size: 14px">
                    <div class="row">
                        <div class="col-3 p-0">
                            <p class="text-secondary"
                               style="font-size: 17px; margin:1px">${image}</p>
                        </div>
                        <div class="col-9 p-0">
                            <div class="form-group input-group ">
                                <input type="file" class="form-control" id="image" name="image" value="product.ima">
                            </div>
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <div class="row">
                        <div class="col-3 p-0">

                        </div>
                        <div class="col-9 p-0">
                            <img src="<c:url value="/img/${product.id}"/>" style="height: 120px">
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

    <div class="row">
        <div class="col-12 p-0">
            <my:footer/>
        </div>
    </div>
</div>