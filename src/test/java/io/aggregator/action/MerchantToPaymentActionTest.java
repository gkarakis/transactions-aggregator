package io.aggregator.action;

import org.junit.jupiter.api.Test;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class MerchantToPaymentActionTest {

  @Test
  public void exampleTest() {
    MerchantToPaymentActionTestKit testKit = MerchantToPaymentActionTestKit.of(MerchantToPaymentAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onMerchantAggregationRequestedTest() {
    MerchantToPaymentActionTestKit testKit = MerchantToPaymentActionTestKit.of(MerchantToPaymentAction::new);
    // ActionResult<Empty> result = testKit.onMerchantAggregationRequested(MerchantEntity.MerchantAggregationRequested.newBuilder()...build());
  }

  @Test
  public void onMerchantPaymentRequestedTest() {
    MerchantToPaymentActionTestKit testKit = MerchantToPaymentActionTestKit.of(MerchantToPaymentAction::new);
    // ActionResult<Empty> result = testKit.onMerchantPaymentRequested(MerchantEntity.MerchantPaymentRequested.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    MerchantToPaymentActionTestKit testKit = MerchantToPaymentActionTestKit.of(MerchantToPaymentAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
