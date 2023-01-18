    // ATM.java
    // Represents an automated teller machine
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;    
public class ATM extends JFrame
{
   private boolean userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser
   private DepositSlot depositSlot; // ATM's deposit slot
   private BankDatabase bankDatabase; // account information database
   
   // ==============================modified============================
   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int TRANSFER = 3;
   private static final int EXIT = 4;
   // ==============================modified============================

   private BorderLayout borderLayout;
   private JPanel numberPad;
   private JButton[] numberPad_Button = new JButton[15];
   private JPanel leftSide;
   private JButton[] Left_Button = new JButton[4];
   private JPanel rightSide;
   private JButton[] Right_Button = new JButton[4];
   private JPanel mon;
   private JLabel AccountNumber_label;
   private JTextField AccountNumber_field;
   private JLabel AccountPassword_label;
   private JPasswordField AccountPassword_field;
   
   // no-argument ATM constructor initializes instance variables
   public ATM() 
   {
      super("ATM"); 
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad 
      cashDispenser = new CashDispenser(); // create cash dispenser
      depositSlot = new DepositSlot(); // create deposit slot
      bankDatabase = new BankDatabase(); // create acct info database
      
      setLayout(borderLayout = new BorderLayout());
      
      numberPad = new JPanel();
      numberPad.setLayout( new GridLayout(4,4,2,2) );
      //set grid size
      for(int i = 0; i < numberPad_Button.length; i++)
          numberPad_Button[i] = new JButton(Integer.toString(i));
          //loop to print keypad
      for(int i = 7; i < 10; i++)
          numberPad.add(numberPad_Button[i]);
          //set text for keypad
      numberPad_Button[12].setText("Cancel");
      //word "Cancel" added to the keypad button
      numberPad.add( numberPad_Button[12] );
      for(int i = 4; i < 7; i++)
          numberPad.add(numberPad_Button[i]);
          //set text for keypad
      numberPad_Button[13].setText("Clear");
      //word "Clear" added to the keypad button
      numberPad.add( numberPad_Button[13] );
      for(int i = 1; i < 4; i++)
          numberPad.add(numberPad_Button[i]);
          //set text for keypad
      numberPad_Button[14].setText("Enter");
      //word "Enter" added to the keypad button
      numberPad.add( numberPad_Button[14] );
      numberPad_Button[0].setText("0");
      numberPad_Button[10].setText(".");
      numberPad_Button[11].setText("00");
      numberPad.add(numberPad_Button[0]);
      numberPad.add(numberPad_Button[10]);
      numberPad.add(numberPad_Button[11]);
           
      leftSide = new JPanel();
      leftSide.setLayout( new GridLayout(4,1) );
      rightSide = new JPanel();
      rightSide.setLayout( new GridLayout(4,1) );
      //loop to print array of left and right button
      for(int i = 0; i < Left_Button.length && i < Right_Button.length; i++){
          Left_Button[i] = new JButton();
          //add left button to jframe
          leftSide.add(Left_Button[i]);
          Right_Button[i] = new JButton();
          //add right button to jframe
          rightSide.add(Right_Button[i]);
      }
      
      mon = new JPanel();
      mon.setLayout( null ); //no layout
      //absolute position to set jlabel, jtextfield position
      AccountNumber_label = new JLabel("Account Number: ");
      AccountNumber_label.setBounds(150,200,200,250);
      AccountNumber_field = new JTextField();
      AccountNumber_field.setBounds(250,316,100,20);
      AccountPassword_label = new JLabel("Password: ");
      AccountPassword_label.setBounds(187,225,200,250);
      AccountPassword_field = new JPasswordField(10);
      AccountPassword_field.setBounds(250,341,100,20);
      mon.add(AccountNumber_label);
      mon.add(AccountNumber_field);
      mon.add(AccountPassword_label);
      mon.add(AccountPassword_field);
      
      add(leftSide, borderLayout.WEST);
      add(rightSide, borderLayout.EAST);
      add(numberPad, borderLayout.SOUTH);
      add(mon, borderLayout.CENTER);
      //size button add to the screen
      
      ButtonHandler1 handler1 = new ButtonHandler1();
      for(int i = 0; i < numberPad_Button.length; i++)
          numberPad_Button[i].addActionListener( handler1 );
   } // end no-argument ATM constructor

