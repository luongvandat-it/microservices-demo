package vn.edu.iuh.bookingservice.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.bookingservice.models.Booking;
import vn.edu.iuh.bookingservice.services.BookingServices;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
@Log4j2
public class BookingController {
    private final BookingServices bookingServices;

    public BookingController(BookingServices bookingServices) {
        this.bookingServices = bookingServices;
    }

    @PostMapping("/save")
    public ResponseEntity<Booking> saveBooking(@RequestBody Booking booking, @RequestHeader String roles) {
        if (!roles.contains("ROLE_ADMIN") && !roles.contains("ROLE_GUEST")) {
            log.info(roles);
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(bookingServices.saveBooking(booking));
    }

    @GetMapping("/findBookingsByUser")
    public ResponseEntity<List<Booking>> findBookingsByUser(@RequestParam long userId, @RequestHeader String roles) {
        if (!roles.contains("ROLE_ADMIN") && !roles.contains("ROLE_GUEST")) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(bookingServices.findBookingsByUser(userId));
    }
}