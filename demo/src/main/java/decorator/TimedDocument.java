package decorator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimedDocument implements Document{
    private final Document document;

    @Override
    public String parse() {
        long startTime = System.currentTimeMillis();
        String result = document.parse();
        long endTime = System.currentTimeMillis();

        System.out.println("Parsing took " + (endTime - startTime) + " milliseconds");
        return result;
    }
    
}
