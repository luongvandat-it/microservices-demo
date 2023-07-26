package vn.edu.iuh.bookingservice.services;



import vn.edu.iuh.bookingservice.models.Booking;

import java.util.List;

public interface BookingServices {
    Booking saveBooking(Booking booking);

    List<Booking> findBookingsByUser(long userId);
}