package com.weissdennis.tsas.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Ban;
import com.github.theholywaffle.teamspeak3.api.wrapper.DatabaseClient;
import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.common.ts3users.TS3UserImpl;
import com.weissdennis.tsas.tsuds.configuration.Ts3PropertiesConfig;
import com.weissdennis.tsas.tsuds.mapper.TS3UserMapper;
import com.weissdennis.tsas.tsuds.persistence.TS3UserEntity;
import com.weissdennis.tsas.tsuds.persistence.TS3UserRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TS3UserRetrievalTask implements Runnable {

    private static Map<String, Date> userUniqueIdToLastUpdated = new HashMap<>();

    private final TS3Api ts3Api;
    private final Ts3PropertiesConfig ts3PropertiesConfig;
    private final TS3UserRepository ts3UserRepository;

    public TS3UserRetrievalTask(TS3Api ts3Api, Ts3PropertiesConfig ts3PropertiesConfig,
                                TS3UserRepository ts3UserRepository) {
        this.ts3Api = ts3Api;
        this.ts3PropertiesConfig = ts3PropertiesConfig;
        this.ts3UserRepository = ts3UserRepository;
    }

    private TS3UserImpl mapDatabaseClientToUser(DatabaseClient databaseClient, List<Ban> bans) {
        TS3UserImpl ts3User = new TS3UserImpl();
        ts3User.setUniqueId(databaseClient.getUniqueIdentifier());
        ts3User.setClientId(databaseClient.getDatabaseId());
        ts3User.setNickName(databaseClient.getNickname());
        ts3User.setIp(databaseClient.getLastIp());
        ts3User.setBanned(bans.stream().anyMatch(ban -> ban.getBannedUId().equals(databaseClient.getUniqueIdentifier())));
        return ts3User.withExtendedUserInfo();
    }

    private boolean getDatabaseClientPredicate(DatabaseClient client) {
        return (!userUniqueIdToLastUpdated.containsKey(client.getUniqueIdentifier()) ||
                new Date().getTime() - userUniqueIdToLastUpdated.get(client.getUniqueIdentifier()).getTime() >
                        TimeUnit.MILLISECONDS.convert(ts3PropertiesConfig.getLocationInfoUpdateInterval(), TimeUnit.DAYS))
                && client.getLastIp() != null;
    }

    private TS3UserEntity mergeUserData(TS3UserEntity ts3UserEntity, TS3User ts3User) {
        TS3UserEntity mergedTs3User = new TS3UserEntity();

        mergedTs3User.setUniqueId(ts3User.getUniqueId());
        mergedTs3User.setBanned(ts3User.isBanned());
        mergedTs3User.setCity(ts3User.getCity() == null ? ts3UserEntity.getCity() : ts3User.getCity());
        mergedTs3User.setClientId(ts3User.getClientId());
        mergedTs3User.setCountry(ts3User.getCountry() == null ? ts3UserEntity.getCountry() : ts3User.getCountry());
        mergedTs3User.setHostName(ts3User.getHostName() == null ? ts3UserEntity.getHostName() : ts3User.getHostName());
        mergedTs3User.setIp(ts3User.getIp());
        mergedTs3User.setLastOnline(ts3User.getLastOnline() == null ? ts3UserEntity.getLastOnline() : ts3User.getLastOnline());
        mergedTs3User.setLatitude(ts3User.getLatitude() == null ? ts3UserEntity.getLatitude() : ts3User.getLatitude());
        mergedTs3User.setLongitude(ts3User.getLongitude() == null ? ts3UserEntity.getLongitude() : ts3User.getLongitude());
        mergedTs3User.setNickName(ts3User.getNickName());
        mergedTs3User.setOrg(ts3User.getOrg() == null ? ts3UserEntity.getOrg() : ts3User.getOrg());
        mergedTs3User.setPostalCode(ts3User.getPostalCode() == null ? ts3UserEntity.getPostalCode() : ts3User.getPostalCode());
        mergedTs3User.setRegion(ts3User.getRegion() == null ? ts3UserEntity.getRegion() : ts3User.getRegion());

        return mergedTs3User;
    }

    @Override
    public void run() {
        List<Ban> bans = ts3Api.getBans();
        ts3Api.getDatabaseClients()
                .parallelStream()
                .filter(this::getDatabaseClientPredicate)
                .map(databaseClient -> mapDatabaseClientToUser(databaseClient, bans))
                .forEach(user -> {
                    ts3UserRepository.findById(user.getUniqueId())
                            .map(ts3UserEntity -> ts3UserRepository.save(mergeUserData(ts3UserEntity, user)))
                            .orElseGet(() -> ts3UserRepository.save(TS3UserMapper.INSTANCE.ts3UserImplToTS3UserEntity(user)));
                    userUniqueIdToLastUpdated.put(user.getUniqueId(), new Date());
                });
    }


}
