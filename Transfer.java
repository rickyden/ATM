public class Transfer extends Transaction
{
   private Screen screen = getScreen();
   private Keypad keypad; // reference to keypad
   private BankDatabase bankdatabase = getBankDatabase();// reference to bankdatabase
   private final static int CANCELED = 0; // constant for cancel option
   private int TargetAccount = 0;
   private double amount = 0; // amount to deposit
   // Deposit constructor
   public Transfer( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad)
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase );

      // initialize references to keypad and deposit slot
      keypad = atmKeypad;
   } // end Deposit constructor
     
   public int getTargetAccount()
   {
       return TargetAccount;
   }

   public double getAmount()
   {
       return amount;
   }
   
   public void setTargetAccount()
   {
       TargetAccount = keypad.getInput();
   }
   
   public void setAmount()
   {
       amount = keypad.getDoubleInput();
   }
   
   public boolean TargetAccountExist()
   {
       for(int i = 0; i < bankdatabase.getAccountsQuantity(); i++) 
       {
           if(TargetAccount == bankdatabase.getTargetAccount(i))
           return true;
       }
       return false;
   }
   
   public boolean TransferConfirm()
   {
       screen.displayMessage("Transfer Account number: ");
       System.out.println(TargetAccount);
       screen.displayMessage("Transfer Amount: ");
       screen.displayDollarAmount(amount);
       System.out.print("\n");
       boolean correct = false;
       int option = 0;
       while(!correct)
       {
           screen.displayMessage("\nYes - Enter 1 ; No - Enter 2 ");
           option = keypad.getInput();
           if (option != 1 || option != 2)
           {
               correct = true;
           }
           else
           {
               screen.displayMessageLine("Wrong Input. Please enter again.");
           }
       }
       if(option == 1)
       {
           return true;
       }
       else
       {
           return false;
       }
   }
    
   // perform transaction
   public void execute()
   {
      BankDatabase bankDatabase = getBankDatabase(); // get reference
      Screen screen = getScreen(); // get reference
      screen.displayMessage("Please Enter the Transfer Account Number: ");
      setTargetAccount();      
      if(TargetAccountExist() == false)
      {
          screen.displayMessageLine("Transfer Account doesn't exist.");
          screen.displayMessageLine("Transfer Failure");
          return;
      }      
      if(TargetAccount == super.getAccountNumber())
      {
          screen.displayMessageLine("You cannot transfer to your own account.");
          screen.displayMessageLine("Transfer Failure");
          return;
      }      
      screen.displayMessage("Please Enter the Transfer Amount: ");
      setAmount();      
      if(amount <= 0)
      {
          screen.displayMessageLine("Transfer Amount must be greater than 0");
          screen.displayMessageLine("Transfer Failure");
          return;          
      }      
      if(TransferConfirm() == false)
      {
          screen.displayMessageLine("Transfer Cancelled");
          return;
      }      
      if(amount > bankdatabase.getTotalBalance(super.getAccountNumber()) ||
            amount > bankdatabase.getAvailableBalance(super.getAccountNumber()))
      {
         screen.displayMessageLine("\nInsufficient balance to transfer");
         screen.displayMessageLine("Transfer Failure");
         return;
      }     
      bankdatabase.transferCredit(TargetAccount, amount);
      bankdatabase.debit(super.getAccountNumber(), amount);
      System.out.println("\nTransfer Successful");
   } // end method execute
} // end class Transfer
    



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