package com.tng.abt.jobnotificationservice.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialJobPojo {

    private String jobName;
    private String jobStartTime;

}
