package decorator.decorator;


public class PaperDecorator extends ItemDecorator {

    public PaperDecorator(Item item) {
        super(item);
    }

    @Override
    public int getPrice() {
        return 13 + item.getPrice();
    }

    @Override
    public String getDescription() {
        return item.getDescription() + " in Paper";
    }
    
}
