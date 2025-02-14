import java.io.FileInputStream
import java.io.IOException
import java.util.Properties

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "at.mikenet.serbianlatintocyrillic"
    compileSdk = 34

    defaultConfig {
        applicationId = "at.mikenet.serbianlatintocyrillic"
        minSdk = 23
        targetSdk = 34
        versionCode = 61
        versionName = "5.5.1"

        resourceConfigurations += listOf("en", "de", "hr", "sr", "uk")
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        debug {
            versionNameSuffix = "-debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            signingConfigs {
                create("release") {
                    try {
                        val keystorePropertiesFile = rootProject.file("credentials/keystore.properties")
                        val keystoreProperties = Properties()
                        keystoreProperties.load(FileInputStream(keystorePropertiesFile))

                        keyAlias = keystoreProperties.getProperty("KEY_ALIAS")
                        keyPassword = keystoreProperties.getProperty("KEY_PASSWORD")
                        storeFile = rootProject.file("credentials/${keystoreProperties.getProperty("STORE_FILE")}")
                        storePassword = keystoreProperties.getProperty("STORE_PASSWORD")
                    } catch(ignored: IOException) {
                        println("No signing configuration found, ignoring: $ignored")
                    }
                }
            }
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

kotlin.jvmToolchain(17)

dependencies {
    implementation(platform(libs.org.jetbrains.kotlin.bom))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.com.google.code.gson)
    implementation(libs.com.michaeltroger.latintocyrillic)
    implementation(libs.com.vorlonsoft.androidrate)
}

