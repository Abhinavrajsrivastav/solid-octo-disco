package com.cloudl.cloudle.Service;

import com.cloudl.cloudle.Model.Meeting;
import com.cloudl.cloudle.Repo.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;


    // Create a new meeting
    public Meeting createMeeting(String name, String email) {
        Meeting meeting = new Meeting();
                meeting.setName(name);
                meeting.setEmail(email);
                meeting.setMeetingCode(generateMeetingCode());
                meeting.setOngoing(true);
                meeting.setTimestamp(LocalDateTime.now());
        
                // Save the meeting to the database
                meeting = meetingRepository.save(meeting);

        return meeting;
    }

    // Join an existing meeting
    public Optional<Meeting> joinMeeting(String meetingCode, String name, String email) {
        Optional<Meeting> existingMeeting = meetingRepository.findByMeetingCode(meetingCode);

        if (existingMeeting.isPresent() && existingMeeting.get().isOngoing()) {
            Meeting meeting = existingMeeting.get();
            meeting.setName(name);
            meeting.setEmail(email);

            // Save the updated meeting
            meeting = meetingRepository.save(meeting);
            return Optional.of(meeting);
        }
        return Optional.empty();
    }

    // Generate a unique meeting code
    private String generateMeetingCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
