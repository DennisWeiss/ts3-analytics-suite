package com.weissdennis.tsas.tsurs.service;

import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.common.ts3users.TS3UserImpl;
import com.weissdennis.tsas.tsurs.mapper.TS3UserMapper;
import com.weissdennis.tsas.tsurs.persistence.TS3UserEntity;
import com.weissdennis.tsas.tsurs.persistence.TS3UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TS3UserImportService {

    private final TS3UserRepository ts3UserRepository;

    private static Logger logger = LoggerFactory.getLogger(TS3UserImportService.class);

    @Autowired
    public TS3UserImportService(TS3UserRepository ts3UserRepository) {
        this.ts3UserRepository = ts3UserRepository;
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

    @KafkaListener(topics = "ts3_user", containerFactory = "ts3UserConcurrentKafkaListenerContainerFactory")
    public void listen(TS3User ts3User) {
        ts3UserRepository.findById(ts3User.getUniqueId())
                .map(ts3UserEntity -> ts3UserRepository.save(mergeUserData(ts3UserEntity, ts3User)))
                .orElseGet(() -> ts3UserRepository.save(TS3UserMapper.INSTANCE.ts3UserImplToTS3UserEntity((TS3UserImpl) ts3User)));
    }
}
