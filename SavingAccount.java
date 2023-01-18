public class SavingAccount extends Account
{
    // instance variables - replace the example below with your own
    private double InterestRate = 0.001;

    public SavingAccount( int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance )
    {
        super ( theAccountNumber, thePIN, theAvailableBalance, theTotalBalance );        
    }
    
    public void setInterestRate( double rate )
    {
        InterestRate = ( rate > 0.000 && rate < 1.000 ) ? rate : 0.000; 
        // if rate > 0.000 and < 1.000, IntRate = rate  else rate = 0.000
    }
    
    public double getInterestRate()
    {
        return InterestRate;
    }
}
