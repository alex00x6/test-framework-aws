package mechanics.load;

/**
 * Created by user on 04.04.2017.
 */
public class ThreadLaunchDelayer {
    private static int countOfInvocations = 0;

    public static void delay(int delayForEachThreadMs) {
        countOfInvocations++;
        if (delayForEachThreadMs != 0) {
            int resultSleepTime = countOfInvocations * delayForEachThreadMs;
            try {
                Thread.sleep(resultSleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
