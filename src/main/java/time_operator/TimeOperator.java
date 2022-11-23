package time_operator;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.Arrays;

public class TimeOperator {

    /**
     * 명세에서 제공하는 시간 형태를 일반적인 형태로 바꿔줌
     *
     * @param time 바꾸고자 하는 String 을 입력받음
     * @return 일반적인 형태의 String return. "HH:MM,DAY"
     */
    public static String changeTimeFormatWithDay(String time) {
        String day = time.substring(0, 2);

        int tem = Integer.parseInt(time.substring(3, 5));
        if (tem + 12 < 24)
            tem += 12;
        else if (tem + 12 >= 24) {
            tem -= 12;
            int temDay = Integer.parseInt(day.substring(1, 2)) + 1;
            day = "0" + temDay;
        }
        switch (day) {
            case "01":
                day = "MON";
                break;
            case "02":
                day = "TUE";
                break;
            case "03":
                day = "WED";
                break;
            case "04":
                day = "THU";
                break;
            case "05":
                day = "FRI";
                break;
            case "06":
                day = "SAT";
                break;
        }
        String result_time = tem + time.substring(5, 8);
        return result_time + "," + day;
    }


    /**
     * 각 수업의 시작 시간과 끝 시간을 가져오고, 일반적인 형태를 부르는 함수를 호출하며
     * 일주일에 여러개의 수업이 있으면, 각 시작과 끝 끼리 묶어줌.
     *
     * @param begin
     * @param end
     * @return 일주일에 진행되는 전체 강의 시간을 return 해줌, 각 수업을 //으로 구분함. "begin1 ~ end1 // begin2 ~ end2 // ..."
     */
    public String showBeingEndToNormalForm(String begin, String end) {
        String[] beginArray = begin.split(",");
        String endArray[] = end.split(",");

        if (beginArray.length != endArray.length) {
            return "Error at time conversion";
        }
        String result = "";

        for (int i = 0; i < beginArray.length; i++) {
            if (!beginArray[i].equals("NO")) {
                beginArray[i] = beginArray[i].substring(8, 16);
                endArray[i] = endArray[i].substring(8, 16);
            } else return "NO";
        }

        if (beginArray.length > 1) {
            Arrays.sort(beginArray);
            Arrays.sort(endArray);
        }

        for (int i = 0; i < beginArray.length; i++) {
            if (i == 0) {
                result += changeTimeFormatWithDay(beginArray[i]) + " ~ ";
                result += changeTimeFormatWithDay(endArray[i]);
            } else {
                result += "  //  " + changeTimeFormatWithDay(beginArray[i]) + " ~ ";
                result += changeTimeFormatWithDay(endArray[i]);
            }
        }
        return result;
    }


