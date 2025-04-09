import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

val localProps = Properties()
val localPropsFile = rootProject.file("local.properties")
if (localPropsFile.exists()) {
    localProps.load(localPropsFile.inputStream())
}

val baseUrl = localProps.getProperty("BASE_URL") ?: ""
val hostName = localProps.getProperty("HOST_NAME") ?: ""
val certPin = localProps.getProperty("RAWG_CERT_PIN") ?: ""
val apiKey = localProps.getProperty("RAWG_API_KEY") ?: ""
val passKey = localProps.getProperty("PASS_KEY") ?: ""

android {
    namespace = "com.saifur.gamezone.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "HOST_NAME", "\"$hostName\"")
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
            buildConfigField("String", "CERT_PIN", "\"$certPin\"")
            buildConfigField("String", "PASS_KEY", "\"$passKey\"")
        }
        debug {
            isMinifyEnabled = true
            proguardFiles(
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "HOST_NAME", "\"$hostName\"")
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
            buildConfigField("String", "CERT_PIN", "\"$certPin\"")
            buildConfigField("String", "PASS_KEY", "\"$passKey\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

apply(from = "$rootDir/shared_dependencies.gradle.kts")

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.feature.delivery)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.retrofit)
    implementation(libs.gson.converter)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.android.database.sqlcipher)
    testImplementation(kotlin("test"))
}