   private class ButtonHandler1 implements ActionListener{
       public void actionPerformed(ActionEvent event){
           for(int i = 0; i <= 9; i++){
               if( event.getSource() == numberPad_Button[i] && AccountNumber_field.getText().length() == 5 &&
                       AccountPassword_field.getText().length() < 5){
                   String temp = AccountPassword_field.getText();
                   AccountPassword_field.setText(temp + Integer.toString(i));
               }
               else if( event.getSource() == numberPad_Button[i] && AccountNumber_field.getText().length() < 5){
                   String temp = AccountNumber_field.getText();
                   AccountNumber_field.setText(temp + Integer.toString(i));
               }               
           }
           if( event.getSource() == numberPad_Button[13] ){//Clear    
               AccountNumber_field.setText("");
               AccountPassword_field.setText("");
             }
           if( event.getSource() == numberPad_Button[14] ){//Enter
               try{
                   int accountNumber = Integer.parseInt( AccountNumber_field.getText() ); // input account number
                   int pin = Integer.parseInt( AccountPassword_field.getText() ); // input PIN
                   userAuthenticated = bankDatabase.authenticateUser( accountNumber, pin );
                   currentAccountNumber = accountNumber;
               }catch(NumberFormatException e){
                   
               }
               if ( userAuthenticated == true){
                   performTransactions_GUI();
               }
               else{
                   mon.removeAll();
                   mon.repaint();
                   mon.add(AccountNumber_label);
                   mon.add(AccountNumber_field);
                   mon.add(AccountPassword_label);
                   mon.add(AccountPassword_field);
                   AccountNumber_field.setText("");
                   AccountPassword_field.setText("");
                   JLabel wrong = new JLabel("Account Number or Password incorrect or does not exist");
                   //create error message
                   wrong.setBounds(100,300,400,250);//set wrong position
                   mon.add(wrong);//add wrong to screen
                   mon.revalidate();
               }
           }
           else if( event.getSource() == numberPad_Button[12] )
               System.exit(0);//cancel
       }
   }
   
   // start ATM 
   public void run()
   {
      // welcome and authenticate user; perform transactions
      while ( true )
      {
         // loop while user is not yet authenticated
         while ( !userAuthenticated ) 
         {
            screen.displayMessageLine( "\nWelcome!" );       
            authenticateUser(); // authenticate user
         } // end while
         
         performTransactions(); // user is now authenticated 
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session 
         screen.displayMessageLine( "\nThank you! Goodbye!" );
      } // end while   
   } // end method run

   // attempts to authenticate user against database
   private void authenticateUser() 
   {
      screen.displayMessage( "\nPlease enter your account number: " );
      int accountNumber = keypad.getInput(); // input account number
      screen.displayMessage( "\nEnter your PIN: " ); // prompt for PIN
      int pin = keypad.getInput(); // input PIN
      
      // set userAuthenticated to boolean value returned by database
      userAuthenticated = 
         bankDatabase.authenticateUser( accountNumber, pin );
      
      // check whether authentication succeeded
      if ( userAuthenticated )
      {
         currentAccountNumber = accountNumber; // save user's account #
      } // end if
      else
         screen.displayMessageLine( 
             "Invalid account number or PIN. Please try again." );
   } // end method authenticateUser

   // display the main menu and perform transactions
   private void performTransactions() 
   {
      // local variable to store transaction currently being processed
      if(bankDatabase.isSavingAccountOrCheque(currentAccountNumber) == true)
           System.out.println("\nThis is your Saving Account\n");
      else
           System.out.println("\nThis is your Cheque Account\n");
      Transaction currentTransaction = null;
      
      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while ( !userExited )
      {     
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();

         // decide how to proceed based on user's menu selection
         switch ( mainMenuSelection )
         {
            // user chose to perform one of three transaction types
            
            // ==============================modified============================            
            case BALANCE_INQUIRY: 
            case WITHDRAWAL: 
            case TRANSFER:
            // ==============================modified============================    
               // initialize as new object of chosen type
               currentTransaction = 
                  createTransaction( mainMenuSelection );

               currentTransaction.execute(); // execute transaction
               break; 
            case EXIT: // user chose to terminate session
               screen.displayMessageLine( "\nExiting the system..." );
               userExited = true; // this ATM session should end
               break;
            default: // user did not enter an integer from 1-4
               screen.displayMessageLine( 
                  "\nYou did not enter a valid selection. Try again." );
               break;
         } // end switch
      } // end while
   } // end method performTransactions
   
