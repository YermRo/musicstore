package com.epam.musicstore.entity;

public class ProductType extends LocaleName  {
    public ProductType(){

    }

    public ProductType(Integer id){
        setId(id);
    }

    @Override
    public String toString() {
        return "ProductType{}" + super.toString();
    }
}
