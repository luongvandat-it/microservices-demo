package vn.edu.iuh.bookingservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.bookingservice.models.Booking;
import vn.edu.iuh.bookingservice.services.BookingServices;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingServices bookingServices;

    public BookingController(BookingServices bookingServices) {
        this.bookingServices = bookingServices;
    }

    @PostMapping("/save")
    public ResponseEntity<Booking> saveBooking(Booking booking) {
        return ResponseEntity.ok(bookingServices.saveBooking(booking));
    }

    @GetMapping("/findBookingsByUser")
    public ResponseEntity<List<Booking>> findBookingsByUser(long userId) {
        return ResponseEntity.ok(bookingServices.findBookingsByUser(userId));
    }
}