package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingFullDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";
    private final BookingService service;

    @PostMapping
    public ResponseEntity<BookingFullDto> createBooking(@Valid @RequestBody BookingDto bookingDto,
                                                        @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return new ResponseEntity<>(service.saveBooking(bookingDto, userId), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookingFullDto> updateBooking(@PathVariable Long id,
                                                        @RequestHeader(X_SHARER_USER_ID) Long userId,
                                                        @RequestParam Boolean approved) {
        return service.updateBooking(id, userId, approved)
                .map(updatedBooking -> new ResponseEntity<>(updatedBooking, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingFullDto> findBookingById(@PathVariable Long id,
                                                          @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return service.getBooking(id, userId).map(booking -> new ResponseEntity<>(booking, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public Collection<BookingFullDto> findUserBookings(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                       @RequestParam(defaultValue = "ALL") String state,
                                                       @RequestParam(defaultValue = "0") Integer from,
                                                       @RequestParam(defaultValue = "20") Integer size) {
        return service.findUserBookings(userId, state, from, size);
    }

    @GetMapping("/owner")
    public Collection<BookingFullDto> findOwnerBookings(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                        @RequestParam(defaultValue = "ALL") String state,
                                                        @RequestParam(defaultValue = "0") Integer from,
                                                        @RequestParam(defaultValue = "20") Integer size) {
        BookingState.validateState(state);
        return service.findOwnerBookings(userId, state, from, size);
    }
}
