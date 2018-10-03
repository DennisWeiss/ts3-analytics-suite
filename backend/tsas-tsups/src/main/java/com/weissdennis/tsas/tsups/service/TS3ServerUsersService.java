package com.weissdennis.tsas.tsups.service;

import com.weissdennis.tsas.common.ts3users.TS3ServerUsers;
import com.weissdennis.tsas.common.ts3users.TS3ServerUsersImpl;
import com.weissdennis.tsas.tsups.model.DailyTS3ServerUsers;
import com.weissdennis.tsas.tsups.persistence.TS3ServerUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class TS3ServerUsersService {

    private final TS3ServerUsersRepository ts3ServerUsersRepository;

    @Autowired
    public TS3ServerUsersService(TS3ServerUsersRepository ts3ServerUsersRepository) {
        this.ts3ServerUsersRepository = ts3ServerUsersRepository;
    }

    public Iterable<? extends TS3ServerUsers> getServerUsers(LocalDateTime from, LocalDateTime to) {
        return ts3ServerUsersRepository.findAllByDateTimeAfterAndDateTimeBefore(from.toInstant(ZoneOffset.systemDefault()
                .getRules().getOffset(from)), to.toInstant(ZoneOffset.systemDefault().getRules().getOffset(to)));
    }

    public Iterable<DailyTS3ServerUsers> getDailyServerUsers(LocalDate from, LocalDate to) {
        return getDailyData(getServerUsers(from.atStartOfDay(), to.atStartOfDay()));
    }

    private Iterable<DailyTS3ServerUsers> getDailyData(Iterable<? extends TS3ServerUsers> ts3ServerUsers) {
        Map<Date, Long> dateToUserCount = new TreeMap<>();

        for (TS3ServerUsers ts3ServerUsersData : ts3ServerUsers) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Date.from(ts3ServerUsersData.getDateTime()));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Date date = calendar.getTime();
            if (!dateToUserCount.containsKey(date) || dateToUserCount.get(date) < ts3ServerUsersData.getUsers()) {
                dateToUserCount.put(date, ts3ServerUsersData.getUsers());
            }
        }

        List<DailyTS3ServerUsers> dailyData = new ArrayList<>();

        dateToUserCount.forEach((k, v) -> dailyData.add(new DailyTS3ServerUsers(k, v)));

        return dailyData;
    }

}
