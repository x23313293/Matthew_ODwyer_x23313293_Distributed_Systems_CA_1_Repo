package com.yourorg.classroom.studenttracker;

import com.yourorg.classroom.proto.StudentTrackerServiceOuterClass;
import com.yourorg.classroom.studenttracker.StudentTrackerServiceImpl;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class StudentTrackerServiceImplTest {

    private StudentTrackerServiceImpl service;
    private StreamObserver<StudentTrackerServiceOuterClass.TrackAttendanceResponse> trackAttendanceObserver;
    private StreamObserver<StudentTrackerServiceOuterClass.UpdateGradeResponse> updateGradeObserver;
    private StreamObserver<StudentTrackerServiceOuterClass.GetProfileResponse> getProfileObserver;

    @BeforeEach
    void setUp() {
        service = new StudentTrackerServiceImpl();
        trackAttendanceObserver = mock(StreamObserver.class);
        updateGradeObserver = mock(StreamObserver.class);
        getProfileObserver = mock(StreamObserver.class);
    }

    @Test
    void testTrackStudentAttendance() {
        StudentTrackerServiceOuterClass.TrackAttendanceRequest request = StudentTrackerServiceOuterClass.TrackAttendanceRequest.newBuilder()
                .setStudentId("12345")
                .setDate("2025-08-04")
                .setStatus("Present")
                .build();

        service.trackStudentAttendance(request, trackAttendanceObserver);

        verify(trackAttendanceObserver, times(1)).onNext(any());
        verify(trackAttendanceObserver, times(1)).onCompleted();
    }

    @Test
    void testUpdateStudentGrade() {
        StudentTrackerServiceOuterClass.UpdateGradeRequest request = StudentTrackerServiceOuterClass.UpdateGradeRequest.newBuilder()
                .setStudentId("12345")
                .setCourseId("CS101")
                .setGrade("A")
                .build();

        service.updateStudentGrade(request, updateGradeObserver);

        verify(updateGradeObserver, times(1)).onNext(any());
        verify(updateGradeObserver, times(1)).onCompleted();
    }

    @Test
    void testGetStudentProfile() {
        StudentTrackerServiceOuterClass.GetProfileRequest request = StudentTrackerServiceOuterClass.GetProfileRequest.newBuilder()
                .setStudentId("12345")
                .build();

        service.getStudentProfile(request, getProfileObserver);

        verify(getProfileObserver, times(1)).onNext(any());
        verify(getProfileObserver, times(1)).onCompleted();
    }
}
