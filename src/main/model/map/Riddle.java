package model.map;

import model.item.Item;

// Riddle class, has a question and corresponding answer. Will give the player an item if cleared
public class Riddle {
    private final String question;
    private final String answer;
    private boolean isAnswered;
    private final Item item;


    // EFFECTS: Set up riddle to have a question, answer, and item
    //          Also set isAnswered to false
    public Riddle(String question, String answer, Item item) {
        this.question = question;
        this.answer = answer;
        this.isAnswered = false;
        this.item = item;
    }

    // MODIFIES: this
    // EFFECTS: Try to answer to question given an answer
    public void answerQuestion(String answer) throws IllegalArgumentException {
        if (isAnswered) {
            throw new IllegalArgumentException("You have already answered this question");
        }
        if (!answer.equals(this.answer)) {
            throw new IllegalArgumentException("That is not the right answer");
        }
        isAnswered = true;
    }

    // EFFECTS: Return the item that is given
    public Item getItem() {
        return item;
    }

    // EFFECTS: Return the question to be answered
    public String getQuestion() {
        return question;
    }

    // EFFECTS: Return if this riddle has been answered
    public boolean isAnswered() {
        return isAnswered;
    }
}
