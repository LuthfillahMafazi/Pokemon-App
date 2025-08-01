// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.hilt) apply false
    alias(libs.plugins.kotlin.safe.args) apply false
    alias(libs.plugins.kotlin.kapt) apply false
}