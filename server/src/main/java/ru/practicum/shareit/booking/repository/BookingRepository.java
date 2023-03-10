package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAllByBookerId(long bookerId, Pageable pageable);

    Page<Booking> findAllByBookerIdAndStartBeforeAndEndAfter(
            long bookerId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Booking> findAllByBookerIdAndEndBefore(long bookerId, LocalDateTime end, Pageable pageable);

    Page<Booking> findAllByBookerIdAndStartAfter(long bookerId, LocalDateTime start, Pageable pageable);

    Page<Booking> findAllByBookerIdAndStatus(long bookerId, BookingStatus status, Pageable pageable);

    Collection<Booking> findByItemIdAndStatusAndStartBeforeAndEndAfter(
            long itemId, BookingStatus status, LocalDateTime start, LocalDateTime end);

    @Query(" select b from Item i, Booking b " +
            " where i.owner = ?1 " +
            "   and b.itemId = i.id " +
            "   and (?2 = 'ALL' or " +
            "        (?2 = 'CURRENT' and ?3 between b.start and b.end) or " +
            "        (?2 = 'PAST' and ?3 > b.end) or " +
            "        (?2 = 'FUTURE' and ?3 < b.start) or " +
            "        (?2 = 'WAITING' and b.status = ?2) or " +
            "        (?2 = 'REJECTED' and b.status = ?2) " +
            "       )" +
            " order by b.start desc")
    Page<Booking> findAllByOwnerId(long ownerId, String state, LocalDateTime localDateTime, Pageable pageable);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.itemId = :itemId " +
            "AND b.bookerId <> :userId " +
            "AND b.start < :now " +
            "ORDER BY b.start ASC")
    Optional<Booking> findLastBooking(long itemId, long userId, LocalDateTime now);


    @Query("SELECT b FROM Booking b " +
            "WHERE b.itemId = :itemId " +
            "AND b.bookerId <> :userId " +
            "AND b.start > :now " +
            "ORDER BY b.start DESC")
    Optional<Booking> findNextBooking(long itemId, long userId, LocalDateTime now);


    Optional<Booking> findByItemIdAndBookerIdAndStatusAndEndBefore(
            long itemId, long userId, BookingStatus status, LocalDateTime now);
}
