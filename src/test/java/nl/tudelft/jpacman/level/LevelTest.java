package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import static org.mockito.Mockito.spy;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests various aspects of level.
 *
 * @author Jeroen Roosen 
 */
// The four suppress warnings ignore the same rule, which results in 4 same string literals
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.TooManyStaticImports"})
class LevelTest {

    /**
     * The level under test.
     */
    private Level level;

    /**
     * An NPC on this level.
     */
    private final Ghost ghost = mock(Ghost.class);

    /**
     * Starting position 1.
     */
    private final Square square1 = mock(Square.class);

    /**
     * Starting position 2.
     */
    private final Square square2 = mock(Square.class);

    /**
     * The board for this level.
     */
    private final Board board = mock(Board.class);

    /**
     * The collision map.
     */
    private final CollisionMap collisions = mock(CollisionMap.class);

    /**
     * Sets up the level with the default board, a single NPC and a starting
     * square.
     */
    @BeforeEach
    void setUp() {
        final long defaultInterval = 100L;
        level = new Level(board, Lists.newArrayList(ghost), Lists.newArrayList(
            square1, square2), collisions);
        when(ghost.getInterval()).thenReturn(defaultInterval);
    }

    /**
     * Validates the state of the level when it isn't started yet.
     */
    @Test
    void noStart() {
        assertThat(level.isInProgress()).isFalse();
    }

    /**
     * Validates the state of the level when it is stopped without starting.
     */
    @Test
    void stop() {
        level.stop();
        assertThat(level.isInProgress()).isFalse();
    }

    /**
     * Validates the state of the level when it is started.
     */
    @Test
    void start() {
        level.start();
        assertThat(level.isInProgress()).isTrue();
    }

    /**
     * Validates the state of the level when it is started then stopped.
     */
    @Test
    void startStop() {
        level.start();
        level.stop();
        assertThat(level.isInProgress()).isFalse();
    }
    
    
    //Newly Added Tests
    
    /**
     * Validates the state of the level when the level is frozen before starting the game.
     */
    @Test
    void freezeStart() {
    	level.freeze();
    	assertThat(level.isInProgress()).isFalse();
    	level.start();
    	assertThat(level.isInProgress()).isTrue();
    }
    
    /**
     * Validates the state of the level when the level is started and is frozen after.
     */
    @Test
    void startFreeze() {
    	level.start();
    	assertThat(level.isInProgress()).isTrue();
    	level.freeze();
    	assertThat(level.isInProgress()).isTrue();
    }
    
    /**
     * Validates the state of the level when the level is started -> frozen -> unfrozen -> stopped.
     */
    @Test
    void startFreezeUnFreezeStop() {
    	level.start();
    	assertThat(level.isInProgress()).isTrue();
    	level.freeze();
    	assertThat(level.isInProgress()).isTrue();
    	level.unfreeze();
    	assertThat(level.isInProgress()).isTrue();
    	level.stop();
    	assertThat(level.isInProgress()).isFalse();
    }
    
    /**
     * Validates the state of the level when the level is stopped -> frozen.
     */
    @Test
    void stopFreeze() {
    	level.start();
    	assertThat(level.isInProgress()).isTrue();
    	level.stop();
    	assertThat(level.isInProgress()).isFalse();
    	level.freeze();
    	assertThat(level.isInProgress()).isFalse();
    }
    
    /**
     * Verifies NPC movement is stopped, when freeze button is clicked
     */
    
    void freeze() {
    	
    	
    }
    
    /**
     * Verifies NPC movement is resumed, when freeze button is clicked again
     */
    void unfreeze() {
    	
    }
    
    

    /**
     * Verifies registering a player puts the player on the correct starting
     * square.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void registerPlayer() {
        Player p = mock(Player.class);
        level.registerPlayer(p);
        verify(p).occupy(square1);
    }

    /**
     * Verifies registering a player twice does not do anything.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void registerPlayerTwice() {
        Player p = mock(Player.class);
        level.registerPlayer(p);
        level.registerPlayer(p);
        verify(p, times(1)).occupy(square1);
    }

    /**
     * Verifies registering a second player puts that player on the correct
     * starting square.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void registerSecondPlayer() {
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        level.registerPlayer(p1);
        level.registerPlayer(p2);
        verify(p2).occupy(square2);
    }

    /**
     * Verifies registering a third player puts the player on the correct
     * starting square.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void registerThirdPlayer() {
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        Player p3 = mock(Player.class);
        level.registerPlayer(p1);
        level.registerPlayer(p2);
        level.registerPlayer(p3);
        verify(p3).occupy(square1);
    }
    
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void collidePlayerGhost() {
    	Player p1 = mock(Player.class);
    	Ghost g1 = mock(Ghost.class);
    	DefaultPlayerInteractionMap collisionMap = mock(DefaultPlayerInteractionMap.class);
    	collisionMap.collide(p1, g1);
    	assertThat(p1.isAlive()).isFalse();
    }
    
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void collidePlayerPellet() {
    	Player p1 = mock(Player.class);
    	Pellet g1 = mock(Pellet.class);
    	DefaultPlayerInteractionMap collisionMap = mock(DefaultPlayerInteractionMap.class);
    	collisionMap.collide(p1, g1);
    	//assertThat(p1.isAlive()).isTrue();
    	assertEquals(p1.getScore(),g1.getValue());
    }
    
    
}
