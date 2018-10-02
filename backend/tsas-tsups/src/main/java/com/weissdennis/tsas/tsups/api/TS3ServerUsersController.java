package com.weissdennis.tsas.tsups.api;

import com.weissdennis.tsas.common.ts3users.TS3ServerUsers;
import com.weissdennis.tsas.tsups.service.TS3ServerUsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/ts3/server-users")
@CrossOrigin
public class TS3ServerUsersController {

    private final TS3ServerUsersService ts3ServerUsersService;

    @Autowired
    public TS3ServerUsersController(TS3ServerUsersService ts3ServerUsersService) {
        this.ts3ServerUsersService = ts3ServerUsersService;
    }

    @RequestMapping("/series-data")
    @ApiOperation(value = "Gets time series data of users count between given timestamps")
    public HttpEntity<Iterable<? extends TS3ServerUsers>> getServersUsers(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return new ResponseEntity<>(ts3ServerUsersService.getServerUsers(from, to), HttpStatus.OK);
    }
}
