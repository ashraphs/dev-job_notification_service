package com.tng.abt.jobnotificationservice.utils;

public class Global {
    public static final String DING_TALK_TOKEN = "DING_TALK_WEBHOOK_TOKEN";
    public static final String JOB_START_DATETIME = "job_start_datetime";
    public static final String JOB_END_DATETIME = "job_end_datetime";
    public static final String BATCH_JOB_START = "START_TIME";
    public static final String BATCH_JOB_END = "END_TIME";

    private Global() {
        throw new IllegalStateException("Wot m8? It is a utility class!");
    }
}
