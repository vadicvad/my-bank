package com.mybank.batch;

import com.mybank.domain.*;

public class AccumulateSavingsBatch {

  public AccumulateSavingsBatch() {
  }

  public void doBatch() {

      Bank bank= Bank.getBank();
    // For each customer...
    for ( int cust_idx = 0;
          cust_idx < bank.getNumberOfCustomers();
          cust_idx++ ) {
      Customer customer = bank.getCustomer(cust_idx);

      // For each account for this customer...
      for ( int acct_idx = 0;
            acct_idx < customer.getNumberOfAccounts();
            acct_idx++ ) {
        Account account = customer.getAccount(acct_idx);
        String  account_type = "";

        // Determine the account type
        if ( account instanceof SavingsAccount ) {
          SavingsAccount savings = (SavingsAccount) account;
	  savings.accumulateInterest();
        } else {
          // ignore all other account types
        }
      }
    }
  }
}