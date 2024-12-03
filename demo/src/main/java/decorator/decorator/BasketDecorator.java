package decorator.decorator;

// import flower.store.Item;

public class BasketDecorator extends ItemDecorator {

    public BasketDecorator(Item item) {
        super(item);
    }

    @Override
    public int getPrice() {
        return 4 + item.getPrice();
    }

    @Override
    public String getDescription() {
        return item.getDescription() + " with a Basket";
    }
    
}
