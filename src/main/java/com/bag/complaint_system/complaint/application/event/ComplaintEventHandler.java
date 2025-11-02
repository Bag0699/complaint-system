package com.bag.complaint_system.complaint.application.event;

import com.bag.complaint_system.complaint.domain.event.ComplaintStatusChangedEvent;
import com.bag.complaint_system.shared.infrastructure.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ComplaintEventHandler {

//    private final EmailService emailService;
//
//    @Async
//    @EventListener
//    public void handleComplaintStatusChanged(ComplaintStatusChangedEvent event) {
//        log.info("Handling complaint status changed event for complaint ID: {}", event.getComplaintId());
//
//        try {
//            String subject = "Complaint Status Update - Case #" + event.getComplaintId();
//
//            String body = buildEmailBody(event);
//
//            emailService.sendEmail(
//                    event.getVictimEmail(),
//                    subject,
//                    body
//            );
//
//            log.info("Email notification sent to: {}", event.getVictimEmail());
//
//        } catch (Exception ex) {
//            log.error("Failed to send email notification for complaint {}: {}",
//                    event.getComplaintId(), ex.getMessage());
//        }
//    }
//
//    private String buildEmailBody(ComplaintStatusChangedEvent event) {
//        return String.format("""
//                Dear User,
//
//                Your complaint (Case #%d) status has been updated.
//
//                Previous Status: %s
//                New Status: %s
//                Updated On: %s
//
//                %s
//
//                If you have any questions, please contact our support team.
//
//                Best regards,
//                Complaints System Team
//                """,
//                event.getComplaintId(),
//                event.getPreviousStatus().getDisplayName(),
//                event.getNewStatus().getDisplayName(),
//                event.getChangedAt(),
//                getStatusMessage(event.getNewStatus().name())
//        );
//    }
//
//    private String getStatusMessage(String status) {
//        return switch (status) {
//            case "RECEIVED" -> "Your complaint has been received and will be reviewed shortly.";
//            case "IN_REVIEW" -> "Your complaint is currently being reviewed by our team.";
//            case "ACTION_TAKEN" -> "Action has been taken on your complaint. You may be contacted for further information.";
//            case "CLOSED" -> "Your complaint has been closed. Thank you for using our service.";
//            default -> "";
//        };
//    }

}
