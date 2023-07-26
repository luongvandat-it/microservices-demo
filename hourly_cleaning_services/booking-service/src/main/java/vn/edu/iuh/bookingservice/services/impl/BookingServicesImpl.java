package vn.edu.iuh.bookingservice.services.impl;

import org.springframework.stereotype.Service;
import vn.edu.iuh.bookingservice.models.Booking;
import vn.edu.iuh.bookingservice.repositories.BookingRepository;
import vn.edu.iuh.bookingservice.services.BookingServices;

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