import sys
import Ice
import Banks

with Ice.initialize(sys.argv) as communicator:
    currencyList = "EUR, USD, CHF, GBP, JPY, PLN"
    status = 0
    PESEL = None

    print("Bank Port? 5d")
    port = input()
    adminServObj = communicator.stringToProxy(
        "admin/admin:tcp -h localhost -p {}:udp -h localhost -p {}".format(port, port))
    adminServ = Banks.AccountManagerPrx.checkedCast(adminServObj)

    if (adminServ is None):
        raise BaseException("Invalid proxy")

    ######################################
    ####    CREATE NEW USER OR CONNECT
    ######################################

    try:
        print("0-create new; 1-connect")
        choice = input()
        context = {}
        if(choice == "0"):
            print("monthIncome?")
            mIncome = int(input())
            print("PESEL?")
            PESEL = input()

            context["name"] = "name1"
            context["surname"] = "surname"
            context["PESEL"] = PESEL

            accountPrx = adminServ.createNewAccount(mIncome, context)
            print("New account:\tkey: " + str(accountPrx.getKey()))
        else:
            print("PESEL?")
            PESEL = input()
            print("0-standard; 1-premium")
            line = input()
            if(line == "0"):
                accType = "standard"
            else:
                accType = "premium"
            accountPrxObj = communicator.stringToProxy("{}/{}:tcp -h localhost -p {}:udp -h localhost -p {}".format(accType, PESEL, port, port))
            accountPrx = Banks.AccountPrx.checkedCast(accountPrxObj)
    except Banks.ErrorNewUser:
        print("ErrorNewUser")
    except Ice.ObjectNotExistException:
        print("ERROR: User not found")

    ######################################
    ####    GET ACCOUNT TYPE AND KEY
    ######################################

    if(accountPrx is None):
        raise BaseException("Invalid proxy")
    if(accountPrx.ice_isA("::Banks::AccountPremium")):
        print("Account type: PREMIUM")
    elif(accountPrx.ice_isA("::Banks::Account")):
        print("Account type: STANDARD")

    accKey = accountPrx.getKey()
    print("Account key: " + str(accKey))


    ######################################
    ####    MAIN LOOP
    ######################################

    while(True):
        print("0-getStatus, 1-getLoan")
        line = input()
        if(line == "x"):
            break

        if(line == "0"):
            stat = accountPrx.getStatus()
            print(stat)
        elif(line == "1"):
            ctx = {}
            ctx["key"] = str(accKey)
            ctx["PESEL"] = PESEL
            print("currency? " + currencyList +" 0-default")
            line = input()
            if(line == "0"):
                curr = Banks.Currency.EUR
                amount = 1000
                time = 100
            else:
                if(line.upper()=="EUR") : curr = Banks.Currency.EUR
                elif(line.upper()=="USD") : curr = Banks.Currency.USD
                elif(line.upper()=="CHF") : curr = Banks.Currency.CHF
                elif(line.upper()=="GBP") : curr = Banks.Currency.GBP
                elif(line.upper()=="JPY") : curr = Banks.Currency.JPY
                elif(line.upper()=="PLN") : curr = Banks.Currency.PLN
                print("amount?")
                line = input()
                amount = int(line)
                print("time?")
                line = input()
                time = int(line)

            print("loan:\ncurrency: {}\nammount: {}\ntime: {}".format(
                curr, amount, time))

            try:
                loanDet = accountPrx.getLoan(curr, amount, time, ctx)
                print("loan: amount in local:{}, in global ({}): {}".format(
                    loanDet.localCurrencyValue, loanDet.globalCurrency, loanDet.globalCurrencyValue))
            except Banks.ErrorLoan:
                print("Loan cannot be granted")
        else:
            print("Wrong")


    communicator.destroy()
