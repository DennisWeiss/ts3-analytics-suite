package com.weissdennis.ts3socialai;

import com.weissdennis.database.DbLoader;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/ts3")
public class Controller {

   @RequestMapping(value = "/users", method = RequestMethod.GET)
   public List<User> users() throws SQLException {
      DbLoader dbLoader = new DbLoader(Configuration.mariaDBLocation);
      return dbLoader.load();
   }

   @RequestMapping(value = "/user/{nickname}", method = RequestMethod.GET)
   public List<User> user(@PathVariable(value = "nickname") String nickname) throws SQLException {
      DbLoader dbLoader = new DbLoader(Configuration.mariaDBLocation);
      return dbLoader.loadUser(nickname);
   }

   @RequestMapping(value = "/user", method = RequestMethod.GET)
   public User userById(@RequestParam(value = "id") String uniqueId) throws SQLException {
      DbLoader dbLoader = new DbLoader(Configuration.mariaDBLocation);
      return dbLoader.loadUserById(uniqueId);
   }
}
