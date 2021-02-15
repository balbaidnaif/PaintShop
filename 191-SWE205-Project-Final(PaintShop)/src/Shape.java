import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public interface Shape{
	
	public void addShape(Shape shape, ArrayList<Shape> shapeArray);
	public boolean isSelected(double x, double y);
	public void createShape(MouseEvent event, AnchorPane canvasGroup);
	public void editShape(MouseEvent event);
	public void editShape(double w, double l);
	public Color stringToColor(String color);
	public void setColor(String color);
	public void setPosX(double x);
	public void setPosY(double y);
	public String getColor();
	public double getPosX();
	public double getPosY();
	public void createShape(double x, double y, String color, AnchorPane canvasGroup );

	
}
