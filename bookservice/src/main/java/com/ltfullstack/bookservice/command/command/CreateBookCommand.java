package com.ltfullstack.bookservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookCommand {

    @TargetAggregateIdentifier
    private String id;
    private String name;
    private String author;
    private Boolean isReady;
}
