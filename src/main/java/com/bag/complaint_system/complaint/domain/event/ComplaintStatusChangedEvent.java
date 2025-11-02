package com.bag.complaint_system.complaint.domain.event;

import com.bag.complaint_system.complaint.domain.model.valueObject.ComplaintStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ComplaintStatusChangedEvent {

    private Long complaintId;
    private Long victimId;
    private String victimEmail;
    private ComplaintStatus previousStatus;
    private ComplaintStatus newStatus;
    private LocalDateTime changedAt;

//    public static ComplaintStatusChangedEvent create(
//            Long complaintId,
//            Long victimId,
//            String victimEmail,
//            ComplaintStatus previousStatus,
//            ComplaintStatus newStatus
//    ) {
//        return new ComplaintStatusChangedEvent(
//                complaintId,
//                victimId,
//                victimEmail,
//                previousStatus,
//                newStatus,
//                LocalDateTime.now()
//        );
//    }
}
