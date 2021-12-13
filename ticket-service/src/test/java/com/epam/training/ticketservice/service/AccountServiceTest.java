package com.epam.training.ticketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ticketservice.model.Account;
import ticketservice.model.AccountRole;
import ticketservice.repository.AccountRepository;
import ticketservice.repository.LoginRepository;
import ticketservice.service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    private Account testAcc;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private LoginRepository loginRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void initAccount() {
        testAcc = Account.builder()
                .username("testAcc")
                .passw("123")
                .accountRole(AccountRole.ADMIN)
                .build();
    }

}
