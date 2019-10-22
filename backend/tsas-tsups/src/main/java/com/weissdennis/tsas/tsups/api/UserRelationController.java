package com.weissdennis.tsas.tsups.api;

import com.weissdennis.tsas.common.ts3users.UserRelation;
import com.weissdennis.tsas.tsups.service.UserRelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ts3")
@CrossOrigin
public class UserRelationController {

    private final UserRelationService userRelationService;

    @Autowired
    public UserRelationController(UserRelationService userRelationService) {
        this.userRelationService = userRelationService;
    }

    @RequestMapping(value = "/relations", method = RequestMethod.GET)
    @ApiOperation(value = "Gets relation values of a specific user if a unique id is provided otherwise gets relation values of all users",
            response = UserRelation.class, responseContainer = "List")
    public HttpEntity<Iterable<? extends UserRelation>> getRelations(@RequestParam(required = false) String user, @RequestParam(required = false) Double minRelation) {
        if (minRelation == null) {
            minRelation = 0d;
        }
        return new ResponseEntity<>(userRelationService.getRelations(user, minRelation), HttpStatus.OK);
    }
}
