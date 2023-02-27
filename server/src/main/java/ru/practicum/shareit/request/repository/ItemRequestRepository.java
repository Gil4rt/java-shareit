package ru.practicum.shareit.request.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.model.ItemRequest;
@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    Page<ItemRequest> findByRequestorOrderByCreatedDesc(long userId, Pageable pageable);

    Page<ItemRequest> findByRequestorNotOrderByCreatedDesc(long userId, Pageable pageable);
}

