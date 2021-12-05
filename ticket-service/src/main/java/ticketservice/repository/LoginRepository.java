package ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticketservice.model.AccountRole;
import ticketservice.model.Login;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByRole(AccountRole role);
}