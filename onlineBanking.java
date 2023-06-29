import java.util.Scanner;

class PANRequiredException extends Exception
{
String req;
PANRequiredException(String str)
{ req=str; }
}
class PANFormatMismatchException extends Exception
{
String req;
PANFormatMismatchException(String str)
{ req=str; }
}
class NotEnougMoneyInAccountException extends Exception
{
String req;
NotEnougMoneyInAccountException(String str)
{ req=str; }
}
class MinBalRequiredException extends Exception
{
String req;
MinBalRequiredException(String str)
{ req=str; }
}

class Account {
    public String accno;
    public String name;
    public String branch;
    public String PAN;
    public long balance;

    Scanner s = new Scanner(System.in);

    //method to open an account
    void openAccount() {
        System.out.print("Enter Account Number: ");
        accno = s.next();
        System.out.print("Enter Holder Name: ");
        name = s.next();
        System.out.print("Enter Account Branch: ");
        branch = s.next();
        System.out.print("Enter your Balance: ");
        balance = s.nextLong();
    }

    //method to display account details
    void showAccount() {
        System.out.println("\n\n<<<<<<DISPALY THE ACCOUNT DETAILS>>>>>>");
        System.out.println("Account Number : "+accno); 
        System.out.println("Account Holder Name : "+name);
        System.out.println("Account Branch : "+branch);
        System.out.println("Balance in your Account : "+balance);
    }

    //method to deposit money
    void deposit() throws PANRequiredException {
        Float amt;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n<<<<<<<<<<DEPOSIT AMOUNT>>>>>>>>>>");
        System.out.println("Enter Amount you want to Deposit to your Account : ");
        amt=sc.nextFloat();
        if(amt >= 50000)
        throw new PANRequiredException("\n Please Enter your PAN Number: ");
        balance+=amt;
        System.out.println("Your Balance is : "+balance);
    }

    void deposit(String PAN) throws PANFormatMismatchException {
      int i;
      this.PAN=PAN;
      Scanner sc = new Scanner(System.in);
      System.out.println("\n\n<<<<<<<<<<DEPOSIT AMOUNT>>>>>>>>>>");
      if(PAN.length()!=10)
      throw new PANFormatMismatchException("Enter a valid PAN ID");
      for(i=0;i<5;i++)
        if(Character.isAlphabetic(PAN.charAt(i))==false)
         {//System.out.print(PAN.charAt(i));
           throw new PANFormatMismatchException("First five entries not a character");
          }
      for(i=5;i<9;i++)
        if(Character.isDigit(PAN.charAt(i))==false)
          {//System.out.print(PAN.charAt(i));
            throw new PANFormatMismatchException("Next four not a number");
          }
       if(Character.isAlphabetic(PAN.charAt(i))==false)
         {//System.out.print(PAN.charAt(i));
           throw new PANFormatMismatchException("Last entry not a character");
          }
       System.out.print("Enter Amount to be deposited:");
       Float amt =sc.nextFloat();
       balance+= amt;
       System.out.println("Your Balance is : "+balance);
    }

    //method to withdraw money
    void withdraw() throws MinBalRequiredException,NotEnougMoneyInAccountException 
    {
        Float amt;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n<<<<<<<<<<WITHDRAW MONEY>>>>>>>>>>");
        System.out.println("Enter Amount you want to Withdraw from your Account : ");
        amt = sc.nextFloat();
        if (balance-amt<0)
        throw new NotEnougMoneyInAccountException(" Not Enough of Money in your Account ");
        else if (balance-amt<=1000)
        throw new MinBalRequiredException(" Not Enough Minimum Balance in your Account ");
        if (balance >= amt) {
            balance-=amt;
        } else {
            System.out.println("Less Balance..Transaction Failed..");
        }
        System.out.println("Your Balance is : "+balance);
    }

    //method to search an account number
    boolean search(String acn) {
        System.out.println("\n\n<<<<<<<<<<SEARCH ACCOUNT>>>>>>>>>>");
        if (accno.equals(acn)) {
            showAccount();
            return (true);
        }
        return (false);
    }
}


public class onlineBanking 
{
  static void deposit(Account acc)
  {
    Scanner sc=new Scanner(System.in);
    int flag=0;
    try{ 
       acc.deposit();
       flag =1;
    }
    catch(PANRequiredException ex)
    {
      System.out.print("\nException occurred:PAN number required"+ex.req);
    }
    if(flag!=1)
    { 
      String pan=sc.next();
      try{
        acc.deposit(pan);
        }
      catch(PANFormatMismatchException pex)
      {
        System.out.println("ERROR OCCUREED:"+pex.req);
        }
    }
  }

  static void withdraw(Account acc)
  {
    Scanner sc=new Scanner(System.in);
    try{ 
       acc.withdraw();
      }
    catch(NotEnougMoneyInAccountException ex)
    {
      System.out.println("ERROR OCCUREED:"+ex.req);
      }
    catch(MinBalRequiredException ex)
    {
      System.out.println("ERROR OCCUREED:"+ex.req);
      }
  }

  

    public static void main(String arg[]) {
        Scanner KB = new Scanner(System.in);
        int flag =0,i;
        
        //create initial accounts
        System.out.print("How Many Customer you want to Input : ");
        int n = KB.nextInt();
        Account acc[] = new Account[n];
        for (int j = 0; j < acc.length; j++) {
            acc[j] = new Account();
            acc[j].openAccount();
        }

        //run loop until menu 5 is not pressed
        int ch;
        do {
            System.out.println("\n\n<<<<<<<<<<WELCOME TO ONLINE BANK>>>>>>>>>>");
            System.out.println("Main Menu\n 1. Display All\n 2. Search Account\n 3. Deposit\n 4. Withdrawal\n 5. Exit ");
                System.out.println("Your Choice :"); 
                ch = KB.nextInt();
                switch (ch) {
                    case 1:
                        for (int j = 0; j < acc.length; j++) {
                            acc[j].showAccount();
                        }
                        break;

                    case 2:
                        System.out.print("Enter Account Number you want to Search...: ");
                        String acn = KB.next();
                        boolean found = false;
                        for (int j = 0; j < acc.length; j++) {
                            found = acc[j].search(acn);
                            if (found) {
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("Search Failed..Account Not Exist..");
                        }
                        break;

                    case 3:
                        System.out.print("Enter Account Number : ");
                        acn = KB.next();
                        found = false;
                        //acc[i]= new Account();
                        for (int j = 0; j < acc.length; j++) {
                            found = acc[j].search(acn);
                            if (found) {
                                deposit(acc[j]);
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("Search Failed..Account Not Exist..");
                        }
                        break;

                    case 4:
                        System.out.print("Enter Account Number : ");
                        acn = KB.next();
                        found = false;
                        for (int j = 0; j < acc.length; j++) {
                            found = acc[j].search(acn);
                            if (found) {
                                withdraw(acc[j]);
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("Search Failed..Account Not Exist..");
                        }
                        break;

                    case 5:
                        System.out.println("......THANK YOU FOR USING ONLINE BANK......");
                        break;
                }
            }
            while (ch != 5);
        }
    }
