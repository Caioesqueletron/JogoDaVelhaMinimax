package ia;

import jogo.Arvore;

public class Minimax{

	public int minimax(Arvore no, boolean isMax, int profundidade) {
		int pontuacao = avaliarEstado();

		if (Math.abs(pontuacao) == 1 || no.estaTudoPreenchido() || profundidade == 0) {
			return pontuacao;
		}
		if (isMax) {
			int maiorPontuacao = Integer.MIN_VALUE;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (no.estaMarcado(i, j)) {
						no.marcarCampo(i, j, "X");
						maiorPontuacao = Math.max(maiorPontuacao, minimax(no, false, profundidade - 1));
						no.marcarCampo(i, j, "");
					}
				}
			}

			return maiorPontuacao;

		} else {
			int menorPontuacao = Integer.MAX_VALUE;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (no.estaMarcado(i, j)) {
						no.marcarCampo(i, j, "O");
						menorPontuacao = Math.min(menorPontuacao, minimax(no, true, profundidade - 1));
						no.marcarCampo(i, j, "");

					}
				}
			}
			return menorPontuacao;

		}

	}

	public int avaliarEstado() {
		return 0;
	}

}
