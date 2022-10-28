package io.aggregator.action;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

import org.junit.jupiter.api.Test;

public class DayToMerchantActionTest {

  @Test
  public void exampleTest() {
    DayToMerchantActionTestKit testKit = DayToMerchantActionTestKit.of(DayToMerchantAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onDayActivatedTest() {
    DayToMerchantActionTestKit testKit = DayToMerchantActionTestKit.of(DayToMerchantAction::new);
    // ActionResult<Empty> result = testKit.onDayActivated(DayEntity.DayActivated.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    DayToMerchantActionTestKit testKit = DayToMerchantActionTestKit.of(DayToMerchantAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
