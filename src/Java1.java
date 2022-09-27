import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public abstract class Java1 extends JFrame
        implements ActionListener,
                KeyListener,
                MouseListener,
                MouseMotionListener,
                ComponentListener {

    private static final long serialVersionUID = 1L;

    Turtle turtle;
    HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
    HashMap<String, AudioInputStream> audioObjects = new HashMap<String, AudioInputStream>();
    BufferedImage bi;
    JPanel pane;
    Graphics2D g2;

    /** Width of window */
    int WIDTH = 1000;

    /** Height of window */
    int HEIGHT = 600;

    /** Width of draw area */
    int width = 985; // Draw area

    /** Height of draw area */
    int height = 560; // Draw area

    Timer timer;
    int delay = 10;
    boolean keyUp = false, keyDown = false, keyLeft = false, keyRight = false;
    boolean keyW = false, keyZ = false, keyA = false, keyS = false, keyD = false;
    boolean keySpace = false, keyEnter = false;
    Font font;
    Color bgColor = Color.BLACK;
    protected int mouseX = 0, mouseY = 0;
    protected boolean mouseDown = false;
    
    
    /**
     * Shows an alert box with specified message
     *
     * @param message The message to show in the alert
     */
    protected void alert(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Shows an alert box with specified message and title
     *
     * @param message the message to show in the alert
     * @param title the title of the alert
     */
    protected void alert(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Opens a prompt with an input field, and a message. When OK is pressed it returns the entered
     * string
     *
     * @param message the message in the input prompt
     * @return user's input
     */
    protected String prompt(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

    /**
     * Opens a prompt with an input field, a specified title and a message. When OK is pressed it
     * returns the entered string
     *
     * @param message the message in the input prompt
     * @param title the title of the prompt window
     * @return user's input
     */
    protected String prompt(String message, String title) {
        return JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Opens a prompt with an input field and a message. When OK is pressed it returns the entered
     * string parsed as an integer.
     *
     * @param message the message in the input prompt
     * @return user's input
     */
    protected int intPrompt(String message) {
        return Integer.parseInt(JOptionPane.showInputDialog(null, message));
    }

    /**
     * Opens a prompt with an input field, a specified title and a message. When OK is pressed it
     * returns the entered string parsed as an integer.
     *
     * @param message the message in the input prompt
     * @param title the title of the prompt window
     * @return user's input
     */
    protected int intPrompt(String message, String title) {
        return Integer.parseInt(
                JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE));
    }


    /**
     * Determines the distance between two points
     *
     * @param x1 the x value of point one
     * @param y1 the y value of point one
     * @param x2 the x value point two
     * @param y2 the y value point two
     * @return the distance between the two points, as int
     */
    protected int getDistance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    /**
     * Gets the colour of the pixel at the given position
     *
     * @param x the x position of pixel
     * @param y the y position of pixel
     * @return a Color object with sRGB value of pixel
     */
    protected Color getColor(int x, int y) {
        return new Color(bi.getRGB(x, y));
    }

    /** Hides the cursor by setting it's texture to an empy image */
    protected void hideCursor() {
        BufferedImage emptyCursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blank =
                Toolkit.getDefaultToolkit()
                        .createCustomCursor(emptyCursorImage, new Point(0, 0), "blank");
        pane.setCursor(blank);
    }

    /**
     * Reverts the cursor to it's default texture, thus making it visible if hideCursor() has been
     * run
     */
    protected void showCursor() {
        pane.setCursor(null);
    }

    /**
     * Creates a random number between 0 and max
     *
     * @param max highest number the function can return
     * @return the random int that was generated
     */
    protected int random(int max) {
        return (int) (Math.random() * max);
    }

    /**
     * Draws an oval filled with the specified color with it's left corner at the given x and y
     * coordinates
     *
     * @param x the x value of the upper left corner
     * @param y the y value of the upper left corner
     * @param width the width of the oval
     * @param height the height of the oval
     * @param color the color of the oval
     */
    protected void fillOval(int x, int y, int width, int height, Color color) {
        g2.setColor(color);
        g2.fillOval(x, y, width, height);
    }

    /**
     * Draws an oval outlined by the specified color with it's left corner at the given x and y
     * coordinates
     *
     * @param x the x value of the upper left corner
     * @param y the y value of the upper left corner
     * @param width the width of the oval
     * @param height the height of the oval
     * @param color the color of the oval line
     */
    protected void drawOval(int x, int y, int width, int height, Color color) {
        g2.setColor(color);
        g2.drawOval(x, y, width, height);
    }

    /**
     * Draws a circle filled with the specified color with it's left corner at the given x and y
     * coordinates
     *
     * @param x the x value of the upper left corner
     * @param y the y value of the upper left corner
     * @param radius the radius of the circle
     * @param color the color of the circle
     */
    protected void fillCircle(int x, int y, int radius, Color color) {
        g2.setColor(color);
        g2.fillOval(x, y, radius, radius);
    }

    /**
     * Draws a circle outlined by the specified color with it's left corner at the given x and y
     * coordinates
     *
     * @param x the x value of the upper left corner
     * @param y the y value of the upper left corner
     * @param radius the radius of the circle
     * @param color the color of the circle line
     */
    protected void drawCircle(int x, int y, int radius, Color color) {
        g2.setColor(color);
        g2.drawOval(x, y, radius, radius);
    }

    /**
     * Draws a rectangle filled with the speified color with it's left corner at the given x and y
     * coordinates
     *
     * @param x the x value for the upper left corner
     * @param y the y value for the upper left corner
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param color the color of rectangle
     */
    protected void fillRectangle(int x, int y, int width, int height, Color color) {
        g2.setColor(color);
        g2.fillRect(x, y, width, height);
    }

    /**
     * Draws a rectangle outlined by the speified color with it's left corner at the given x and y
     * coordinates
     *
     * @param x the x value for the upper left corner
     * @param y the y value for the upper left corner
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param color the color of rectangle outline
     */
    protected void drawRectangle(int x, int y, int width, int height, Color color) {
        g2.setColor(color);
        g2.drawRect(x, y, width, height);
    }

    /**
     * Draws a triangle filled by the specified color this function doesn't care which order the
     * points are passed in
     *
     * @param x1 the x value of the first point
     * @param y1 the y value of the first point
     * @param x2 the x value of the second point
     * @param y2 the y value of the second point
     * @param x3 the x value of the third point
     * @param y3 the y value of the third point
     * @param color the color of the triangle
     */
    protected void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
        g2.setColor(color);
        int[] x = {x1, x2, x3};
        int[] y = {y1, y2, y3};
        int amountOfPoints = 3;
        Polygon triangle = new Polygon(x, y, amountOfPoints);
        g2.fillPolygon(triangle);
    }

    /**
     * Draws a triangle outlined by the specified color this function doesn't care which order the
     * points are passed in
     *
     * @param x1 the x value of the first point
     * @param y1 the y value of the first point
     * @param x2 the x value of the second point
     * @param y2 the y value of the second point
     * @param x3 the x value of the third point
     * @param y3 the y value of the third point
     * @param color the color of the triangle outline
     */
    protected void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
        g2.setColor(color);
        g2.drawLine(x1, y1, x2, y2);
        g2.drawLine(x1, y1, x3, y3);
        g2.drawLine(x3, y3, x2, y3);
    }

    /**
     * Draws a line between the specified points with the color specified
     *
     * @param x1 the x value of the first point
     * @param y1 the y value of the first point
     * @param x2 the x value of the second point
     * @param y2 the y value of the second point
     * @param color the color of the line
     */
    protected void drawLine(int x1, int y1, int x2, int y2, Color color) {
        g2.setColor(color);
        g2.drawLine(x1, y1, x2, y2);
    }

    /**
     * Draws image to screen given a path name, first checks to see if the image is loaded, and if
     * not loads it into memory before printing
     *
     * @param pathname path to image file
     * @param x the x value of the top left point
     * @param y the y value of the top left point
     * @throws IOException
     */
    protected void drawImage(String pathname, int x, int y) throws IOException {
        var image = images.get(pathname);
        if(image == null) {
            image = ImageIO.read(new File(pathname));
            images.put(pathname, image);
        }
        g2.drawImage(image, x, y, null);
    }

    /**
     * Draws an Image object to screen at the given x and y coordinates
     *
     * @param image the Image object to draw
     * @param x the x value of the top left point
     * @param y the y value of the top left point
     */
    protected void drawImage(Image image, int x, int y) {
        g2.drawImage(image, x, y, null);
    }

    /**
     * Loads and returns an image object given a file path
     *
     * @param pathname path to the image that should be loaded
     * @return the File object loaded from the given path
     * @throws IOException
     */
    protected Image loadImage(String pathname) throws IOException { // Load image
        File file = new File(pathname);
        return ImageIO.read(file);
    }

    /**
     * Writes the given text to screen with the given colour and the given position
     *
     * @param text the text to write to the screen
     * @param x the x coordinate for the top left corner of the text
     * @param y the y coordinate for the top left corner of the text
     * @param color the color of the text
     */
    protected void text(String text, int x, int y, Color color) {
        // set font to global var font, which can be changed with setTextFont()
        g2.setFont(font);
        // set the pen colour to the give colour
        g2.setColor(color);
        // if the text message is empty we just return, maybe we want to raise an error?
        if (text == null) return;
        // finally, draw the text to the screen
        g2.drawString(text, x, y);
    }

    /**
     * Writes the given text to screen, but rotated 180 degress has the given colour and position,
     * but the x and y values are in the top right instead of the top left
     *
     * @param text the text to write to the screen
     * @param x the x coordinate for the top right corner of the text
     * @param y the y coordinate for the top left corner of the text
     * @param color the color of the text
     */
    protected void text180(String text, int x, int y, Color color) {
        // set font to global var font, which can be changed with setTextFont()
        g2.setFont(font);
        // set the pen colour to the give colour
        g2.setColor(color);
        // if the text message is empty we just return, maybe we want to raise an error?
        if (text == null) return;
        // rotates the pen 180 degrees around the x, y point
        // making the x and y values the top right coordinates
        g2.rotate(Math.toRadians(180), x, y);
        // draw the text to the screen
        g2.drawString(text, x, y);
        // reset the rotation of the pen so nothing else gets screwed
        g2.rotate(Math.toRadians(-180), x, y);
    }

    /**
     * Clears the screen, flooding it with a specified colour
     *
     * @param color
     */
    protected void clear(Color color) {
        // set colour
        g2.setColor(color);
        // fill screen
        g2.fillRect(0, 0, WIDTH, HEIGHT);
    }

    /**
     * Clears the screen, filling it with the current Background colour the background colour can be
     * changed with setBackGround
     *
     * @see #setBackGround(Color)
     */
    protected void clear() {
        g2.setColor(bgColor);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
    }

    /**
     * Sets the refresh rate, or how long the program takes between every screen draw
     *
     * @param wait the amount of time in ms to wait between every frame
     */
    protected void setDelay(int delay) {
        timer.setDelay(delay);
        this.delay = delay;
    }

    /** Stops all graphics updates */
    protected void stop() {
        // stops the loop function from running
        timer.stop();
    }

    /**
     * Changes the default font of the entire program
     *
     * @param font the font to change to
     */
    protected void setTextFont(Font font) {
        this.font = font;
    }
    
    /**
     * Changes the default fontSize of the entire program keeping fontName and style
     *
     * @param fontSize the fontSize to change to
     */
    protected void setTextSize(int fontSize) {
        this.font=new Font(this.font.getFontName(),this.font.getStyle(),fontSize);
    }

    /**
     * Changes the default background colour of the entire program and also clears the screen
     *
     * @param color the new background colour
     */
    protected void setBackGround(Color color) {
        bgColor = color;
        clear();
    }
    /**
     * Changes the size of the Frame and sets the variables WIDTH, HEIGHT, width, height
     *
     * @param w the new WIDTH
     * @param h the new HEIGHT
     */
    public void setSize(int w, int h){
    	super.setSize(w,h);
    	// sets the object values to the new height and width values
    	WIDTH = w;
    	HEIGHT = h;
        // calculate the new size of the grahics2d component
    	width = WIDTH - 15;
    	height = HEIGHT - 40;
        // creates the new image to draw and display things on
        bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // create a new graphics2d object using the buffered image
        g2 = (Graphics2D) bi.createGraphics();
        // update the window
        pane.setSize(WIDTH, HEIGHT);
    }

    /**
     * Plays a sound given a filename
     *
     * @param filename
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    protected void play(String filename)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // create a new empty clip object
                    Clip clip = AudioSystem.getClip();
                    
                    // load a file and process it as an input stream
                    File f = new File("./" + filename);
                    AudioInputStream stream = AudioSystem.getAudioInputStream(f.toURI().toURL());
                    
                    // sets up the click and then plays the sound
                    clip.open(stream);
                    clip.start();
                    
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                    System.out.println("play sound error: " + e.getMessage() + " for " + filename);
                }
            }
        }).start();
    }

    /**
     * Construcotr of class, runs init()
     *
     * @see #init()
     */
    protected Java1() {
        // initialize class
        init();
    }

    /** initialization function that's used in the constructor this sets up the object. */
    protected void init() {
        // setup some global vars
        font = new Font("Arial", Font.PLAIN, 30);
        bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) bi.createGraphics();

        // init new turtle
        turtle = new Turtle();

        pane =
                new JPanel() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g = (Graphics2D) g;
                        g.drawImage(bi, 0, 0, null);
                    }
                };

        // starts a timer, which is the maximum amount of time between frames
        timer = new Timer(delay, this); // timer triggers ActionEvent
        timer.start();

        // add listeners for different inputs
        pane.addKeyListener(this);
        addKeyListener(this);
        pane.addMouseListener(this);
        pane.addMouseMotionListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addComponentListener(this);
        add(pane);

        // set window options
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setTitle("JAVA");
        setVisible(true);
    }

    /**
     * Update function for the object, this function is run every frame the delay value determines
     * the maximum time between frames, and this value can be changed with setDelay() loop() is run
     * every time an event is triggered
     *
     * @see #setDelay(int)
     */
    protected abstract void loop();

    /**
     * runs the loop figure whenever an event triggers. the timer constantly runnign triggers this
     * event, giving us a stable framerate
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        loop();
        repaint();
    }

    /** Event handler that checks a few specific keys and sets flags if they are pressed */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                keyUp = true;
                break;
            case KeyEvent.VK_DOWN:
                keyDown = true;
                break;
            case KeyEvent.VK_LEFT:
                keyLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                keyRight = true;
                break;
            case KeyEvent.VK_W:
                keyW = true;
                break;
            case KeyEvent.VK_Z:
                keyZ = true;
                break;
            case KeyEvent.VK_A:
                keyA = true;
                break;
            case KeyEvent.VK_S:
                keyS = true;
                break;
            case KeyEvent.VK_D:
                keyD = true;
                break;
            case KeyEvent.VK_SPACE:
                keySpace = true;
                break;
            case KeyEvent.VK_ENTER:
                keyEnter = true;
                break;
        }
    }

    /** Event handler that checks a few specific keys and sets flags if they have been released */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                keyUp = false;
                break;
            case KeyEvent.VK_DOWN:
                keyDown = false;
                break;
            case KeyEvent.VK_LEFT:
                keyLeft = false;
                break;
            case KeyEvent.VK_RIGHT:
                keyRight = false;
                break;
            case KeyEvent.VK_W:
                keyW = false;
                break;
            case KeyEvent.VK_Z:
                keyZ = false;
                break;
            case KeyEvent.VK_A:
                keyA = false;
                break;
            case KeyEvent.VK_S:
                keyS = false;
                break;
            case KeyEvent.VK_D:
                keyD = false;
                break;
            case KeyEvent.VK_SPACE:
                keySpace = false;
                break;
            case KeyEvent.VK_ENTER:
                keyEnter = false;
                break;
        }
    }

    /** Event handler for when a key is pressed in a text field */
    @Override
    public void keyTyped(KeyEvent e) {}

    /** Event handler for when the mouse has been clicked (pressed and released) */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /** Event handler for when the mouse button has been pressed */
    @Override
    public void mousePressed(MouseEvent e) {
        mouseDown = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    /** Event handler for when the mouse button has been released */
    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }

    /** Event handler for when the mouse has entered a component */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /** Event handler for when the mouse has exited a component */
    @Override
    public void mouseExited(MouseEvent e) {}

    /** Event handler for when the mouse button is pressed and mouse is moved */
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    /** Event handler for when the mouse has been moved */
    @Override
    public void mouseMoved(MouseEvent e) {}

    /** Event handler for when a component has been hidden */
    public void componentHidden(ComponentEvent ce) {}
    ;

    /** Event handler for when a component has been made visible */
    public void componentShown(ComponentEvent ce) {}
    ;

    /** Event handler for when a component has been moved */
    public void componentMoved(ComponentEvent ce) {}
    ;

    /** Event handler for when a component has been resized */
    public void componentResized(ComponentEvent ce) {
        // sets the object values to the new height and width values
        HEIGHT = this.getHeight();
        WIDTH = this.getWidth();
        // calculate the new size of the grahics2d component
        height = HEIGHT - 40;
        width = WIDTH - 15;
        // creates the new image to draw and display things on
        bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // create a new graphics2d object using the buffered image
        g2 = (Graphics2D) bi.createGraphics();
        // update the window
        pane.setSize(WIDTH, HEIGHT);
    }
    ;

    /**
     * The Turtle library provides commands to create and control a turtle, that can be moved
     * around, drawing a line behind it.
     */
    public class Turtle {
        public double x, y;
        private Stack<Double> xList = new Stack<Double>();
        private Stack<Double> yList = new Stack<Double>();

        /** The current angle of the Turtle, 0 is right */
        public double angle;

        public Color color = new Color(100, 100, 255);

        /**
         * Constructor without arguments, for constructor with arguments
         *
         * @see #Turtle(int, int, double)
         */
        public Turtle() {
            y = 280;
            x = 492;
            angle = 0;
        }

        /**
         * constructor with arguments
         *
         * @param x the initial x value of the turtle
         * @param y the initial y value of the turtle
         * @param angle the inital angle of the turtle
         */
        public Turtle(int x, int y, double angle) {
            this.x = x;
            this.y = y;
            this.angle = angle;
        }

        /**
         * Turns the turtle to the left a specified amount of degrees
         *
         * @param angle the amount of degrees to turn
         */
        public void turnLeft(double angle) {
            this.angle += angle;
        }

        /**
         * Turns the turtle to the right a specified amount of degrees
         *
         * @param angle the amount of degrees to turn
         */
        public void turnRight(double angle) {
            this.angle -= angle;
        }

        /**
         * Moves the turtle a specified distance forwards, drawing a line behind it.
         *
         * @param distance the distance to move forwards
         */
        public void move(int distance) {
            // calcualte the new x and y values taking into account current x, y and angle
            double xx = (x + distance * Math.cos(Math.toRadians(angle)));
            double yy = (y - distance * Math.sin(Math.toRadians(angle)));
            // draw line from old position to new position
            g2.setColor(color);
            g2.drawLine((int) x, (int) y, (int) xx, (int) yy);
            // update to the new values
            x = xx;
            y = yy;
        }

        /**
         * Moves the turtle a specified distance forwards, not drawing anything
         *
         * @param distance the distance to move forwards
         */
        public void moveX(int distance) {
            x = (x + distance * Math.cos(Math.toRadians(angle)));
            y = (y - distance * Math.sin(Math.toRadians(angle)));
        }

        /**
         * Save x and y positions to the stack to be recovered laters
         *
         * @see #pop()
         */
        public void push() {
            xList.push(x);
            yList.push(y);
        }

        /**
         * Recover x and y positions from the stack
         *
         * @see #push()
         */
        public void pop() {
            x = xList.pop();
            y = yList.pop();
        }

        /**
         * Runs a series of commands, specified by letters in a string. char to commands:
         *
         * <p>F & G : Move forward with pen
         *
         * <p>f : Move forwards without pen
         *
         * <p>+ : turn left
         *
         * <p>- : turn right
         *
         * <p>[ : push coordinates to stack (save coordinates)
         *
         * <p>] : pop coordinates from stack (load saved coordinates)
         *
         * <p>
         *
         * @param string a string of characters that all symbolise different commands
         * @param angle the angle which all turns should be taken as
         * @param length the length all move commands should move forwards
         * @see #move(int)
         * @see #moveX(int)
         * @see #turnLeft(double)
         * @see #turnRight(double)
         * @see #push()
         * @see #pop()
         */
        public void LString(String string, double angle, int length) {
            for (int i = 0; i < string.length(); i++) {
                switch (string.charAt(i)) {
                    case 'F':
                    case 'G':
                        // move forward and draw line
                        move(length);
                        break;
                    case 'f':
                        // "jump" forward without drawing a line
                        moveX(length);
                        break;
                    case '+':
                        // turn left
                        turnLeft(angle);
                        break;
                    case '-':
                        // turn right
                        turnRight(angle);
                        break;
                    case '[':
                        // save position to stack
                        push();
                        break;
                    case ']':
                        // recover position from stack
                        pop();
                        break;
                }
            }
        }

        /**
         * Draws a fractal at the given position. Fractal takes an axiom, a base value, and then
         * modifies it according to the specified rules. The itereations variable defines how many
         * iterations it does. an example of how the function is rus is the following: <br>
         * turtle.Fractal("G", "F,FF,G,F[-G]++G-,[,[,+,+,-,-,],]", 8, 45, 2); <br>
         * This creates a standard binary tree fractal. For every iteration, the rules state that
         * the character F becomes FF, G becomes F[-G]++G-, and all other characters remain the same
         *
         * <p>For more information on LString Fractals, refer to <a
         * href="https://en.wikipedia.org/wiki/L-system">this page</a>
         *
         * @param axiom The original string, that gets modified by the values in rules
         * @param rules A list of In, Out; where every occurance of in becomes the out string.
         *     Example string that doesn't modify the axiom is "F,F,G,G,f,f,[,[,+,+,-,-,],]"
         * @param iterations The amount of iterations the fractal goes through
         * @param angle The angle for all turns
         * @param length The length of all forward moves
         * @see #LString(String, double, int)
         */
        public void Fractal(String axiom, String rules, int iterations, double angle, int length) {
            // split and save the comma separated values into a list
            // even indexes (and 0) are the keys that get searched through sys,
            // whilst odd numbers are the value they are changed to
            String[] _rules = rules.replaceAll(" ", "").split(",");

            // this is the first value the program iterates over, the "base stub" so to say
            String sys = axiom;

            for (int i = 0; i < iterations; i++) {

                int currentLen = sys.length();
                String newSys = "";

                // this loops goes through every character in sys
                // for every character, check if it's in the indexes of _rules, otherwise we ingore
                // it
                // if it is in the indexes, we append the new characters corresponding to that
                // index, to
                // newSys
                // this essentially goes through our original string, and appends the rules from
                // _rules to
                // that string
                for (int u = 0; u < currentLen; u++) {
                    for (int r = 0; r < _rules.length; r += 2) {
                        if (("" + sys.charAt(u)).equals(_rules[r])) {
                            newSys += _rules[r + 1];
                        }
                    }
                }
                sys = newSys;
            }
            LString(sys, angle, length);
        }
    }

}
