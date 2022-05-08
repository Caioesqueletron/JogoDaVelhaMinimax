package minimax;

import jogoDaVelha.Arvore;

public class Minimax {

	public static int[] melhorJogada(Arvore no, int profundidade, String simboloJogador, String simboloIA) {
		int[] melhorJogada = new int[2];
		melhorJogada[0] = -1;
		melhorJogada[1] = -1;
		int melhorPontuacao = Integer.MIN_VALUE;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!no.estaMarcado(i, j)) {
					no.estipularJogada(i, j, simboloIA);
					int maiorPontuacao = minimax(no, false, profundidade - 1, simboloJogador, simboloIA);
					no.estipularJogada(i, j, "");

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

	public static int minimax(Arvore no, boolean isMax, int profundidade, String simboloJogador, String simboloIA) {
		int pontuacao = avaliarEstado(no, simboloJogador, simboloIA);

		if (Math.abs(pontuacao) == 1 || no.estaTudoPreenchido() || profundidade == -1) {
			return pontuacao;
		}
		if (isMax) {
			return max(no, profundidade, simboloIA, simboloJogador);

		} else {
			return min(no, profundidade, simboloJogador, simboloIA);

		}

	}
	
	public static int min(Arvore no, int profundidade, String simboloJogador, String simboloIA) {
		int menorPontuacao = Integer.MAX_VALUE;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!no.estaMarcado(i, j)) {
					no.estipularJogada(i, j, simboloJogador);
					menorPontuacao = Math.min(menorPontuacao, minimax(no, true, profundidade - 1, simboloJogador, simboloIA));
					no.estipularJogada(i, j, "");

				}
			}
		}
		return menorPontuacao;

	}
	
	public static int max(Arvore no, int profundidade, String simboloIA, String simboloJogador) {
		int maiorPontuacao = Integer.MIN_VALUE;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!no.estaMarcado(i, j)) {
					no.estipularJogada(i, j, simboloIA);
					maiorPontuacao = Math.max(maiorPontuacao, minimax(no, false, profundidade - 1, simboloJogador, simboloIA));
					no.estipularJogada(i, j, "");
				}
			}
		}

		return maiorPontuacao;
	}

	public static int avaliarEstado(Arvore arvore, String simboloJogador, String simboloIA) {
		int somatorio = 0;
		String[][] aux = arvore.getNo();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (aux[i][j] == simboloIA) {
					somatorio++;
				}else if (aux[i][j] == simboloJogador) {
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
				if (aux[i][j] == simboloIA) {
					somatorio++;
				} else if (aux[i][j] == simboloJogador) {
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
			if (aux[i][i] == simboloIA) {
				somatorio++;
			} else if (aux[i][i] == simboloJogador) {
				somatorio--;
			}
			

		}
		if (somatorio == 3) {
			return 1;
		} else if (somatorio == -3) {
			return -1;
		}

		somatorio = 0;

		int index = 2;
		for (int i = 0; i < 3; i++) {
			if (aux[i][index] == simboloIA) {
				somatorio++;
			} else if (aux[i][index] == simboloJogador) {
				somatorio--;
			}
			
			index--;
		}
		if (somatorio == 3) {
			return 1;
		} else if (somatorio == -3) {
			return -1;
		}

		return 0;
	}

}
