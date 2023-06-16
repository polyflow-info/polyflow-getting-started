package info.polyflow.polyflowminimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import io.holunda.polyflow.bus.jackson.ObjectMapperConfigurationHelper;
import io.holunda.polyflow.bus.jackson.config.FallbackPayloadObjectMapperAutoConfiguration;
import io.holunda.polyflow.taskpool.core.EnablePolyflowTaskPool;
import io.holunda.polyflow.view.jpa.EnablePolyflowJpaView;
import org.axonframework.eventsourcing.eventstore.jpa.DomainEventEntry;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.sql.SQLException;

@SpringBootApplication
@EnableProcessApplication
@EnablePolyflowJpaView
@EnablePolyflowTaskPool
@EntityScan(
        basePackageClasses = {
                DomainEventEntry.class
        }
)
public class PolyflowMinimalApplication {
    @Autowired
    private RuntimeService runtimeService;

    public static void main(String[] args) {
        SpringApplication.run(PolyflowMinimalApplication.class, args);
    }

    // Create process instance after the application started
    @EventListener
    public void processPostDeploy(PostDeployEvent event) {
        runtimeService.startProcessInstanceByKey("loanApproval");
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseaServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    /**
     * Custom object mapper.
     */
    @Bean
    @Qualifier(FallbackPayloadObjectMapperAutoConfiguration.PAYLOAD_OBJECT_MAPPER)
    ObjectMapper objectMapper() {
        return ObjectMapperConfigurationHelper.configurePolyflowJacksonObjectMapper(new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new KotlinModule.Builder().build())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));
    }

    @Bean
    @Qualifier("eventSerializer")
    Serializer mySerializer(ObjectMapper objectMapper) {
        return new JacksonSerializer.Builder().objectMapper(objectMapper).build();
    }
}
