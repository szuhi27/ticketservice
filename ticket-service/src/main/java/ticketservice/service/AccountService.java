package ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.exception.NotAdminException;
import ticketservice.model.Account;
import ticketservice.model.AccountRole;
import ticketservice.model.Login;
import ticketservice.repository.AccountRepository;
import ticketservice.repository.LoginRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final LoginRepository loginRepository;

    public void loginPriv(String uname, String pw) throws DoesNotExistsException {
        if (accountRepository.existsByUsername(uname)) {
            Account account = accountRepository.findByUsername(uname);
            if (Objects.equals(account.getUsername(), uname) && Objects.equals(account.getPassw(), pw)
                    && account.getAccountRole() == AccountRole.ADMIN) {
                loginRepository.save(new Login(account.getUsername(),account.getAccountRole()));
            } else {
                throw new DoesNotExistsException("Login failed due to incorrect credentials");
            }
        } else {
            throw new DoesNotExistsException("User does not exists!");
        }
    }

    public void signout() throws DoesNotExistsException {
        if (loginRepository.findAll().size() == 0) {
            throw new DoesNotExistsException("Ur not logged in!");
        } else {
            loginRepository.deleteAll();
        }
    }

    public String describeAcc() {
        Login login = loginRepository.findByRole(AccountRole.ADMIN);
        if (login == null) {
            return new String("You are not signed in");
        } else {
            return new String("Signed in with privileged account " + login.getUname());
        }
    }

    public void isAdmin() throws NotAdminException {
        //Login login = loginRepository.findByRole(AccountRole.ADMIN);
        //if (login == null) {
        if(!loginRepository.existsByRole(AccountRole.ADMIN)){
            throw new NotAdminException();
        }
    }

}