   // display the main menu and return an input selection
   private int displayMainMenu()
   {
      screen.displayMessageLine( "\nMain menu:" );
      screen.displayMessageLine( "1 - View my balance" );
      screen.displayMessageLine( "2 - Withdraw cash" );
      // ==============================modified============================
      screen.displayMessageLine( "3 - Transfer funds" );
      // ==============================modified============================
      screen.displayMessageLine( "4 - Exit\n" );
      screen.displayMessage( "Enter a choice: " );
      return keypad.getInput(); // return user's selection
   } // end method displayMainMenu
         
   // return object of specified Transaction subclass
   private Transaction createTransaction( int type )
   {
      Transaction temp = null; // temporary Transaction variable
      
      // determine which type of Transaction to create     
      switch ( type )
      {
         case BALANCE_INQUIRY: // create new BalanceInquiry transaction 
            temp = new BalanceInquiry( 
               currentAccountNumber, screen, bankDatabase );
            break;
         case WITHDRAWAL: // create new Withdrawal transaction
            temp = new Withdrawal( currentAccountNumber, screen, 
               bankDatabase, keypad, cashDispenser );
            break; 
         // ==============================modified============================   
         case TRANSFER:
            temp = new Transfer( currentAccountNumber, screen, 
               bankDatabase, keypad);
            break;
         // ==============================modified============================   
      } // end switch

      return temp; // return the newly created object
   } // end method createTransaction
   
   private void performTransactions_GUI(){
      mon.removeAll();
      mon.repaint();
      //check account is saving or cheque
      if( bankDatabase.isSavingAccountOrCheque( currentAccountNumber ) == true ){
          JLabel SorCaccount = new JLabel("This is your Saving Account");
          SorCaccount.setBounds(180,270,400,250);
          mon.add(SorCaccount);
      }
      else{
          JLabel SorCaccount = new JLabel("This is your Cheque Account");
          SorCaccount.setBounds(180,270,400,250);
          mon.add(SorCaccount);
      }
      //set position for withdrawal, view balance, transfer
      JLabel withdrawal = new JLabel("Withdrawal");
      withdrawal.setBounds(10,295,200,200);
      JLabel viewBalance = new JLabel("View Balance");
      viewBalance.setBounds(435,180,200,200);
      JLabel transfer = new JLabel("Transfer");
      transfer.setBounds(460,295,200,200);
      mon.add(withdrawal);
      mon.add(viewBalance);
      mon.add(transfer);
      mon.revalidate();
      
      ButtonHandler2_MainMenu handler2 = new ButtonHandler2_MainMenu();
      Left_Button[3].addActionListener( handler2 ); // withdrawal
      Right_Button[2].addActionListener( handler2 );// view balance
      Right_Button[3].addActionListener( handler2 );// transfer
   }
   private class ButtonHandler2_MainMenu implements ActionListener{
       