    /**
     * 수강신청시 수강신청하고자 하는 수업이 이미 신청한 다른 수업과 시간이 겹치는 지 검사하는 함수.
     * 수업 시간이 없는 E class의 경우 계산하지 않음
     * @param rsWanted   수강신청하고자 하는 수업의 시간 정보.
     * @param rsEnrolled 수강신청한 모든 수업들의 시간 정보.
     * @return 겹치지 않으면 true를 return.
     */
    public boolean compareTimeWithRS(ResultSet rsWanted, ResultSet rsEnrolled) {
        String[] wantedBegin;
        String[] wantedEnd;
        String[] wantedPeriod;
        String[] enrolledPeriod;
        try {
            if (rsWanted.next()) {
                wantedBegin = rsWanted.getString(1).split(",");
                wantedEnd = rsWanted.getString(2).split(",");
            } else return false;

            if (wantedBegin[0].equals("NO"))
                return true;
            else {
                wantedPeriod = changeToPeriod(wantedBegin, wantedEnd);
                while (rsEnrolled.next()) {
                    if (rsEnrolled.getString(1) == null) {
                        return true;
                    }
                    enrolledPeriod = changeToPeriod(rsEnrolled.getString(2).split(","), rsEnrolled.getString(3).split(","));
                    return compareTimeArray(wantedPeriod, enrolledPeriod);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 비교하고자 하는 모든 시간대를 배열에 넣고 비교함.
     * 겹치지 않는지 검사하는 것이 중요함.
     * @param wantedPeriod
     * @param period
     * @return 겹치치 않으면 true를 return
     */
    public boolean compareTimeArray(String[] wantedPeriod, String[] period) {
        for (int i = 0; i < wantedPeriod.length; i++) {
            for (int j = 0; j < period.length; j++) {
                // 먼저 요일이 같은지 검사함.
                if (wantedPeriod[i].substring(0, 2).equals(period[j].substring(0, 2))) {
                    double wantedStartTime = Double.parseDouble(wantedPeriod[i].substring(3, 8));
                    double wantedEndTime = Double.parseDouble(wantedPeriod[i].substring(12));
                    double enrolledStartTime = Double.parseDouble(period[j].substring(3, 8));
                    double enrolledEndTime = Double.parseDouble(period[j].substring(12));

                    // 기존의 과목이 먼저 시작했는데,
                    if (enrolledStartTime < wantedStartTime) {
                        // 더 늦게 끝나거나
                        if (enrolledEndTime > wantedStartTime)
                            return false;
                        // 추가를 희망하는 과목이 먼저 시작했는데
                    } else if (enrolledStartTime > wantedStartTime) {
                        // 더 늦게 끝나거나
                        if (enrolledStartTime < wantedEndTime) {
                            return false;
                        }
                        // 동시에 시작하거나 동시에 끝나면 겹칠 수 밖에 없음.
                    } else if (enrolledStartTime == wantedStartTime || enrolledEndTime == wantedEndTime)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * String 으로 들어오는 시간을 겹치는 것의 존재 여부를 비교함
     * @param wantedPeriod
     * @param period
     * @return 겹치지 않으면 true return.
     */
    public boolean compareTime(String wantedPeriod, String period) {
        // 요일을 비교함.
        if (wantedPeriod.substring(0, 2).equals(period.substring(0, 2))) {
            double wantedStartTime = Double.parseDouble(wantedPeriod.substring(3, 8));
            double wantedEndTime = Double.parseDouble(wantedPeriod.substring(12));
            double enrolledStartTime = Double.parseDouble(period.substring(3, 8));
            double enrolledEndTime = Double.parseDouble(period.substring(12));

            if (enrolledStartTime < wantedStartTime) {
                if (enrolledEndTime > wantedStartTime)
                    return false;
            } else if (enrolledStartTime > wantedStartTime) {
                if (enrolledStartTime < wantedEndTime) {
                    return false;
                }
            } else if (enrolledStartTime == wantedStartTime)
                return false;
        }
        return true;
    }


    /**
     * 수업시간이 begin 배열과 end 배열로 들어오는 것을
     * period 단위로 바꿔서, 한 수업의 시작과 끝 배열 형태로 바꿔줌
     * 예를 들어 begin[] = {"화요일 3시", "수요일 3시"}, end[] = {"화요일 4시 30분", "수요일 4시 30분"} 을
     * result = {"화요일 3시, 화요일 4시 30분", "수요일 3시, 수요일 4시 30분"}으로 바꿈.
     *
     * @param begin
     * @param end
     * @return
     */
    public String[] changeToPeriod(String[] begin, String[] end) {
        String[] result = new String[begin.length];
        for (int i = 0; i < begin.length; i++) {
            result[i] = changeTimeFormat(begin[i].split("-")[2]) + "," + changeTimeFormat(end[i].split("-")[2]);
        }
        return result;
    }

    /**
     * 배열이 아닌 String 형태로 들어오는 것을 period로 바꿔주는 함수
     *
     * @param begin
     * @param end
     * @return
     */
    public String changeToPeriod(String begin, String end) {
        String result = null;
        result = changeTimeFormat(begin.split("-")[2]) + "," + changeTimeFormat(end.split("-")[2]);
        return result;
    }

    /**
     * 과제 명세의 시간 형태를 24H 형태로 바꿔주는 함수
     *
     * @param time
     * @return
     */
    public String changeTimeFormat(String time) {
        String day = time.substring(0, 2);
        int tem = Integer.parseInt(time.substring(3, 5));
        if (tem + 12 < 24)
            tem += 12;
        else if (tem + 12 >= 24) {
            tem -= 12;
            int temDay = Integer.parseInt(day.substring(1, 2)) + 1;
            day = "0" + temDay;
        }
        return day + ":" + tem + "." + time.substring(6, 8);
    }

}
