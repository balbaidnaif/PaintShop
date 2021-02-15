import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

@SuppressWarnings("serial")
public class DrawCircle extends Circle implements Serializable, Shape {

	private double posX;
	private double posY;
	private String color;
	private double radius;

	
	public DrawCircle(double posX, double posY, String color) {
		this.posX = posX;
		this.posY = posY;
		this.color = color;
	}
	
	
	
	
	//to check if a shape is selected or not 
	@Override
	public boolean isSelected(double x, double y) {
		if (x >= (posX - radius) && x <= (posX + radius) && y >= (posY - radius) && y <= (posY + radius)) {
			return true;
		} else
			return false;
	}
	
	
	
	
	//to create the main properties of a shape
	@Override
	public void createShape(MouseEvent event, AnchorPane canvasGroup) {

		setCenterX(event.getX());
		setCenterY(event.getY());

		canvasGroup.getChildren().add(this);

		setFill(stringToColor(color));

	}
	
	
	//to create the main properties of a shape
	public void createShape(double x, double y, String color, AnchorPane canvasGroup ) {

		setCenterX(x);
		setCenterY(y);
		setFill(stringToColor(color));
		
	}

	
	
	
	//to edit shape properties by mouse
	@Override
	public void editShape(MouseEvent event) {

		double deltaR = event.getY() - posY;

		radius = getRadius() + deltaR;

		setRadius(radius);

	}

	
	//to edit shape properties by textField
	@Override
	public void editShape(double w, double l) {
		setRadius(w);
		radius = w;

	}

	
	
	//add the shape to the arraylist<shape>
		public void addShape(Shape shape, ArrayList<Shape> shapeArray) {

			if (shapeArray.size() == 0)
				shapeArray.add(shape);
			else {
				for (int i = 0; i < shapeArray.size(); i++) {

					if (!shapeArray.get(i).equals(shape) && i == shapeArray.size() - 1) {
						shapeArray.add(shape);

					}
				}
			}

		}
		
		
		
		
		
		
		
	//convert a string to color.
	public Color stringToColor(String color) {
		switch (color) {
		case "BLACK":
			return Color.BLACK;

		case "BROWN":
			return Color.BROWN;

		case "RED":
			return Color.RED;

		case "BLUE":
			return Color.BLUE;

		case "YELLOW":
			return Color.YELLOW;

		case "ORANGE":
			return Color.ORANGE;

		case "GREEN":
			return Color.GREEN;

		case "GREY":
			return Color.GREY;

		case "WHITE":
			return Color.WHITE;

		default:
			return Color.BLACK;
		}
	}

	
	
	
	
	
	
	//setters and getters
	public double getMyRadius() {
		return radius;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public void setColor(String color) {
		this.color = color;
		this.setFill(stringToColor(color));
	}

	public void setMyRadius(double radius) {
		this.radius = radius;
		setRadius(radius);
	}

	public String toString() {
		return posX + " " + posY + " " + radius + " " + color;
	}

	@Override
	public String getColor() {
		return color;
	}
	@Override
	public double getPosX() {
		return posX;
	}
	@Override
	public double getPosY() {
		return posY;
	}

}
