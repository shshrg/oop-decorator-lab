package decorator.decorator;


public class RibbonDecorator extends ItemDecorator {

    public RibbonDecorator(Item item) {
        super(item);
    }

    @Override
    public int getPrice() {
        return 40 + item.getPrice();
    }

    @Override
    public String getDescription() {
        return item.getDescription() + " with a Ribbon";
    }
    
}
