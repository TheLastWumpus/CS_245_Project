
package hangman.References;
import java.util.Random;

/**
 * The WordsList class holds a list
 * of words that can be used in the Hangman
 * game. It also has a function to randomly 
 * choose a word from the list. 
 */
public class WordsList {
    public String[] list = 
                            {
                                "nurse",
                                "abstract",
                                "cemetery",
                                "pharmacy",
                                "climbing",
                                "cellular",
                                "squirtle",
                                "onepunch",
                                "panthers",
                                "clippers",
                                "engineer",
                                "backpack",
                                "roach",
                                "coach",
                                "think",
                                "sucky",
                                "yucky",
                                "crime",
                                "rhyme"   
                            };
    
    public String getRandomWord(){
        Random randNum = new Random();
        return list[randNum.nextInt(5)];
    }    
}
