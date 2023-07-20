package com.example.services;

import com.example.models.Booking;

import java.util.List;

public interface BookingServices {
    Booking saveBooking(Booking booking);

    List<Booking> findBookingsByUser(long userId);
}