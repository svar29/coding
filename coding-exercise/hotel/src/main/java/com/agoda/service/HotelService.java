package com.agoda.service;

import au.com.bytecode.opencsv.CSVReader;
import com.agoda.model.Hotel;
import com.agoda.model.ORDER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HotelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelService.class);

    private List<Hotel> hotels;

    public HotelService(String file) throws IOException {
        this.hotels = loadHotels(file);
    }
    HotelService(List<Hotel> hotels){
        this.hotels = hotels;
    }

    private CSVReader readFile(String fileName) throws FileNotFoundException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        return new CSVReader(new InputStreamReader(inputStream));
    }
    private List<Hotel> loadHotels(String fileName) throws IOException {
        List<Hotel> hotels = new ArrayList<>();
        String [] nextLine;
        try {
            CSVReader reader = this.readFile(fileName);
            if (reader != null) {
                reader.readNext();
                while ((nextLine = reader.readNext()) != null) {
                    hotels.add(new Hotel(Integer.parseInt(nextLine[1]),nextLine[2],nextLine[0],
                            BigDecimal.valueOf(Double.parseDouble(nextLine[3]))));
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error in reading file: " + fileName, e);
            throw new IOException("Error loading file");
        }
        return hotels;
    }

    public List<Hotel> getHotelsByCity(final String city, ORDER order) {
        List<Hotel> hotelsInCity = hotels.stream().filter(hotel -> hotel.getCity().equals(city)).collect(Collectors.toList());
        LOGGER.info("Filtering hotels by city: " + city + " in order: " + order);
        if(order != null){
            if (order.equals(ORDER.ASC)) {
                hotelsInCity.sort(Hotel.sortByPriceASC);
            } else {
                hotelsInCity.sort(Hotel.sortByPriceDESC);
            }
        }
        return hotelsInCity;
    }
}
