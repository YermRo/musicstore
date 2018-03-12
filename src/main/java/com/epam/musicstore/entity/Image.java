package com.epam.musicstore.entity;
import org.joda.time.DateTime;

import java.io.InputStream;

public class Image extends BaseEntity {
    private String name;
    private DateTime timeModified;
    private Product product;
    private String path;
    private InputStream imageStream;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Image image = (Image) o;

        if (name != null ? !name.equals(image.name) : image.name != null) return false;
        if (timeModified != null ? !timeModified.equals(image.timeModified) : image.timeModified != null) return false;
        if (product != null ? !product.equals(image.product) : image.product != null) return false;
        return path != null ? path.equals(image.path) : image.path == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (timeModified != null ? timeModified.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                ", timeModified=" + timeModified +
                ", product=" + product +
                ", path='" + path + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(DateTime timeModified) {
        this.timeModified = timeModified;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public InputStream getImageStream() {
        return imageStream;
    }

    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
    }
}
