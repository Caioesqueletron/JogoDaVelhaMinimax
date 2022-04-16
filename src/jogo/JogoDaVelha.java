package jogo;

import ia.Minimax;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class JogoDaVelha extends Application

{

	private static GridPane gameBoard;
	private static Arvore tabuleiro;
	private AnimationTimer gameTimer;
	private MenuBar menuBar;
	private Menu gameMenu;
	private MenuItem newGameOption;
	private BorderPane root;
	private BorderPane root2;
	private int nível;

	private boolean vezIA;

	public final static class Tile extends Button {

		private final int row;
		private final int col;
		private String marcar;

		public Tile(int initRow, int initCol, String marcar) {
			row = initRow;
			col = initCol;
			this.marcar = marcar;
			initialiseTile();
		}

		private void initialiseTile() {
			this.setOnMouseClicked(e -> {
				if (!tabuleiro.isVezIA()) {
					tabuleiro.marcarCampoPraValer(this.row, this.col);
					System.out.println(tabuleiro.getNoMarcado(this.row, this.col));
					this.update();
				}
			});
			this.setStyle("-fx-font-size:20");
			this.setTextAlignment(TextAlignment.CENTER);
			this.setMinSize(100.0, 100.0);
			this.setText("" + this.marcar);
		}

		public void update() {
			this.marcar = tabuleiro.getNoMarcado(this.row, this.col);
			this.setText("" + marcar);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		root = new BorderPane();

		root.setCenter(generateGUI());
		root.setTop(initialiseMenu());
		Scene scene = new Scene(root);

		root2 = new BorderPane();

		root2.setCenter(menuconfig(primaryStage, scene));

		root.setCenter(generateGUI());
		root.setTop(initialiseMenu());
		Scene sceneDois = new Scene(root2, 450, 450);
		primaryStage.setTitle("Jogo da Velha");
		primaryStage.setScene(sceneDois);
		primaryStage.setResizable(false);


		primaryStage.show();
	}

	private static GridPane generateGUI() {
		gameBoard = new GridPane();
		tabuleiro = new Arvore(true);
		gameBoard.setAlignment(Pos.CENTER);

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Tile tile = new Tile(row, col, tabuleiro.getNoMarcado(row, col));
				GridPane.setConstraints(tile, col, row);
				gameBoard.getChildren().add(tile);
			}
		}
		return gameBoard;
	}

	private MenuBar initialiseMenu() {
		menuBar = new MenuBar();
		gameMenu = new Menu("game");
		newGameOption = new MenuItem("New Game");

		gameMenu.getItems().add(newGameOption);
		menuBar.getMenus().add(gameMenu);
		newGameOption.setOnAction(e -> {
			resetGame();
		});
		return menuBar;
	}

	
	private VBox menuconfig(Stage primaryStage, Scene scene) {
		VBox vBox = new VBox();
		vBox.prefHeight(400.0);
		vBox.prefWidth(640.0);
		vBox.setAlignment(Pos.CENTER);
		
		
		AnchorPane grid = new AnchorPane();
		grid.setMaxHeight(-1.0);
		grid.setMaxWidth(-1.0);
		grid.prefHeight(-1.0);
		grid.prefWidth(-1.0);

		/*
		Text scenetitle = new Text("Jogo da Velha - Menu");
		scenetitle.setFont(Font.font("Sans-Serif", FontWeight.NORMAL, 15));
		grid.getChildren().add(scenetitle);
		scenetitle.setLayoutX(800.0);
		scenetitle.setLayoutY(0.0);
		*/

		Label userName = new Label("Quem começa?");
		userName.setAlignment(Pos.CENTER);
		userName.setTextAlignment(TextAlignment.CENTER);
		userName.setTextFill(Paint.valueOf("#9f9f9f"));
		userName.setStyle("&#10; -fx-font-size:18");
		userName.setLayoutX(175.0);
		userName.setLayoutY(69.0);
		userName.prefHeight(27.0);
		userName.prefWidth(177.0);
		grid.getChildren().add(userName);



		RadioButton userRadioButton = new RadioButton("Usuário - O");
		grid.getChildren().add(userRadioButton);
		userRadioButton.setLayoutX(129.0);
		userRadioButton.setLayoutY(111.0);

		RadioButton iaRadioButton = new RadioButton("IA - X");
		grid.getChildren().add(iaRadioButton);
		iaRadioButton.setLayoutX(289.0);
		iaRadioButton.setLayoutY(111.0);

		iaRadioButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (userRadioButton.isSelected()) {
					userRadioButton.setSelected(false);
				}
			}
		});

		userRadioButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (iaRadioButton.isSelected()) {
					iaRadioButton.setSelected(false);
				}
			}
		});

		Label pw = new Label("Nível de dificuldade:");
		grid.getChildren().add(pw);
		pw.setLayoutX(175);
		pw.setLayoutY(159);

		ObservableList<String> options = FXCollections.observableArrayList("Nivel 1" , "Nivel 2", "Nivel 3", "Nivel 4",
				"Nivel 5", "Nivel 6", "Nivel 7", "Nivel 8", "Nivel 9");

		ComboBox<String> comboBox = new ComboBox<String>(options);
		grid.getChildren().add(comboBox);
		comboBox.setLayoutX(185);
		comboBox.setLayoutY(200);

		comboBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println(comboBox.getValue());
			}

		});

		Button btn = new Button("Começar Jogo");
		HBox hbBtn = new HBox();
		hbBtn.getChildren().add(btn);
		grid.getChildren().add(hbBtn);
		hbBtn.setLayoutX(182);
		hbBtn.setLayoutY(250);

		final Text actiontarget = new Text();
		grid.getChildren().add(actiontarget);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("Todos os campos precisam ser preenchidos");
				primaryStage.setScene(scene);

				primaryStage.show();
				runGameLoop();

			}
		});
		
		vBox.getChildren().add(grid);
		VBox.setVgrow(grid, Priority.ALWAYS);

		return vBox;
	}

	
	private void runGameLoop() {
		gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (tabuleiro.isFimDeJogo()) {
					System.out.println("passou por aqui1");
					endGame();
				} else {
					if (tabuleiro.isVezIA()) {			
						playAI();
					}
				}
			}
		};
		gameTimer.start();
	}

	private static void playAI() {
		int[] move = Minimax.melhorJogada(tabuleiro, 9);
		int row = move[0];
		int col = move[1];
		tabuleiro.marcarCampoPraValer(row, col);
		for (Node child : gameBoard.getChildren()) {
			if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
				Tile t = (Tile) child;
				t.update();
				return;
			}
		}
	}

	private void resetGame() {
		root.setCenter(generateGUI());
		runGameLoop();
	}

	private void endGame() {
		gameTimer.stop();
		Alert gameOverAlert = new Alert(AlertType.INFORMATION, "", new ButtonType("Novo Jogo"));
		String vencedor = tabuleiro.getGanhador();

		gameOverAlert.setTitle("Fim de Jogo");
		gameOverAlert.setHeaderText("Jogo da Velha");
		if (vencedor == "Empate") {
			gameOverAlert.setContentText("Empate!");
		} else {
			gameOverAlert.setContentText(vencedor + " venceu!");
		}
		gameOverAlert.setOnHidden(e -> {
			gameOverAlert.close();
			resetGame();
		});
		gameOverAlert.show();
	}

	public int getNível() {
		return nível;
	}

	public void setNível(int nível) {
		this.nível = nível;
	}

	public boolean isVezIA() {
		return vezIA;
	}

	public void setVezIA(boolean vezIA) {
		this.vezIA = vezIA;
	}
}
