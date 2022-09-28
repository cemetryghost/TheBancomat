import java.util.Scanner;
class Account{
    private int balance = 0;
    private int output = 10000;
    int subtraction;
    public synchronized void Refill(){
        while(balance >= output){
            try{
                wait();
            }
            catch (Exception ex){
                ex.getMessage();
            }
        }
        balance += 1000;
        System.out.println("Пополнение баланса на 1000р");
        System.out.println("Остаток баланcа: " + balance);
        notify();
    }
    public synchronized void Output(){
        while(balance < output){
            try {
                wait();
            }
            catch (Exception ex){
                ex.getMessage();
            }
        }
        System.out.println("Введите сумму вывода: ");
        Scanner in = new Scanner(System.in);
        subtraction = in.nextInt();
        balance -= subtraction;
        if(balance < 0){
            System.out.println("На балансе нет такой суммы, необходимо пополнить счет!");
            System.exit(1);
        }
        System.out.println("Произошло списание в размере " + subtraction);
        System.out.println("Остаток баланса: " + balance);
        notify();
    }
}

class Output implements Runnable{
    Account account;
    Output(Account account){
        this.account=account;
    }
    public void run(){
        while (true) {
            account.Refill();
        }
    }
}
class Refill implements Runnable{
    Account account;
    Refill(Account account){
        this.account=account;
    }
    public void run(){
        while (true) {
            account.Output();
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Account account = new Account();
        Refill refill = new Refill(account);
        Output output = new Output(account);
        new Thread(refill).start();
        new Thread(output).start();
    }
}
