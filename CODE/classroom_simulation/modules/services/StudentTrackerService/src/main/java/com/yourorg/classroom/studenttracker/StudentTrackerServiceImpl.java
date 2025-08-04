package com.yourorg.classroom.studenttracker;

import com.yourorg.classroom.proto.StudentTrackerServiceGrpc;
import com.yourorg.classroom.proto.StudentTrackerServiceOuterClass;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentTrackerServiceImpl extends StudentTrackerServiceGrpc.StudentTrackerServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(StudentTrackerServiceImpl.class);

    @Override
    public void trackStudentAttendance(StudentTrackerServiceOuterClass.TrackAttendanceRequest request,
                                       StreamObserver<StudentTrackerServiceOuterClass.TrackAttendanceResponse> responseObserver) {
        logger.info("Received attendance tracking request for student ID: {}", request.getStudentId());

        // Process the attendance tracking logic here

        StudentTrackerServiceOuterClass.TrackAttendanceResponse response = StudentTrackerServiceOuterClass.TrackAttendanceResponse.newBuilder()
                .setMessage("Attendance recorded successfully for student ID: " + request.getStudentId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateStudentGrade(StudentTrackerServiceOuterClass.UpdateGradeRequest request,
                                   StreamObserver<StudentTrackerServiceOuterClass.UpdateGradeResponse> responseObserver) {
        logger.info("Received grade update request for student ID: {}", request.getStudentId());

        // Process the grade update logic here

        StudentTrackerServiceOuterClass.UpdateGradeResponse response = StudentTrackerServiceOuterClass.UpdateGradeResponse.newBuilder()
                .setMessage("Grade updated successfully for student ID: " + request.getStudentId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentProfile(StudentTrackerServiceOuterClass.GetProfileRequest request,
                                  StreamObserver<StudentTrackerServiceOuterClass.GetProfileResponse> responseObserver) {
        logger.info("Received profile request for student ID: {}", request.getStudentId());

        // Retrieve the student profile logic here

        StudentTrackerServiceOuterClass.GetProfileResponse response = StudentTrackerServiceOuterClass.GetProfileResponse.newBuilder()
                .setStudentId(request.getStudentId())
                .setName("John Doe") // Replace with actual student name
                .setGrade("A") // Replace with actual student grade
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
