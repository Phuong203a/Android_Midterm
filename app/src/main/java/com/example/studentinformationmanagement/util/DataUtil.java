package com.example.studentinformationmanagement.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.widget.ImageView;

import com.example.studentinformationmanagement.EditProfileActivity;
import com.example.studentinformationmanagement.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {
    public static String DATE_FORMAT_ddMMyyyy = "dd/MM/yyyy";
    public static String DATE_FORMAT_ddMMyyyyHHmmss = "dd/MM/yyyy HH:mm:ss";

    public static int calculateAge(String birthDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_ddMMyyyy);
            LocalDate localBirthDate = simpleDateFormat.parse(birthDate).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if ((birthDate != null)) {
                int age = Period.between(localBirthDate, LocalDate.now()).getYears();
                return age;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    public static void setAvatar(String url, ImageView imageView, int defaultId){
        try{
            if(url == null || url.equals("")){
                imageView.setImageResource(defaultId);
                return;
            }
            Uri uri = Uri.parse(url);
            Picasso.get()
                    .load(uri)
                    .fit()
                    .into(imageView);
        }catch (Exception ex){
            imageView.setImageResource(defaultId);
        }
    }
    public static void setDate(Context context, DatePickerDialog.OnDateSetListener datePickerListener) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        DatePickerDialog dialog = new DatePickerDialog(
                context,
                R.style.Theme_StudentInformationManagement,
                datePickerListener,
                year, month, day);
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public static String parseDateToString(Date date,String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(date);

        } catch (Exception ex) {

        }
        return null;
    }
}
