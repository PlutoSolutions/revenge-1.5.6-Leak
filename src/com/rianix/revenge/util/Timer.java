/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.util;

public class Timer {
    private long current = -1L;
    private long time = -1L;

    public boolean passedS(double s) {
        return this.getMs(System.nanoTime() - this.time) >= (long)(s * 1000.0);
    }

    public boolean passedM(double m) {
        return this.getMs(System.nanoTime() - this.time) >= (long)(m * 1000.0 * 60.0);
    }

    public boolean passedDms(double dms) {
        return this.getMs(System.nanoTime() - this.time) >= (long)(dms * 10.0);
    }

    public boolean passedDs(double ds) {
        return this.getMs(System.nanoTime() - this.time) >= (long)(ds * 100.0);
    }

    public boolean passedMs(long ms) {
        return this.getMs(System.nanoTime() - this.time) >= ms;
    }

    public boolean passedNS(long ns) {
        return System.nanoTime() - this.time >= ns;
    }

    public void setMs(long ms) {
        this.time = System.nanoTime() - ms * 1000000L;
    }

    public long getPassedTimeMs() {
        return this.getMs(System.nanoTime() - this.time);
    }

    public boolean passed(long delay) {
        return System.currentTimeMillis() - this.current >= delay;
    }

    public Timer reset() {
        this.time = System.nanoTime();
        return this;
    }

    public long getMs(long time) {
        return time / 1000000L;
    }

    public boolean hasReached(long delay) {
        return System.currentTimeMillis() - this.current >= delay;
    }

    public boolean hasReached(long delay, boolean reset) {
        if (reset) {
            this.reset();
        }
        return System.currentTimeMillis() - this.current >= delay;
    }

    public boolean sleep(long time) {
        if (System.nanoTime() / 1000000L - time >= time) {
            this.reset();
            return true;
        }
        return false;
    }

    public final boolean hasReachedRealth(long delay) {
        return System.currentTimeMillis() - this.current >= delay;
    }

    public boolean hasReachedRealth(long delay, boolean reset) {
        if (reset) {
            this.reset();
        }
        return System.currentTimeMillis() - this.current >= delay;
    }

    public final void resetRealth() {
        this.current = System.currentTimeMillis();
    }

    public void resetTimeSkipTo(long p_MS) {
        this.time = System.currentTimeMillis() + p_MS;
    }
}

