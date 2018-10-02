package com.weissdennis.tsas.tsups.api;

import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.tsups.service.TS3UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/ts3")
@CrossOrigin
@Api(description = "Operations giving data of users")
public class TS3UserController {

    private final TS3UserService ts3UserService;

    @Autowired
    public TS3UserController(TS3UserService ts3UserService) {
        this.ts3UserService = ts3UserService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ApiOperation(value = "Gets all users with the given nickname", response = Iterable.class)
    public HttpEntity<Iterable<? extends TS3User>> getAllUsersByNickname(@RequestParam(required = false) String nickname) {
        return new ResponseEntity<>(nickname == null || nickname.equals("") ? ts3UserService.getAllUsers() :
                ts3UserService.getAllUsersByNickname(nickname), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{uniqueId}", method = RequestMethod.GET)
    @ApiOperation(value = "Gets a specific user by its unique id", response = TS3User.class)
    public HttpEntity<? extends TS3User> getUserByUniqueId(@PathVariable String uniqueId) {
        return ts3UserService.getUserByUniqueId(uniqueId)
                .map(ts3User -> new ResponseEntity<>(ts3User, HttpStatus.OK))
                .orElseThrow(UserNotFoundException::new);
    }



}
