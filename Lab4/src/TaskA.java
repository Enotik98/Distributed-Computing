import java.util.ArrayList;

class RWL {
    private int readers = 0;
    private int writers = 0;
    private int writeRequests = 0;

    public synchronized void lockRead() throws InterruptedException {
        while (writers > 0 || writeRequests > 0 || readers > 0){
            wait();
        }
        readers++;
    }
    public synchronized void unlockRead(){
        readers--;
        notifyAll();
    }
    public synchronized void lockWrite() throws InterruptedException{
        writeRequests++;

        while(readers > 0 || writers > 0){
            wait();
        }
        writeRequests--;
        writers++;
    }

    public synchronized void unlockWrite() throws InterruptedException{
        writers--;
        notifyAll();
    }

}
class ThreadWork extends Thread {
    private int task;
    private Base base;

    public ThreadWork(int task, Base base) {
        this.task = task;
        this.base = base;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 3) {
            switch (task) {
                case 1:
                    try {
                        findPhoneBySurname();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    try {
                        findAccountByPhone();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    try {
                        RWAccount();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    System.out.println("error ");
            }
            i++;
            System.out.println("_______________________________");
        }
    }
    private void findPhoneBySurname() throws InterruptedException {
        String surnameForFind = "Поліна";
        base.lockRead();
        System.out.println("Searching phone ...");
        for (Account account: base.accounts){
            if (account.getName().equals(surnameForFind))
                System.out.println("Founded by searching phone by surname " + account.toString());
        }
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e ){
            e.printStackTrace();
        }
        System.out.println("Searching finished");
        base.unlockRead();

    }
    private void findAccountByPhone() throws InterruptedException {
        String phoneForFind = "+380984125118";
        base.lockRead();
        System.out.println("Searching name ...");
        for (Account account : base.accounts){
            if (account.getPhone().equals(phoneForFind))
                System.out.println("Founded by phone " + account.toString());
        }
        System.out.println("Searching finished");
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e ){
            e.printStackTrace();
        }
        base.unlockRead();
    }

    private void RWAccount () throws InterruptedException {
        Account acc1 = new Account("Кирило", "Поклади", "Альбертович", "+380978899445");
        Account acc2 = new Account("Олексій",  "Владиленович", "Олександрович", "+380776655609");
        boolean check = true;

        base.lockWrite();
        System.out.println("Writing/Deleting accounts.");
        if (check) {
            base.accounts.add(acc1);
            System.out.println(acc1.toString());
            check =false;
        }
        for (Account account : base.accounts){
            if (account.equals(acc1) ) {
                base.accounts.remove(account);
                System.out.println("Remove " + acc1.toString());
                check = true;
                break;
            }
        }
        try {Thread.sleep(2000);}
        catch(InterruptedException e){e.printStackTrace();}
        System.out.println("Writing/Deleting accounts is finished.");
        base.unlockWrite();
    }
}
class Account {
    private String name;
    private String surname;
    private String patronymic;
    private String phone;

    public Account(String name, String surname, String patronymic, String phone) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Name: " + name + " " + surname + " " + patronymic + ", phone: "  + phone;
    }
}
class Base {
    public ArrayList<Account> accounts;
    private RWL readWriteLock;
    private ArrayList<Account> startAccount(){
        ArrayList<Account> account = new ArrayList<>();
        account.add(new Account("Поліна", "Бондаревська", "Олександрівна", "+380984125118"));
        account.add(new Account("Захар", "Кириленко", "Владиславович", "+380984673228"));
        account.add(new Account("Мішання", "Плакса", "Захарович", "+380765677324"));
        account.add(new Account("Олександр", "Білий", "Михайлович", "+380675578339"));


        return account;
    }
    public Base(){
        accounts = startAccount();
        readWriteLock = new RWL();
    }
    public void lockRead() throws InterruptedException {
        readWriteLock.lockRead();
    }
    public void lockWrite() throws InterruptedException {
        readWriteLock.lockWrite();
    }
    public void unlockRead() {
        readWriteLock.unlockRead();
    }
    public void unlockWrite() throws InterruptedException {
        readWriteLock.unlockWrite();
    }

}
public class TaskA {
    public static void main(String[] args) {
        Base base = new Base();
        ThreadWork threadWork1 = new ThreadWork(1, base);
        ThreadWork threadWork2 = new ThreadWork(2, base);
        ThreadWork threadWork3 = new ThreadWork(3, base);

        threadWork1.setDaemon(true);
        threadWork2.setDaemon(true);
        threadWork3.setDaemon(true);

        try {
            threadWork1.start();
            Thread.sleep(500);
            threadWork2.start();
            Thread.sleep(500);
            threadWork3.start();
            Thread.sleep(500);

            threadWork1.join();
            threadWork2.join();
            threadWork3.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
