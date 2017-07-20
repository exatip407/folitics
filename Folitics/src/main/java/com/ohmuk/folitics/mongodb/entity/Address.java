package com.ohmuk.folitics.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Address {
    @Field
    private String addressId = "";
    @Field
    private String addressValue = "";

    public Address() {

    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddressValue() {
        return addressValue;
    }

    public void setAddressValue(String addressValue) {
        this.addressValue = addressValue;
    }

    @Override
    public String toString() {
        return "Address [addressId=" + addressId + ", addressValue=" + addressValue + "]";
    }
}