package com.weissdennis.application;

import com.weissdennis.ai.RelationUpdater;
import com.weissdennis.database.DbUserInfoWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Ts3socialaiApplication {
    static String configLocation = "config.cfg";
    static String queryName = "TS3SocialAI-Test2";

    public static void main(String[] args) {
        SpringApplication.run(Ts3socialaiApplication.class, args);
        Configuration.loadConfig(configLocation);
        writeUserInfoIntoDB();
        updateRelations();
        ServerQuery serverQuery = new ServerQuery(queryName);
        serverQuery.login();
        serverQuery.getUserData();
    }

    private static void writeUserInfoIntoDB() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        ScheduledFuture scheduledFuture =
                scheduler.scheduleAtFixedRate(new DbUserInfoWriter(Configuration.dbLocation, Configuration.mariaDBLocation), 0, 60, TimeUnit.SECONDS);
    }

    private static void updateRelations() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        ScheduledFuture scheduledFuture = scheduler.scheduleAtFixedRate(new RelationUpdater(), 15, 3600, TimeUnit.SECONDS);
    }
}
