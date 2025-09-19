public class Timer implements Runnable{
    private Thread timerThread;
    private volatile boolean isTimeUp = false; // 공유 플래그
    static final long timeoutMilis = 20000; // 20초

    @Override
    public void run() {
        try {
            // 20초 대기
            Thread.sleep(timeoutMilis);
            isTimeUp = true;
            System.out.println("\n손님이 떠났습니다...");
            System.out.println("[Enter]를 눌러 게임 종료");
        } catch (InterruptedException e) {
            return; // 타임아웃 전에 성공 시 타이머 종료
        }
    }

    // Runnable을 Thread로 전달, 실행
    public void start() {
        timerThread = new Thread(this);
        timerThread.start();
    }

    public void stop() {
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.interrupt();
        }
    }

    public void checkTimeout() {
        if (isTimeUp) throw new IllegalStateException("[Game over]");
    }
}
