import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

@SuppressWarnings("serial")
public class DrawRectangle extends Rectangle implements Serializable, Shape {
	
	private double posX;
	private double posY;
	private String color;
	protected double width;
	protected double length;
	
	

	public DrawRectangle(double posX, double posY, String color) {
		this.posX = posX;
		this.posY = posY;
		this.color = color;
	}

	
	
	
	
	//to check if a shape is selected or not 
	public boolean isSelected(double x, double y) {
		if (x >= posX && x <= (posX + width) && y >= posY && y <= (posY + length)) {
			return true;
		} else
			return false;

	}
	
	
	
	
	
	//to create the main properties of a shape
	@Override
	public void createShape(MouseEvent event, AnchorPane canvasGroup) {

		setX(event.getX());
		setY(event.getY());
		setFill(stringToColor(color));

		canvasGroup.getChildren().add(this);

	}
	
	
	
	
	//to create the main properties of a shape
	public void createShape(double x, double y, String color, AnchorPane canvasGroup ) {

		setX(x);
		setY(y);
		setFill(stringToColor(color));

	}
	
	
	
	
	
	
	//to edit shape properties by mouse
	public void editShape(MouseEvent event) {

		double deltaX = event.getX() - posX;
		double deltaY = event.getY() - posY;

		width = getWidth() + deltaX;
		length = getHeight() + deltaY;

		setWidth(width);
		setHeight(length);
	}

	
	
	
	
	
	
	//to edit shape properties by textField
	public void editShape(double w, double l) {
		setWidth(w);
		setHeight(l);
		
		width = w;
		length = l;

	}

	
	
	
	
	
	//add the shape to the arraylist<shape>
	@Override
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
	@Override
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
	@Override
	public void setColor(String color) {
		this.color = color;
		this.setFill(stringToColor(color));
	}

	@Override
	public void setPosX(double x) {
		this.posX = x;
	}

	@Override
	public void setPosY(double y) {
		this.posY = y;

	}
	public String toString() {
		return posX + " " + posY + " " + length + " " + width + " " + color;
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
	public double getLength() {
		return length;
	}
	
	public double getMyWidth() {
		return width;
	}
	public void setLength(double length) {
		this.length = length;
		setHeight(length);
	}
	
	public void setMyWidth(double width) {
		this.width = width;
		setWidth(width);
	}

}
