package com.example.services.impl;

import com.example.models.Booking;
import com.example.repositories.BookingRepository;
import com.example.services.BookingServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServicesImpl implements BookingServices {
    private final BookingRepository bookingRepository;

    public BookingServicesImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> findBookingsByUser(long userId) {
        return bookingRepository.findBookingsByUser(userId);
    }
}