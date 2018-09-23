package com.weissdennis.tsas.tsurs.mapper;

import com.weissdennis.tsas.common.ts3users.TS3UserImpl;
import com.weissdennis.tsas.tsurs.persistence.TS3UserEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-09-21T17:11:58+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_171 (Oracle Corporation)"
)
public class TS3UserMapperImpl implements TS3UserMapper {

    @Override
    public TS3UserEntity ts3UserImplToTS3UserEntity(TS3UserImpl ts3UserImpl) {
        if ( ts3UserImpl == null ) {
            return null;
        }

        TS3UserEntity tS3UserEntity = new TS3UserEntity();

        tS3UserEntity.setClientId( ts3UserImpl.getClientId() );
        tS3UserEntity.setUniqueId( ts3UserImpl.getUniqueId() );
        tS3UserEntity.setNickName( ts3UserImpl.getNickName() );
        tS3UserEntity.setIp( ts3UserImpl.getIp() );
        tS3UserEntity.setHostName( ts3UserImpl.getHostName() );
        tS3UserEntity.setCity( ts3UserImpl.getCity() );
        tS3UserEntity.setRegion( ts3UserImpl.getRegion() );
        tS3UserEntity.setCountry( ts3UserImpl.getCountry() );
        tS3UserEntity.setLongitude( ts3UserImpl.getLongitude() );
        tS3UserEntity.setLatitude( ts3UserImpl.getLatitude() );
        tS3UserEntity.setPostalCode( ts3UserImpl.getPostalCode() );
        tS3UserEntity.setOrg( ts3UserImpl.getOrg() );
        tS3UserEntity.setLastOnline( ts3UserImpl.getLastOnline() );
        tS3UserEntity.setBanned( ts3UserImpl.isBanned() );

        return tS3UserEntity;
    }

    @Override
    public TS3UserImpl ts3UserEntityToTS3UserImpl(TS3UserEntity ts3UserEntity) {
        if ( ts3UserEntity == null ) {
            return null;
        }

        TS3UserImpl tS3UserImpl = new TS3UserImpl();

        tS3UserImpl.setClientId( ts3UserEntity.getClientId() );
        tS3UserImpl.setUniqueId( ts3UserEntity.getUniqueId() );
        tS3UserImpl.setNickName( ts3UserEntity.getNickName() );
        tS3UserImpl.setIp( ts3UserEntity.getIp() );
        tS3UserImpl.setHostName( ts3UserEntity.getHostName() );
        tS3UserImpl.setCity( ts3UserEntity.getCity() );
        tS3UserImpl.setRegion( ts3UserEntity.getRegion() );
        tS3UserImpl.setCountry( ts3UserEntity.getCountry() );
        tS3UserImpl.setLongitude( ts3UserEntity.getLongitude() );
        tS3UserImpl.setLatitude( ts3UserEntity.getLatitude() );
        tS3UserImpl.setPostalCode( ts3UserEntity.getPostalCode() );
        tS3UserImpl.setOrg( ts3UserEntity.getOrg() );
        tS3UserImpl.setLastOnline( ts3UserEntity.getLastOnline() );
        tS3UserImpl.setBanned( ts3UserEntity.isBanned() );

        return tS3UserImpl;
    }
}
