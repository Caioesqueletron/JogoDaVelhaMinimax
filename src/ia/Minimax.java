package ia;

import jogo.Arvore;

public class Minimax {

	public int[] melhorJogada(Arvore no, int profundidade) {
		int[] melhorJogada = { -1, 1 };
		int melhorPontuacao = Integer.MAX_VALUE;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (no.estaMarcado(i, j)) {
					no.marcarCampo(i, j, "X");
					int maiorPontuacao = minimax(no, false, profundidade - 1);
					no.marcarCampo(i, j, "");

					if (maiorPontuacao > melhorPontuacao) {
						melhorPontuacao = maiorPontuacao;
						melhorJogada[0] = i;
						melhorJogada[1] = j;
					}
				}
			}

		}
		return melhorJogada;
	}

	public int minimax(Arvore no, boolean isMax, int profundidade) {
		int pontuacao = avaliarEstado(no);

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

	public int avaliarEstado(Arvore arvore) {
		int somatorio = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String[][] aux = arvore.getNo();
				if (aux[i][j] == "X") {
					somatorio++;
				} else if (aux[i][j] == "O") {
					somatorio--;
				}
			}
			if (somatorio == 3) {
				return 1;
			} else if (somatorio == -3) {
				return -1;
			}

			somatorio = 0;
		}

		somatorio = 0;

		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				String[][] aux = arvore.getNo();
				if (aux[i][j] == "X") {
					somatorio++;
				} else if (aux[i][j] == "O") {
					somatorio--;
				}
			}
			if (somatorio == 3) {
				return 1;
			} else if (somatorio == -3) {
				return -1;
			}

			somatorio = 0;
		}

		somatorio = 0;
		for (int i = 0; i < 3; i++) {
			String[][] aux = arvore.getNo();
			if (aux[i][i] == "X") {
				somatorio++;
			} else if (aux[i][i] == "O") {
				somatorio--;
			}
			if (somatorio == 3) {
				return 1;
			} else if (somatorio == -3) {
				return -1;
			}

			somatorio = 0;
		}

		somatorio = 0;

		int index = 2;
		for (int i = 0; i < 3; i++) {
			String[][] aux = arvore.getNo();
			if (aux[i][index] == "X") {
				somatorio++;
			} else if (aux[i][index] == "O") {
				somatorio--;
			}
			if (somatorio == 3) {
				return 1;
			} else if (somatorio == -3) {
				return -1;
			}

			index--;
			somatorio = 0;
		}

		return 0;
	}

}
