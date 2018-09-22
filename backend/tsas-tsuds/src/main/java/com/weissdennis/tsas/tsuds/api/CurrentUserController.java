package com.weissdennis.tsas.tsuds.api;

import com.github.theholywaffle.teamspeak3.api.wrapper.Ban;
import com.weissdennis.tsas.tsuds.model.CurrentUser;
import com.weissdennis.tsas.tsuds.service.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/ts3")
@CrossOrigin
public class CurrentUserController {

    private final CurrentUserService currentUserService;

    @Autowired
    public CurrentUserController(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @RequestMapping(value = "/currentusers", method = RequestMethod.GET)
    public HttpEntity<Iterable<CurrentUser>> getCurrentUser() {
        return new ResponseEntity<>(currentUserService.getCurrentUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/bans", method = RequestMethod.GET)
    public HttpEntity<Iterable<Ban>> getBans() {
        return new ResponseEntity<>(currentUserService.getBans(), HttpStatus.OK);
    }
}
