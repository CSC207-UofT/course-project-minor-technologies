package com.minortechnologies.workr_backend.controllers.backgroundoperations;

import com.minortechnologies.workr_backend.framework.networkhandler.Application;

public class BackgroundSerialization implements IBackgroundOperation{
    @Override
    public void run() {
        while (BackgroundOperations.isRunBackgroundOps()){
            try {
                serialize();
                Thread.sleep(BackgroundOperations.getUpdateInterval());
            } catch (InterruptedException e) {
                serialize();
            }
        }
    }

    private void serialize(){
        Long start = System.nanoTime();
        System.out.println("Beginning Serialization");
        Application.getUserManagement().saveUsers();
        Application.getLocalCache().saveAllListings();
        Application.getAuthTokenController().saveTokens();
        Long end = System.nanoTime();
        Long period = end - start;
        double durationSec = (end - start) * (Math.pow(10, -9));
        System.out.println("Serialization completed in " + durationSec + "seconds");
    }
}
