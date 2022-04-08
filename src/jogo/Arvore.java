package jogo;

public class Arvore {
	boolean fimDeJogo, vez;
	private String[][] no;
	
	public Arvore() {
		this.no = new String[3][3];
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
		// TODO Auto-generated method stub
		return false;
	}
}
