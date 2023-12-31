plugins {
    alias(jopitCatalog.plugins.android.application) apply false
    alias(jopitCatalog.plugins.android.library) apply false
    alias(jopitCatalog.plugins.kotlin.android) apply false
    alias(jopitCatalog.plugins.kotlin.kapt) apply false
    alias(jopitCatalog.plugins.hilt.android) apply false
    alias(jopitCatalog.plugins.jopit.publish) apply false
    alias(jopitCatalog.plugins.jopit.configuration) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
