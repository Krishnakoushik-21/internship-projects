package com.hospital.interfaces;

/**
 * Interface demonstrating INTERFACES.
 * Any schedulable entity (appointment) must implement this.
 */
public interface Schedulable {
    void schedule();
    void cancel();
    boolean isScheduled();
}
