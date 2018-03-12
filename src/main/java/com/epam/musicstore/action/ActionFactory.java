package com.epam.musicstore.action;


import com.epam.musicstore.action.cart.AddToCart;
import com.epam.musicstore.action.cart.BuyCart;
import com.epam.musicstore.action.cart.ClearCart;
import com.epam.musicstore.action.cart.RecountCart;
import com.epam.musicstore.action.common.*;
import com.epam.musicstore.action.order.*;
import com.epam.musicstore.action.product.*;
import com.epam.musicstore.action.user.*;
import com.epam.musicstore.constants.PageConstants;

import java.util.Map;
import java.util.HashMap;

public final class ActionFactory {
    private static Map<String, Action> actions;

    static {
        actions = new HashMap<>();
        actions.put("GET/home", new ShowHomePage());
        actions.put("GET/success", new ShowPageAction("success"));
        actions.put("GET/edit/userData", new ShowPageAction("userData"));
        actions.put("GET/locale", new SetLocale());
        actions.put("GET/logout", new Logout());
        actions.put("GET/edit/userAddress", new ShowPageAction("userAddress"));
        actions.put("GET/register", new ShowRegisterPage());
        actions.put("GET/manage/editProduct", new ShowEditProductPage());
        actions.put("GET/manage/addProduct", new ShowAddProductPage());
        actions.put("GET/manage/products", new ShowProductManagePage());
        actions.put("GET/manage/orders", new ShowOrdersManagePage());
        actions.put("GET/manage/users", new ShowUserManagePage());
        actions.put("GET/manage/editUser", new ShowEditUserAsAdminPage());
        actions.put("GET/manage/deleteProduct", new DeleteProduct());
        actions.put("GET/manage/deleteUser", new DeleteUser());
        actions.put("GET/manage/editBalance", new ShowRefillPage());
        actions.put("GET/catalog", new ShowCatalog());
        actions.put("GET/product", new ShowProductPage());
        actions.put("GET/cart", new ShowPageAction("cart"));
        actions.put("GET/cart/buy", new BuyCart());
        actions.put("GET/orderingItem/delete", new DeleteOrderingItem());
        actions.put("GET/cart/clear", new ClearCart());
        actions.put("GET/manage/deleteOrder", new DeleteOrder());
        actions.put("GET/order", new ShowOrderPage());
        actions.put("GET/edit/password", new ShowPageAction(PageConstants.CHANGE_PASSWORD));
        actions.put("GET/myOrders", new ShowMyOrdersPage());
        actions.put("GET/addToCart", new AddToCart());

        actions.put("POST/register", new RegisterUser());
        actions.put("POST/login", new Login());
        actions.put("POST/edit/userData", new EditUser());
        actions.put("POST/edit/userAddress", new EditAddress());
        actions.put("POST/manage/addProduct", new AddProduct());
        actions.put("POST/manage/editProduct", new EditProduct());
        actions.put("POST/manage/editUser", new EditUserAsAdmin());
        actions.put("POST/manage/editBalance", new EditUserCash());
        actions.put("POST/addToCart", new AddToCart());
        actions.put("POST/edit/orderStatus", new EditOrderStatus());
        actions.put("POST/cart/recount", new RecountCart());
        actions.put("POST/edit/password", new ChangePassword());
        actions.put("POST/manage/balance", new EditUserCash());
    }

    public static Action getAction(String actionName) {
        return actions.get(actionName);
    }
}
