
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GUIAndDriver extends Application {

	static File workspace = new File("DataBase.dat");
	static FileOutputStream out;
	static FileInputStream in;
	static AnchorPane anchorPane = new AnchorPane();
	static BorderPane mainPane = new BorderPane();
	// this array stores the shapes with javafx objects for GUI
	static ArrayList<Shape> shapeArray = new ArrayList<Shape>();
	public static String shapeType = "";
	public String color = "";
	private static Shape tempShape;
	double inX, inY;
	int selected = -1;
	


	//saves the the workspace to a .dat file.
	public static void save(Stage stage) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Export");

			FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("DAT files (*.dat*)",
					"*.dat");
			fileChooser.getExtensionFilters().add(extensionFilter);

			workspace = fileChooser.showSaveDialog(stage);

			out = new FileOutputStream(workspace);
			ObjectOutputStream write = new ObjectOutputStream(out);

			write.writeObject(shapeArray);
			write.close();
		} catch (Exception e) {

			e.getStackTrace();

		}
	}
	
	
	
	//opens a saved workspace
	@SuppressWarnings("unchecked")
	public static void open(Stage stage) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open");
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("DAT files (*.dat*)", "*.dat");
		fc.getExtensionFilters().add(extensionFilter);
		File datFile = fc.showOpenDialog(stage);
		try {
			FileInputStream in = new FileInputStream(datFile);
			ObjectInputStream read = new ObjectInputStream(in);
			
			shapeArray = (ArrayList<Shape>)read.readObject();
			
			for (int i = 0; i < shapeArray.size(); i++) {
				switch (shapeArray.get(i).getClass().toString()) {
				case "class DrawRectangle":
					DrawRectangle tempRec = (DrawRectangle)shapeArray.get(i);
					tempRec.createShape(tempRec.getPosX(), tempRec.getPosY(), tempRec.getColor(), anchorPane);
					
					tempRec.editShape(tempRec.getMyWidth(),tempRec.getLength());
					
					anchorPane.getChildren().add(tempRec);
					
					break;
				case "class DrawSquare":
					System.out.println("hi");
					DrawSquare tempSq = (DrawSquare)shapeArray.get(i);
					tempSq.createShape(tempSq.getPosX(), tempSq.getPosY(), tempSq.getColor(), anchorPane);
					
					tempSq.editShape(tempSq.getMyWidth(),tempSq.getLength());
					
					anchorPane.getChildren().add(tempSq);

					System.out.println(tempSq);
					
					break;
				case "class DrawCircle":
					DrawCircle tempCir = (DrawCircle)shapeArray.get(i);
					tempCir.createShape(tempCir.getPosX(), tempCir.getPosY(), tempCir.getColor(), anchorPane);
					
					tempCir.editShape(tempCir.getMyRadius(),tempCir.getMyRadius());
					
					anchorPane.getChildren().add(tempCir);
					
					break;
				case "class DrawEllipse":
					System.out.println("hi");
					DrawEllipse tempElps = (DrawEllipse)shapeArray.get(i);

					tempElps.createShape(tempElps.getPosX(), tempElps.getPosY(), tempElps.getColor(), anchorPane);
					
					tempElps.editShape(tempElps.getMajor(),tempElps.getMinor());
					
					anchorPane.getChildren().add(tempElps);

					System.out.println(tempElps);
					
					break;

				default:
					break;
				}
			}


		} catch (Exception e) {
		}
	}

	
	
	
	

	
	//start method controls the GUI and main functionality of the program
	@Override
	public void start(Stage arg0) throws Exception {

		// Background setup
		GridPane vMenuPane = new GridPane();
		GridPane hMenuPane = new GridPane();
		vMenuPane.setPadding(new Insets(15));
		hMenuPane.setPadding(new Insets(10));

		vMenuPane.setVgap(25);
		vMenuPane.setHgap(10);
		hMenuPane.setVgap(8);
		hMenuPane.setHgap(10);
		
		//scene setup
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		Scene mainScene = new Scene(mainPane, screenBounds.getWidth(), (screenBounds.getHeight() * 0.94));
		mainScene.setFill(Color.LIGHTGREY);
		arg0.setScene(mainScene);
		arg0.setTitle("MyPaintShop");
		arg0.show();

		//draw area setup
		Canvas drawRegion = new Canvas((mainScene.getWidth()), (mainScene.getHeight() - 210));
		mainPane.getChildren().add(anchorPane);
		anchorPane.getChildren().add(drawRegion);
		mainPane.getChildren().add(drawRegion);
		
		
		Rectangle vMenuBar = new Rectangle(0, 0, 250, mainScene.getWidth());
		vMenuBar.setFill(Color.GREY);
		
		Rectangle shapeRec = new Rectangle(10, 60, 230, 345);
		shapeRec.setFill(Color.LIGHTGREY);

		Rectangle colorRec = new Rectangle(10, 500, 230, 160);
		colorRec.setFill(Color.LIGHTGREY);

		Rectangle hMenuBar = new Rectangle(250, (drawRegion.getHeight() + 25), mainScene.getWidth() - 250, 300);
		hMenuBar.setFill(Color.GREY);

		Rectangle propRec = new Rectangle(285, (drawRegion.getHeight() + 40), mainScene.getWidth() - 765, 155);
		propRec.setFill(Color.LIGHTGREY);

		Rectangle workSpcRec = new Rectangle(mainScene.getWidth() - 410, (drawRegion.getHeight() + 40), 200, 155);
		workSpcRec.setFill(Color.LIGHTGREY);
		

		Line vMenuBorderLine = new Line(250, 0, 250, mainScene.getHeight());
		Line hMenuBorderLine = new Line(250, (drawRegion.getHeight() + 25), mainScene.getWidth(),(drawRegion.getHeight() + 25));

		mainPane.getChildren().addAll(vMenuBar, hMenuBar, vMenuBorderLine, hMenuBorderLine, shapeRec, colorRec, propRec,workSpcRec);
		mainPane.setLeft(vMenuPane);
		mainPane.setBottom(hMenuPane);

		
		
		// change font settings
		Font mainFont = new Font(20);

		// Shapes setup
		Label shapes = new Label("Shapes:");
		shapes.setFont(mainFont);
		vMenuPane.add(shapes, 0, 0);

		//color label
		Label colorLabl = new Label("Color Selected =");
		colorLabl.setFont(mainFont);
		GridPane.setHalignment(colorLabl, HPos.RIGHT);
		hMenuPane.add(colorLabl, 1, 1);
		Label colorLab2 = new Label();
		colorLab2.setFont(mainFont);
		hMenuPane.add(colorLab2, 2, 1);

		//shape label
		Label shapLabl = new Label("Shape Seleceted=");
		shapLabl.setFont(mainFont);
		GridPane.setHalignment(shapLabl, HPos.RIGHT);
		Label shapLab2 = new Label();
		shapLab2.setFont(mainFont);
		hMenuPane.add(shapLab2, 2, 2);
		hMenuPane.add(shapLabl, 1, 2);

		
		
		
		// square setup
		Rectangle sqrBtn = new Rectangle(90, 190, 80, 80);
		sqrBtn.setFill(Color.GREY);

		sqrBtn.setOnMouseClicked(e -> {
			setLabel(shapLab2, "Square");
			drawRegion.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
						tempShape = new DrawSquare(event.getX(), event.getY(), color);
						tempShape.setPosX(event.getX());
						tempShape.setPosY(event.getY());
						tempShape.createShape(event, anchorPane);
						inX = event.getX();
						inY = event.getY();
					}
					if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

						tempShape.editShape(event);
						tempShape.setPosX(event.getX());
						tempShape.setPosY(event.getY());

					}
					if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
						tempShape.addShape(tempShape, shapeArray);

						drawRegion.removeEventFilter(MouseEvent.ANY, this);
						tempShape.setPosX(inX);
						tempShape.setPosY(inY);
						setLabel(shapLab2, "");
					}
				}
			});

		});

		
		
		
		
		// ellipse setup
		Ellipse elpsBtn = new Ellipse(75, 120, 55, 30);
		elpsBtn.setFill(Color.GREY);

		elpsBtn.setOnMouseClicked(e -> {
			setLabel(shapLab2, "Ellipse");
			drawRegion.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
						tempShape = new DrawEllipse(event.getX(), event.getY(), color);

						tempShape.setPosX(event.getX());
						tempShape.setPosY(event.getY());
						tempShape.createShape(event, anchorPane);

						inX = event.getX();
						inY = event.getY();
					}
					if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

						tempShape.editShape(event);
						tempShape.setPosX(event.getX());
						tempShape.setPosY(event.getY());

					}
					if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
						tempShape.addShape(tempShape, shapeArray);
						drawRegion.removeEventFilter(MouseEvent.ANY, this);
						tempShape.setPosX(inX);
						tempShape.setPosY(inY);
						setLabel(shapLab2, "");
					}
				}
			});

		});

		
		
		
		// rectangle setup
		Rectangle recBtn = new Rectangle(40, 300, 170, 75);
		recBtn.setFill(Color.GREY);

		recBtn.setOnMouseClicked(e -> {
			setLabel(shapLab2, "Rectangle");
			drawRegion.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {

						tempShape = new DrawRectangle(event.getX(), event.getY(), color);

						tempShape.setPosX(event.getX());
						tempShape.setPosY(event.getY());

						inX = event.getX();
						inY = event.getY();
						tempShape.createShape(event, anchorPane);

					}
					if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

						tempShape.editShape(event);
						tempShape.setPosX(event.getX());
						tempShape.setPosY(event.getY());

					}
					if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
						tempShape.addShape(tempShape, shapeArray);
						drawRegion.removeEventFilter(MouseEvent.ANY, this);
						tempShape.setPosX(inX);
						tempShape.setPosY(inY);
						setLabel(shapLab2, "");
					}

				}

			});

		});
		
		
		
		

		// circle setup
		Circle cirBtn = new Circle(180, 120, 40);
		cirBtn.setFill(Color.GREY);

		cirBtn.setOnMouseClicked(e -> {
			setLabel(shapLab2, "Circle");
			drawRegion.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
						tempShape = new DrawCircle(event.getX(), event.getY(), color);

						tempShape.setPosX(event.getX());
						tempShape.setPosY(event.getY());
						tempShape.createShape(event, anchorPane);

						inX = event.getX();
						inY = event.getY();
					}
					if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

						tempShape.editShape(event);
						tempShape.setPosX(event.getX());
						tempShape.setPosY(event.getY());

					}
					if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
						tempShape.addShape(tempShape, shapeArray);
						drawRegion.removeEventFilter(MouseEvent.ANY, this);
						setLabel(shapLab2, "");
						tempShape.setPosX(inX);
						tempShape.setPosY(inY);

					}

				}
			});

		});
		
		
		
		
		//add shapes buttons
		mainPane.getChildren().addAll(cirBtn, elpsBtn, recBtn, sqrBtn);

		
		// Filler (empty Label)
		Label filler1 = new Label("");
		filler1.setFont(new Font(250));
		vMenuPane.add(filler1, 0, 1);

		
		
		
		// Colors setup
		Label colors = new Label("Colors:");
		colors.setFont(mainFont);
		vMenuPane.add(colors, 0, 2);
		Button[][] colorArray = new Button[3][3];
		for (int i = 0; i < colorArray.length; i++) {
			for (int j = 0; j < colorArray.length; j++) {
				colorArray[i][j] = new Button("               ");
				colorArray[i][j].setShape(new Circle(1.5));
				vMenuPane.add(colorArray[i][j], j, i + 3);
			}
		}
		colorArray[0][0].setStyle("-fx-background-color: black");
		colorArray[0][0].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				color = "BLACK";
				setLabel(colorLab2, "BLACK");

				shapeArray.get(selected).setColor("BLACK");
			}
		});

		colorArray[0][1].setStyle("-fx-background-color: brown");
		colorArray[0][1].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				color = "BROWN";
				setLabel(colorLab2, "BROWN");

				shapeArray.get(selected).setColor("BROWN");
			}
		});

		colorArray[0][2].setStyle("-fx-background-color: red");
		colorArray[0][2].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				color = "RED";
				setLabel(colorLab2, "RED");

				shapeArray.get(selected).setColor("RED");
			}
		});

		colorArray[1][0].setStyle("-fx-background-color: blue");
		colorArray[1][0].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				color = "BLUE";
				setLabel(colorLab2, "BLUE");

				shapeArray.get(selected).setColor("BLUE");
			}
		});

		colorArray[1][1].setStyle("-fx-background-color: yellow");
		colorArray[1][1].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				color = "YELLOW";
				setLabel(colorLab2, "YELLOW");

				shapeArray.get(selected).setColor("YELLOW");
			}
		});

		colorArray[1][2].setStyle("-fx-background-color: orange");
		colorArray[1][2].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				color = "ORANGE";
				setLabel(colorLab2, "ORANGE");

				shapeArray.get(selected).setColor("ORANGE");
			}
		});
		colorArray[2][0].setStyle("-fx-background-color: green");
		colorArray[2][0].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				color = "GREEN";
				setLabel(colorLab2, "GREEN");

				shapeArray.get(selected).setColor("GREEN");
			}
		});

		colorArray[2][1].setStyle("-fx-background-color: grey");
		colorArray[2][1].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				color = "GREY";
				setLabel(colorLab2, "GREY");

				shapeArray.get(selected).setColor("GREY");
			}
		});
		colorArray[2][2].setStyle("-fx-background-color: white");
		colorArray[2][2].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				color = "WHITE";
				setLabel(colorLab2, "WHITE");

				shapeArray.get(selected).setColor("WHITE");
			}
		});

		
		
		
		
		// Filler (Empty Label)
		Label filler2 = new Label("                                        ");
		filler2.setFont(new Font(30));
		hMenuPane.add(filler2, 0, 0);

		
		
		// Filler (Empty Label)
		Label filler3 = new Label("");
		filler3.setFont(new Font(0.5));
		hMenuPane.add(filler3, 1, 4);

		
		
		// Shape Properties (Position & Delete-Shape Button)
		Label shapeprop = new Label("Shape Properties:");
		shapeprop.setFont(mainFont);
		hMenuPane.add(shapeprop, 1, 0);

		
		
		// Shape Properties (Width, Length and Radius(major/minor) )
		//length
		Label lengthLabel = new Label("              Length = ");
		lengthLabel.setFont(mainFont);
		GridPane.setHalignment(lengthLabel, HPos.RIGHT);
		TextField lengthIn = new TextField();
		hMenuPane.add(lengthIn, 4, 1);
		hMenuPane.add(lengthLabel, 3, 1);
		//width
		Label widthLabel = new Label("              Width = ");
		widthLabel.setFont(mainFont);
		GridPane.setHalignment(widthLabel, HPos.RIGHT);
		TextField widthIn = new TextField();
		hMenuPane.add(widthIn, 4, 2);
		hMenuPane.add(widthLabel, 3, 2);
		//radius
		Label radiusLabel = new Label("              Radius = ");
		radiusLabel.setFont(mainFont);
		GridPane.setHalignment(radiusLabel, HPos.RIGHT);
		TextField radiusIn = new TextField();
		hMenuPane.add(radiusIn, 4, 3);
		hMenuPane.add(radiusLabel, 3, 3);
		//major
		Label majorLabel = new Label("Major = ");
		majorLabel.setFont(mainFont);
		GridPane.setHalignment(majorLabel, HPos.RIGHT);
		TextField majorIn = new TextField();
		hMenuPane.add(majorIn, 6, 3);
		hMenuPane.add(majorLabel, 5, 3);
		//minor
		Label minorLabel = new Label("Minor = ");
		minorLabel.setFont(mainFont);
		GridPane.setHalignment(minorLabel, HPos.RIGHT);
		TextField minorIn = new TextField();
		hMenuPane.add(minorIn, 8, 3);
		hMenuPane.add(minorLabel, 7, 3);

		
		
		// Workspace Options (Save, Open and Delete)
		Label Workoptn = new Label("              Workspace Options:");
		Workoptn.setFont(mainFont);
		hMenuPane.add(Workoptn, 9, 0);

		widthIn.setDisable(true);
		lengthIn.setDisable(true);
		radiusIn.setDisable(true);
		majorIn.setDisable(true);
		minorIn.setDisable(true);

		//save button setup
		Button saveBtn = new Button("        Save...    ");
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				save(arg0);

			}
		});
		hMenuPane.add(saveBtn, 9, 1);
		GridPane.setHalignment(saveBtn, HPos.CENTER);

		
		//open button setup
		Button openBtn = new Button("       Open...    ");
		hMenuPane.add(openBtn, 9, 2);
		GridPane.setHalignment(openBtn, HPos.CENTER);
		openBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				open(arg0);
			}
		});

		mainPane.setStyle("-fx-background-color: #FFFFFF;");

		
		//submit button setup
		Button submit = new Button("      Submit Changes      ");
		hMenuPane.add(submit, 4, 0);
		GridPane.setHalignment(submit, HPos.CENTER);
		GridPane.setValignment(submit, VPos.BOTTOM);
		drawRegion.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			
			
			
			// is selected code
			@Override
			public void handle(MouseEvent event) {
				for (int i = 0; i < shapeArray.size(); i++) {
					if (shapeArray.get(i).isSelected(event.getX(), event.getY())) {

						selected = i;
					}
				}
				if (selected >= 0) {
					if (shapeArray.get(selected).getClass().toString().equals("class DrawRectangle")) {
						widthIn.setDisable(false);
						lengthIn.setDisable(false);
						radiusIn.setDisable(true);
						majorIn.setDisable(true);
						minorIn.setDisable(true);

					}

					else if (shapeArray.get(selected).getClass().toString().equals("class DrawSquare")) {
						widthIn.setDisable(false);
						lengthIn.setDisable(true);
						radiusIn.setDisable(true);
						majorIn.setDisable(true);
						minorIn.setDisable(true);
					}

					else if (shapeArray.get(selected).getClass().toString().equals("class DrawCircle")) {
						widthIn.setDisable(true);
						lengthIn.setDisable(true);
						radiusIn.setDisable(false);
						majorIn.setDisable(true);
						minorIn.setDisable(true);
					} 
					
					else if (shapeArray.get(selected).getClass().toString().equals("class DrawEllipse")) {
						widthIn.setDisable(true);
						lengthIn.setDisable(true);
						radiusIn.setDisable(true);
						majorIn.setDisable(false);
						minorIn.setDisable(false);
					}

				}
			}

		});
		
		
		//submit changes to the selected shape
		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (shapeArray.get(selected).getClass().toString().equals("class DrawRectangle"))
					shapeArray.get(selected).editShape(Double.parseDouble(widthIn.getText()),
							Double.parseDouble(lengthIn.getText()));
				else if (shapeArray.get(selected).getClass().toString().equals("class DrawSquare"))
					shapeArray.get(selected).editShape(Double.parseDouble(widthIn.getText()),
							Double.parseDouble(widthIn.getText()));
				else if (shapeArray.get(selected).getClass().toString().equals("class DrawCircle"))
					shapeArray.get(selected).editShape(Double.parseDouble(radiusIn.getText()),
							Double.parseDouble(radiusIn.getText()));
				else if (shapeArray.get(selected).getClass().toString().equals("class DrawEllipse"))
					shapeArray.get(selected).editShape(Double.parseDouble(majorIn.getText()),
							Double.parseDouble(minorIn.getText()));

			}
		});

		
		//delete shape setup
		Button delShape = new Button("Delete Shape");
		delShape.setStyle("-fx-text-fill: red");
		GridPane.setValignment(delShape, VPos.BOTTOM);
		GridPane.setHalignment(delShape, HPos.CENTER);
		hMenuPane.add(delShape, 2, 0);
		//delete selected shape
		delShape.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				shapeArray.get(selected).editShape(0, 0);
				shapeArray.remove(selected);
			}
		});

		
		//clear button setup
		Button clearBtn = new Button("      CLEAR      ");
		clearBtn.setStyle("-fx-text-fill: red");
		hMenuPane.add(clearBtn, 9, 3);
		GridPane.setHalignment(clearBtn, HPos.CENTER);
		//clear selected shape
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				for (int i = shapeArray.size() - 1; i >= 0; i--) {
					shapeArray.get(i).editShape(0, 0);
					shapeArray.remove(i);
				}
			}
		});
	}

	
	//shows the selected color and shape
	public void setLabel(Label a, String b) {
		a.setText(b);
	}
	
	
	//main method 
	public static void main(String[] args) {

		launch(args);
	}

}
