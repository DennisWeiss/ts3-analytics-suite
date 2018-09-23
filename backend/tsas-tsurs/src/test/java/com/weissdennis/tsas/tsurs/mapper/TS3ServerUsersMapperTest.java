package com.weissdennis.tsas.tsurs.mapper;

import com.weissdennis.tsas.common.ts3users.TS3ServerUsers;
import com.weissdennis.tsas.common.ts3users.TS3ServerUsersImpl;
import com.weissdennis.tsas.tsurs.persistence.TS3ServerUsersEntity;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;


public class TS3ServerUsersMapperTest {



    @Test
    public void ts3ServerUsersImplToTS3ServerUsersEntity() {
        Instant instant = Instant.now();
        Long userCount = 10L;
        TS3ServerUsersImpl ts3ServerUsers = new TS3ServerUsersImpl(instant, userCount);

        TS3ServerUsersEntity ts3ServerUsersEntity = TS3ServerUsersMapper.INSTANCE
                .ts3ServerUsersImplToTS3ServerUsersEntity(ts3ServerUsers);

        assertEquals(instant.getNano(), ts3ServerUsersEntity.getDateTime().getNano());
        assertEquals(userCount, ts3ServerUsersEntity.getUsers());
    }

    @Test
    public void ts3ServerUsersEntityToTS3ServerUsersImpl() {
        Instant instant = Instant.now();
        Long userCount = 10L;
        TS3ServerUsersEntity ts3ServerUsersEntity = new TS3ServerUsersEntity();
        ts3ServerUsersEntity.setDateTime(instant);
        ts3ServerUsersEntity.setUsers(userCount);

        TS3ServerUsers ts3ServerUsers = TS3ServerUsersMapper.INSTANCE.ts3ServerUsersEntityToTS3ServerUsersImpl
                (ts3ServerUsersEntity);

        assertEquals(instant.getNano(), ts3ServerUsers.getDateTime().getNano());
        assertEquals(userCount, ts3ServerUsers.getUsers());
    }
}