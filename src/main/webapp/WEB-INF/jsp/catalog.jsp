<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<fmt:bundle basename="i18n">
    <fmt:message key="common.product.name" var="name"/>
    <fmt:message key="common.product.type" var="type"/>
    <fmt:message key="common.product.price" var="price"/>
    <fmt:message key="common.product.description" var="description"/>
    <fmt:message key="common.delete" var="delete"/>
    <fmt:message key="product.productId" var="id"/>
    <fmt:message key="product.newProduct" var="add"/>
    <fmt:message key="product.addToCart" var="add_to_cart"/>
    <fmt:message key="product.view" var="view"/>
    <fmt:message key="common.edit" var="edit"/>
    <fmt:message key="common.delete" var="delete"/>
    <fmt:message key="product.inStock" var="in_stock"/>
    <fmt:message key="error.productNotAvailable" var="not_available"/>


</fmt:bundle>

<%--@elvariable id="types" type="java.util.List"--%>
<%--@elvariable id="products" type="java.util.List"--%>
<%--@elvariable id="types" type="src.main.java.com.epam.rodion.musicstore.entity.ProductType"--%>
<%--@elvariable id="item" type="src.main.java.com.epam.rodion.musicstore.entity.StorageItem"--%>
<%--@elvariable id="product" type="src.main.java.com.epam.rodion.musicstore.entity.Product"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>
<%--@elvariable id="storageItems" type="java.util.List"--%>


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


    <div class="row">
        <div class="col-2" style="padding: 0px;">
            <my:sidebar/>
        </div>
        <div class="col-1" style="padding: 0px;">

        </div>



        <div class="col-9">
            <div class="row">


                <c:forEach items="${storageItems}" var="item" varStatus="itemRow">

                    <div class="col-md-4">

                        <div class="card mb-3 box-shadow" style="border-color: #343A40 ; border-width: 2px">
                            <div class="row justify-content-center" style="height: 255px">
                                <a href="<c:url value="/do/product?id=${item.id}"/>"><img
                                        style="max-height: 240px; max-width: 290px; margin-top: 15px"
                                        src="<c:url value="/img/${item.product.id}"/>"></a>
                            </div>
                            <div class="card-body" style="padding-top: 2px">

                                <div class="row" style="height:75px">
                                    <a href="<c:url value="/do/product?id=${item.id}"/>"
                                       class="card-link text-dark">
                                        <p class="card-text" style="margin-right: 17px; margin-left: 17px">
                                                ${item.product.name}
                                        </p>
                                    </a>
                                </div>


                                <div class="row" style="height: 35px">
                                    <a href="<c:url value="/do/product?id=${item.id}"/>"
                                       class="card-link text-dark">
                                        <p class="font-weight-bold" style="font-size: 20px; margin-right: 17px;
                                        color: #F16143; margin-left: 22px">
                                            ₸ ${item.product.price.amount.intValue()}
                                        </p>
                                    </a>
                                </div>

                                <div class="d-flex justify-content-between align-items-center">
                                    <form action="<c:url value="/do/addToCart"/>" method="post" style="margin: 0px">
                                        <input type="hidden" name="product" value="${item.product.id}">
                                        <input type="hidden" name="amount" value="1">
                                        <div class="btn-group">
                                            <a class="btn btn-dark btn-sm"
                                               href="<c:url value="/do/product?id=${item.id}"/>"
                                               role="button">
                                                    ${view}
                                            </a>

                                            <c:if test="${(loggedUser.role.name.equals('user')
                                        || loggedUser.role.name.equals('admin'))}">
                                                <c:if test="${!(errorMap.get(itemRow.index).equals('itemAmountError'))}">
                                                    <button class="btn  btn-dark btn-sm" value="submit" type="submit"
                                                            style="background-color: #F16143; border-color: #F16143;
                                                    color: #212529;">
                                                            ${add_to_cart}
                                                    </button>
                                                </c:if>




                                            </c:if>
                                        </div>
                                    </form>

                                    <c:if test="${!(errorMap.get(itemRow.index).equals('itemAmountError'))}">
                                        <small class="text-muted">${in_stock} ${item.amount}</small>
                                    </c:if>
                                    <c:if test="${errorMap.get(itemRow.index).equals('itemAmountError')}">
                                        <small class="text-danger" >${not_available}</small>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>

                </c:forEach>


            </div>
        </div>
    </div>


    <div class="row justify-content-center" style="margin-top: 30px">
        <div class="col-12 p-0">
            <my:pagination url="/do/catalog" pagesCount="${pagesCount}"/>
        </div>
    </div>


    <div class="row justify-content-center">
        <div class="col-12 p-0">
            <my:footer/>
        </div>
    </div>
</div>