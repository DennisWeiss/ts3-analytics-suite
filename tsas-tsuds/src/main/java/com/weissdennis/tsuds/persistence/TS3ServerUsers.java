package com.weissdennis.tsuds.persistence;

import java.time.Instant;

public interface TS3ServerUsers {

    Instant getDateTime();

    Long getUsers();
}
