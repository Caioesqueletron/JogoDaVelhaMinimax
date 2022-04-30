package jogo;

public class Arvore {
	private boolean fimDeJogo, vezIA;
	private String[][] no;
	String ganhador,simboloJogador,simboloIA;
	public Arvore(boolean vezIA, String simboloJogador, String simboloIA) {
		this.no = new String[3][3];
		this.fimDeJogo = false;
		this.vezIA = vezIA;
		this.simboloIA = simboloIA;
		this.simboloJogador = simboloJogador;
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

	public boolean marcarCampoPraValer(int i, int j, String simbolo) {
		if (i < 0 || i >= 3 || j < 0 || j >= 3 || estaMarcado(i, j) || fimDeJogo) {
			return false;
		}
		no[i][j] = simbolo;
		trocarVez();
		checarVitoria();
		return true;
	}

	public boolean estaTudoPreenchido() {
		int blocos = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.no[i][j] == this.simboloIA || no[i][j] == this.simboloJogador) {
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
				if (aux[i][j] == this.simboloIA) {
					somatorio++;
				} else if (aux[i][j] == this.simboloJogador) {
					somatorio--;
				}
			}
			if (somatorio == 3) {
				this.setGanhador(this.simboloIA);
				fimDeJogo = true;
				return;

			} else if (somatorio == -3) {
				this.setGanhador(this.simboloJogador);
				fimDeJogo = true;
				return;
			}

			somatorio = 0;
		}

		somatorio = 0;

		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				if (aux[i][j] == this.simboloIA) {
					somatorio++;
				} else if (aux[i][j] == this.simboloJogador) {
					somatorio--;
				}
			}
			if (somatorio == 3) {
				this.setGanhador(this.simboloIA);
				fimDeJogo = true;
				return;
			} else if (somatorio == -3) {
				this.setGanhador(this.simboloJogador);
				fimDeJogo = true;
				return;
			}

			somatorio = 0;
		}

		somatorio = 0;
		for (int i = 0; i < 3; i++) {
			if (aux[i][i] == this.simboloIA) {
				somatorio++;
			} else if (aux[i][i] == this.simboloJogador) {
				somatorio--;
			}
			if (somatorio == 3) {
				this.setGanhador(this.simboloIA);
				fimDeJogo = true;
				return;
			} else if (somatorio == -3) {
				this.setGanhador(this.simboloJogador);
				fimDeJogo = true;
				return;
			}
		}

		somatorio = 0;

		int index = 2;
		for (int i = 0; i < 3; i++) {
			if (aux[i][index] == this.simboloIA) {
				somatorio++;
			} else if (aux[i][index] ==this.simboloJogador) {
				somatorio--;
			}
			if (somatorio == 3) {
				this.setGanhador(this.simboloIA);
				fimDeJogo = true;
				return;
			} else if (somatorio == -3) {
				this.setGanhador(this.simboloJogador);
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
