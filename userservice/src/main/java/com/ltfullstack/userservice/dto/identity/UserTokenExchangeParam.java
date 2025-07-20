package com.ltfullstack.userservice.dto.identity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTokenExchangeParam{
    String username;
    String password;
    String grant_type;
    String client_id;
    String client_secret;
    String scope;
}
