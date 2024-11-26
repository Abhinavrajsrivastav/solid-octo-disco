package com.cloudl.cloudle.Controller;

import com.cloudl.cloudle.Model.Meeting;
import com.cloudl.cloudle.Service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:3000")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping("/start-meeting")
    public ResponseEntity<Map<String, Object>> createMeeting(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");

        Meeting meeting = meetingService.createMeeting(name, email);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Meeting Created Successfully");
        response.put("meetingCode", meeting.getMeetingCode());
        response.put("ongoing", meeting.isOngoing());
        response.put("timestamp", meeting.getTimestamp());
        System.out.println("Succeccfully created meeting with code: " + meeting.getMeetingCode());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> joinMeeting(@RequestBody Map<String, String> request) {
        String meetingCode = request.get("meetingCode");
        String name = request.get("name");
        String email = request.get("email");

        if (meetingCode == null || name == null || email == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "All fields are required"));
        }

        Optional<Meeting> meeting = meetingService.joinMeeting(meetingCode, name, email);
        if (meeting.isPresent()) {
            return ResponseEntity.ok(Map.of(
                    "message", "Successfully Joined the Meeting",
                    "meetingCode", meeting.get().getMeetingCode()
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Invalid Meeting Code or Meeting not Found"));
        }
    }
}
