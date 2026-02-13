package GameController;
public class Money {
    
    int amount;
    public Money() {
        this.amount = 50;
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
}
