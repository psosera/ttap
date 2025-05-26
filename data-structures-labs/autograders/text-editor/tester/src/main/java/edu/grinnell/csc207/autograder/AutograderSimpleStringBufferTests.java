package edu.grinnell.csc207.autograder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.grinnell.csc207.texteditor.SimpleStringBuffer;

public class AutograderSimpleStringBufferTests {
    private SimpleStringBuffer makeBufferWith(String s) {
        SimpleStringBuffer buf = new SimpleStringBuffer();
        for (int i = 0; i < s.length(); i++) {
            buf.insert(s.charAt(i));
        }
        return buf;
    }

    @Test
    @DisplayName("String: hello end-to-end")
    public void helloExampleTest() {
        SimpleStringBuffer buffer = new SimpleStringBuffer();
        buffer.insert('h');
        buffer.insert('e');
        buffer.insert('l');
        buffer.insert('l');
        buffer.insert('o');
        buffer.insert(' ');
        buffer.insert('w');
        buffer.insert('o');
        buffer.insert('r');
        buffer.insert('l');
        buffer.insert('d');
    
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.insert('!');
    
        buffer.moveLeft();
        buffer.delete();
        buffer.delete();
        buffer.delete();
        buffer.delete();
        buffer.delete();
    
        buffer.insert('a');
        buffer.insert('b');
        buffer.insert('c');
        assertEquals("abc! world", buffer.toString());
    }

    @Test
    @DisplayName("String: empty")
    public void emptyBufTest() {
        SimpleStringBuffer buf = makeBufferWith("");
        assertEquals(0, buf.getSize(), "size");
        assertEquals(0, buf.getCursorPosition(), "cursor");
    }

    @Test
    @DisplayName("String: cursor movement")
    public void cursorMovementTest() {
        SimpleStringBuffer buf = makeBufferWith("abc");
        assertEquals(3, buf.getCursorPosition(), "initial cursor");
        buf.moveLeft();
        assertEquals(2, buf.getCursorPosition(), "after L");
        buf.moveLeft();
        assertEquals(1, buf.getCursorPosition(), "after LL");
        buf.moveLeft();
        assertEquals(0, buf.getCursorPosition(), "after LLL");
        buf.moveLeft();
        assertEquals(0, buf.getCursorPosition(), "after LLLL");
        buf.moveRight();
        assertEquals(1, buf.getCursorPosition(), "after LLLLR");
        buf.moveRight();
        assertEquals(2, buf.getCursorPosition(), "after LLLLRR");
        buf.moveRight();
        assertEquals(3, buf.getCursorPosition(), "after LLLLRRR");
        buf.moveRight();
        assertEquals(3, buf.getCursorPosition(), "after LLLLRRRR");
    }

    @Test
    @DisplayName("String: insert middle")
    public void cursorInsertMiddleTest() {
        SimpleStringBuffer buf = makeBufferWith("abc");
        buf.moveLeft();
        buf.moveLeft();
        buf.insert('!');
        buf.insert('!');
        assertEquals(5, buf.getSize(), "size");
        assertEquals(3, buf.getCursorPosition(), "cursor");
        assertEquals("a!!bc", buf.toString(), "contents");
    }

    @Test
    @DisplayName("String: delete middle")
    public void cursorDeleteMiddleTest() {
        SimpleStringBuffer buf = makeBufferWith("abc");
        buf.moveLeft();
        buf.delete();
        buf.delete();
        assertEquals(1, buf.getSize(), "size");
        assertEquals(0, buf.getCursorPosition(), "cursor");
        assertEquals("c", buf.toString(), "contents");
    }

    @Test
    @DisplayName("String: insert front")
    public void cursorInsertFrontTest() {
        SimpleStringBuffer buf = makeBufferWith("abc");
        for (int i = 0; i < 3; i++) {
            buf.moveLeft();
        }
        buf.insert('!');
        buf.insert('!');
        assertEquals(5, buf.getSize(), "size");
        assertEquals(2, buf.getCursorPosition(), "cursor");
        assertEquals("!!abc", buf.toString(), "contents");
    }

    @Test
    @DisplayName("String: delete front")
    public void cursorDeleteFrontTest() {
        SimpleStringBuffer buf = makeBufferWith("abc");
        for (int i = 0; i < 3; i++) {
            buf.moveLeft();
        }
        buf.delete();
        assertEquals(3, buf.getSize(), "size");
        assertEquals(0, buf.getCursorPosition(), "cursor");
        assertEquals("abc", buf.toString(), "contents");
    }

    @Test
    @DisplayName("String: delete end")
    public void cursorDeleteEndTest() {
        SimpleStringBuffer buf = makeBufferWith("abc");
        buf.delete();
        buf.delete();
        assertEquals(1, buf.getSize(), "size");
        assertEquals(1, buf.getCursorPosition(), "cursor");
        assertEquals("a", buf.toString(), "contents");
    }
}
