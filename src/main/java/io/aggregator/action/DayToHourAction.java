package io.aggregator.action;

import java.util.concurrent.CompletableFuture;

import kalix.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.aggregator.api.HourApi;
import io.aggregator.entity.DayEntity;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class DayToHourAction extends AbstractDayToHourAction {

  public DayToHourAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onDayAggregationRequested(DayEntity.DayAggregationRequested event) {

    var result = CompletableFuture.allOf(event.getEpochHoursList().stream()
            .map(epochHour -> HourApi.AggregateHourCommand
                    .newBuilder()
                    .setMerchantId(event.getMerchantKey().getMerchantId())
                    .setEpochHour(epochHour)
                    .setAggregateRequestTimestamp(event.getAggregateRequestTimestamp())
                    .setPaymentId(event.getPaymentId())
                    .build())
            .map(command -> components().hour().aggregateHour(command).execute()).toArray(CompletableFuture[]::new))
        .thenApply(reply -> effects().reply(Empty.getDefaultInstance()));

    return effects().asyncEffect(result);
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }
}
