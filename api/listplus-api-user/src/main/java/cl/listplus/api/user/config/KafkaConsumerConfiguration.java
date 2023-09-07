package cl.listplus.api.user.config;

import cl.listplus.api.user.events.Event;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

    private static final String BOOTSTRAP_ADDRESS = "%s:%s";

    @Value("${kafka.broker.host:localhost}")
    private String host;

    @Value("${kafka.broker.port:29092}")
    private String port;

    @Bean
    public ConsumerFactory<String, Event<?>> consumerFactory() {
        Map<String, String> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format(BOOTSTRAP_ADDRESS, host, port));
        properties.put(JsonSerializer.TYPE_MAPPINGS, "cl.listplus.api.user:cl.listplus.api.user.events.Event");

        JsonDeserializer<Event<?>> jsonDeserializer = new JsonDeserializer<>();
        return new DefaultKafkaConsumerFactory(properties, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Event<?>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Event<?>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
