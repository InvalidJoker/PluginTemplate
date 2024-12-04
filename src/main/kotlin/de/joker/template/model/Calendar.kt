package de.joker.template.model

import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import java.util.Calendar as JavaUtilCalendar

/**
 * This class is a calendar, which can be transformed from & to a [JavaUtilCalendar].
 * @param timeInMillis the milliseconds stored in the calendar
 * @param timeZoneId the id of the used timezone
 * @author Fruxz
 * @since 2023.1
 */
@Serializable
data class Calendar(
    private var timeInMillis: Long,
    private var timeZoneId: String,
) : Comparable<Calendar> {

    constructor(
        timeInMillis: Long,
        timeZone: TimeZone = TimeZone.getDefault(),
    ) : this(timeInMillis, timeZone.id)

    constructor(
        origin: JavaUtilCalendar
    ) : this(origin.timeInMillis, origin.timeZone.id)

    constructor(
        instant: Instant,
        timeZone: TimeZone = TimeZone.getDefault(),
    ) : this(origin = GregorianCalendar.from(ZonedDateTime.from(instant.atZone(timeZone.toZoneId()))))

    constructor(
        localDateTime: LocalDateTime,
        timeZone: TimeZone = TimeZone.getDefault(),
    ) : this(origin = GregorianCalendar.from(ZonedDateTime.of(localDateTime, timeZone.toZoneId())))

    private var origin: JavaUtilCalendar
        set(value) {
            timeInMillis = value.timeInMillis
            timeZoneId = value.timeZone.id
        }
        get() = JavaUtilCalendar.getInstance(TimeZone.getTimeZone(timeZoneId)).apply {
            this.timeInMillis = this@Calendar.timeInMillis
        }


    /**
     * This function adds some time to the calendar.
     * It takes the time from the [duration] and adds
     * it to the calendar using the internal units.
     * @param duration the amount of time which should be added
     * @return the calendar itself
     * @author Fruxz
     * @since 2023.1
     */
    fun add(duration: Duration) = apply {
        timeInMillis += duration.inWholeMilliseconds
    }

    /**
     * This function takes some time from the calendar.
     * It takes the time from the [duration] and takes
     * it from the calendar using the internal units.
     * @param duration the amount of time which should be taken
     * @return the calendar itself
     * @author Fruxz
     * @since 2023.1
     */
    fun take(duration: Duration) = apply {
        timeInMillis -= duration.inWholeMilliseconds
    }

    /**
     * This function returns, if this calendar is
     * after the [it] calendar.
     * @param it the calendar which should be compared
     * @return if this calendar is after the [it] calendar
     * @author Fruxz
     * @since 2023.1
     */
    fun isAfter(it: Calendar) = origin.after(it.origin)

    /**
     * This function returns, if this calendar is
     * before the [it] calendar.
     * @param it the calendar which should be compared
     * @return if this calendar is before the [it] calendar
     * @author Fruxz
     * @since 2023.1
     */
    fun isBefore(it: Calendar) = !isAfter(it)

    /**
     * This function returns, if this calendar is
     * after the [latest] calendar. This basically
     * means, this calendar is the expiration date
     * and [latest] is the current date, to check,
     * if the expiration data (this) is expired.
     * @param latest the calendar which should be compared
     * @return if this calendar is after the [latest] calendar
     * @author Fruxz
     * @since 2023.1
     */
    fun isInputExpired(latest: Calendar) = isAfter(latest)

    /**
     * This value returns, if this calendar is
     * after [now] calendar. This basically
     * means, this calendar is the expiration date
     * and [now] is the current date, to check,
     * if the expiration data (this) is expired.
     * @author Fruxz
     * @since 2023.1
     */
    val isExpired: Boolean
        get() = isBefore(now())

    val inFuture: Boolean
        get() = !isExpired

    val inPast: Boolean
        get() = isExpired

    val javaDate: Date
        get() = origin.time

    val timeZone: TimeZone
        get() = javaCalendar.timeZone

    val javaCalendar: JavaUtilCalendar
        get() = origin

    val javaInstant: Instant
        get() = Instant.ofEpochMilli(origin.timeInMillis)

    val javaLocalDateTime: LocalDateTime
        get() = LocalDateTime.ofInstant(javaInstant, timeZone.toZoneId())

    val timeInMilliseconds: Long
        get() = origin.timeInMillis

    fun editInJavaEnvironment(action: JavaUtilCalendar.() -> Unit) {
        origin = origin.apply(action)
    }

    fun durationTo(javaCalendar: JavaUtilCalendar) = (javaCalendar.timeInMillis - origin.timeInMillis).milliseconds

    fun durationTo(ascendCalendar: Calendar) = durationTo(ascendCalendar.origin)

    fun durationToNow() = durationTo(now())

    fun durationFrom(javaCalendar: JavaUtilCalendar) = (origin.timeInMillis - javaCalendar.timeInMillis).milliseconds


    fun durationFrom(ascendCalendar: Calendar) = durationFrom(ascendCalendar.origin)

    fun durationFromNow() = durationFrom(now())

    operator fun plus(duration: Duration) =
        clone().add(duration)

    operator fun minus(duration: Duration) =
        clone().take(duration)


    fun clone(): Calendar {
        return Calendar(origin.clone() as JavaUtilCalendar)
    }

    /**
     * This function returns the result of the [toString] function
     * of the [origin] object.
     * @author Fruxz
     * @since 2023.1
     */
    override fun toString(): String = getFormatted()

    /**
     * Returns the current [Calendar]-Time as a localized String,
     * using the [locale], a [dateStyle] and a [timeStyle].
     * This function gives the ability, to easily and quickly
     * create a usable String, which can be displayed to average
     * customers or in the front-end.
     * @param locale in which language & language-format it should be generated
     * @param dateStyle the style & length of the date
     * @param timeStyle the style & length of the time
     * @return A locale-based formatted [String] of the [javaDate]
     * @author Fruxz
     * @since 2023.1
     */
    fun getFormatted(
        locale: Locale = Locale.getDefault(),
        dateStyle: FormatStyle = FormatStyle.FULL,
        timeStyle: FormatStyle = FormatStyle.MEDIUM
    ): String =
        SimpleDateFormat
            .getDateTimeInstance(dateStyle.ordinal, timeStyle.ordinal, locale)
            .format(javaDate)

    override fun compareTo(other: Calendar): Int {
        return origin.compareTo(other.origin)
    }

    /**
     * This function returns, if the [JavaUtilCalendar.getTimeInMillis] of this == [other].
     * @param other the calendar which should be compared
     * @author Fruxz
     * @since 2023.1
     */
    override fun equals(other: Any?) = when (other) {
        is Calendar -> other.timeInMillis == origin.timeInMillis
        else -> false
    }

    override fun hashCode(): Int {
        return origin.hashCode()
    }

    companion object {

        /**
         * This function returns the current date and time.
         * @return the current date and time
         * @author Fruxz
         * @since 2023.1
         */
        @JvmStatic
        fun now() = Calendar(JavaUtilCalendar.getInstance())

        /**
         * This function returns the current date and time.
         * @return the current date and time
         * @author Fruxz
         * @since 2023.1
         */
        @JvmStatic
        fun now(instance: JavaUtilCalendar) = Calendar(instance)

        /**
         * This function returns the current date and time.
         * @param timeZone the time zone which should be used
         * @return the current date and time of [timeZone]
         * @author Fruxz
         * @since 2023.1
         */
        @JvmStatic
        fun now(timeZone: TimeZone) = now(instance = JavaUtilCalendar.getInstance(timeZone))

        /**
         * This function returns the current date and time.
         * @param locale the locale which should be used
         * @return the current date and time of [locale]
         * @author Fruxz
         * @since 2023.1
         */
        @JvmStatic
        fun now(locale: Locale) = now(instance = JavaUtilCalendar.getInstance(locale))

        /**
         * This function returns the current date and time.
         * @param timeZone the time zone which should be used
         * @param locale the locale which should be used
         * @return the current date and time of [timeZone] and [locale]
         * @author Fruxz
         */
        @JvmStatic
        fun now(locale: Locale, timeZone: TimeZone) = now(instance = JavaUtilCalendar.getInstance(timeZone, locale))

        /**
         * This function returns the current date and time plus the [duration].
         * @param duration the duration which should be added
         * @return the current date and time plus the [duration]
         * @author Fruxz
         * @since 2023.1
         */
        @JvmStatic
        fun fromNow(duration: Duration) = now().plus(duration)

        /**
         * This function returns the current date and time plus the [duration].
         * @param duration the duration which should be added
         * @return the current date and time plus the [duration]
         * @author Fruxz
         * @since 2023.1
         */
        @JvmStatic
        fun durationSince(duration: Duration) = now().minus(duration)

        /**
         * This function returns a new [Calendar] with the
         * same values as the [calendar].
         * @param calendar the calendar which should be cloned
         * @return a new [Calendar] with the same values
         * @author Fruxz
         * @since 2023.1
         */
        @JvmStatic
        fun fromLegacy(calendar: JavaUtilCalendar) = now(instance = calendar)

        /**
         * This function returns a new [Calendar] with the [milliseconds] and [timeZone].
         * @param timeInMillis the milliseconds which should be used
         * @param timeZone the time zone which should be used
         * @return a new [Calendar] with the [milliseconds] and [timeZone] values
         * @author Fruxz
         * @since 2023.4
         */
        @JvmStatic
        fun fromMilliseconds(timeInMillis: Long, timeZone: TimeZone = TimeZone.getDefault()) =
            Calendar(timeInMillis, timeZone)

    }

    enum class FormatStyle {
        FULL, HUGE, MEDIUM, SHORT;
    }

}

fun calendarFromDateString(dateFormat: String): Calendar {
    val cal: java.util.Calendar = java.util.Calendar.getInstance()
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)
    cal.time = sdf.parse(dateFormat) // all done
    return Calendar.fromLegacy(cal)
}