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

  interface Account {
    DualCurrency getLoan(Currency currency, int amount, int time) 
      throws ErrorLoan;
    int getStatus();
    string getKey();
  };

  interface AccountPremium extends Account {
  };
  
  interface AccountManager {
    Account* createNewAccount(int monthIncome)
      throws ErrorNewUser;
  };
};

#endif
