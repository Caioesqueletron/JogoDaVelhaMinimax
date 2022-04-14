package jogo;

public class Arvore {
	boolean fimDeJogo, vezIA;
	private String[][] no;
	String ganhador;
	

	public Arvore(boolean vezIA) {
		this.no = new String[3][3];
		this.fimDeJogo = false;
		this.vezIA = vezIA;
		this.ganhador = " ";
		
		inicializar();
	}
	
	public void inicializar() {
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3;j++) {
				no[i][j] = " ";
			}
		}
	}
	
	public boolean estaMarcado(int i, int j) {
		if(this.no[i][j] == "X"|| this.no[i][j] == "O") {
			return true;
		}
		
		return false;
	}

	public void marcarCampo(int i, int j, String c) {
		this.no[i][j] = c;
	}

	public boolean estaTudoPreenchido() {
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				if(no[i][j] == " ") {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean podeMarcar(int i, int j) {
		if(this.no[i][j] == " ") {
			return true;
		}
		return false;
	}
	
	public boolean trocarVez() {
		return vezIA =! vezIA;
	}
	
	public void checarVitoria() {
		
	}
	
	public String[][] getNo() {
		return no;
	}

	public void setNo(String[][] no) {
		this.no = no;
	}
}
