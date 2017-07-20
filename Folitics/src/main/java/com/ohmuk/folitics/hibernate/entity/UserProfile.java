package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * 
 * @author Mayank Sharma
 *
 */
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = true)
    private Long mobileNo;

    @Column(nullable = true, length = 1024)
    private String hobbies;

    @Column(nullable = true, length = 255)
    private String City;

    @Column(nullable = true, length = 255)
    private String country;

    @Column(nullable = true, length = 255)
    private String currentLocation;

    @Column(nullable = true, length = 255)
    private String nationality;

    @Column(nullable = true, length = 255)
    private String occupation;

    @Column(nullable = true, length = 255)
    private String motherTongue;

    /**
     * @return the mobileNo
     */
    public Long getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo the mobileNo to set
     */
    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * @return the hobbies
     */
    public String getHobbies() {
        return hobbies;
    }

    /**
     * @param hobbies the hobbies to set
     */
    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return City;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        City = city;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the currentLocation
     */
    public String getCurrentLocation() {
        return currentLocation;
    }

    /**
     * @param currentLocation the currentLocation to set
     */
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * @return the occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * @param occupation the occupation to set
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * @return the motherTongue
     */
    public String getMotherTongue() {
        return motherTongue;
    }

    /**
     * @param motherTongue the motherTongue to set
     */
    public void setMotherTongue(String motherTongue) {
        this.motherTongue = motherTongue;
    }
}
