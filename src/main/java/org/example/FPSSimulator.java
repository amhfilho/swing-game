package org.example;

public class FPSSimulator implements Runnable {

	// =========================================================================
	// ALTERE AQUI PARA SIMULAR: 15, 60 ou 120 (ou qualquer outro valor de FPS)
	// =========================================================================
	private static final int TARGET_FPS = 60;

	// Tempo que CADA FRAME DEVE DURAR (em milissegundos)
	// Ex: 1000ms / 15 FPS = ~66.6ms por frame
	// Ex: 1000ms / 60 FPS = ~16.6ms por frame
	private static final long FRAME_TIME_MS = 1000 / TARGET_FPS;

	private boolean running = true;
	private float positionX = 0.0f;
	private final float speedX = 100.0f; // A velocidade física é FIXA: 100px por segundo

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		int totalFrames = 0;
		long startTime = System.currentTimeMillis();

		System.out.println("=== SIMULANDO JOGO A " + TARGET_FPS + " FPS ===");

		while (running) {
			long frameStart = System.currentTimeMillis();

			// 1. CÁLCULO DO DELTA TIME (Tempo REAL desde o último tick em segundos)
			long currentTime = System.nanoTime();
			float deltaTime = (currentTime - lastTime) / 1_000_000_000.0f;
			lastTime = currentTime;

			// 2. UPDATE (Usa o deltaTime para manter a física constante)
			float stepDistance = speedX * deltaTime;
			positionX += stepDistance;
			totalFrames++;

			// 3. RENDER / LOG (Exibe o salto exato em pixels que o objeto deu)
			System.out.printf("Frame %03d | Delta: %.4fs | Salto no Frame: %5.2fpx | Posição Total: %6.2fpx\n",
					totalFrames, deltaTime, stepDistance, positionX);

			// Para a simulação ao atingir 300px (equivale a 3 segundos de movimento)
			if (positionX >= 300.0f) {
				running = false;
			}

			// 4. FORÇA A TAXA DE FPS DESEJADA
			// Calcula quanto tempo sobrou no frame atual e dorme exatamente esse tempo
			long timeSpentThisFrame = System.currentTimeMillis() - frameStart;
			long sleepMillis = FRAME_TIME_MS - timeSpentThisFrame;

			if (sleepMillis > 0) {
				try {
					Thread.sleep(sleepMillis);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}

		long totalTimeSpent = System.currentTimeMillis() - startTime;
		System.out.printf("\nFIM: Percorreu 300px em %.2f segundos executando %d frames.\n",
				totalTimeSpent / 1000.0f, totalFrames);
	}

	public static void main(String[] args) {
		new Thread(new FPSSimulator()).start();
	}
}