package nl.tudelft.jpacman;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.sprite.Sprite;
import nl.tudelft.jpacman.sprite.SpriteStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;



/**
 * Tests the various methods provided by the {@link PacmanConfigurationException} class.
 *
 * @author Nikita Dua,Radhika Krishnan
 *
 */

public class PacmanConfigurationExceptionTest {
    /**
     * Map parser used to construct boards.
     */
    private MapParser parser;
    private Sprite sprite;
    private SpriteStore store;


    /**
     * Set up the map parser.
     */
    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        parser = new MapParser(new LevelFactory(sprites, new GhostFactory(
            sprites)), new BoardFactory(sprites));
        store = new SpriteStore();
    }


    /** Test for MapParser class functions **/
    /**
     * Test parse map with a invalid character in the map of characters , addSquare function throws the exception
     */
    @Test()
    void testAddSquareInvalidCharacter(){
        try {
            parser.parseMap(Lists.newArrayList("#####", "XXXXX", "#####"));
        } catch (PacmanConfigurationException exception){
            System.out.println(exception);
            assertEquals("Invalid character at 0,1: X", exception.getMessage());
        }
    }
    /**
     * Test parse map with a valid characters in the map of characters , addSquare function does not exception and
     * parseMap returns a not null level
     */
    @Test()
    void testAddSquareValidCharacter(){
        try {

            Level l = parser.parseMap(Lists.newArrayList("  ", "P"));
            assertNotNull(l);
        } catch(PacmanConfigurationException exception){

        }
    }

    /**
     * Test checkMap with null text
     */
    @Test()
    void testCheckFormatWithNullList(){
        try {
            Level l = parser.parseMap((List<String>) null);
        }
        catch (PacmanConfigurationException exception){
            assertEquals("Input text cannot be null.", exception.getMessage());
        }
    }

    /**
     * Test checkMap with empty list text
     */
    @Test()
    void testCheckFormatWithEmptyList(){
        try {
            List<String> text = new ArrayList<>();
            Level l = parser.parseMap(text);
        }
        catch (PacmanConfigurationException exception){
            assertEquals("Input text must consist of at least 1 row.", exception.getMessage());
        }
    }

    /**
     * Test checkMap with list text having an empty string as a parameter
     */
    @Test()
    void testCheckFormatWithEmptyStringInList(){
        try {
            List<String> text = Lists.newArrayList("");
            Level l = parser.parseMap(text);
        }
        catch (PacmanConfigurationException exception){
            assertEquals("Input text lines cannot be empty.", exception.getMessage());
        }
    }

    /**
     * Test checkMap with list having unequal length of strings as parameters
     */
    @Test()
    void testCheckFormatWithUnequalStringsInList(){
        try {
            List<String> text = Lists.newArrayList("###", "##");
            Level l = parser.parseMap(text);
        }
        catch (PacmanConfigurationException exception){
            assertEquals("Input text lines are not of equal width.", exception.getMessage());
        }
    }

    /**
     * Test checkMap with list having unequal length of strings as parameters
     * This covers the off point for all boundaries. As the list is non null, not empty, not a
     * list with a empty string and not a list with strings of unequal lengths
     */
    @Test()
    void testCheckFormatWithValidList(){
        try {
            List<String> text = Lists.newArrayList("###", "###");
            Level l = parser.parseMap(text);
            assertNotNull(l);
        }
        catch (PacmanConfigurationException exception){
        }
    }

    /**
     * Test parseMap(String mapName) with invalid mapname off point
     */
    @Test()
    void testParseMapWithInvalidMapName() {
        try {
           parser.parseMap(("xyz"));
        }
        catch (IOException exception){
        }
        catch(PacmanConfigurationException exception){
            assertEquals("Could not get resource for: xyz", exception.getMessage());
        }
    }

    /**
     * Test parseMap(String mapName) with valid mapname off point
     */
    @Test()
    void testParseMapWithValidMapName() {
        try {
           Level l = parser.parseMap(("/board.txt"));
           assertNotNull(l);
        }
        catch (IOException exception){
        }
        catch(PacmanConfigurationException exception){
        }
    }

    /**
     * Test PacManSprites functions
     */

    /**
     * Test loadSprite with a valid resource
     */

    @Test()
    void testLoadSpriteWithValidResource() {
        try {
            Sprite sprite = store.loadSprite("/sprite/64x64white.png");
            assertNotNull(sprite);
        }
        catch (IOException exception){
        }
    }


    /**
     * Test loadSprite with a invalid resource
     */

    @Test()
    void testLoadSpriteWithInvalidResource() {
        try {
            Sprite sprite = store.loadSprite("/sprite/32x32white.png");
        }
        /* PacmanConfigurationException is thrown as a runtime exception */
        catch (IOException exception){
            assertEquals("Unable to load /sprite/32x32white.png, resource does not exist.", exception.getMessage());
        }
    }





}
