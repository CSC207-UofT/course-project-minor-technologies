package com.minortechnologies.workr_backend.controllers.backgroundoperations;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;

public class BackgroundOperations {

    public static ArrayList<Thread> threads = new ArrayList<>();

    private static int updateInterval = 5000;

    public static boolean isRunBackgroundOps() {
        return runBackgroundOps;
    }

    private static boolean runBackgroundOps = true;

    public static int getUpdateInterval() {
        return updateInterval;
    }

    public static void setUpdateInterval(int updateInterval) {
        BackgroundOperations.updateInterval = updateInterval;
    }

    private static ScheduledExecutorService ses; // will test how ScheduledExecutorService works in the future.

    /**
     * A loop that runs the background operations for this program such as automatic refreshing/updating
     * of the listings that a user watches.
     *
     * Ends existing threads if there are any threads running.
     */
    public static void startBackgroundLoop(){

        if (threads.size() > 0){
            endBackgroundThreads();
        }

        BackgroundSerialization bs = new BackgroundSerialization();

        threads.add(new Thread(bs));

        for (Thread thread:
             threads) {
            thread.start();
        }
        runBackgroundOps = true;
    }

    public static void endBackgroundThreads(){
        runBackgroundOps = false;

        while (threads.size() > 0) {
            Thread thread = threads.get(threads.size() - 1);
            threads.remove(threads.size() - 1);

            try {
                thread.interrupt();
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
    }
}
