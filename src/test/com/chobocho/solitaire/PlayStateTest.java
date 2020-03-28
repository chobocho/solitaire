package test.com.chobocho.solitaire;

import com.chobocho.card.Card;
import com.chobocho.solitaire.PlayState;
import com.chobocho.solitaire.Solitare;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayStateTest {
    PlayState playState;

    @Before
    public void setUp() throws Exception {
        playState = new PlayState();
    }

    @After
    public void tearDown() throws Exception {
        playState = null;
    }

    @Test
    public void initGame(){
        assertEquals(playState.isFinishGame(), false);
        System.out.println(playState);
        playState.openCard(Solitare.PLAY_DECK);
        System.out.println(playState);
    }

    @Test
    public void moveCard() {
        System.out.println("moveCard");
        Card card1 = new Card(Card.FIGURE.SPADE,Card.NUMBER.QUEEN);
        assertEquals(card1.open(), true);
        Card card2 = new Card(Card.FIGURE.HEART,Card.NUMBER.KING);
        assertEquals(card2.open(), true);
        Card card3 = new Card(Card.FIGURE.DIAMOND,Card.NUMBER.JACK);
        assertEquals(card3.open(), true);

        playState.initGame();
        playState.getDeck(Solitare.BOARD_DECK_1).clear();
        assertEquals(playState.getDeck(Solitare.BOARD_DECK_1).push(card2), true);
        assertEquals(playState.getDeck(Solitare.BOARD_DECK_1).push(card1), true);
        assertEquals(playState.getDeck(Solitare.BOARD_DECK_1).push(card3), true);
        playState.getDeck(Solitare.BOARD_DECK_2).clear();
        Card card4 = new Card(Card.FIGURE.DIAMOND,Card.NUMBER.KING);
        assertEquals(card4.open(), true);
        assertEquals(playState.getDeck(Solitare.BOARD_DECK_2).push(card4), true);
        assertEquals(playState.moveCard(Solitare.BOARD_DECK_1, Solitare.BOARD_DECK_2, 2), true);
        System.out.println(playState);

        // Result Deck check
        Card carda = new Card(Card.FIGURE.SPADE,Card.NUMBER.ACE);
        assertEquals(carda.open(), true);
        assertEquals(playState.getDeck(Solitare.RESULT_DECK_1).push(carda), true);
        Card card5 = new Card(Card.FIGURE.SPADE,Card.NUMBER.THREE);
        assertEquals(card5.open(), true);
        assertEquals(playState.getDeck(Solitare.RESULT_DECK_1).push(card5), false);
    }

}