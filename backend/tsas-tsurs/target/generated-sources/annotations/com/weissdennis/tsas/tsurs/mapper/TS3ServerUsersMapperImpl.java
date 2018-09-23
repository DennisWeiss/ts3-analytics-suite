package com.weissdennis.tsas.tsurs.mapper;

import com.weissdennis.tsas.common.ts3users.TS3ServerUsersImpl;
import com.weissdennis.tsas.tsurs.persistence.TS3ServerUsersEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-09-23T18:15:03+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_171 (Oracle Corporation)"
)
public class TS3ServerUsersMapperImpl implements TS3ServerUsersMapper {

    @Override
    public TS3ServerUsersEntity ts3ServerUsersImplToTS3ServerUsersEntity(TS3ServerUsersImpl ts3ServerUsersImpl) {
        if ( ts3ServerUsersImpl == null ) {
            return null;
        }

        TS3ServerUsersEntity tS3ServerUsersEntity = new TS3ServerUsersEntity();

        tS3ServerUsersEntity.setDateTime( ts3ServerUsersImpl.getDateTime() );
        tS3ServerUsersEntity.setUsers( ts3ServerUsersImpl.getUsers() );

        return tS3ServerUsersEntity;
    }

    @Override
    public TS3ServerUsersImpl ts3ServerUsersEntityToTS3ServerUsersImpl(TS3ServerUsersEntity ts3ServerUsersEntity) {
        if ( ts3ServerUsersEntity == null ) {
            return null;
        }

        TS3ServerUsersImpl tS3ServerUsersImpl = new TS3ServerUsersImpl();

        tS3ServerUsersImpl.setDateTime( ts3ServerUsersEntity.getDateTime() );
        tS3ServerUsersImpl.setUsers( ts3ServerUsersEntity.getUsers() );

        return tS3ServerUsersImpl;
    }
}
