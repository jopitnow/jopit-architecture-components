package com.gperre.jopit.architecture.components.android.network.annotations

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CacheInfo(

    /**
     * life time cache online is defined in Seconds
     **/
    val lifeTimeCache: Int = 0,
    /**
     * life time offline is defined in Days
     **/
    val lifeTimeOffline: Int = 0,
    /**
     * life time local database is defined in Seconds
     **/
    val lifeTimeDatabase: Int = 0
) {
    companion object {
        const val ONE_HOUR = 3600
        const val ONE_DAY = ONE_HOUR * 24
        const val ONE_WEEK = ONE_DAY * 7
        const val ONE_MONTH = ONE_WEEK * 30
    }
}
