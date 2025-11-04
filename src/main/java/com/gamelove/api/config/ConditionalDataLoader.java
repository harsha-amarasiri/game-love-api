package com.gamelove.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Did not use migrations to prevent overengineering
 * Added this to prevent causing conflicts with data.sql inserts in the dev profile
 */
@Slf4j
@Configuration
@Profile("dev")
public class ConditionalDataLoader {

    public static final String SCHEMA_SQL = "schema.sql";
    public static final String DATA_SQL = "data.sql";

    private final DataSource dataSource;

    @Autowired
    public ConditionalDataLoader(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public CommandLineRunner initDatabase() {

        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                populateSchema();
                populateData(conn);
            }
        };
    }

    private void populateSchema() {
        try {
            populateWithSql(new ClassPathResource(SCHEMA_SQL));
        } catch (Exception e) {
            log.error("Error populating schema: {}", e.getMessage());
        }
    }

    private void populateData(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        // Check both tables
        ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) FROM GL_PLAYERS");
        rs1.next();
        long players = rs1.getLong(1);

        ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) FROM GL_GAMES");
        rs2.next();
        long games = rs2.getLong(1);

        // Execute data.sql only if BOTH tables are empty
        if (players == 0 && games == 0) {
            populateWithSql(new ClassPathResource(DATA_SQL));
            log.info("Populated data with {}", DATA_SQL);
        } else {
            log.info("Skipped populating data (Players: {}, Games: {})", players, games);
        }
    }

    private void populateWithSql(ClassPathResource script) {
        log.debug("Executing script: {}", script.getFilename());

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(script);
        populator.execute(dataSource);
    }
}
