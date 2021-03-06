package me.confuser.banmanager.runnables;

import java.util.HashMap;
import me.confuser.banmanager.PluginLogger;

public class Runner implements Runnable {

    private final HashMap<String, BmRunnable> runners;
    private final long lastExecuted = 0L;

    public Runner(BmRunnable... runners) {
        this.runners = new HashMap<>();

        for (int i = 0; i < runners.length; i++) {
            BmRunnable runner = runners[i];

            this.runners.put(runner.getName(), runner);
        }
    }

    @Override
    public void run() {
        for (BmRunnable runner : runners.values()) {
            if (!runner.shouldExecute()) {
                continue;
            }

            runner.beforeRun();

            // Ensure runner exceptions are caught to still allow others to execute
            try {
                runner.run();
            } catch (Exception e) {
                PluginLogger.warn(e);
            }

            runner.afterRun();
        }
    }

    public BmRunnable getRunner(String name) {
        return runners.get(name);
    }
}
