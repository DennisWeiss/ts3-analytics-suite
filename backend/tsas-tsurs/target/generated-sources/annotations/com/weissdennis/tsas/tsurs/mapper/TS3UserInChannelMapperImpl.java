package com.weissdennis.tsas.tsurs.mapper;

import com.weissdennis.tsas.common.ts3users.TS3UserInChannelImpl;
import com.weissdennis.tsas.tsurs.persistence.TS3UserInChannelEntity;
import com.weissdennis.tsas.tsurs.persistence.TS3UserInChannelIdentity;
import java.time.Instant;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-09-21T17:11:58+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_171 (Oracle Corporation)"
)
public class TS3UserInChannelMapperImpl implements TS3UserInChannelMapper {

    @Override
    public TS3UserInChannelEntity ts3UserInChannelImplToTS3UserInChannelEntity(TS3UserInChannelImpl ts3UserInChannelImpl) {
        if ( ts3UserInChannelImpl == null ) {
            return null;
        }

        TS3UserInChannelEntity tS3UserInChannelEntity = new TS3UserInChannelEntity();

        tS3UserInChannelEntity.setTs3UserInChannelIdentity( tS3UserInChannelImplToTS3UserInChannelIdentity( ts3UserInChannelImpl ) );
        tS3UserInChannelEntity.setChannelId( ts3UserInChannelImpl.getChannelId() );

        return tS3UserInChannelEntity;
    }

    @Override
    public TS3UserInChannelImpl ts3UserInChannelEntityToTS3UserInChannelImpl(TS3UserInChannelEntity ts3UserInChannelEntity) {
        if ( ts3UserInChannelEntity == null ) {
            return null;
        }

        TS3UserInChannelImpl tS3UserInChannelImpl = new TS3UserInChannelImpl();

        Instant dateTime = ts3UserInChannelEntityTs3UserInChannelIdentityDateTime( ts3UserInChannelEntity );
        if ( dateTime != null ) {
            tS3UserInChannelImpl.setDateTime( dateTime );
        }
        String uniqueId = ts3UserInChannelEntityTs3UserInChannelIdentityUniqueId( ts3UserInChannelEntity );
        if ( uniqueId != null ) {
            tS3UserInChannelImpl.setUniqueId( uniqueId );
        }
        tS3UserInChannelImpl.setChannelId( ts3UserInChannelEntity.getChannelId() );

        return tS3UserInChannelImpl;
    }

    protected TS3UserInChannelIdentity tS3UserInChannelImplToTS3UserInChannelIdentity(TS3UserInChannelImpl tS3UserInChannelImpl) {
        if ( tS3UserInChannelImpl == null ) {
            return null;
        }

        TS3UserInChannelIdentity tS3UserInChannelIdentity = new TS3UserInChannelIdentity();

        tS3UserInChannelIdentity.setDateTime( tS3UserInChannelImpl.getDateTime() );
        tS3UserInChannelIdentity.setUniqueId( tS3UserInChannelImpl.getUniqueId() );

        return tS3UserInChannelIdentity;
    }

    private Instant ts3UserInChannelEntityTs3UserInChannelIdentityDateTime(TS3UserInChannelEntity tS3UserInChannelEntity) {
        if ( tS3UserInChannelEntity == null ) {
            return null;
        }
        TS3UserInChannelIdentity ts3UserInChannelIdentity = tS3UserInChannelEntity.getTs3UserInChannelIdentity();
        if ( ts3UserInChannelIdentity == null ) {
            return null;
        }
        Instant dateTime = ts3UserInChannelIdentity.getDateTime();
        if ( dateTime == null ) {
            return null;
        }
        return dateTime;
    }

    private String ts3UserInChannelEntityTs3UserInChannelIdentityUniqueId(TS3UserInChannelEntity tS3UserInChannelEntity) {
        if ( tS3UserInChannelEntity == null ) {
            return null;
        }
        TS3UserInChannelIdentity ts3UserInChannelIdentity = tS3UserInChannelEntity.getTs3UserInChannelIdentity();
        if ( ts3UserInChannelIdentity == null ) {
            return null;
        }
        String uniqueId = ts3UserInChannelIdentity.getUniqueId();
        if ( uniqueId == null ) {
            return null;
        }
        return uniqueId;
    }
}
