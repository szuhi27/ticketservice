package ticketservice;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ticketservice.model.Account;
import ticketservice.model.AccountRole;
import ticketservice.repository.AccountRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class AdminInit {

    private final AccountRepository accountRepository;

    @PostConstruct
    private void initAdmin() {
        Account admin = new Account("admin","admin",AccountRole.ADMIN);
        accountRepository.save(admin);
    }

}
