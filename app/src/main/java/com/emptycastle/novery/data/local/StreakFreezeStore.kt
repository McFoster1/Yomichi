package com.emptycastle.novery.data.local

import android.content.Context

/**
 * Remembers the days the user froze their reading streak.
 * Only one freeze is allowed per calendar week (Monday to Sunday).
 *
 * Days are stored as epoch days, the same unit the streak uses.
 */
object StreakFreezeStore {
    private const val PREFS = "yomichi_streak_freeze"
    private const val KEY_DAYS = "frozen_days"

    @Volatile
    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private fun prefs() = appContext?.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun frozenDays(): Set<Long> {
        val raw = prefs()?.getString(KEY_DAYS, "") ?: ""
        return raw.split(',').mapNotNull { it.trim().toLongOrNull() }.toSet()
    }

    /** The Monday of the week that contains [day]. Epoch day 0 was a Thursday. */
    private fun weekStart(day: Long): Long = day - Math.floorMod(day + 3, 7L)

    /** True if no freeze has been used yet in the calendar week of [today]. */
    fun canFreeze(today: Long): Boolean {
        val start = weekStart(today)
        return frozenDays().none { it >= start && it < start + 7 }
    }

    /** Freezes [today]. Returns false if this week's freeze was already used. */
    fun freeze(today: Long): Boolean {
        if (!canFreeze(today)) return false
        val days = (frozenDays() + today).filter { it >= today - 60 }
        prefs()?.edit()?.putString(KEY_DAYS, days.joinToString(","))?.apply()
        return true
    }

    /**
     * True if the streak survives from [lastRead] to [day]: either the days are
     * back to back, or every day in between was frozen.
     */
    fun bridges(lastRead: Long, day: Long): Boolean {
        if (lastRead >= day - 1) return true
        val frozen = frozenDays()
        var d = lastRead + 1
        while (d < day) {
            if (d !in frozen) return false
            d++
        }
        return true
    }
}
