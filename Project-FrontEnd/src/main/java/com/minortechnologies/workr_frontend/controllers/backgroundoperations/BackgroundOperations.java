package com.minortechnologies.workr_frontend.controllers.backgroundoperations;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;

public class BackgroundOperations {

    public static final ArrayList<Thread> threads = new ArrayList<>();

    private static int updateInterval = 5000;

    public static boolean isRunBackgroundOps() {
        return runBackgroundOps;
    }

    private static boolean runBackgroundOps = true;

    public static int getUpdateInterval() {
        return updateInterval;
    }

    /**
     * Sets the interval at which background operations occur in milliseconds
     * @param updateInterval the interval in milliseconds
     */
    public static void setUpdateInterval(int updateInterval) {
        BackgroundOperations.updateInterval = updateInterval;
    }


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

        BackgroundUpdateListings bul = new BackgroundUpdateListings();

        threads.add(new Thread(bul));

        for (Thread thread:
             threads) {
            thread.start();
        }
    }

    public static void endBackgroundThreads(){
        runBackgroundOps = false;

        while (threads.size() > 0) {
            Thread thread = threads.get(threads.size() - 1);
            threads.remove(threads.size() - 1);

            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
    }
}
