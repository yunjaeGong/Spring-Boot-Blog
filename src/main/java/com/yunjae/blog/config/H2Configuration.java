package com.yunjae.blog.config;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class H2Configuration {

    private final String PORT = "9092";

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource datasource() throws SQLException {
        Server server = Server.createTcpServer(
                "-tcp",
                "-tcpAllowOthers",
                "-ifNotExists",
                "-tcpPort", PORT).start();

        if(server.isRunning(true)) {
            System.out.println("server is running!");
            System.out.println("h2 tcp server url: " + server.getURL());
            System.out.println("h2 tcp server port: " + server.getPort());
        }
        return new HikariDataSource();
    }

}
