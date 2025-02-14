package io.aggregator.entity;

import com.google.protobuf.Empty;
import io.aggregator.TimeTo;
import io.aggregator.api.StripedSecondApi;
import io.aggregator.service.MoneyMovementKey;
import io.aggregator.service.RuleService;
import kalix.javasdk.eventsourcedentity.EventSourcedEntityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the Event Sourced Entity Service described in your io/aggregator/api/striped_second_api.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StripedSecond extends AbstractStripedSecond {

  @SuppressWarnings("unused")
  private final String entityId;

  static final Logger log = LoggerFactory.getLogger(StripedSecond.class);

  public StripedSecond(EventSourcedEntityContext context) {
    this.entityId = context.entityId();
  }

  @Override
  public StripedSecondEntity.StripedSecondState emptyState() {
    return StripedSecondEntity.StripedSecondState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> addLedgerItems(StripedSecondEntity.StripedSecondState state, StripedSecondApi.AddLedgerItemsCommand command) {
    return handle(state, command);
  }

  @Override
  public Effect<Empty> aggregateStripedSecond(StripedSecondEntity.StripedSecondState state, StripedSecondApi.AggregateStripedSecondCommand command) {
    return handle(state, command);
  }

  @Override
  public StripedSecondEntity.StripedSecondState stripedSecondActivated(StripedSecondEntity.StripedSecondState state, StripedSecondEntity.StripedSecondActivated event) {
    return handle(state, event);
  }

  @Override
  public StripedSecondEntity.StripedSecondState stripedSecondLedgeringActivitiesAdded(StripedSecondEntity.StripedSecondState state, StripedSecondEntity.StripedSecondLedgeringActivitiesAdded event) {
    return handle(state, event);
  }

  @Override
  public StripedSecondEntity.StripedSecondState stripedSecondAggregated(StripedSecondEntity.StripedSecondState state, StripedSecondEntity.StripedSecondAggregated event) {
    return handle(state, event);
  }

  private Effect<Empty> handle(StripedSecondEntity.StripedSecondState state, StripedSecondApi.AddLedgerItemsCommand command) {
    log.debug(Thread.currentThread().getName() + " - state: {}\nAddLedgerItemsCommand: {}", state, command);
    log.info(Thread.currentThread().getName() + " - RECEIVED COMMAND: AddLedgerItemsCommand");

    boolean newEntry = command.getLedgerItemList().stream()
        .allMatch(ledgerItem -> state.getLedgeringActivityList().stream()
            .noneMatch(existingLedgeringActivity -> existingLedgeringActivity.getLedgeringActivityKey().getTransactionId().equals(command.getTransactionId()) &&
                    existingLedgeringActivity.getLedgeringActivityKey().getServiceCode().equals(ledgerItem.getServiceCode()) &&
                    existingLedgeringActivity.getLedgeringActivityKey().getAccountFrom().equals(ledgerItem.getAccountFrom()) &&
                    existingLedgeringActivity.getLedgeringActivityKey().getAccountTo().equals(ledgerItem.getAccountTo()) &&
                    existingLedgeringActivity.getTimestamp().equals(command.getTimestamp())));
    if (!newEntry) {
      return effects().reply(Empty.getDefaultInstance());
    }

    return effects()
        .emitEvents(eventsFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  private Effect<Empty> handle(StripedSecondEntity.StripedSecondState state, StripedSecondApi.AggregateStripedSecondCommand command) {
    log.debug("state: {}\nAggregateStripedSecondCommand: {}", state, command);
    log.info(Thread.currentThread().getName() + " - RECEIVED COMMAND: AggregateStripedSecondCommand");

    return effects()
        .emitEvents(eventsFor(state, command))
        .thenReply(newState -> Empty.getDefaultInstance());
  }

  static StripedSecondEntity.StripedSecondState handle(StripedSecondEntity.StripedSecondState state, StripedSecondEntity.StripedSecondActivated event) {
    log.debug(Thread.currentThread().getName() + " - StripedSecondActivated: {}", event);
    log.info(Thread.currentThread().getName() + " - RECEIVED EVENT: StripedSecondActivated");

    return state.toBuilder()
        .setMerchantKey(
            TransactionMerchantKey.MerchantKey
                .newBuilder()
                .setMerchantId(event.getMerchantKey().getMerchantId())
                .build())
        .setStripe(event.getStripe())
        .setEpochSecond(event.getEpochSecond())
        .setEpochMinute(TimeTo.fromEpochSecond(event.getEpochSecond()).toEpochMinute())
        .setEpochHour(TimeTo.fromEpochSecond(event.getEpochSecond()).toEpochHour())
        .setEpochDay(TimeTo.fromEpochSecond(event.getEpochSecond()).toEpochDay())
        .build();
  }

  static StripedSecondEntity.StripedSecondState handle(StripedSecondEntity.StripedSecondState state, StripedSecondEntity.StripedSecondLedgeringActivitiesAdded event) {
    log.debug(Thread.currentThread().getName() + " - StripedSecondLedgeringActivitiesAdded: {}", event);
    log.info(Thread.currentThread().getName() + " - RECEIVED EVENT: StripedSecondLedgeringActivitiesAdded");

    var newState = state.toBuilder();
    event.getLedgeringActivityList()
//        .filter(ledgerEntry -> state.getLedgerEntriesList().stream()
//            .noneMatch(existingLedgerEntry -> existingLedgerEntry.getTransactionKey().equals(ledgerEntry.getTransactionKey()) &&
//                existingLedgerEntry.getTimestamp().equals(ledgerEntry.getTimestamp())))
        .forEach(newState::addLedgeringActivity);
    return newState.setShopId(event.getShopId()).build();
  }

  static StripedSecondEntity.StripedSecondState handle(StripedSecondEntity.StripedSecondState state, StripedSecondEntity.StripedSecondAggregated event) {
    log.info(Thread.currentThread().getName() + " - RECEIVED EVENT: StripedSecondAggregated");

    var ledgerEntries = state.getLedgeringActivityList().stream()
        .map(ledgerEntry -> {
          if (ledgerEntry.getAggregateRequestTimestamp().getSeconds() == 0) {
            return ledgerEntry.toBuilder()
                .setAggregateRequestTimestamp(event.getAggregateRequestTimestamp())
                .build();
          } else {
            return ledgerEntry;
          }
        })
        .toList();

    return state.toBuilder()
        .clearLedgeringActivity()
        .addAllLedgeringActivity(ledgerEntries)
        .build();
  }

  static List<?> eventsFor(StripedSecondEntity.StripedSecondState state, StripedSecondApi.AddLedgerItemsCommand command) {
    var stripedSecondLedgerItemsAdded = StripedSecondEntity.StripedSecondLedgeringActivitiesAdded
        .newBuilder()
        .setMerchantKey(
            TransactionMerchantKey.MerchantKey
                .newBuilder()
                .setMerchantId(command.getMerchantId())
                .build())
            .setShopId(command.getShopId())
        .setEpochSecond(command.getEpochSecond())
        .setStripe(command.getStripe())
        .setTimestamp(command.getTimestamp())
        .setEventType(command.getEventType())
        .addAllLedgeringActivity(
            command.getLedgerItemList().stream()
                .map(ledgerItem -> StripedSecondEntity.LedgeringActivity.newBuilder()
                    .setLedgeringActivityKey(StripedSecondEntity.LedgeringActivityKey.newBuilder()
                        .setTransactionId(command.getTransactionId())
                        .setServiceCode(ledgerItem.getServiceCode())
                        .setAccountFrom(ledgerItem.getAccountFrom())
                        .setAccountTo(ledgerItem.getAccountTo())
                        .build())
                    .setAmount(ledgerItem.getAmount())
                    .setEpochSecond(command.getEpochSecond())
                    .setStripe(command.getStripe())
                    .setTimestamp(command.getTimestamp())
                    .build())
                .collect(Collectors.toList())
        )
        .build();

    var isInactive = state.getLedgeringActivityCount() == 0 || state.getLedgeringActivityList().stream()
        .allMatch(transaction -> transaction.getAggregateRequestTimestamp().getSeconds() > 0);

    if (isInactive) {
      var stripedSecondActivated = StripedSecondEntity.StripedSecondActivated.newBuilder()
          .setMerchantKey(
              TransactionMerchantKey.MerchantKey.newBuilder()
                  .setMerchantId(command.getMerchantId())
                  .build())
          .setEpochSecond(command.getEpochSecond())
          .setStripe(command.getStripe())
          .build();

      return List.of(stripedSecondActivated, stripedSecondLedgerItemsAdded);
    } else {
      return List.of(stripedSecondLedgerItemsAdded);
    }
  }

  static List<?> eventsFor(StripedSecondEntity.StripedSecondState state, StripedSecondApi.AggregateStripedSecondCommand command) {
    var ledgerEntries = state.getLedgeringActivityList().stream()
        .filter(ledgerEntry -> ledgerEntry.getAggregateRequestTimestamp().getSeconds() == 0)
        .toList();

    if (ledgerEntries.size() == 0) {
      return List.of(StripedSecondEntity.StripedSecondAggregated.newBuilder()
          .setMerchantKey(state.getMerchantKey())
          .setEpochSecond(state.getEpochSecond())
          .setStripe(state.getStripe())
          .setAggregateRequestTimestamp(command.getAggregateRequestTimestamp())
          .setPaymentId(command.getPaymentId())
          .build());
    } else {
      Map<MoneyMovementKey, TransactionMerchantKey.MoneyMovement> summarisedMoneyMovementsMap = new HashMap<>();
      ledgerEntries.stream()
          .map(ledgerEntry -> TransactionMerchantKey.MoneyMovement.newBuilder()
              .setAccountFrom(ledgerEntry.getLedgeringActivityKey().getAccountFrom())
              .setAccountTo(ledgerEntry.getLedgeringActivityKey().getAccountTo())
              .setAmount(ledgerEntry.getAmount())
              .setTransactionId(ledgerEntry.getLedgeringActivityKey().getTransactionId())
                  .setServiceCode(ledgerEntry.getLedgeringActivityKey().getServiceCode())
              .build())
          .forEach(transfer -> {
            MoneyMovementKey key = MoneyMovementKey.builder()
                .from(transfer.getAccountFrom())
                .to(transfer.getAccountTo())
                .build();
            summarisedMoneyMovementsMap.merge(key, transfer, (transfer1, transfer2) -> TransactionMerchantKey.MoneyMovement.newBuilder()
                .setAccountFrom(transfer1.getAccountFrom())
                .setAccountTo(transfer1.getAccountTo())
                .setAmount(RuleService.sumFunction(transfer1.getAmount(), transfer2.getAmount()))
                .build());
          });
      var lastUpdate = ledgerEntries.stream()
          .map(StripedSecondEntity.LedgeringActivity::getTimestamp)
          .max(TimeTo.comparator())
          .get();

      return List.of(StripedSecondEntity.StripedSecondAggregated.newBuilder()
          .setMerchantKey(state.getMerchantKey())
          .setEpochSecond(state.getEpochSecond())
          .setStripe(state.getStripe())
          .setAggregateRequestTimestamp(command.getAggregateRequestTimestamp())
          .setLastUpdateTimestamp(lastUpdate)
          .setPaymentId(command.getPaymentId())
          .addAllMoneyMovements(summarisedMoneyMovementsMap.values())
          .setShopId(state.getShopId())
          .build());
    }
  }

}
