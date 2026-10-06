package org.example;

public class SimpleGameLoop implements Runnable {

	private boolean running = true;
	private float positionX = 0.0f;
	private final float speedX = 100.0f; // Move 100 pixels por segundo

	@Override
	public void run() {
		long lastTime = System.nanoTime();

		while (running) {
			// 1. Calcula o tempo decorrido desde o último tick
			long currentTime = System.nanoTime();
			// Converter nanosegundos para segundos (1s = 1.000.000.000ns)
			float deltaTime = (currentTime - lastTime) / 1_000_000_000.0f;
			lastTime = currentTime;

			// 2. Update: atualiza o estado usando o deltaTime
			positionX += speedX * deltaTime;

			// 3. Render / Log simples para visualização
			System.out.printf("DeltaTime: %.4fs | Posição X: %.2fpx\n", deltaTime, positionX);

			// Parar a simulação ao atingir 500px
			if (positionX >= 500.0f) {
				running = false;
			}

			// 4. Pausa de ~16ms para simular ~60 FPS e aliviar a CPU
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public static void main(String[] args) {
		// Roda o game loop em uma Thread dedicada
		Thread gameThread = new Thread(new SimpleGameLoop());
		gameThread.start();
	}
}
