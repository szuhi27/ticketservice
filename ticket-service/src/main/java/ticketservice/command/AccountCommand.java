package ticketservice.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.service.AccountService;

@ShellComponent
@RequiredArgsConstructor
public class AccountCommand {

    private final AccountService accountService;

    @ShellMethod(value = "login priv", key = "sign in privileged")
    public String loginPriv(String uname, String pw) {
        try {
            accountService.loginPriv(uname,pw);
        } catch (DoesNotExistsException e) {
            return e.getMessage();
        }
        return uname + " logged in";
    }

    @ShellMethod(value = "sing out", key = "sign out")
    public String singOut() {
        try {
            accountService.signout();
        } catch (DoesNotExistsException e) {
            return e.getMessage();
        }
        return "signed out";
    }

    @ShellMethod(value = "descr acc",key = "describe account")
    public String describeAcc() {
        return accountService.describeAcc();
    }

}
