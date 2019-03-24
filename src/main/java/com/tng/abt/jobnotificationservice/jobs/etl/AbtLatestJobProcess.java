package com.tng.abt.jobnotificationservice.jobs.etl;

import com.tng.abt.jobnotificationservice.entities.AbtJob;
import com.tng.abt.jobnotificationservice.enums.ExitCode;
import com.tng.abt.jobnotificationservice.enums.JobName;
import com.tng.abt.jobnotificationservice.pojos.JobPojo;
import com.tng.abt.jobnotificationservice.repositories.AbtJobRepository;
import com.tng.abt.jobnotificationservice.services.InitialJob;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static com.tng.abt.jobnotificationservice.utils.Global.*;


@Slf4j
@Service
public class AbtLatestJobProcess {

    public JobProcessInterface makeJob() {
        return (JobProcessInterface) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{JobProcessInterface.class},
                new JobProcessInvocationHandler(new LatestJobImpl()));
    }

    private static class LatestJobImpl implements JobProcessInterface {

        @Qualifier("abtJdbcTemplate")
        @Autowired
        private JdbcTemplate abtJdbcTemplate;

        @Autowired
        private InitialJob initialJob;

        @Autowired
        private AbtJobRepository abtJobRepository;

        @Value("${dormant.latest.job.query}")
        private String dormantLatestJobQuery;

        @Value("${card-update.latest.job.query}")
        private String cardUpdateLatestJobQuery;

        @Value("${advance-settlement.latest.job.query}")
        private String advanceSettlementJobQuery;

        @Value("${gp-batch.latest.job.query}")
        private String gpBatchJobQuery;

        @Value("${report.latest.job.query}")
        private String reportJobQuery;

        private boolean enabled = false;

        @Override
        public boolean getEnabled() {
            return this.enabled;
        }

        @Override
        public void setEnabled(boolean enable) {
            this.enabled =enable;
        }

        @Override
        public void getDormantLatestJob() {

            String timestamp = initialJob.getFirstTimeDormantJob();
            String query = dormantLatestJobQuery.replace("@", timestamp);

            List<JobPojo> getDormantLatestJob = new ArrayList<>(abtJdbcTemplate.query(query, (resultSet, rowNum) -> JobPojo.builder()
                    .jobStart(resultSet.getTimestamp(JOB_START_DATETIME))
                    .jobEnd(resultSet.getTimestamp(JOB_END_DATETIME))
                    .isSuccessfulRun(resultSet.getBoolean(IS_SUCCESSFUL_RUN))
                    .build()));

            AbtJob abtJob = new AbtJob();
            for (JobPojo job : getDormantLatestJob) {
                abtJob.setJobName(JobName.DORMANT_EXCLUSION.name());
                abtJob.setAbtJobStartDatetime(job.getJobStart());
                abtJob.setAbtJobEndDatetime(job.getJobEnd());
                abtJob.setIsSuccessfulRun(job.getIsSuccessfulRun());

                if (job.getIsSuccessfulRun())
                    abtJob.setExitCode(ExitCode.COMPLETED.name());
                else
                    abtJob.setExitCode(ExitCode.FAILED.name());

                abtJob = abtJobRepository.save(abtJob);
            }

        }

        @Override
        public void getCardUpdateLatestJob() {

            String timestamp = initialJob.getFirsTimeCardUpdateJob();
            String query = cardUpdateLatestJobQuery.replace("@", timestamp);

            List<JobPojo> getCardUpdateLatestJob = new ArrayList<>(abtJdbcTemplate.query(query, (resultSet, rowNum) -> JobPojo.builder()
                    .jobStart(resultSet.getTimestamp(JOB_START_DATETIME))
                    .jobEnd(resultSet.getTimestamp(JOB_END_DATETIME))
                    .isSuccessfulRun(resultSet.getBoolean(IS_SUCCESSFUL_RUN))
                    .build()));

            AbtJob abtJob = new AbtJob();
            for (JobPojo job : getCardUpdateLatestJob) {
                abtJob.setJobName(JobName.CARD_UPDATE_STATUS.name());
                abtJob.setAbtJobStartDatetime(job.getJobStart());
                abtJob.setAbtJobEndDatetime(job.getJobEnd());
                abtJob.setIsSuccessfulRun(job.getIsSuccessfulRun());

                if (job.getIsSuccessfulRun())
                    abtJob.setExitCode(ExitCode.COMPLETED.name());
                else
                    abtJob.setExitCode(ExitCode.FAILED.name());

                abtJob = abtJobRepository.save(abtJob);
            }

        }

        @Override
        public void getAdvanceSettlementLatestJob() {

            String timestamp = initialJob.getFirstTimeAdvanceSettlementJob();
            String query = advanceSettlementJobQuery.replace("@", timestamp);

            List<JobPojo> getAdvanceSettlementLatestJob = new ArrayList<>(abtJdbcTemplate.query(query, (resultSet, rowNum) -> JobPojo.builder()
                    .jobStart(resultSet.getTimestamp(JOB_START_DATETIME))
                    .jobEnd(resultSet.getTimestamp(JOB_END_DATETIME))
                    .build()));

            AbtJob abtJob = new AbtJob();
            for (JobPojo job : getAdvanceSettlementLatestJob) {
                abtJob.setJobName(JobName.ADVANCE_SETTLEMENT.name());
                abtJob.setAbtJobStartDatetime(job.getJobStart());
                abtJob.setAbtJobEndDatetime(job.getJobEnd());
//                abtJob.setIsSuccessfulRun(job.getIsSuccessfulRun());
//
//                if (job.getIsSuccessfulRun())
//                    abtJob.setExitCode(ExitCode.COMPLETED.name());
//                else
//                    abtJob.setExitCode(ExitCode.FAILED.name());

                abtJob = abtJobRepository.save(abtJob);
            }

        }

        @Override
        public void getGpBatchLatestJob() {

            String timestamp = initialJob.getFirstTimeGpBatchJob();
            String query = gpBatchJobQuery.replace("@", timestamp);

            List<JobPojo> getGpBatchLatestJob = new ArrayList<>(abtJdbcTemplate.query(query, (resultSet, rowNum) -> JobPojo.builder()
                    .jobStart(resultSet.getTimestamp("START_TIME"))
                    .jobEnd(resultSet.getTimestamp("END_TIME"))
                    .exitCode(resultSet.getString("EXIT_CODE"))
                    .build()));

            AbtJob abtJob = new AbtJob();
            for (JobPojo job : getGpBatchLatestJob) {
                abtJob.setJobName(JobName.GP_BATCH.name());
                abtJob.setAbtJobStartDatetime(job.getJobStart());
                abtJob.setAbtJobEndDatetime(job.getJobEnd());
                abtJob.setExitCode(job.getExitCode());
                abtJob = abtJobRepository.save(abtJob);
            }

        }

        @Override
        public void getReportLatestJob() {

            String timestamp = initialJob.getReportJobFirstTimeJob();
            String query = reportJobQuery.replace("@", timestamp);

            List<JobPojo> getReportLatestJob = new ArrayList<>(abtJdbcTemplate.query(query, (resultSet, rowNum) -> JobPojo.builder()
                    .jobStart(resultSet.getTimestamp("START_TIME"))
                    .jobEnd(resultSet.getTimestamp("END_TIME"))
                    .exitCode(resultSet.getString("EXIT_CODE"))
                    .build()));

            AbtJob abtJob = new AbtJob();
            for (JobPojo job : getReportLatestJob) {
                abtJob.setJobName(JobName.REPORT_JOB.name());
                abtJob.setAbtJobStartDatetime(job.getJobStart());
                abtJob.setAbtJobEndDatetime(job.getJobEnd());
                abtJob.setExitCode(job.getExitCode());
                abtJob = abtJobRepository.save(abtJob);
            }

        }

    }

    @AllArgsConstructor
    private static class JobProcessInvocationHandler implements InvocationHandler {

        @Autowired
        private LatestJobImpl latestJob;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getDeclaringClass() == JobProcessInterface.class &&
                    !method.getName().equals("getEnabled") &&
                    !method.getName().equals("setEnabled") && (!this.latestJob.getEnabled())){
                return null;

            }

            return method.invoke(this.latestJob, args);
        }
    }
}
