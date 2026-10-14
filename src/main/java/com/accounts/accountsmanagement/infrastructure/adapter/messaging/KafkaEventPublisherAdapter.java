package com.accounts.accountsmanagement.infrastructure.adapter.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.accounts.accountsmanagement.application.port.out.PublishDomainEventPort;
import com.accounts.accountsmanagement.domain.event.AccountCreatedDomainEvent;
import com.accounts.accountsmanagement.domain.event.DomainEvent;
import com.accounts.accountsmanagement.infrastructure.adapter.messaging.config.KafkaTopicConfig;
import com.accounts.accountsmanagement.infrastructure.adapter.messaging.dto.EventMessage;
import com.accounts.accountsmanagement.infrastructure.adapter.messaging.exception.MessagingException;
import com.accounts.accountsmanagement.infrastructure.adapter.messaging.mapper.EventMessageMapper;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisherAdapter implements PublishDomainEventPort {

  private static final String LOG_PUBLISH_FAILED =
      "[KafkaEventPublisherAdapter] evento no publicado. event={} key={} errorType={}";
  private static final String LOG_PUBLISH_OK =
      "[KafkaEventPublisherAdapter] evento publicado. event={} key={} partition={} offset={}";

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;
  private final KafkaTopicConfig topicConfig;

  @Override
  public void publish(final DomainEvent event) {
    final String key = event.payload().get(AccountCreatedDomainEvent.PAYLOAD_KEY_ACCOUNT_ID);
    final String json = serialize(event);
    try {
      kafkaTemplate
          .send(topicConfig.notificationsTopic(), key, json)
          .whenComplete(
              (result, error) -> {
                if (Objects.nonNull(error)) {
                  log.error(
                      LOG_PUBLISH_FAILED,
                      event.getEventName(),
                      key,
                      error.getClass().getSimpleName());
                  return;
                }
                log.info(
                    LOG_PUBLISH_OK,
                    event.getEventName(),
                    key,
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());
              });
    } catch (final KafkaException exception) {
      // Kafka caido: la cuenta ya fue guardada, no se rompe la peticion. Solo datos tecnicos.
      log.error(
          LOG_PUBLISH_FAILED, event.getEventName(), key, exception.getClass().getSimpleName());
    }
  }

  private String serialize(final DomainEvent event) {
    final EventMessage message = EventMessageMapper.fromDomainEventToMessage(event);
    try {
      return objectMapper.writeValueAsString(message);
    } catch (final JsonProcessingException exception) {
      throw MessagingException.becauseSerializationFailed(event.getEventName(), exception);
    }
  }
}