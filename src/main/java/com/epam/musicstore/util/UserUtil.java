package com.epam.musicstore.util;

import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.Address;
import com.epam.musicstore.entity.Role;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import javax.servlet.http.HttpServletRequest;

public class UserUtil {
    private static final String CHANGE_PASSWORD_FORM = "changePasswordForm";
    private static final String TRUE = "true";
    private static final String CHANGE_USER_AS_ADMIN = "changeUserAsAdmin";
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String country;
    private String city;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postIndex;
    private String gender;

    private void setUserParamsFromRequest(HttpServletRequest req) {
        String changePasswordFlag = req.getParameter(CHANGE_PASSWORD_FORM);
        firstName = req.getParameter(UserConstants.FIRST_NAME);
        lastName = req.getParameter(UserConstants.LAST_NAME);
        email = req.getParameter(UserConstants.EMAIL);
        phoneNumber = req.getParameter(UserConstants.PHONE_NUMBER);
        gender = req.getParameter(UserConstants.GENDER);

        if (changePasswordFlag != null && changePasswordFlag.equals(TRUE)) {
            password = req.getParameter(UserConstants.NEW_PASSWORD);
        }
        password = req.getParameter(UserConstants.PASS_WORD);
    }

    private void setUserParamsFromRequestExceptPassword(HttpServletRequest req) {
        firstName = req.getParameter(UserConstants.FIRST_NAME);
        lastName = req.getParameter(UserConstants.LAST_NAME);
        email = req.getParameter(UserConstants.EMAIL);
        phoneNumber = req.getParameter(UserConstants.PHONE_NUMBER);
        gender = req.getParameter(UserConstants.GENDER);
    }

    private void setAddressParamsFromRequest(HttpServletRequest req) {
        country = req.getParameter(UserConstants.COUNTRY);
        city = req.getParameter(UserConstants.CITY);
        street = req.getParameter(UserConstants.STREET);
        buildingNumber = req.getParameter(UserConstants.BUILDING_NUMBER);
        apartmentNumber = req.getParameter(UserConstants.APARTMENT_NUMBER);
        postIndex = req.getParameter(UserConstants.POST_INDEX);

    }

    public User fillUser(HttpServletRequest req) {
        setUserParamsFromRequest(req);
        String md5HexPassword = DigestUtils.md5Hex(password);
        Role role = new Role(1);
        return new User.Builder(firstName, lastName)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setPassword(md5HexPassword)
                .setRole(role)
                .setGender(gender)
                .build();

    }

    public Address fillAddress(HttpServletRequest req) {
        setAddressParamsFromRequest(req);
        return new Address.Builder()
                .setCountry(country)
                .setCity(city)
                .setStreet(street)
                .setBuildingNumber(buildingNumber)
                .setApartmentNumber(apartmentNumber)
                .setPostIndex(postIndex)
                .build();
    }

    public void fillUser(HttpServletRequest req, User user) throws ServiceException {
        setUserParamsFromRequest(req);
        String md5HexPassword;
        DigestUtils.md5Hex(password);
        UserService userService = new UserService();
        if (email != null) {
            user.setEmail(email);
        }
        if (req.getParameter(CHANGE_PASSWORD_FORM).equals(TRUE)) {

            md5HexPassword = DigestUtils.md5Hex(password);
            user.setPassword(md5HexPassword);
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.getRole().setId(Integer.valueOf(req.getParameter(UserConstants.ROLE_ID)));
        user.setGender(gender);
        userService.updateUser(user);
        req.getSession(false).removeAttribute(UserConstants.GENDERS);

    }

    public void fillUserExceptPassword(HttpServletRequest req, User user) throws ServiceException {
        setUserParamsFromRequestExceptPassword(req);
        UserService userService = new UserService();
        if (email != null) {
            user.setEmail(email);
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.getRole().setId(Integer.valueOf(req.getParameter(UserConstants.ROLE_ID)));
        user.setGender(gender);
        userService.updateUser(user);
        req.getSession(false).removeAttribute(UserConstants.GENDERS);

    }

    public void getFilledAddress(HttpServletRequest req, User user) throws ServiceException {
        setAddressParamsFromRequest(req);
        UserService userService = new UserService();
        Address userAddress = user.getAddress();
        userAddress.setCountry(country);
        userAddress.setCity(city);
        userAddress.setStreet(street);
        userAddress.setBuildingNumber(buildingNumber);
        userAddress.setApartmentNumber(apartmentNumber);
        userAddress.setPostIndex(postIndex);
        userService.updateUserAddress(userAddress);
    }
}
