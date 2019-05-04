#ifndef CURRENCIES_ICE
#define CURRENCIES_ICE

module Banks
{
  enum Currency { EUR, USD, CHF, GBP, JPY, PLN };
  const Currency localCurrency = PLN;
  enum AccountType { STANDARD, PREMIUM };

  exception ErrorLoan {};
  exception ErrorNewUser {};
  exception ErrorConnectUser {};

  struct DualCurrency {
    Currency globalCurrency;
    int globalCurrencyValue;
    int localCurrencyValue;
  };

  struct NewAccountDetails {
    AccountType accountType;
    string key;
  };
  
  interface AccountManager {
    NewAccountDetails createNewAccount(int monthIncome)
      throws ErrorNewUser;
    NewAccountDetails connectAccount()
      throws ErrorConnectUser;
  };

  interface Account {
    DualCurrency getLoan(Currency currency, int amount, int time) 
      throws ErrorLoan;
    int getStatus();
  };

  interface AccountPremium extends Account {
  };
};

#endif
