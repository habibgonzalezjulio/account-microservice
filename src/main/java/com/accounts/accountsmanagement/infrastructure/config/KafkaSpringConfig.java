package com.accounts.accountsmanagement.infrastructure.config;

import com.accounts.accountsmanagement.infrastructure.adapter.messaging.config.KafkaTopicConfig;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/** Productor Kafka hacia Aiven (SASL_SSL + CA en formato PEM). */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class KafkaSpringConfig {

  private static final String PROP_BOOTSTRAP_SERVERS = "${kafka.bootstrap-servers}";
  private static final String PROP_SECURITY_PROTOCOL = "${kafka.security-protocol}";
  private static final String PROP_SASL_MECHANISM = "${kafka.sasl-mechanism}";
  private static final String PROP_SASL_USERNAME = "${kafka.sasl-username}";
  private static final String PROP_SASL_PASSWORD = "${kafka.sasl-password}";
  private static final String PROP_CA_CERTIFICATE = "${kafka.ssl-ca-certificate}";
  private static final String PROP_TOPIC_NOTIFICATIONS = "${kafka.topic.notifications}";

  private static final String ACKS_ALL = "all";
  private static final String TRUSTSTORE_TYPE_PEM = "PEM";
  private static final String ESCAPED_NEW_LINE = "\\n";
  private static final String NEW_LINE = "\n";
  private static final String JAAS_SCRAM_TEMPLATE =
      "org.apache.kafka.common.security.scram.ScramLoginModule required "
          + "username=\"%s\" password=\"%s\";";
  private static final int MAX_BLOCK_MS = 5_000;
  private static final int REQUEST_TIMEOUT_MS = 10_000;
  private static final int DELIVERY_TIMEOUT_MS = 20_000;

  private static final String LOG_KAFKA_INIT =
      "[KafkaSpringConfig] Productor Kafka inicializado. servers={} topic={}";

  @Value(PROP_BOOTSTRAP_SERVERS)
  private String bootstrapServers;

  @Value(PROP_SECURITY_PROTOCOL)
  private String securityProtocol;

  @Value(PROP_SASL_MECHANISM)
  private String saslMechanism;

  @Value(PROP_SASL_USERNAME)
  private String saslUsername;

  @Value(PROP_SASL_PASSWORD)
  private String saslPassword;

  @Value(PROP_CA_CERTIFICATE)
  private String caCertificate;

  @Value(PROP_TOPIC_NOTIFICATIONS)
  private String notificationsTopic;

  @Bean
  public ProducerFactory<String, String> producerFactory() {
    final Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.ACKS_CONFIG, ACKS_ALL);
    props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
    props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, MAX_BLOCK_MS);
    props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, REQUEST_TIMEOUT_MS);
    props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, DELIVERY_TIMEOUT_MS);
    applySecurity(props);

    log.info(LOG_KAFKA_INIT, bootstrapServers, notificationsTopic);
    return new DefaultKafkaProducerFactory<>(props);
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate(
      final ProducerFactory<String, String> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public KafkaTopicConfig kafkaTopicConfig() {
    return new KafkaTopicConfig(notificationsTopic);
  }

  private void applySecurity(final Map<String, Object> props) {
    props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
    if (!saslUsername.isBlank()) {
      props.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
      props.put(
          SaslConfigs.SASL_JAAS_CONFIG,
          String.format(JAAS_SCRAM_TEMPLATE, saslUsername, saslPassword));
    }
    if (!caCertificate.isBlank()) {
      props.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, TRUSTSTORE_TYPE_PEM);
      props.put(
          SslConfigs.SSL_TRUSTSTORE_CERTIFICATES_CONFIG,
          caCertificate.replace(ESCAPED_NEW_LINE, NEW_LINE));
    }
  }
}