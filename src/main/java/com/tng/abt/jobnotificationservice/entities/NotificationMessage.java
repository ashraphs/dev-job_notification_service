package com.tng.abt.jobnotificationservice.entities;

import com.tng.abt.jobnotificationservice.enums.NotificationStatus;
import com.touchngo.abt.utils.entities.MasterEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "jn_notification_message")
public class NotificationMessage extends MasterEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

}
