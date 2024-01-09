package com.lalala.chart.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(basePackages = ["com.lalala.chart.repository"])
@EnableReactiveMongoAuditing
class MongoConfiguration(
    private val env: Environment
) : AbstractReactiveMongoConfiguration() {

    override fun getDatabaseName() = "chart"

    override fun reactiveMongoClient() = mongoClient()

    override fun mappingMongoConverter(
        databaseFactory: ReactiveMongoDatabaseFactory,
        customConversions: MongoCustomConversions,
        mappingContext: MongoMappingContext
    ): MappingMongoConverter {
        val converter = MappingMongoConverter(NoOpDbRefResolver.INSTANCE, mappingContext)
        converter.customConversions = customConversions
        converter.setCodecRegistryProvider(databaseFactory)
        converter.setTypeMapper(DefaultMongoTypeMapper(null))
        return converter
    }

    @Bean
    fun mongoClient(): MongoClient {
        val uri = env.getProperty("spring.data.mongodb.uri") ?: "mongodb://localhost"
        return MongoClients.create(uri)
    }

    @Bean
    override fun reactiveMongoTemplate(
        databaseFactory: ReactiveMongoDatabaseFactory,
        mongoConverter: MappingMongoConverter
    ): ReactiveMongoTemplate = ReactiveMongoTemplate(mongoClient(), databaseName)
}
