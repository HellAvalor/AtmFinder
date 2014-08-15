package com.andreykaraman.atmfinderukraine.model;

/**
 * Created by Andrew on 12.08.2014.
 */

//        lat: 0                  // Широта
//        lng: 0,                 // Долгота
//        address: '',            // Адресс
//        city: ''                // Город
//        bank: '',               // Название банка
//        workTime: '9:00-20:00', // Время работы
//        locationType: ''        // Местоположения банкомата

public class Atm {
    long lat;
    long lng;
    String address;
    String city;
    String bank;
    String workTime;
    String locationType;

    public Atm(long lat, long lng, String address, String city, String bank, String workTime, String locationType) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.city = city;
        this.bank = bank;
        this.workTime = workTime;
        this.locationType = locationType;
    }

    public long getLat() {
        return lat;
    }

    public long getLng() {
        return lng;
    }

    public String getAdress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getBank() {
        return bank;
    }

    public String getWorkTime() {
        return workTime;
    }

    public String getLocationType() {
        return locationType;
    }
}
