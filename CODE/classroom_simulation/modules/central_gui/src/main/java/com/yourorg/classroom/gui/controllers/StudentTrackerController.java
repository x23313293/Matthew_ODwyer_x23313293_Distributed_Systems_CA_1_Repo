package com.yourorg.classroom.gui;

import com.yourorg.classroom.discovery.ServiceRegistry;
import com.yourorg.classroom.studenttracker.StudentTrackerServiceGrpc;
import com.yourorg.classroom.studenttracker.StudentTrackerOuter.EnrollmentReportRequest;
import com.yourorg.classroom.studenttracker.StudentTrackerOuter.EnrollmentReport;
import com.yourorg.classroom.studenttracker.StudentTrackerOuter.EnrollmentEntry;
import com.yourorg.classroom.studenttracker.StudentTrackerOuter.MarkAttendanceRequest;
import com.yourorg.classroom.studenttracker.StudentTrackerOuter.TrackEngagementRequest;
import com.yourorg.classroom.studenttracker.StudentTrackerOuter.RegisterStudentRequest;
import com.yourorg.classroom.studenttracker.StudentTrackerOuter.StatusResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class StudentTrackerController {

    private static final Logger logger = Logger.getLogger(StudentTrackerController.class.getName());
    private StudentTrackerServiceGrpc.StudentTrackerServiceBlockingStub stub;

    @FXML private TextField studentIdField;
    @FXML private TextField fullNameField;
    @FXML private TextField attendanceDateField;
    @FXML private Button registerBtn;
    @FXML private Button markAttendanceBtn;
    @FXML private Button trackEngagementBtn;
    @FXML private TableView<EngagementEntry> reportTable;
    @FXML private TableColumn<EngagementEntry, String> dateCol;
    @FXML private TableColumn<EngagementEntry, Double> scoreCol;
    @FXML private TableColumn<EngagementEntry, String> commentsCol;

    private final ObservableList<EngagementEntry> reportData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dateCol.setCellValueFactory(cd -> new ReadOnlyStringWrapper(
                Instant.ofEpochSecond(cd.getValue().getTimestamp()).atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_LOCAL_DATE)));
        scoreCol.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().getParticipationScore()));
        commentsCol.setCellValueFactory(cd -> new ReadOnlyStringWrapper(cd.getValue().getComments()));
        reportTable.setItems(reportData);

        String address = ServiceRegistry.discover("StudentTrackerService");
        if (address == null) {
            showAlert(Alert.AlertType.ERROR, "Discovery Failed", "StudentTrackerService not found");
            Platform.exit();
            return;
        }
        ManagedChannel channel = ManagedChannelBuilder.forTarget(address)
            .usePlaintext()
            .build();
        stub = StudentTrackerServiceGrpc.newBlockingStub(channel);

        registerBtn.setOnAction(evt -> registerStudent());
        markAttendanceBtn.setOnAction(evt -> markAttendance());
        trackEngagementBtn.setOnAction(evt -> fetchEngagementReport());
    }

    private void registerStudent() {
        String sid = studentIdField.getText().trim();
        String name = fullNameField.getText().trim();
        if (sid.isEmpty() || name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Enter both Student ID and Name");
            return;
        }
        RegisterStudentRequest req = RegisterStudentRequest.newBuilder()
            .setStudentId(sid)
            .setFullName(name)
            .build();
        try {
            StatusResponse resp = stub.registerStudent(req);
            showAlert(Alert.AlertType.INFORMATION, "Registration", resp.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "RPC Error", e.getMessage());
        }
    }

    private void markAttendance() {
        String sid = studentIdField.getText().trim();
        String dateIso = attendanceDateField.getText().trim();
        long epoch;
        try {
            epoch = Instant.parse(dateIso + "T00:00:00Z").getEpochSecond();
        } catch (DateTimeParseException ex) {
            showAlert(Alert.AlertType.WARNING, "Invalid Date", "Use ISO format YYYY-MM-DD");
            return;
        }
        MarkAttendanceRequest req = MarkAttendanceRequest.newBuilder()
            .setStudentId(sid)
            .setPresent(true)
            .setTimestamp(epoch)
            .build();
        try {
            StatusResponse resp = stub.markAttendance(req);
            showAlert(Alert.AlertType.INFORMATION, "Attendance", resp.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "RPC Error", e.getMessage());
        }
    }

    private void fetchEngagementReport() {
        String sid = studentIdField.getText().trim();
        EnrollmentReportRequest req = EnrollmentReportRequest.newBuilder()
            .setStudentId(sid)
            .build();
        try {
            EnrollmentReport report = stub.getEngagementReport(req);
            reportData.setAll(report.getEntriesList());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "RPC Error", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type, msg, ButtonType.OK);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }
}
