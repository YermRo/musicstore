package com.epam.musicstore.web.servlet;

import com.epam.musicstore.entity.Image;
import com.epam.musicstore.service.ProductService;
import com.epam.musicstore.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

import java.io.*;


public class ImgServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ImgServlet.class);
    private static final int DEFAULT_SIZE = 1024;
    private static final String PRODUCT_ID_IMAGE_LOADED = "Product (id = {}) image loaded. {}";
    private static final String COULDN_T_LOAD_IMAGE = "Couldn't load image";
    private static final String IMAGES = "productImages";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        final byte[] buffer = new byte[DEFAULT_SIZE];
        int length;
        String imagePath;
        try (ServletOutputStream sos = resp.getOutputStream()) {
            ProductService productService = new ProductService();
            String productId = pathInfo.substring(1);
            Image productImage = productService.getProductPreviewImage(productId);

            if (productImage.getPath().startsWith(IMAGES)) {
                imagePath = productImage.getPath();
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(imagePath);
                while ((length = inputStream.read(buffer)) != -1) {
                    sos.write(buffer, 0, length);
                }
            } else {
                imagePath = productImage.getPath() + "/" +
                        productImage.getName();
                try (FileInputStream content = new FileInputStream(imagePath)) {
                    while ((length = content.read(buffer)) != -1) {
                        sos.write(buffer, 0, length);
                    }
                }
            }

            LOG.debug(PRODUCT_ID_IMAGE_LOADED, productId, productImage);

        } catch (ServiceException | IOException e) {
            throw new ServletException(COULDN_T_LOAD_IMAGE, e);
        }
    }
}


