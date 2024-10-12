package com.ltfullstack.borrowingservice.command.event;

import com.ltfullstack.borrowingservice.command.data.Borrowing;
import com.ltfullstack.borrowingservice.command.data.BorrowingRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BorrowingEventsHandler {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @EventHandler
    public void on(BorrowingCreatedEvent event){
        Borrowing model = new Borrowing();
        model.setId(event.getId());
        model.setBorrowingDate(event.getBorrowingDate());
        model.setBookId(event.getBookId());
        model.setEmployeeId(event.getEmployeeId());
        borrowingRepository.save(model);
    }

    @EventHandler
    public void on(BorrowingDeletedEvent event){
        Optional<Borrowing> oldEntity = borrowingRepository.findById(event.getId());
        oldEntity.ifPresent(borrowing -> borrowingRepository.delete(borrowing));
    }
}