       JTextField otherAccountNum_field = new JTextField();
       JTextField transferAmount_field = new JTextField();
        public void actionPerformed(ActionEvent event) {
            for(int i = 0; i < 10; i++)
                    numberPad_Button[i].removeActionListener( this );

            if( event.getSource() == numberPad_Button[12] )
                System.exit(0);
            if( event.getSource() == Left_Button[3]){
                mon.removeAll();
                mon.repaint();
                //show amount label and set position        
                JLabel amount100_label = new JLabel("$100");
                amount100_label.setBounds(10,-50,200,200);
                JLabel amount200_label = new JLabel("$200");
                amount200_label.setBounds(10,70,200,200);
                JLabel amount500_label = new JLabel("$500");
                amount500_label.setBounds(10,180,200,200);
                JLabel amount1000_label = new JLabel("$1000");
                amount1000_label.setBounds(10,295,200,200);
                JLabel otherAmount_label = new JLabel("Other Value");
                otherAmount_label.setBounds(440,180,200,200);
                JLabel back_label = new JLabel("Back to Main Menu");
                back_label.setBounds(405,295,200,200);
                mon.add(amount100_label);
                mon.add(amount200_label);
                mon.add(amount500_label);
                mon.add(amount1000_label);
                mon.add(otherAmount_label);
                mon.add(back_label);
                
                mon.revalidate();
                
                Left_Button[3].removeActionListener( this );
                Right_Button[2].removeActionListener( this );
                Right_Button[3].removeActionListener( this );
                
                ButtonHandler4_Withdrawal handler4 = new ButtonHandler4_Withdrawal();
                Left_Button[0].addActionListener( handler4 );//$100
                Left_Button[1].addActionListener( handler4 );//$200
                Left_Button[2].addActionListener( handler4 );//$500
                Left_Button[3].addActionListener( handler4 );//$1000
                Right_Button[2].addActionListener( handler4 );//other value
                Right_Button[3].addActionListener( handler4 );//back to main menu
                numberPad_Button[14].addActionListener( handler4 );
            }
            if( event.getSource() == Right_Button[2]){
                mon.removeAll();
                mon.repaint();
                double availableBalance = bankDatabase.getAvailableBalance( currentAccountNumber );
                double totalBalance = bankDatabase.getTotalBalance( currentAccountNumber );
                JLabel availableBalance_label = new JLabel( "Available Balance: " + Double.toString( availableBalance ) );
                JLabel totalBalance_label = new JLabel( "Total Balance: " + Double.toString( totalBalance ) );
                availableBalance_label.setBounds(200,200,400,250);
                totalBalance_label.setBounds(210,250,400,250);
                mon.add( availableBalance_label );
                mon.add( totalBalance_label );
                //add tthe available balance and total balance label to the screen
                JLabel back_label = new JLabel("Back to Main Menu");
                back_label.setBounds(405,295,200,200);
                mon.add(back_label);
                //add back to menu lable
                mon.revalidate();
                
                Left_Button[3].removeActionListener( this );
                Right_Button[2].removeActionListener( this );
                Right_Button[3].removeActionListener( this );
                
                ButtonHandler3_BalanceInquiry handler3 = new ButtonHandler3_BalanceInquiry();
                Right_Button[3].addActionListener(handler3);
            }
            if( event.getSource() == Right_Button[3]){
                Left_Button[3].removeActionListener( this );
                Right_Button[2].removeActionListener( this );
                Right_Button[3].removeActionListener( this );
                mon.removeAll();
                mon.repaint();
                
                JLabel otherAccountNum_label = new JLabel("Transfer Account Number: ");
                otherAccountNum_label.setBounds(128,200,200,250);
                JLabel transferAmount_label = new JLabel("Transfer Amount: ");
                transferAmount_label.setBounds(180,250,200,250);
                
                JLabel back_label = new JLabel("Back to Main Menu");
                back_label.setBounds(405,295,200,200);
              
                otherAccountNum_field.setBounds(280,317,100,20);
                transferAmount_field.setBounds(280,367,100,20);
                
                mon.add(otherAccountNum_label);
                mon.add(otherAccountNum_field);
                mon.add(transferAmount_label);
                mon.add(transferAmount_field);
                mon.add(back_label);
       
                mon.revalidate();
                ButtonHandler3_BalanceInquiry handler3 = new ButtonHandler3_BalanceInquiry();
                Right_Button[3].addActionListener(handler3);
                ButtonHandler5_Transfer buttonHandler5_Transfer = new ButtonHandler5_Transfer();
                for(int i = 0; i <= 9 ; i++)
                    numberPad_Button[i].addActionListener(buttonHandler5_Transfer);
                numberPad_Button[13].addActionListener(buttonHandler5_Transfer);
                numberPad_Button[14].addActionListener(buttonHandler5_Transfer);
            }
        }
        private class ButtonHandler5_Transfer implements ActionListener{
            public void actionPerformed(ActionEvent event){
                for(int i = 0; i <= 9 ; i++){
                    if( event.getSource() == numberPad_Button[i] && otherAccountNum_field.getText().length() < 5 ){
                        String temp = otherAccountNum_field.getText();
                        otherAccountNum_field.setText( temp + Integer.toString(i));
                    }
                    else if( event.getSource() == numberPad_Button[i] && otherAccountNum_field.getText().length() == 5 &&
                            transferAmount_field.getText().length() < 5){
                        String temp = transferAmount_field.getText();
                        transferAmount_field.setText( temp + Integer.toString(i));
                    }
                }
                    if( event.getSource() == numberPad_Button[13] ){
                        otherAccountNum_field.setText("");//clear account field
                        transferAmount_field.setText("");//clear amount field
                    }
                    if( event.getSource() == numberPad_Button[14] ){
                        mon.removeAll();
                        mon.repaint();
                        Transfer transfer = new Transfer( currentAccountNumber, screen, bankDatabase, keypad );
                        int temp1 = Integer.parseInt( otherAccountNum_field.getText() );
                        transfer.setTargetAccount(temp1);
                        double temp2 = Double.parseDouble( transferAmount_field.getText() );
                        transfer.setAmount(temp2);
                        if( transfer.TargetAccountExist() == true && transfer.getTargetAccount() != currentAccountNumber &&
                                transfer.getAmount() > 0 && 
                                transfer.getAmount() <= bankDatabase.getAvailableBalance(currentAccountNumber)){
                            //check input valid,account exist, not equal to login account, transfer amount > 0, available > amount 
                            bankDatabase.transferCredit( transfer.getTargetAccount(), transfer.getAmount());
                            bankDatabase.debit( currentAccountNumber, transfer.getAmount());
                            JLabel transferSuccessful = new JLabel("Transfer Successful");
                            transferSuccessful.setBounds(180,250,200,250);
                            mon.add(transferSuccessful);
                            mon.revalidate();
                        }
                        else{
                            JLabel transferFailure = new JLabel("Transfer Failure");
                            transferFailure.setBounds(180,250,200,250);
                            mon.add(transferFailure);
                            mon.revalidate();
                        }
                    }
                }
            }
        }
        private class ButtonHandler3_BalanceInquiry implements ActionListener{
            public void actionPerformed(ActionEvent event){
                if( event.getSource() == Right_Button[3] ){
                    Right_Button[3].removeActionListener( this );
                    performTransactions_GUI();
                }
            }
        }
        private class ButtonHandler4_Withdrawal implements ActionListener{
           Withdrawal withdrawal = new Withdrawal( currentAccountNumber, screen, 
               bankDatabase, keypad, cashDispenser );
           boolean cashDispensed = false;
           double availableBalance = bankDatabase.getAvailableBalance( currentAccountNumber );
           int amount = 0;
           
