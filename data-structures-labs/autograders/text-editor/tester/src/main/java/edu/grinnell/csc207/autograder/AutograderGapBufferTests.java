package edu.grinnell.csc207.autograder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.grinnell.csc207.texteditor.GapBuffer;

public class AutograderGapBufferTests {
    private GapBuffer makeBufferWith(String s) {
        GapBuffer buf = new GapBuffer();
        for (int i = 0; i < s.length(); i++) {
            buf.insert(s.charAt(i));
        }
        return buf;
    }

    @Test
    @DisplayName("Gap: hello end-to-end")
    public void helloExampleTest() {
        GapBuffer buffer = new GapBuffer();
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
    @DisplayName("Gap: empty")
    public void emptyBufTest() {
        GapBuffer buf = makeBufferWith("");
        assertEquals(0, buf.getSize(), "size");
        assertEquals(0, buf.getCursorPosition(), "cursor");
    }

    @Test
    @DisplayName("Gap: cursor movement")
    public void cursorMovementTest() {
        GapBuffer buf = makeBufferWith("abc");
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
    @DisplayName("Gap: insert middle")
    public void cursorInsertMiddleTest() {
        GapBuffer buf = makeBufferWith("abc");
        buf.moveLeft();
        buf.moveLeft();
        buf.insert('!');
        buf.insert('!');
        assertEquals(5, buf.getSize(), "size");
        assertEquals(3, buf.getCursorPosition(), "cursor");
        assertEquals("a!!bc", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: delete middle")
    public void cursorDeleteMiddleTest() {
        GapBuffer buf = makeBufferWith("abc");
        buf.moveLeft();
        buf.delete();
        buf.delete();
        assertEquals(1, buf.getSize(), "size");
        assertEquals(0, buf.getCursorPosition(), "cursor");
        assertEquals("c", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: insert front")
    public void cursorInsertFrontTest() {
        GapBuffer buf = makeBufferWith("abc");
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
    @DisplayName("Gap: delete front")
    public void cursorDeleteFrontTest() {
        GapBuffer buf = makeBufferWith("abc");
        for (int i = 0; i < 3; i++) {
            buf.moveLeft();
        }
        buf.delete();
        assertEquals(3, buf.getSize(), "size");
        assertEquals(0, buf.getCursorPosition(), "cursor");
        assertEquals("abc", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: delete end")
    public void cursorDeleteEndTest() {
        GapBuffer buf = makeBufferWith("abc");
        buf.delete();
        buf.delete();
        assertEquals(1, buf.getSize(), "size");
        assertEquals(1, buf.getCursorPosition(), "cursor");
        assertEquals("a", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: big buffer")
    public void bigBufferTest() {
        GapBuffer buf = new GapBuffer();
        for (int i = 0; i < 16384; i++) {
            buf.insert((char) (i % 10 + '0'));
        }
        assertEquals(16384, buf.getSize(), "size");
        assertEquals(16384, buf.getCursorPosition(), "cursor");

        for (int i = 0; i < 300; i++) {
            buf.moveLeft();
        }
        buf.insert('!');
        buf.insert('!');
        buf.delete();
        assertEquals(16385, buf.getSize(), "size");
        assertEquals(16085, buf.getCursorPosition(), "cursor");
    }
}
