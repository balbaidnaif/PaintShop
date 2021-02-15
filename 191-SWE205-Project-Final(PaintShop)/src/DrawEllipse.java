import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

@SuppressWarnings("serial")
public class DrawEllipse extends Ellipse implements Serializable, Shape {

	
	private double posX;
	private double posY;
	private String color;
	private double minor;
	private double major;
	
	public DrawEllipse(double posX, double posY, String color) {
		this.posX = posX;
		this.posY = posY;
		this.color = color;

	}

	
	
	//to check if a shape is selected or not 
	@Override
	public boolean isSelected(double x, double y) {
		if (x >= (posX - major) && x <= (posX + major) && y >= (posY - minor) && y <= (posY + minor)) {
			return true;
		} else
			return false;

	}

	
	
	
	
	
	//to create the main properties of a shape
	@Override
	public void createShape(MouseEvent event, AnchorPane canvasGroup) {

		setCenterX(event.getX());
		setCenterY(event.getY());
		setFill(stringToColor(color));

		canvasGroup.getChildren().add(this);

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

		double deltaX = event.getX() - posX;
		double deltaY = event.getY() - posY;

		major = getRadiusX() + deltaX;
		minor = getRadiusY() + deltaY;

		setRadiusX(major);
		setRadiusY(minor);
	}

	
	
	//to edit shape properties by textField
	@Override
	public void editShape(double w, double l) {
		setRadiusX(w);
		setRadiusY(l);
		
		major = w;
		minor = l;
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
		return posX + " " + posY + " " + major + " " + minor + " " + color;
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
	public double getMajor() {
		return major;
	}

	public double getMinor() {
		return minor;
	}

}
