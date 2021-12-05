package ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ticketservice.model.Account;
import ticketservice.model.AccountRole;

import javax.transaction.Transactional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Transactional
    Account findByUsername(String username);

    boolean existsByUsername(String username);

}