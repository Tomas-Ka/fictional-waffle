import java.awt.Color;
import java.io.IOException;

public class Grafik1 extends Java1{

	private static final long serialVersionUID = 1L;
	private int x = 0;
	private boolean right = true;
	private boolean run_once = false;
	
	@SuppressWarnings("unused")
	private Color Green = new Color(0, 100, 0); //green color
	@SuppressWarnings("unused")
	private Color Brown = new Color(92, 64, 51); //brown color
	
	public static void main(String[] args) {
		new Grafik1();
		
	}

	@Override
	protected void loop() {
		if(!run_once) {
			//turtle.move(1);
			//fillCircle(50, 50, 100, Color.red);
			//Sturtle.Fractal("1213131213", "1,F,2,+,3,-,4,f", 4, 10, 50);
			//turtle.LString("F+F-F-F+F-", 70, 50);
		}
		//fillCircle(50, 50, 100, Color.red);
		
		
		if(right) {
			x += 5;
		} else {
			x -= 5;
		}
		
		if(right && x>=width-50) {
			right = !right;
			x = width-50;
		} else if (!right && x<=0) {
			right = !right;
			x = 0;
		}
		
		/*
		fillRectangle(0, 0, width, height, Color.black); //black background
		fillRectangle(0, height-height/6, width, height/6, Green); //grass
		
		fillCircle(10, 10, 100, Color.yellow); //yellow part of the moon
		fillCircle(35, 10, 100, Color.black); //black shadow to make the moon appear as a slice
		
		fillRectangle(740, 360, 50, 150, Brown); //Tree trunk
		fillOval(665, 230, 200, 150, Green); // Tree leaves
		
		fillRectangle(250, height-height/6-180, 250, 180, Color.red); // house body
		fillRectangle(260, 340, 50, 70, Color.orange); //window 1
		fillRectangle(340, 340, 50, 70, Color.orange); //window 2
		fillRectangle(420, 346, 60, 120, Brown); //door
		fillTriangle(209, 287, 540, 287, 376, 176, Brown); //Roof
		*/
		
		//half shaded triangle test
		//fillT(500, 200, 100, 100, 110, 100, Brown);
		
		
		try {
			drawImage("astley2.jpg", 300, 00);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Bouncing ball
		//fillCircle(x, 50, 50, Color.red);
		
		//text("x: " + Integer.toString(mouseX) + ", y:" + Integer.toString(mouseY), 50, 50, Color.white);
	}
}