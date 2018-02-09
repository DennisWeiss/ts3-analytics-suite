package com.weissdennis.application;

import com.weissdennis.ai.User;
import com.weissdennis.database.DbLoader;
import com.weissdennis.database.RelationWrapper;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/ts3")
public class Controller {

   @CrossOrigin
   @RequestMapping(value = "/users", method = RequestMethod.GET)
   public List<User> users() throws SQLException {
      DbLoader dbLoader = new DbLoader(Configuration.mariaDBLocation);
      return dbLoader.load();
   }

   @CrossOrigin
   @RequestMapping(value = "/user/{nickname}", method = RequestMethod.GET)
   public List<User> user(@PathVariable(value = "nickname") String nickname) throws SQLException {
      DbLoader dbLoader = new DbLoader(Configuration.mariaDBLocation);
      return dbLoader.loadUser(nickname);
   }

   @CrossOrigin
   @RequestMapping(value = "/user", method = RequestMethod.GET)
   public User userById(@RequestParam(value = "id") String uniqueId) throws SQLException {
      DbLoader dbLoader = new DbLoader(Configuration.mariaDBLocation);
      return dbLoader.loadUserById(uniqueId);
   }

   @CrossOrigin
   @RequestMapping(value = "/relations", method = RequestMethod.GET)
   public List<RelationWrapper> relations() throws  SQLException {
      DbLoader dbLoader = new DbLoader(Configuration.mariaDBLocation);
      return dbLoader.loadRelations();
   }

   @CrossOrigin
   @RequestMapping(value = "/relation/{nickname}", method = RequestMethod.GET)
   public List<RelationWrapper> relationsOfUser(@PathVariable(value = "nickname") String nickname) throws SQLException {
      DbLoader dbLoader = new DbLoader(Configuration.mariaDBLocation);
      return dbLoader.loadRelationOfUser(nickname);
   }

   @CrossOrigin
   @RequestMapping(value = "/currentusers", method = RequestMethod.GET)
   public List<CurrentUser> currentUsers() {
      return Ts3socialaiApplication.serverQuery.getCurrentUsers();
   }

   @CrossOrigin
   @RequestMapping(value = "/bans", method = RequestMethod.GET)
   public List<String> bans() {
      return Ts3socialaiApplication.serverQuery.getBans();
   }
}
