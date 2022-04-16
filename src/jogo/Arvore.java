package jogo;

public class Arvore {
	private boolean fimDeJogo, vezIA;
	private String[][] no;
	String ganhador;
	public Arvore(boolean vezIA) {
		this.no = new String[3][3];
		this.fimDeJogo = false;
		this.vezIA = vezIA;
		this.ganhador = "";
		inicializar();
	}

	public void inicializar() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				no[i][j] = " ";
			}
		}
	}

	public boolean estaMarcado(int i, int j) {
		if (this.no[i][j] == "X" || this.no[i][j] == "O") {
			return true;
		}

		return false;
	}

	public boolean marcarCampoPraValer(int i, int j) {
		if (i < 0 || i >= 3 || j < 0 || j >= 3 || estaMarcado(i, j) || fimDeJogo) {
			return false;
		}
		no[i][j] = this.isVezIA() ? "X" : "O";
		trocarVez();
		checarVitoria();
		return true;
	}

	public boolean estaTudoPreenchido() {
		int blocos = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.no[i][j] == "X" || no[i][j] == "O") {
					blocos++;
				}
			}
		}
		return blocos == 9 ? true : false;
	}

	public boolean podeMarcar(int i, int j) {
		if (this.no[i][j] == " ") {
			return true;
		}
		return false;
	}

	public boolean trocarVez() {
		return vezIA = !vezIA;
	}

	public void checarVitoria() {
		int somatorio = 0;
		String[][] aux = getNo();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (aux[i][j] == "X") {
					somatorio++;
				} else if (aux[i][j] == "O") {
					somatorio--;
				}
			}
			if (somatorio == 3) {
				this.setGanhador("X");
				fimDeJogo = true;
				return;

			} else if (somatorio == -3) {
				this.setGanhador("O");
				fimDeJogo = true;
				return;
			}

			somatorio = 0;
		}

		somatorio = 0;

		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				if (aux[i][j] == "X") {
					somatorio++;
				} else if (aux[i][j] == "O") {
					somatorio--;
				}
			}
			if (somatorio == 3) {
				this.setGanhador("X");
				fimDeJogo = true;
				return;
			} else if (somatorio == -3) {
				this.setGanhador("O");
				fimDeJogo = true;
				return;
			}

			somatorio = 0;
		}

		somatorio = 0;
		for (int i = 0; i < 3; i++) {
			if (aux[i][i] == "X") {
				somatorio++;
			} else if (aux[i][i] == "O") {
				somatorio--;
			}
			if (somatorio == 3) {
				this.setGanhador("X");
				fimDeJogo = true;
				System.out.println("Venceu");
				return;
			} else if (somatorio == -3) {
				this.setGanhador("O");
				System.out.println("Venceu");
				fimDeJogo = true;
				return;
			}
		}

		somatorio = 0;

		int index = 2;
		for (int i = 0; i < 3; i++) {
			if (aux[i][index] == "X") {
				somatorio++;
			} else if (aux[i][index] == "O") {
				somatorio--;
			}
			if (somatorio == 3) {
				this.setGanhador("X");
				System.out.println("Venceu");
				fimDeJogo = true;
				return;
			} else if (somatorio == -3) {
				this.setGanhador("O");
				System.out.println("Venceu");
				fimDeJogo = true;
				return;
			}

			index--;
		}
		
		if(this.estaTudoPreenchido()) {
			this.setGanhador("Empate");
			fimDeJogo = true;
			return;
		}
		
	}

	public String[][] getNo() {
		return no;
	}
	
	public String getNoMarcado(int i, int j) {
		return this.no[i][j];
	}

	public void setNo(String[][] no) {
		this.no = no;
	}
	
	public void marcarCampo(int i, int j, String c) {
		this.no[i][j] = c;
	}
	
	public boolean isVezIA() {
		return vezIA;
	}

	public void setVezIA(boolean vezIA) {
		this.vezIA = vezIA;
	}
	

	public boolean isFimDeJogo() {
		return fimDeJogo;
	}

	public void setFimDeJogo(boolean fimDeJogo) {
		this.fimDeJogo = fimDeJogo;
	}
	
	public String getGanhador() {
		return ganhador;
	}

	public void setGanhador(String ganhador) {
		this.ganhador = ganhador;
	}
}
