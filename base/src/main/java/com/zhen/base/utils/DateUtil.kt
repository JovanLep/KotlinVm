package com.zhen.base.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间处理工具类
 */
object DateUtil {
    /**
     * 格式化时间（Format：yyyy-MM-dd HH:mm:ss）
     *
     * @param timeInMillis 时间
     * @return 格式化后时间字符串
     */
    fun formatHmsTime(timeInMillis: Long): String {
        @SuppressLint("SimpleDateFormat") val sdf =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date(timeInMillis * 1000))
    }

    /**
     * 格式化时间（Format：yyyy-MM-dd HH:mm）
     *
     * @param timeInMillis 时间
     * @return 格式化后时间字符串
     */
    fun formatTime(timeInMillis: Long): String {
        @SuppressLint("SimpleDateFormat") val sdf =
            SimpleDateFormat("yyyy-MM-dd HH:mm")
        return sdf.format(Date(timeInMillis))
    }

    /**
     * 格式化时间（Format：MM/dd HH:mm）
     *
     * @param timeInMillis 时间
     * @return 格式化后时间字符串
     */
    fun formatTimeWithoutYear(timeInMillis: Long): String {
        @SuppressLint("SimpleDateFormat") val sdf =
            SimpleDateFormat("MM/dd HH:mm")
        return sdf.format(Date(timeInMillis))
    }

    /**
     * 格式化时间（Format：yyyy-MM-dd）
     *
     * @param timeInMillis 时间
     * @return 格式化后时间字符串
     */
    fun formatTimeOnlyDate(timeInMillis: Long): String {
        @SuppressLint("SimpleDateFormat") val sdf =
            SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date(timeInMillis * 1000))
    }

    /**
     * 格式化时间
     *
     * @param timeInMillis 时间
     * @return 格式化后时间字符串
     */
    fun formatTimeOnlyDate2(timeInMillis: Long): String {
        @SuppressLint("SimpleDateFormat") val sdf =
            SimpleDateFormat("yyyyMMdd")
        return sdf.format(Date(timeInMillis))
    }

    /**
     * 格式化时间（Format：yyyy-MM-dd）
     *
     * @param date 时间
     * @return 格式化后时间字符串
     */
    fun formatTimeOnlyDate(date: Date?): String {
        @SuppressLint("SimpleDateFormat") val sdf =
            SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(date)
    }

    /**
     * 格式化时间
     *
     * @param time 时间
     * @return 格式化后时间字符串
     */
    fun formatPHPTimeForLong(time: Long): Long {
        val tate = Date(time * 1000L)
        @SuppressLint("SimpleDateFormat") val dateFormat =
            SimpleDateFormat("yyyyMMdd")
        val format = dateFormat.format(tate)
        return format.toLong()
    }

    /**
     * 格式化时间（Format：MM/dd HH:mm）
     *
     * @param timeInMillis 时间
     * @return 格式化后时间字符串
     */
    fun formatTimeWithoutYearForCN(timeInMillis: Long): String {
        @SuppressLint("SimpleDateFormat") val sdf =
            SimpleDateFormat("M月d日 HH:mm")
        return sdf.format(Date(timeInMillis * 1000))
    }

    /**
     * 格式化时间
     *
     * @param time 时间
     * @return 格式化后时间字符串
     */
    fun formatTimeDate(time: Long): String {
        @SuppressLint("SimpleDateFormat") val sdf =
            SimpleDateFormat("HH:mm")
        return sdf.format(Date(time * 1000))
    }

    /**
     * 格式化时间
     *
     * @param s 时间
     * @return 格式化后时间字符串
     */
    fun formatTimeForDay(s: Long): String {
        val time = StringBuilder()
        return if (s >= 0) {
            val day = s / (24 * 3600)
            val hour = s % (24 * 3600) / 3600
            val minute = s % 3600 / 60
            val second = s % 60
            if (day > 0) {
                time.append(day).append("天 ")
            }
            if (hour > 0) {
                if (hour < 10) {
                    time.append("0").append(hour).append(":")
                } else {
                    time.append(hour).append(":")
                }
            } else {
                time.append("00:")
            }
            if (minute > 0) {
                if (minute < 10) {
                    time.append("0").append(minute).append(":")
                } else {
                    time.append(minute).append(":")
                }
            } else {
                time.append("00:")
            }
            if (second > 0) {
                if (second < 10) {
                    time.append("0").append(second)
                } else {
                    time.append(second)
                }
            } else {
                time.append("00")
            }
            time.toString()
        } else {
            "0"
        }
    }

    /**
     * 获取任意日期
     *
     * @param date   日期对象
     * @param dayNum 天数 小于0，日期前dayNum；等于0，日期本身；大于0，日期后dayNum
     * @return 格式化后时间字符串
     */
    fun getAnyDay(date: Date?, dayNum: Int): String {
        var date = date
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, dayNum)
        date = calendar.time
        return formatTimeOnlyDate(date)
    }

    /**
     * 秒杀倒计时时间格式化
     *
     * @param time 时间
     * @return 格式化后时间字符串
     */
    fun getPreferenceDateFormat(time: Long): String {
        var builder = StringBuilder(time.toString())
        if (time < 10L) {
            builder = builder.append(0)
            builder.reverse()
        }
        return builder.toString()
    }

    /**
     * 消息列表格式化时间
     *
     * @param time 时间字符串
     * @return 格式化后时间字符串
     */
    @SuppressLint("SimpleDateFormat")
    fun formatPhpTime(time: String): String {
        val currentTime = System.currentTimeMillis()
        val format: String
        val date = Date(time.toLong() * 1000)
        val currentDateFormat =
            SimpleDateFormat("yyyy年M月d日 HH:mm")
        val currentMonthDateFormat =
            SimpleDateFormat("M月d日HH:mm")
        val currentHourDateFormat = SimpleDateFormat("HH:mm")
        val yearFormat = SimpleDateFormat("yyyy")
        val currentDate = Date((currentTime.toString() + "").toLong())
        val currentYear = yearFormat.format(currentDate).toInt()
        val msgYear = yearFormat.format(date).toInt()
        val now = Calendar.getInstance()
        //毫秒数
        val ms =
            1000 * (now[Calendar.HOUR_OF_DAY] * 3600 + now[Calendar.MINUTE] * 60 + now[Calendar.SECOND]).toLong()
        val newTime = currentTime - time.toLong() * 1000
        return if (newTime < ms) {
            //今天
            format = currentHourDateFormat.format(date)
            format
        } else if (newTime < ms + 24 * 3600 * 1000) {
            //昨天
            format = "昨天 " + currentHourDateFormat.format(date)
            format
        } else if (newTime < ms + 24 * 3600 * 1000 * 2) {
            //前天
            format = "前天 " + currentHourDateFormat.format(date)
            format
        } else if (currentYear == msgYear && newTime > ms + 24 * 3600 * 1000 * 2) {
            //前天之前
            format = currentMonthDateFormat.format(date)
            format
        } else {
            //去年
            format = currentDateFormat.format(date)
            format
        }
    }

    /**
     * 设定显示文字
     *
     * @param time 时间字符串
     * @return 格式化后时间字符串
     */
    fun getInterval(time: Long): HashMap<String?, Long?>? {
        var timeRecord: HashMap<String?, Long?>? = null
        if (time >= 0) {
            // 天
            val day = time / (24 * 3600)
            // 小时
            val hour = time % (24 * 3600) / 3600
            // 分钟
            val minute = time % 3600 / 60
            // 秒
            val second = time % 60
            timeRecord = HashMap(16)
            timeRecord["day"] = day
            timeRecord["hour"] = hour
            timeRecord["minute"] = minute
            timeRecord["second"] = second
        }
        return timeRecord
    }

    fun canPhone(): Boolean {
        val cal = Calendar.getInstance() // 当前日期
        val hour = cal[Calendar.HOUR_OF_DAY] // 获取小时
        val minute = cal[Calendar.MINUTE] // 获取分钟
        val minuteOfDay = hour * 60 + minute // 从0:00分开是到目前为止的分钟数
        val start = 9 * 60 // 起始时间 9:00的分钟数
        val end = 22 * 60 // 结束时间22:00的分钟数
        //在范围内
        //在范围外
        return minuteOfDay >= start && minuteOfDay <= end
    }
}