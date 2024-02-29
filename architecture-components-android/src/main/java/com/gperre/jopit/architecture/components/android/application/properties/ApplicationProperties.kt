package com.gperre.jopit.architecture.components.android.application.properties

import com.gperre.jopit.architecture.components.android.application.properties.models.ApplicationType

interface ApplicationProperties {

    fun setApplicationType(type: ApplicationType)

    fun isDemo(): Boolean

    fun isProduction(): Boolean
}
