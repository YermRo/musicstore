package com.epam.musicstore.util;

import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.common.Validate;
import com.epam.musicstore.constants.ErrorConstants;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.ProductConstants;
import com.epam.musicstore.entity.Image;
import com.epam.musicstore.entity.Product;
import com.epam.musicstore.entity.ProductType;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Properties;

public class ProductUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ProductUtil.class);
    private static final String INVALID_CONTENT_TYPE = "Invalid content type - {}";
    private static final String PROPERTIES = "Invalid content type - {}";
    private static final String IMAGE_PATH = "imagePath";
    private static final String RELATIVE_IMAGE_PATH = "relativeImagePath";

    private Validate validation = new Validate();
    private String name;
    private String type;
    private String descriptionRU;
    private String descriptionEN;
    private String price;
    private Part imagePart;

    public boolean validateMoney(HttpServletRequest req) throws IOException, ServletException, ActionException {
        setProductParams(req);
        boolean invalid = false;
        if(validation.checkMoney(req, price)) {
            req.setAttribute(ErrorConstants.ERROR_MONEY, ErrorConstants.TRUE);
            invalid = true;
        }
        return invalid;
    }

    private void setProductParams(HttpServletRequest req){
        name = req.getParameter(ProductConstants.NAME);
        type = req.getParameter(ProductConstants.TYPE_ID);
        price = req.getParameter(ProductConstants.PRICE);

        if(req.getParameter(PageConstants.DESCRIPTION_LANGUAGE).equals(PageConstants.DESCRIPTION_RU)) {
            descriptionRU = req.getParameter(ProductConstants.DESCRIPTION);
            descriptionEN=PageConstants.EMPTY;
        }
        else {
            descriptionEN= req.getParameter(ProductConstants.DESCRIPTION);
            descriptionRU=PageConstants.EMPTY;
        }
    }

    public Product getFilledProduct(HttpServletRequest req) {
        setProductParams(req);
        Product filledProduct = new Product();
        filledProduct.setName(name);
        filledProduct.setType(new ProductType(Integer.valueOf(type)));
        filledProduct.setPrice(Money.parse(ProductConstants.KZT + price));
        filledProduct.setRuDescription(descriptionRU);
        filledProduct.setEnDescription(descriptionEN);
        return filledProduct;
    }

    public Image getFilledImage(Image image, HttpServletRequest req)
            throws IOException,ServletException {

        Properties properties = new Properties();
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream("database.properties");
        properties.load(is);

        final String path = properties.getProperty(IMAGE_PATH);
        final Part filePart = req.getPart(ProductConstants.IMAGE);
        final String fileName = getFileName(filePart);
        OutputStream out = null;
        InputStream filecontent = null;


        try {
            File imagePath = new File(path);
            if (!imagePath.exists()){
                imagePath.mkdirs();
            }

            File img = new File(path + File.separator
                    + fileName);

            out = new FileOutputStream(img);
            filecontent = filePart.getInputStream();
            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException fne) {
            System.out.println("Image not found");
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }

        image.setName(fileName);
        image.setTimeModified(DateTime.now());
        image.setPath(properties.getProperty(IMAGE_PATH));
        return image;
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }


    public boolean validateImage(HttpServletRequest req) throws IOException, ServletException  {
        boolean invalid = false;
        if(checkImagePart(req)){
            req.setAttribute(ErrorConstants.ERROR_IMAGE, ErrorConstants.TRUE);
            LOG.error(INVALID_CONTENT_TYPE);
            invalid = true;
        }
        return invalid;
    }

    private boolean checkImagePart(HttpServletRequest req) throws IOException, ServletException {

        imagePart = req.getPart(ProductConstants.IMAGE);
        if(imagePart.getSize() != 0) {
            return !imagePart.getContentType().startsWith(ProductConstants.IMAGE);
        }
        LOG.info(INVALID_CONTENT_TYPE);
        return true;
    }

}