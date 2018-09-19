package com.weissdennis.tsuds.persistence;

import java.time.Instant;

public interface TS3UserInChannel {

    Integer getChannelId();

    String getUniqueId();

    Instant getDateTime();
}
