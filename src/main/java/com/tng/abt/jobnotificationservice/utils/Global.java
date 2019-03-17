package com.tng.abt.jobnotificationservice.utils;

public class Global {
    public static final String JOB_START_DATETIME = "job_start_datetime";
    public static final String JOB_END_DATETIME = "job_end_datetime";
    public static final String IS_SUCCESSFUL_RUN = "is_successful_run";
    public static final String ERROR_MESSAGE = "error_message";

    private Global() {
        throw new IllegalStateException("Wot m8? It is a utility class!");
    }
}
