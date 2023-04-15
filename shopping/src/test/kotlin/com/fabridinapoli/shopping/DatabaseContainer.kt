package com.fabridinapoli.shopping

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.configuration.FluentConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class DatabaseContainer {

    val postgresContainer: PostgreSQLContainer<*> = PostgreSQLContainer(DockerImageName.parse("postgres:14.1"))
        .withDatabaseName("shopping")
        .withUsername("postgres")
        .withPassword("postgres")
        .also{ it.start() }

    val dataSource = HikariConfig()
        .apply {
            jdbcUrl = postgresContainer.jdbcUrl
            username = postgresContainer.username
            password = postgresContainer.password
            driverClassName = postgresContainer.driverClassName
        }.let {
            HikariDataSource(it)
        }.also {
            Flyway(
                FluentConfiguration().dataSource(it)
            ).migrate()
        }
}
