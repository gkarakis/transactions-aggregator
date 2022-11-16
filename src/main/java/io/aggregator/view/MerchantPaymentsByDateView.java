package io.aggregator.view;

import kalix.javasdk.view.View;
import kalix.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import io.aggregator.entity.PaymentEntity;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the View Service described in your io/aggregator/view/merchant_payments_by_date.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class MerchantPaymentsByDateView extends AbstractMerchantPaymentsByDateView {

  public MerchantPaymentsByDateView(ViewContext context) {
  }

  @Override
  public MerchantPaymentModel.MerchantPayment emptyState() {
    return MerchantPaymentModel.MerchantPayment.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<MerchantPaymentModel.MerchantPayment> onPaymentAggregated(MerchantPaymentModel.MerchantPayment state, PaymentEntity.PaymentAggregated event) {
    return effects().updateState(MerchantPaymentsEventHandler.handle(state, event));
  }

  @Override
  public View.UpdateEffect<MerchantPaymentModel.MerchantPayment> ignoreOtherEvents(MerchantPaymentModel.MerchantPayment state, Any any) {
    return effects().ignore();
  }
}
