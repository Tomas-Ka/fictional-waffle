import java.awt.Color;
import java.awt.event.MouseEvent;

public class Fractals extends Java1 {

	private double x = 500;
	private double y = 500;
	
	private Color Green = new Color(0, 100, 0); //green color
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new Fractals();
		
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX=e.getX();
		mouseY=e.getY();
	}

	@Override
	protected void loop() {
		clear();
		setDelay(10);
		turtle.x = x;
		turtle.y = y;
		turtle.angle = 90;
		
		//Koch curve
		turtle.x = 200;
		turtle.y = 500;
		turtle.angle=0;
		turtle.Fractal("F", "F,F+F-F-F+F,+,+,[,[,-,-,],]", 4, 90, 7);

		//Fractal (Binary) Tree
		/*
		turtle.x = 500;
		turtle.y = 550;
		turtle.angle = 90;
		turtle.Fractal("G", "F,FF,G,F[-G]++G-,[,[,+,+,-,-,],]", 8, 45, 2);
		*/
		
		//Sierpinski triangle
		/*
		turtle.x = 300;
		turtle.y = 500;
		turtle.angle = 60;
		turtle.Fractal("F-G-G-", "F,F-G+F+G-F,G,GG,[,[,+,+,-,-,],]", 7, 120, 4);
		*/
		
		//Sierpiński arrowhead curve
		/*
		turtle.x = 300;
		turtle.y = 450;
		turtle.angle = 60;
		turtle.Fractal("F", "F,G-F-G,G,F+G+F,[,[,+,+,-,-,],]", 7, 60, 3);
		*/
		
		//Dragon curve
		/*
		turtle.x = 400;
		turtle.y = 150;
		turtle.angle = 90;
		turtle.Fractal("F", "F,F+G,G,F-G,[,[,+,+,-,-,],]", 13, 90, 4);
		*/
		
		//Fractal Plant (Barnsley Fern)
		/*
		turtle.x = 50;
		turtle.y = 500;
		turtle.angle = 60;
		turtle.Fractal("X", "X,F+[[X]-X]-F[-FX]+X+,F,FF,[,[,+,+,-,-,],]", 6, 25, 6);
		*/
	}

}
