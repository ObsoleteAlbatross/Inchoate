package map;

import items.Item;

public class Riddle {
    private final String question;
    private final String answer;
    private boolean isAnswered;
    private final Item item;


    public Riddle(String question, String answer, Item item) {
        this.question = question;
        this.answer = answer;
        this.isAnswered = false;
        this.item = item;
    }


    public void answerQuestion(String answer) throws IllegalArgumentException {
        if (isAnswered) {
            throw new IllegalArgumentException("You have already answered this question");
        }
        if (answer.equals(this.answer)) {
            System.out.println("That is the correct answer!");
            System.out.println("You here a door unlock in a distant area...");
            isAnswered = true;
            return;
        }
        throw new IllegalArgumentException("That is not the right answer");
    }

    public Item getItem() {
        return item;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isAnswered() {
        return isAnswered;
    }
}
