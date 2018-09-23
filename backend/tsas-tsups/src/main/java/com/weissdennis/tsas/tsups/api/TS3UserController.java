package com.weissdennis.tsas.tsups.api;

import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.tsups.service.TS3UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/ts3")
@CrossOrigin
public class TS3UserController {

    private final TS3UserService ts3UserService;

    @Autowired
    public TS3UserController(TS3UserService ts3UserService) {
        this.ts3UserService = ts3UserService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public HttpEntity<Iterable<? extends TS3User>> getAllUsersByNickname(@RequestParam(required = false) String nickname) {
        return new ResponseEntity<>(nickname == null || nickname.equals("") ? ts3UserService.getAllUsers() :
                ts3UserService.getAllUsersByNickname(nickname), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{uniqueId}", method = RequestMethod.GET)
    public HttpEntity<? extends TS3User> getUserByUniqueId(@PathVariable String uniqueId) {
        return ts3UserService.getUserByUniqueId(uniqueId)
                .map(ts3User -> new ResponseEntity<>(ts3User, HttpStatus.OK))
                .orElseThrow(UserNotFoundException::new);
    }



}
