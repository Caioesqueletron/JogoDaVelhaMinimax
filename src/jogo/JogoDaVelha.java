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
	private MenuBar barra;
	private Menu menuJogo;
	private MenuItem novoJogo;
	private BorderPane root;
	private BorderPane root2;
	private int nivel;
	private boolean vezIA;
	private boolean quemComeca;

	public final static class Blocos extends Button {

		private final int row;
		private final int col;
		private String marcar;

		public Blocos(int linhaInicial, int colunaInicial, String marcar) {
			row = linhaInicial;
			col = colunaInicial;
			this.marcar = marcar;
			inicializarBlocos();
		}

		private void inicializarBlocos() {
			this.setOnMouseClicked(e -> {
				if (!tabuleiro.isVezIA()) {
					tabuleiro.marcarCampoPraValer(this.row, this.col);
					this.atualiza();
				}
			});
			this.setStyle("-fx-font-size:20");
			this.setTextAlignment(TextAlignment.CENTER);
			this.setMinSize(100.0, 100.0);
			this.setText("" + this.marcar);
		}

		public void atualiza() {
			this.marcar = tabuleiro.getNoMarcado(this.row, this.col);
			this.setText("" + marcar);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		root2 = new BorderPane();
		root2.setCenter(menuconfig(primaryStage));

		Scene sceneDois = new Scene(root2, 450, 450);
		primaryStage.setTitle("Jogo da Velha");
		primaryStage.setScene(sceneDois);
		primaryStage.setResizable(false);

		primaryStage.show();
	}

	private GridPane gerarInterface() {
		gameBoard = new GridPane();
		tabuleiro = new Arvore(isQuemComeca());
		gameBoard.setAlignment(Pos.CENTER);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Blocos tile = new Blocos(i, j, tabuleiro.getNoMarcado(i, j));
				GridPane.setConstraints(tile, j, i);
				gameBoard.getChildren().add(tile);
			}
		}
		return gameBoard;
	}

	private MenuBar menuInicial() {
		barra = new MenuBar();
		menuJogo = new Menu("Jogo");
		novoJogo = new MenuItem("Novo Jogo");

		menuJogo.getItems().add(novoJogo);
		barra.getMenus().add(menuJogo);
		novoJogo.setOnAction(e -> {
			resetarJogo();
		});
		return barra;
	}

	private VBox menuconfig(Stage primaryStage) {
		Text actiontarget = new Text();
		VBox vBox = new VBox();
		vBox.prefHeight(400.0);
		vBox.prefWidth(640.0);
		vBox.setAlignment(Pos.CENTER);

		AnchorPane grid = new AnchorPane();
		grid.setMaxHeight(-1.0);
		grid.setMaxWidth(-1.0);
		grid.prefHeight(-1.0);
		grid.prefWidth(-1.0);


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
				setQuemComeca(true);
				if (userRadioButton.isSelected()) {
					userRadioButton.setSelected(false);
				}
			}
		});

		userRadioButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setQuemComeca(false);
				if (iaRadioButton.isSelected()) {
					iaRadioButton.setSelected(false);
				}
			}
		});

		Label pw = new Label("Nível de dificuldade:");
		grid.getChildren().add(pw);
		pw.setLayoutX(175);
		pw.setLayoutY(159);

		ObservableList<String> options = FXCollections.observableArrayList("1", "2", "3", "4",
				"5", "6", "7", "8", "9");

		ComboBox<String> comboBox = new ComboBox<String>(options);
		grid.getChildren().add(comboBox);
		comboBox.setLayoutX(185);
		comboBox.setLayoutY(200);

		comboBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setNivel(Integer.parseInt(comboBox.getValue()));
			}

		});

		Button btn = new Button("Começar Jogo");
		HBox hbBtn = new HBox();
		hbBtn.getChildren().add(btn);
		grid.getChildren().add(hbBtn);
		hbBtn.setLayoutX(182);
		hbBtn.setLayoutY(250);
		grid.getChildren().add(actiontarget);
		actiontarget.setLayoutX(110);
		actiontarget.setLayoutY(300);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (comboBox.getValue() == null || (!iaRadioButton.isSelected() && !userRadioButton.isSelected())) {
					actiontarget.setFill(Color.RED);
					actiontarget.setText("Todos os campos precisam ser preenchidos");
				} else {
					root = new BorderPane();
					root.setCenter(gerarInterface());
					root.setTop(menuInicial());
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
					runGameLoop();
				}

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
					fimDeJogo();
				} else {
					if (tabuleiro.isVezIA()) {
						lanceIA();
					}
				}
			}
		};
		gameTimer.start();
	}

	private void lanceIA() {
		int[] movimento = Minimax.melhorJogada(tabuleiro, getNivel());
		int linha = movimento[0];
		int coluna = movimento[1];
		tabuleiro.marcarCampoPraValer(linha, coluna);
		for (Node child : gameBoard.getChildren()) {
			if (GridPane.getRowIndex(child) == linha && GridPane.getColumnIndex(child) == coluna) {
				Blocos t = (Blocos) child;
				t.atualiza();
				return;
			}
		}
	}

	private void resetarJogo() {
		root.setCenter(gerarInterface());
		runGameLoop();
	}

	private void fimDeJogo() {
		gameTimer.stop();
		Alert alertaFimDeJogo = new Alert(AlertType.INFORMATION, "", new ButtonType("Novo Jogo"));
		String vencedor = tabuleiro.getGanhador();

		alertaFimDeJogo.setTitle("Fim de Jogo");
		alertaFimDeJogo.setHeaderText("Jogo da Velha");
		if (vencedor == "Empate") {
			alertaFimDeJogo.setContentText("Empate!");
		} else {
			alertaFimDeJogo.setContentText(vencedor + " venceu!");
		}
		alertaFimDeJogo.setOnHidden(e -> {
			alertaFimDeJogo.close();
			resetarJogo();
		});
		alertaFimDeJogo.show();
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public boolean isVezIA() {
		return vezIA;
	}

	public void setVezIA(boolean vezIA) {
		this.vezIA = vezIA;
	}

	public boolean isQuemComeca() {
		return quemComeca;
	}

	public void setQuemComeca(boolean quemComeca) {
		this.quemComeca = quemComeca;
	}
}
