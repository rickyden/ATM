// BankDatabase.java
// Represents the bank account information database 

public class BankDatabase
{
   private Account accounts[]; // array of Accounts
   
   //======================= modified =================================
   private SavingAccount savingAccounts[];
   private ChequeAccount chequeAccounts[];   
   // no-argument BankDatabase constructor initializes accounts
   public BankDatabase()
   {
      accounts = new Account[ 4 ]; // just 4 accounts for testing
      savingAccounts = new SavingAccount[ 2 ]; 
      chequeAccounts = new ChequeAccount[ 2 ];
      savingAccounts[ 0 ] = new SavingAccount( 12345, 54321, 1000.0, 1200.0 );
      savingAccounts[ 1 ] = new SavingAccount( 98765, 56789, 200.0, 200.0 );
      chequeAccounts[ 0 ] = new ChequeAccount( 13579, 97531, 1500.0, 1700.0 );
      chequeAccounts[ 1 ] = new ChequeAccount( 24680, 11111, 2200.0, 2200.0 );
      
      for(int i = 0, j = 0, k = 0; k < accounts.length; )//put savingAc and cheque Ac into Account array
      {
          accounts[k++] = savingAccounts[i++];
          accounts[k++] = chequeAccounts[j++];
      } // even = savingAc | odd = chequeAc
   } // end no-argument BankDatabase constructor
   //== end modified ==
   
   
   // retrieve Account object containing specified account number
   private Account getAccount( int accountNumber )
   {
      // loop through accounts searching for matching account number
      for ( Account currentAccount : accounts )
      {
         // return current account if match found
         if ( currentAccount.getAccountNumber() == accountNumber )
            return currentAccount;
      } // end for

      return null; // if no matching account was found, return null
   } // end method getAccount

   // determine whether user-specified account number and PIN match
   // those of an account in the database
   public boolean authenticateUser( int userAccountNumber, int userPIN )
   {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccount( userAccountNumber );

      // if account exists, return result of Account method validatePIN
      if ( userAccount != null )
         return userAccount.validatePIN( userPIN );
      else
         return false; // account number not found, so return false
   } // end method authenticateUser

   // return available balance of Account with specified account number
   public double getAvailableBalance( int userAccountNumber )
   {
      return getAccount( userAccountNumber ).getAvailableBalance();
   } // end method getAvailableBalance

   // return total balance of Account with specified account number
   public double getTotalBalance( int userAccountNumber )
   {
      return getAccount( userAccountNumber ).getTotalBalance();
   } // end method getTotalBalance

   // credit an amount to Account with specified account number
   public void credit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).credit( amount );
   } // end method credit

   // debit an amount from of Account with specified account number
   public void debit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).debit( amount );
   } // end method debit
   
   // ==============================modified============================
   public int getTargetAccount(int i)
   {
       return accounts[i].getAccountNumber();
   }
   
   public int getAccountsQuantity()
   {
       return accounts.length;
   }
   
   public void transferCredit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).transferCredit( amount );
   }
   // ==============================modified============================
} // end class BankDatabase



/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/