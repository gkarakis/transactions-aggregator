package io.aggregator.view;

import io.aggregator.entity.PaymentEntity;
import io.aggregator.entity.TransactionMerchantKey;
import io.aggregator.service.RuleService;
import io.aggregator.view.MerchantPaymentModel.MerchantPayment;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MerchantPaymentsEventHandler {

  static MerchantPayment handle(MerchantPayment state, PaymentEntity.ActiveDayAggregated event) {
    Collection<TransactionMerchantKey.MoneyMovement> moneyMovements = RuleService.mergeMoneyMovements(Stream.concat(
        state.getMoneyMovementsList().stream(),
        event.getMoneyMovementsList().stream())
        .collect(Collectors.toList()));
    return state.toBuilder()
        .setMerchantId(event.getMerchantKey().getMerchantId())
        .setPaymentId(event.getPaymentId())
        .clearMoneyMovements()
        .addAllMoneyMovements(moneyMovements)
        .build();
  }

  static MerchantPayment handle(MerchantPayment state, PaymentEntity.PaymentAggregated event) {
    Collection<TransactionMerchantKey.MoneyMovement> moneyMovements = RuleService.mergeMoneyMovements(Stream.concat(
        state.getMoneyMovementsList().stream(),
        event.getMoneyMovementsList().stream())
        .collect(Collectors.toList()));
    return state.toBuilder()
        .setMerchantId(event.getMerchantKey().getMerchantId())
        .setPaymentId(event.getPaymentId())
        .clearMoneyMovements()
        .addAllMoneyMovements(moneyMovements)
        .setPaymentTimestamp(event.getAggregateRequestTimestamp())
        .build();
  }
}
