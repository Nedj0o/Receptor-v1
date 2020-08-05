package prep.service.impl;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LastLoggedInService {
    private Instant loggedOn;

    public LastLoggedInService() {
    }

    public Instant getLoggedOn() {
        return loggedOn;
    }

    public void setLoggedOn(Instant loggedOn) {
        this.loggedOn = loggedOn;
    }
}
