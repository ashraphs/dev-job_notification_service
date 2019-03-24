package com.tng.abt.jobnotificationservice.jobs.etl;

public interface JobProcessInterface {

    boolean getEnabled();

    void setEnabled(boolean enable);

    void getDormantLatestJob();

    void getCardUpdateLatestJob();

    void getAdvanceSettlementLatestJob();

    void getGpBatchLatestJob();

    void getReportLatestJob();

    static JobProcessInterface jobProcess(){
        AbtLatestJobProcess latestJobProcess = new AbtLatestJobProcess();
        return latestJobProcess.makeJob();
    }
}
