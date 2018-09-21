package com.weissdennis.tsas.tsurs;

import com.weissdennis.tsas.tsurs.service.UserRelationService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private final UserRelationService userRelationService;

    public AppStartupRunner(UserRelationService userRelationService) {
        this.userRelationService = userRelationService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRelationService.updateRelations();
    }
}
