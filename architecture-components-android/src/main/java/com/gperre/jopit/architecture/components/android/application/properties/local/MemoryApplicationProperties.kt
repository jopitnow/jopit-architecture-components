package com.gperre.jopit.architecture.components.android.application.properties.local

import com.gperre.jopit.architecture.components.android.application.properties.ApplicationProperties
import com.gperre.jopit.architecture.components.android.application.properties.models.ApplicationType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryApplicationProperties @Inject constructor() : ApplicationProperties {

    private lateinit var type: ApplicationType

    override fun setApplicationType(type: ApplicationType) {
        this.type = type
    }

    override fun isDemo(): Boolean {
        return if (::type.isInitialized) {
            type == ApplicationType.STAGING
        } else {
            throw IllegalStateException(INIT_FIRST_MESSAGE)
        }
    }

    override fun isProduction(): Boolean {
        return if (::type.isInitialized) {
            type == ApplicationType.PRODUCTION
        } else {
            throw IllegalStateException(INIT_FIRST_MESSAGE)
        }
    }

    companion object {
        private const val INIT_FIRST_MESSAGE = "You must call setApplicationType first."
    }
}
