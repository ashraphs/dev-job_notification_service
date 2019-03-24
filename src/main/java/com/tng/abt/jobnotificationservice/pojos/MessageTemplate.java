package com.tng.abt.jobnotificationservice.pojos;

import com.tng.abt.jobnotificationservice.enums.JobName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageTemplate {

    public String BodyTemplate(Body body){

        String name1 = "Job Name: "+body.getDormantJobMessage().getJobName();
        String jobStart1 = "Start: "+body.getDormantJobMessage().getJobStartTime();
        String jobEnd1 = "End: "+body.getDormantJobMessage().getJobEndTime();
        String status1 = "Status: "+body.getDormantJobMessage().getExitCode();
        String lineBreak1 = "---------------------------";
        String dormantJob = name1 +"\n"+jobStart1+"\n"+jobEnd1+"\n"+status1+"\n"+lineBreak1;

        String name2 = "Job Name: "+body.getCardUpdateJobMessage().getJobName();
        String jobStart2 = "Start: "+body.getCardUpdateJobMessage().getJobEndTime();
        String jobEnd2 = "End: "+body.getCardUpdateJobMessage().getJobEndTime();
        String status2 = "Status: "+body.getCardUpdateJobMessage().getExitCode();
        String lineBreak2 = "---------------------------";
        String cardUpdateJob = name2 +"\n"+jobStart2+"\n"+jobEnd2+"\n"+status2+"\n"+lineBreak2;

        String name3 = "Job Name: "+body.getAdvanceSettlementJobMessage().getJobName();
        String jobStart3 = "Start: "+body.getAdvanceSettlementJobMessage().getJobEndTime();
        String jobEnd3 = "End: "+body.getAdvanceSettlementJobMessage().getJobEndTime();
        String status3 = "Status: "+body.getAdvanceSettlementJobMessage().getExitCode();
        String lineBreak3 = "---------------------------";
        String advanceSettlementJob = name3 +"\n"+jobStart3+"\n"+jobEnd3+"\n"+status3+"\n"+lineBreak3;

        return dormantJob + cardUpdateJob + advanceSettlementJob;
    }

    @Data
    public static class Body{

        private DormantJobMessage dormantJobMessage;
        private CardUpdateJobMessage cardUpdateJobMessage;
        private AdvanceSettlementJobMessage advanceSettlementJobMessage;
    }

    @Data
    public static class DormantJobMessage{
        private String jobName = JobName.DORMANT_EXCLUSION.name();
        private String jobStartTime;
        private String jobEndTime;
        private String exitCode;
    }

    @Data
    public static class CardUpdateJobMessage{
        private String jobName = JobName.CARD_UPDATE_STATUS.name();
        private String jobStartTime;
        private String jobEndTime;
        private String exitCode;
    }

    @Data
    public static class AdvanceSettlementJobMessage{
        private String jobName = JobName.ADVANCE_SETTLEMENT.name();
        private String jobStartTime;
        private String jobEndTime;
        private String exitCode;
    }
}


