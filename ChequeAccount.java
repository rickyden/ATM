public class ChequeAccount extends Account
{
    // instance variables - replace the example below with your own
    private int ChequeLimit = 10000;

    public ChequeAccount( int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance )
    {
        super ( theAccountNumber,thePIN, theAvailableBalance, theTotalBalance );        
    }

    public void setChequeLimit( int limit )
    {
        ChequeLimit = limit; 
    }
    
    public int getChequeLimit()
    {
        return ChequeLimit;
    }
}
