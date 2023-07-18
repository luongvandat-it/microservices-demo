package com.example.controllers;

import com.example.models.Booking;
import com.example.services.BookingServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private BookingServices bookingServices;

    public BookingController(BookingServices bookingServices) {
        this.bookingServices = bookingServices;
    }

    @PostMapping("/save")
    public Booking saveBooking(Booking booking) {
        return bookingServices.saveBooking(booking);
    }

    @GetMapping("/findBookingsByUser")
    public List<Booking> findBookingsByUser(long userId) {
        return bookingServices.findBookingsByUser(userId);
    }
}