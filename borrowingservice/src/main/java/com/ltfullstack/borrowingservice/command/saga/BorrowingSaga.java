package com.ltfullstack.borrowingservice.command.saga;

import com.ltfullstack.borrowingservice.command.command.DeleteBorrowingCommand;
import com.ltfullstack.borrowingservice.command.event.BorrowingCreatedEvent;
import com.ltfullstack.borrowingservice.command.event.BorrowingDeletedEvent;
import com.ltfullstack.commonservice.command.RollBackStatusBookCommand;
import com.ltfullstack.commonservice.command.UpdateStatusBookCommand;
import com.ltfullstack.commonservice.event.BookRollBackStatusEvent;
import com.ltfullstack.commonservice.event.BookUpdateStatusEvent;
import com.ltfullstack.commonservice.model.BookResponseCommonModel;
import com.ltfullstack.commonservice.model.EmployeeResponseCommonModel;
import com.ltfullstack.commonservice.query.GetBookDetailQuery;
import com.ltfullstack.commonservice.query.GetDetailEmployeeQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Saga
public class BorrowingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowingCreatedEvent event) {
       log.info("BorrowCreatedEvent in Saga for BookId : "+event.getBookId()+ " : EmployeeId :  "+event.getEmployeeId());

       try {
           SagaLifecycle.associateWith("bookId", event.getBookId());
           GetBookDetailQuery getDetailsBookQuery = new GetBookDetailQuery(event.getBookId());
           BookResponseCommonModel bookResponseModel =
                   queryGateway.query(getDetailsBookQuery,
                                   ResponseTypes.instanceOf(BookResponseCommonModel.class))
                           .join();
           if(bookResponseModel.getIsReady() == true) {
               UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(), false,event.getEmployeeId(),event.getId());
               commandGateway.sendAndWait(command);
           }
           else {
               throw new Exception("Sach Da co nguoi Muon");
           }

       }catch (Exception e) {
           rollBackBorrowRecord(event.getId());
           log.info(e.getMessage());
       }
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookUpdateStatusEvent event) {
        log.info("BookUpdateStatusEvent in Saga for BookId : "+event.getBookId());
        try {
            GetDetailEmployeeQuery getDetailsEmployeeQuery = new GetDetailEmployeeQuery(event.getEmployeeId());
            EmployeeResponseCommonModel employeeResponseCommonModel =
                    queryGateway.query(getDetailsEmployeeQuery,
                                    ResponseTypes.instanceOf(EmployeeResponseCommonModel.class))
                            .join();
            if(employeeResponseCommonModel.getIsDisciplined()) {
                throw new Exception("Nhan vien bi ky luat");
            }else{
                // send notification
                log.info("Da muon sach thanh cong");
                SagaLifecycle.end();
            }
        }catch (Exception e) {
            rollBackBookStatus(event.getBookId(),event.getEmployeeId(),event.getBorrowId());
            log.info(e.getMessage());
        }

    }

    private void rollBackBookStatus(String bookId,String employeeId,String borrowId) {
        SagaLifecycle.associateWith("bookId", bookId);
        RollBackStatusBookCommand command = new RollBackStatusBookCommand(bookId, true,employeeId,borrowId);
        commandGateway.sendAndWait(command);
    }

    private void rollBackBorrowRecord(String id) {
        commandGateway.sendAndWait(new DeleteBorrowingCommand(id));
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    public void handle(BorrowingDeletedEvent event) {
        log.info("BorrowDeletedEvent in Saga for Borrowing Id : {} " +
                event.getId());
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "bookId")
    public void handleRollBackBookStatus(BookRollBackStatusEvent event) {
        System.out.println("BookRollBackStatusEvent in Saga for book Id : {} " + event.getBookId());
        rollBackBorrowRecord(event.getBorrowId());
    }
}
