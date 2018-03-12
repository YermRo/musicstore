package com.epam.musicstore.action.common;

import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.user.RegisterUser;
import com.epam.musicstore.constants.OrderConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    private static final Logger LOG = LoggerFactory.getLogger(Validate.class);
    private static final String CHECK_PARAMETER = "Check parameter '{}' with value '{}' by regex '{}'";
    private static final String WRONG_PARAMETER = "Parameter '{}' with value '{}' is unsuitable.";
    private static final String EMAIL_TAKEN = "email {} has already been taken!";
    private static final String NOT_EMPTY_NUMBER = "not_empty_phone_number.regex";
    private static final String MONEY_REGEX = "money.regex";
    private static final String CORRECT_APARTNENT_NUMBER = "apartment.regex";
    private static final String CORRECT_PHONE_NUMBER = "phone_number.regex";
    private static final String NOT_EMPTY_TEXT = "not_empty_string.regex";
    private static final String NOT_EMPTY_PASSWORD = "not_empty_password.regex";
    private static final String CORRECT_PASSWORD = "password.regex";
    private static final String CORRECT_EMAIL = "email.regex";
    private static final String CORRECT_NAME = "name.regex";
    private static final String CORRECT_POST_INDEX = "postIndex.regex";
    private static final String PROPERTY_PRODUCT_AMOUNT = "product.amount";
    private static final String EMPTY_FIRST_NAME = "emptyFirstName";
    private static final String EMPTY_LAST_NAME = "emptyLastName";
    private static final String EMPTY_PHONE_NUMBER = "emptyPhoneNumber";
    private static final String EMPTY_PASSWORD = "emptyPassword";
    private static final String EMPTY_GENDER_ERROR = "emptyGender";
    private static final String EMAIL_USED_ERROR = "emailUsed";
    private static final String WRONG_EMAIL_ERROR = "wrongEmail";
    private static final String WRONG_FIRSTNAME_ERROR = "wrongFirstName";
    private static final String WRONG_LASTNAME_ERROR = "wrongLastName";
    private static final String WRONG_PHONENUMBER_ERROR = "wrongPhoneNumber";
    private static final String WRONG_PASSWORD_ERROR = "wrongPassword";
    private static final String WRONG_COUNTRY_ERROR = "wrongCountry";
    private static final String WRONG_CITY_ERROR = "wrongCity";
    private static final String WRONG_STREET_ERROR = "wrongStreet";
    private static final String WRONG_BUILDING_ERROR = "wrongBuilding";
    private static final String WRONG_APARTMENT_ERROR = "wrongApartment";
    private static final String WRONG_POST_INDEX_ERROR = "wrongPostIndex";
    private static final String MONEY = "money";
    private static final String ERROR = "Error";
    private static final String TRUE = "true";
    private static final String USED = "used";
    private static final String DIFFERENT_PASSWORD_ERROR = "differentPasswordError";
    private static final String ITEM_AMOUNT = "itemAmount";
    private static final String ITEM_AMOUNT_ERROR = "ItemAmountError";
    private String email;
    private boolean invalid;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String country;
    private String gender;
    private String postIndex;
    private String city;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private Properties properties = new Properties();

    public boolean checkByRegex(String parameter, String parameterName, String regex, HttpServletRequest req) {
        LOG.debug(CHECK_PARAMETER, parameterName, parameter, regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parameter);
        if (!matcher.matches()) {
            LOG.debug(WRONG_PARAMETER, parameterName, parameter);
            req.setAttribute(parameterName, TRUE);
            invalid=true;
            return true;
        }
        return false;
    }

    public boolean checkAmount(HttpServletRequest req, String amount) throws ActionException {
        loadProperties();
        return checkByRegex(amount, OrderConstants.AMOUNT,
                properties.getProperty(PROPERTY_PRODUCT_AMOUNT), req);
    }

    public boolean checkStorageItemAmount(int enteredAmount , int StorageItemAmount){

        if (enteredAmount > StorageItemAmount){
            invalid = true;
        }
        return invalid;
    }

    public boolean checkStorageItemAmount(int amount){
        if (amount <= 0){
            invalid = true;
        }
        return invalid;
    }

    public boolean fullCheck(HttpServletRequest req) throws ServiceException, ActionException {
        loadProperties();
        emailCheck(req);
        emailExistsCheck(req);
        validateUser(req);
        return invalid;
    }

    private void loadProperties() throws ActionException {
        try {
            properties.load(RegisterUser.class.getClassLoader().getResourceAsStream(UserConstants.VALIDATION_PROPERTIES));
        } catch (IOException e) {
            LOG.info(UserConstants.PROPERTIES_ERROR, e);
            throw new ActionException(UserConstants.PROPERTIES_ERROR, e);
        }
    }

    public boolean validateUser(HttpServletRequest req) throws ActionException {
        loadProperties();
        firstNameCheck(req);
        lastNameCheck(req);
        phoneNumberCheck(req);
        genderCheck(req);
        return invalid;
    }



    private boolean emailCheck(HttpServletRequest req) throws ActionException {
        loadProperties();
        email = req.getParameter(UserConstants.EMAIL);

        if( checkByRegex(email, UserConstants.EMAIL,
                properties.getProperty(CORRECT_EMAIL), req)){
            req.setAttribute(WRONG_EMAIL_ERROR, TRUE);
        }

        req.setAttribute(UserConstants.EMAIL, email);
        return invalid;
    }

    private boolean emailExistsCheck(HttpServletRequest req) throws ServiceException {

        UserService userService = new UserService();
        if (!userService.checkEmail(email)) {
            req.setAttribute(EMAIL_USED_ERROR, USED);
            LOG.error(EMAIL_TAKEN, email);
            invalid = true;
        }
        return invalid;
    }


    private boolean genderCheck(HttpServletRequest req) throws ActionException{
        loadProperties();
        gender=req.getParameter(UserConstants.GENDER);


        if(gender.equals(UserConstants.GENDER)){
            req.setAttribute(EMPTY_GENDER_ERROR, TRUE);
            invalid = true;
        }

        req.setAttribute(UserConstants.FIRST_NAME, firstName);
        return invalid;
    }

    private boolean firstNameCheck(HttpServletRequest req) throws ActionException{
        loadProperties();
        firstName=req.getParameter(UserConstants.FIRST_NAME);

        if(checkByRegex(firstName, UserConstants.FIRST_NAME,
                properties.getProperty(NOT_EMPTY_TEXT), req)){
            req.setAttribute(EMPTY_FIRST_NAME, TRUE);
        }
        if(checkByRegex(firstName, UserConstants.FIRST_NAME,
                properties.getProperty(CORRECT_NAME), req)){
            req.setAttribute(WRONG_FIRSTNAME_ERROR, TRUE);
        }

        req.setAttribute(UserConstants.FIRST_NAME, firstName);
        return invalid;
    }

    private boolean lastNameCheck(HttpServletRequest req) throws ActionException{
        loadProperties();
        lastName=req.getParameter(UserConstants.LAST_NAME);
        if(checkByRegex(lastName, UserConstants.LAST_NAME,
                properties.getProperty(NOT_EMPTY_TEXT), req)){
            req.setAttribute(EMPTY_LAST_NAME, TRUE);
        }
        if(checkByRegex(lastName, UserConstants.LAST_NAME,
                properties.getProperty(CORRECT_NAME), req)){
            req.setAttribute(WRONG_LASTNAME_ERROR, TRUE);
        }

        req.setAttribute(UserConstants.LAST_NAME, lastName);
        return invalid;
    }

    public boolean passwordCheck(HttpServletRequest req) throws ActionException{
        loadProperties();
        password=req.getParameter(UserConstants.PASS_WORD);
            if (checkByRegex(password, UserConstants.PASS_WORD,
                    properties.getProperty(NOT_EMPTY_PASSWORD), req)) {
                req.setAttribute(EMPTY_PASSWORD, TRUE);
            }


            if (checkByRegex(password, UserConstants.PASS_WORD,
                    properties.getProperty(CORRECT_PASSWORD), req)) {
                req.setAttribute(WRONG_PASSWORD_ERROR, TRUE);
            }
        req.setAttribute(UserConstants.PASS_WORD, password);
        return invalid;
    }

    public boolean newPasswordCheck(HttpServletRequest req) throws ActionException{
        loadProperties();
        String newPassword = req.getParameter(UserConstants.NEW_PASSWORD);
        String repeatedPassword = req.getParameter(UserConstants.REPEATED_PASSWORD);
        if (!newPassword.equals(repeatedPassword)){
            req.setAttribute(DIFFERENT_PASSWORD_ERROR, TRUE);
            invalid=true;
        }
        return invalid;



    }

    private boolean phoneNumberCheck(HttpServletRequest req) throws ActionException{
        loadProperties();
        phoneNumber=req.getParameter(UserConstants.PHONE_NUMBER);

        if(checkByRegex(phoneNumber, UserConstants.PHONE_NUMBER,
                properties.getProperty(NOT_EMPTY_NUMBER), req)){
            req.setAttribute(EMPTY_PHONE_NUMBER, TRUE);
        }


        if( checkByRegex(phoneNumber, UserConstants.PHONE_NUMBER,
                properties.getProperty(CORRECT_PHONE_NUMBER), req)){
            req.setAttribute(WRONG_PHONENUMBER_ERROR, TRUE);
        }

        req.setAttribute(UserConstants.PHONE_NUMBER, phoneNumber);
        return invalid;
    }

    public boolean validateUserAddress(HttpServletRequest req) throws ActionException {
        loadProperties();
        setAddressData(req);
        checkAddressData(req);

        return invalid;
    }

    private void setAddressData(HttpServletRequest req){
        country = req.getParameter(UserConstants.COUNTRY);
        city = req.getParameter(UserConstants.CITY);
        street = req.getParameter(UserConstants.STREET);
        buildingNumber = req.getParameter(UserConstants.BUILDING_NUMBER);
        apartmentNumber = req.getParameter(UserConstants.APARTMENT_NUMBER);
        postIndex=req.getParameter(UserConstants.POST_INDEX);
    }

    private void checkAddressData(HttpServletRequest req) throws ActionException{
        loadProperties();
        checkByRegex(country, WRONG_COUNTRY_ERROR,
                properties.getProperty(CORRECT_NAME), req);
        checkByRegex(city,WRONG_CITY_ERROR,
                properties.getProperty(CORRECT_NAME), req);
        checkByRegex(street, WRONG_STREET_ERROR,
                properties.getProperty(CORRECT_NAME), req);
        checkByRegex(buildingNumber,WRONG_BUILDING_ERROR,
                properties.getProperty(CORRECT_APARTNENT_NUMBER), req);
        checkByRegex(apartmentNumber, WRONG_APARTMENT_ERROR,
                properties.getProperty(CORRECT_APARTNENT_NUMBER), req);
        checkByRegex(postIndex,WRONG_POST_INDEX_ERROR,
                properties.getProperty(CORRECT_POST_INDEX), req);
    }

    public boolean checkMoney(HttpServletRequest req, String price) throws ActionException {
        loadProperties();
        return checkByRegex(price, MONEY, properties.getProperty(MONEY_REGEX), req);
    }







}




