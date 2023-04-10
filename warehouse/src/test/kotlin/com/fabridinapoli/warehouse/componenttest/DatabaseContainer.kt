package com.fabridinapoli.warehouse.componenttest

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.configuration.FluentConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class DatabaseContainer {

    val postgresContainer: PostgreSQLContainer<*> = PostgreSQLContainer(DockerImageName.parse("postgres:14.1"))
        .withDatabaseName("warehouse")
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
