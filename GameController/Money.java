package GameController;
public class Money {
    
    private int startAmount = 30;
    private int amount;

    public Money() {
        this.amount = startAmount;
    }

    public int getAmount() {
        return amount;
    }

    public void addAmount(int amount){
        this.amount += amount;
    }

    public void decreseAmount(int amount) {
        this.amount -= amount;
    }

    public void increseAmount(int amount) {
        this.amount += amount;
    }

    public boolean CheckMoney(){
        if(amount <= 0){
            return false;
        }
        return true;
    }

    public void RestartMoney(){
        this.amount = startAmount;
    }
}
