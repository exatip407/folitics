package com.ohmuk.folitics.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Set;

import com.ohmuk.folitics.enums.QualificationType;
import com.ohmuk.folitics.hibernate.entity.UserEducation;

public class AggregationUtils {

    public static final int getAgeForDOB(Timestamp dob) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dob.getTime());

        LocalDate today = LocalDate.now();

        LocalDate birthday = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH));
        Period period = Period.between(birthday, today);

        return period.getYears();
    }

    public static final String getHighestEducation(Set<UserEducation> userEducations) {

        String highestEducation = "";

        for (UserEducation userEducation : userEducations) {

            if (userEducation.getCourseName().equals(QualificationType.DOCTORATE.getValue())) {

                highestEducation = QualificationType.DOCTORATE.getValue();
                break;

            } else if (userEducation.getCourseName().equals(QualificationType.POST_GRADUATE.getValue())) {

                if (!highestEducation.equals(QualificationType.DOCTORATE.getValue())) {

                    highestEducation = QualificationType.POST_GRADUATE.getValue();
                }
            } else if (userEducation.getCourseName().equals(QualificationType.GRADUATE.getValue())) {

                if (!highestEducation.equals(QualificationType.DOCTORATE.getValue())
                        && !highestEducation.equals(QualificationType.POST_GRADUATE.getValue())) {

                    highestEducation = QualificationType.GRADUATE.getValue();
                }
            } else if (userEducation.getCourseName().equals(QualificationType.HIGHER_SECONDARY.getValue())) {

                if (!highestEducation.equals(QualificationType.DOCTORATE.getValue())
                        && !highestEducation.equals(QualificationType.POST_GRADUATE.getValue())
                        && !highestEducation.equals(QualificationType.GRADUATE.getValue())) {

                    highestEducation = QualificationType.GRADUATE.getValue();
                }
            } else if (userEducation.getCourseName().equals(QualificationType.MATRICULATION.getValue())) {

                if (!highestEducation.equals(QualificationType.DOCTORATE.getValue())
                        && !highestEducation.equals(QualificationType.POST_GRADUATE.getValue())
                        && !highestEducation.equals(QualificationType.GRADUATE.getValue())
                        && !highestEducation.equals(QualificationType.HIGHER_SECONDARY.getValue())) {

                    highestEducation = QualificationType.MATRICULATION.getValue();
                }
            } else if (userEducation.getCourseName().equals(QualificationType.ILLITERATE.getValue())) {

                if (!highestEducation.equals(QualificationType.DOCTORATE.getValue())
                        && !highestEducation.equals(QualificationType.POST_GRADUATE.getValue())
                        && !highestEducation.equals(QualificationType.GRADUATE.getValue())
                        && !highestEducation.equals(QualificationType.HIGHER_SECONDARY.getValue())
                        && !highestEducation.equals(QualificationType.MATRICULATION.getValue())) {

                    highestEducation = QualificationType.ILLITERATE.getValue();
                }
            }
        }

        return QualificationType.getQualificationType(highestEducation).getValue();
    }
}
