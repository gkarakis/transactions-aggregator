package io.aggregator.service;

import io.aggregator.api.StripedSecondApi;
import io.aggregator.entity.TransactionEntity;
import io.aggregator.entity.TransactionMerchantKey;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RuleService {

  public static final String JPMC_ACCOUNT = "JPMC";
  public static final String TAX_ACCOUNT = "TAX";
  public static final String CARD_SCHEME_ACCOUNT = "CARD-SCHEME";

  public static Iterable<StripedSecondApi.LedgerItem> applyRules(String merchant, List<TransactionEntity.TransactionIncidentService> transactionIncidentServices) {
    return transactionIncidentServices.stream()
        .flatMap(pricedItem -> applyRules(merchant, pricedItem))
        .collect(Collectors.toList());
  }

  static Stream<StripedSecondApi.LedgerItem> applyRules(String merchant, TransactionEntity.TransactionIncidentService transactionIncidentService) {
    String merchantAccount = findMerchantAccount(merchant);

    return switch (transactionIncidentService.getServiceCode()) {
      case "SVC1" -> Stream.of(
          StripedSecondApi.LedgerItem.newBuilder()
              .setServiceCode(transactionIncidentService.getServiceCode())
              .setAmount(transactionIncidentService.getServiceAmount())
              .setAccountFrom(JPMC_ACCOUNT)
              .setAccountTo(merchantAccount)
              .build()
      );
      case "SVC2" -> Stream.of(
          StripedSecondApi.LedgerItem.newBuilder()
              .setServiceCode(transactionIncidentService.getServiceCode())
              .setAmount(transactionIncidentService.getServiceAmount())
              .setAccountFrom(merchantAccount)
              .setAccountTo(JPMC_ACCOUNT)
              .build()
      );
      case "SVC3" -> Stream.of(
          StripedSecondApi.LedgerItem.newBuilder()
              .setServiceCode(transactionIncidentService.getServiceCode())
              .setAmount(transactionIncidentService.getServiceAmount())
              .setAccountFrom(merchantAccount)
              .setAccountTo(TAX_ACCOUNT)
              .build()
      );
      case "SVC4" -> Stream.of(
          StripedSecondApi.LedgerItem.newBuilder()
              .setServiceCode(transactionIncidentService.getServiceCode())
              .setAmount(transactionIncidentService.getServiceAmount())
              .setAccountFrom(merchantAccount)
              .setAccountTo(CARD_SCHEME_ACCOUNT)
              .build()
      );
      default -> Stream.empty();
    };
  }

  private static String findMerchantAccount(String merchantId) {
    return "MERCHANT-" + merchantId.toUpperCase();
  }

  public static Collection<TransactionMerchantKey.MoneyMovement> mergeMoneyMovements(List<TransactionMerchantKey.MoneyMovement> moneyMovements) {
    Map<MoneyMovementKey, TransactionMerchantKey.MoneyMovement> summarisedMoneyMovementsMap = new HashMap<>();
    moneyMovements.forEach(moneyMovement -> summarisedMoneyMovementsMap.merge(
        toMoneyMovementKey(moneyMovement),
        moneyMovement,
        mergeFunction
    ));
    return summarisedMoneyMovementsMap.values();
  }

  private static MoneyMovementKey toMoneyMovementKey(TransactionMerchantKey.MoneyMovement moneyMovement) {
    return MoneyMovementKey.builder()
        .from(moneyMovement.getAccountFrom())
        .to(moneyMovement.getAccountTo())
        .build();
  }

  public static String sumFunction(String amount1, String amount2) {
    return new BigDecimal(amount1).add(new BigDecimal(amount2)).toString();
  }

  private static final BiFunction<TransactionMerchantKey.MoneyMovement, TransactionMerchantKey.MoneyMovement, TransactionMerchantKey.MoneyMovement> mergeFunction = (moneyMovement1, moneyMovement2) -> TransactionMerchantKey.MoneyMovement.newBuilder()
      .setAccountFrom(moneyMovement1.getAccountFrom())
      .setAccountTo(moneyMovement1.getAccountTo())
      .setAmount(sumFunction(moneyMovement1.getAmount(), moneyMovement2.getAmount()))
      .build();
}

//RULES BY SERVICE:
//JPMC      MERCHANT
//MERCHANT  JPMC
//MERCHANT  TAX
//MERCHANT  CARD-SCHEME
