package com.weissdennis.tsas.tsups.api;

import com.weissdennis.tsas.common.ts3users.TS3ServerUsers;
import com.weissdennis.tsas.tsups.model.DailyTS3ServerUsers;
import com.weissdennis.tsas.tsups.service.TS3ServerUsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/ts3/server-users")
@CrossOrigin
public class TS3ServerUsersController {

    private final TS3ServerUsersService ts3ServerUsersService;

    @Autowired
    public TS3ServerUsersController(TS3ServerUsersService ts3ServerUsersService) {
        this.ts3ServerUsersService = ts3ServerUsersService;
    }

    @RequestMapping(value = "/series-data", method = RequestMethod.GET)
    @ApiOperation(value = "Gets time series data of users count between given timestamps")
    public HttpEntity<Iterable<? extends TS3ServerUsers>> getServersUsers(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to
    ) {
        return new ResponseEntity<>(ts3ServerUsersService.getServerUsers(
                from.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime(),
                to.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        ), HttpStatus.OK);
    }

    @RequestMapping(value = "/daily-data", method = RequestMethod.GET)
    @ApiOperation(value = "Gets max user count of all days between given dates")
    public HttpEntity<Iterable<DailyTS3ServerUsers>> getDailyServerUsers(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return new ResponseEntity<>(ts3ServerUsersService.getDailyServerUsers(from, to), HttpStatus.OK);
    }
}
