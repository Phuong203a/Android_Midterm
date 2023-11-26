package com.example.studentinformationmanagement.dao;

import android.os.Environment;
import android.util.Log;

import com.example.studentinformationmanagement.model.Certificate;
import com.example.studentinformationmanagement.model.Student;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class DataExportImportDAO {
    public boolean exportCSVCertificate(List<Certificate> certificates, String fileName) {
        File externalDownloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(externalDownloadsDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);

            for (Certificate certificate : certificates) {
                writer.write(certificate.getName());
                writer.write(",");
                writer.write(certificate.getStudentCode());
                writer.newLine();
            }

            writer.close();

            Log.d("CSVExporter", "CSV file exported to: " + file.getAbsolutePath());
            return true;
        } catch (Exception e) {
            Log.e("CSVExporter", "Error writing to CSV file", e);
        }
        return false;
    }

    public boolean exportCSVStudent(List<Student> studentList, String fileName) {
        File externalDownloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(externalDownloadsDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);

            for (Student student : studentList) {
                writer.write(student.getCode());
                writer.write(",");
                writer.write(student.getName());
                writer.newLine();
            }

            writer.close();

            Log.d("CSVExporter", "CSV file exported to: " + file.getAbsolutePath());
            return true;
        } catch (Exception e) {
            Log.e("CSVExporter", "Error writing to CSV file", e);
        }
        return false;
    }
}
