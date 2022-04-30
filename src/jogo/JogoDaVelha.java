package jogo;

import ia.Minimax;
import ia.MinimaxHeuristicaDiferente;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class JogoDaVelha extends Application

{

	private static GridPane planoFundo;
	private static Arvore tabuleiro;
	private AnimationTimer gameTimer;
	private MenuBar barra;
	private Menu menuJogo;
	private MenuItem novoJogo;
	private BorderPane plano;
	private BorderPane plano2;
	private int nivel;
	private boolean vezIA;
	private boolean quemComeca;
	private int tipoHeuristica;
	private  String simboloJogador;
	private String simbolobIA;

	public final static class Blocos extends Button {

		private final int i;
		private final int j;
		private String marcar;
		private String simboloJogador;

		public Blocos(int linhaInicial, int colunaInicial, String marcar, String simboloJogador) {
			i = linhaInicial;
			j = colunaInicial;
			this.marcar = marcar;
			this.simboloJogador = simboloJogador;
			inicializarBlocos();
		}

		private void inicializarBlocos() {
			this.setOnMouseClicked(e -> {
				if (!tabuleiro.isVezIA()) {
					tabuleiro.marcarCampoPraValer(this.i, this.j, this.simboloJogador);
					this.atualiza();
				}
			});
			this.setTextAlignment(TextAlignment.CENTER);
			this.setMinSize(100.0, 100.0);
			this.setStyle("-fx-border-color:black;-fx-background-color:white");
			this.setText("" + this.marcar);
		}

		public void atualiza() {
			
			this.marcar = tabuleiro.getNoMarcado(this.i, this.j);
			if(this.marcar == "X") {
				this.setStyle("-fx-font-size:30;-fx-color:blue");
				this.setText("" + this.marcar);
			}else if(this.marcar == "O") {
				this.setStyle("-fx-font-size:30;-fx-color:red");
				this.setText("" + this.marcar);
			}
			this.setText("" + marcar);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		plano2 = new BorderPane();
		plano2.setCenter(menuconfig(primaryStage));

		Scene sceneDois = new Scene(plano2, 450, 450);
		primaryStage.setTitle("Jogo da Velha");
		primaryStage.setScene(sceneDois);
		primaryStage.setResizable(false);

		primaryStage.show();
	}

	private GridPane gerarInterface() {
		planoFundo = new GridPane();
		tabuleiro = new Arvore(isQuemComeca(), getSimboloJogador(), getSimbolobIA());
		planoFundo.setAlignment(Pos.CENTER);
		
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Blocos tile = new Blocos(i, j, tabuleiro.getNoMarcado(i, j), getSimboloJogador());
				GridPane.setConstraints(tile, j, i);
				planoFundo.getChildren().add(tile);
			}
		}
		return planoFundo;
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
		userName.setLayoutX(175.0);
		userName.setLayoutY(69.0);
		userName.prefHeight(27.0);
		userName.prefWidth(177.0);
		grid.getChildren().add(userName);

		RadioButton userRadioButton = new RadioButton("Usuário");
		grid.getChildren().add(userRadioButton);
		userRadioButton.setLayoutX(129.0);
		userRadioButton.setLayoutY(111.0);

		RadioButton iaRadioButton = new RadioButton("IA");
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
		
		RadioButton heuristicaButton = new RadioButton("Heuristica");
		grid.getChildren().add(heuristicaButton);
		heuristicaButton.setLayoutX(129.0);
		heuristicaButton.setLayoutY(190.0);

		RadioButton heuristicaMelhoradaButton = new RadioButton("Heuristica Melhorada");
		grid.getChildren().add(heuristicaMelhoradaButton);
		heuristicaMelhoradaButton.setLayoutX(289.0);
		heuristicaMelhoradaButton.setLayoutY(190.0);
		
		heuristicaButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setTipoHeuristica(0);
				if (heuristicaMelhoradaButton.isSelected()) {
					heuristicaMelhoradaButton.setSelected(false);
				}
			}
		});
		
		heuristicaMelhoradaButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setTipoHeuristica(1);
				if (heuristicaButton.isSelected()) {
					heuristicaButton.setSelected(false);
				}
			}
		});
		
		
		RadioButton bolinha = new RadioButton("O");
		grid.getChildren().add(bolinha);
		bolinha.setLayoutX(129.0);
		bolinha.setLayoutY(150.0);

		RadioButton xis = new RadioButton("X");
		grid.getChildren().add(xis);
		xis.setLayoutX(289.0);
		xis.setLayoutY(150);
		xis.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setSimbolobIA("O");
				setSimboloJogador("X");
				if (bolinha.isSelected()) {
					bolinha.setSelected(false);
				}
			}
		});
		bolinha.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setSimbolobIA("X");
				setSimboloJogador("O");
				if (xis.isSelected()) {
					xis.setSelected(false);
				}
			}
		});

		Label pw = new Label("Nível de dificuldade:");
		grid.getChildren().add(pw);
		pw.setLayoutX(175);
		pw.setLayoutY(230);

		ObservableList<String> options = FXCollections.observableArrayList("1", "2", "3", "4",
				"5", "6", "7", "8", "9");

		ComboBox<String> comboBox = new ComboBox<String>(options);
		grid.getChildren().add(comboBox);
		comboBox.setLayoutX(185);
		comboBox.setLayoutY(250);

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
		hbBtn.setLayoutY(300);
		grid.getChildren().add(actiontarget);
		actiontarget.setLayoutX(110);
		actiontarget.setLayoutY(400);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (comboBox.getValue() == null || 
						(!iaRadioButton.isSelected() && !userRadioButton.isSelected())||
						(!heuristicaButton.isSelected() && !heuristicaMelhoradaButton.isSelected())||
						(!xis.isSelected() && !bolinha.isSelected())) {
					actiontarget.setFill(Color.RED);
					actiontarget.setText("Todos os campos precisam ser preenchidos");
				} else {
					plano = new BorderPane();
					plano.setCenter(gerarInterface());
					plano.setTop(menuInicial());
					Scene scene = new Scene(plano);
					primaryStage.setScene(scene);
					primaryStage.show();
					rodando();
				}

			}
		});

		vBox.getChildren().add(grid);
		VBox.setVgrow(grid, Priority.ALWAYS);

		return vBox;
	}
	 public static long bytesToMegabytes(long bytes) {
		  final long MEGABYTE = 1024L * 1024L;

		    return bytes / MEGABYTE;
		  }

	private void rodando() {
		Runtime runtime = Runtime.getRuntime();
	    runtime.gc();
	    
		gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (tabuleiro.isFimDeJogo()) {
					long memory = runtime.totalMemory() - runtime.freeMemory();
				    System.out.println("Used memory is bytes: " + memory);
				    System.out.println("Used memory is megabytes: "
				        + bytesToMegabytes(memory));
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
		int[] movimento = new int[2];
		if(isTipoHeuristica() == 0) {
			 movimento = Minimax.melhorJogada(tabuleiro, getNivel(), getSimboloJogador(), getSimbolobIA());
		}else if(isTipoHeuristica() == 1) {
			 movimento = MinimaxHeuristicaDiferente.melhorJogada(tabuleiro, getNivel(), getSimboloJogador(), getSimbolobIA());

		}
		int linha = movimento[0];
		int coluna = movimento[1];
		tabuleiro.marcarCampoPraValer(linha, coluna, getSimbolobIA());
		for (Node child : planoFundo.getChildren()) {
			if (GridPane.getRowIndex(child) == linha && GridPane.getColumnIndex(child) == coluna) {
				Blocos t = (Blocos) child;
				t.atualiza();
			}
		}
	}

	private void resetarJogo() {
		plano.setCenter(gerarInterface());
		rodando();
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

	public int isTipoHeuristica() {
		return tipoHeuristica;
	}

	public void setTipoHeuristica(int tipoHeuristica) {
		this.tipoHeuristica = tipoHeuristica;
	}

	public  String getSimboloJogador() {
		return simboloJogador;
	}

	public void setSimboloJogador(String simboloJogador) {
		this.simboloJogador = simboloJogador;
	}

	public String getSimbolobIA() {
		return simbolobIA;
	}

	public void setSimbolobIA(String simbolobIA) {
		this.simbolobIA = simbolobIA;
	}
}