           private JLabel amountLabel = new JLabel("Amount: ");
           private JTextField amountField = new JTextField(10);
           
           public void actionPerformed(ActionEvent event)
           {
               mon.removeAll();
               mon.repaint();
            if( event.getSource() == Right_Button[3] ){
               Left_Button[0].removeActionListener( this );
               Left_Button[1].removeActionListener( this );
               Left_Button[2].removeActionListener( this );
               Left_Button[3].removeActionListener( this );
               Right_Button[2].removeActionListener( this );
               Right_Button[3].removeActionListener( this );
               //user press back to main menu 
               performTransactions_GUI();
            }
            if( event.getSource() == Left_Button[0] ){
               amount = 100;
               check();
               JLabel leave = new JLabel("Please take your card"); //remind user take card
               leave.setBounds(190,300,200,20);
               JLabel back_label = new JLabel("Back to Main Menu");
               back_label.setBounds(405,295,200,200);
               mon.add(back_label);
               mon.add(leave);
               mon.revalidate();               
            } 
            if( event.getSource() == Left_Button[1] ){
               amount = 200;
               check();
               JLabel leave = new JLabel("Please take your card"); //remind user take card
               leave.setBounds(190,300,200,20);
               JLabel back_label = new JLabel("Back to Main Menu");
               back_label.setBounds(405,295,200,200);
               mon.add(back_label);
               mon.add(leave);
               mon.revalidate();               
            }  
            if( event.getSource() == Left_Button[2] ){
               amount = 500;
               check();
               JLabel leave = new JLabel("Please take your card"); //remind user take card
               leave.setBounds(190,300,200,20);
               JLabel back_label = new JLabel("Back to Main Menu");
               back_label.setBounds(405,295,200,200);
               mon.add(back_label);
               mon.add(leave);
               mon.revalidate();                
            }
            if( event.getSource() == Left_Button[3] ){
               amount = 1000;
               check();
               JLabel leave = new JLabel("Please take your card"); //remind user take card
               leave.setBounds(190,300,200,20);
               JLabel back_label = new JLabel("Back to Main Menu");
               back_label.setBounds(405,295,200,200);
               mon.add(back_label);
               mon.add(leave);
               mon.revalidate();
            }
           
            if( event.getSource() == Right_Button[2] ){
               Left_Button[0].removeActionListener( this );
               Left_Button[1].removeActionListener( this );
               Left_Button[2].removeActionListener( this );
               Left_Button[3].removeActionListener( this );
               Right_Button[2].removeActionListener( this );
               Right_Button[3].removeActionListener( this );
               //custom amount, remove lable on the screen 
               amountLabel.setBounds(150,200,200,250);
               amountField.setBounds(250,316,100,20);
               mon.add(amountLabel);
               mon.add(amountField);
               ButtonHandler4_CustomWithdrawal buttonHandler4_Withdrawal2 = new ButtonHandler4_CustomWithdrawal();
               for(int i = 0 ; i <= 10; i++)
                   numberPad_Button[i].addActionListener( buttonHandler4_Withdrawal2 );
               numberPad_Button[13].addActionListener( buttonHandler4_Withdrawal2 );
               numberPad_Button[14].addActionListener( buttonHandler4_Withdrawal2 );              
               //mon.revalidate();
            } 
           
           }
        private void stop(){
               try{
                   TimeUnit.SECONDS.sleep(3);  
               }catch(InterruptedException ex){
                   
               }    
              System.exit(0);
        }
       private void check(){
           if ( amount <= availableBalance ){
               if ( cashDispenser.isSufficientCashAvailable( amount ))
               {
                  // update the account involved to reflect withdrawal
                  bankDatabase.debit( currentAccountNumber, amount );
                  
                  cashDispenser.dispenseCash( amount ); // dispense cash
                  cashDispensed = true; // cash was dispensed

                  JLabel takeCash_label = new JLabel("Please take your cash now");
                  takeCash_label.setBounds(190,250,400,250);
                  mon.add(takeCash_label);
               }
               else{
                   JLabel insufficientCashAvailable = new JLabel("It has not sufficient cash. Sorry");
                   insufficientCashAvailable.setBounds(100,300,400,250);
                   mon.add(insufficientCashAvailable);
               }
           }
           else{
               JLabel insufficientBalance = new JLabel("You have not sufficient balance. Sorry");
               insufficientBalance.setBounds(170,250,400,250);
               mon.add(insufficientBalance);
           }
       }
       
       private class ButtonHandler4_CustomWithdrawal implements ActionListener{
           public void actionPerformed(ActionEvent event) {
              for(int i = 0 ; i <= 9; i++)
              {
                  if( event.getSource() == numberPad_Button[i])
                  {
                       String temp = amountField.getText();
                       amountField.setText( temp + Integer.toString(i));
                  }
              }
              if( event.getSource() == numberPad_Button[14]){
                  mon.removeAll();
                  mon.repaint();
                  double temp = Double.parseDouble( amountField.getText() );
                  //check user input amount is mulpiles of 100 or not
                  if(temp%100 == 0){                       
                       amount = (int)temp;
                       check();
                       JLabel leave = new JLabel("Exit after 3 second");
                       leave.setBounds(200,300,100,20);
                       mon.add(leave);
                       mon.revalidate();
                  }
                  else{
                      //user input mod 100 =/= 0
                      JLabel error = new JLabel("Only the muliples of HKD100 is allowed");
                      error.setBounds(200,300,100,20);
                      mon.add(error);
                      mon.revalidate();
                   }
               }
              if( event.getSource() == numberPad_Button[13]){
                   amountField.setText( "" );
              }
           }  
       }
   }
   }
 // end class ATM



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