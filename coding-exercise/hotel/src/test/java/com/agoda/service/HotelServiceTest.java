package com.agoda.service;

import com.agoda.model.Hotel;
import com.agoda.model.ORDER;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HotelServiceTest {
    private List<Hotel> hotels;
    @Before
    public void setUp() throws Exception {
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(new Hotel(1,"standard","bangkok", new BigDecimal(12.0)));
        hotels.add(new Hotel(2,"standard","bangkok", new BigDecimal(20.0)));
        hotels.add(new Hotel(3,"standard","amsterdam", new BigDecimal(2.0)));
        hotels.add(new Hotel(4,"standard","bangkok", new BigDecimal(200.0)));
        hotels.add(new Hotel(5,"standard","bangkok", new BigDecimal(1.0)));
        this.hotels = hotels;
    }

    @Test
    public void getHotelsByCity() throws Exception {

        HotelService hotelService = new HotelService(this.hotels);
        List<Hotel> hotelsByCity = hotelService.getHotelsByCity("bangkok", ORDER.ASC);
        assertEquals(4, hotelsByCity.size());
        assertEquals((Integer) 5, hotelsByCity.get(0).getId());
        assertEquals((Integer) 4, hotelsByCity.get(hotelsByCity.size() - 1).getId());

        hotelsByCity = hotelService.getHotelsByCity("bangkok", ORDER.DESC);
        assertEquals(4, hotelsByCity.size());
        assertEquals((Integer) 4, hotelsByCity.get(0).getId());
        assertEquals((Integer) 5, hotelsByCity.get(hotelsByCity.size() - 1).getId());

    }

}