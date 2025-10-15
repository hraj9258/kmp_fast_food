import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.buildConfig)
}

kotlin {

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.material.icons.extended)
            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.appwrite)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "com.hraj9258.fastfood"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.hraj9258.fastfood"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.hraj9258.fastfood.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.hraj9258.fastfood"
            packageVersion = "1.0.0"
        }
    }
}

buildConfig{
    packageName = "com.hraj9258.fastfood"
    useKotlinOutput { internalVisibility = true }
    val properties = Properties()
    properties.load(project.rootProject.file("secret.properties").inputStream())
    buildConfigField("APPWRITE_PROJECT_ID", properties.getProperty("APPWRITE_PROJECT_ID"))
    buildConfigField("APPWRITE_PROJECT_NAME", properties.getProperty("APPWRITE_PROJECT_NAME"))
    buildConfigField("APPWRITE_ENDPOINT", properties.getProperty("APPWRITE_ENDPOINT"))
    buildConfigField("APPWRITE_DATABASE_ID", properties.getProperty("APPWRITE_DATABASE_ID"))
    buildConfigField("APPWRITE_BUCKET_ID", properties.getProperty("APPWRITE_BUCKET_ID"))
    buildConfigField("APPWRITE_USER_COLLECTION_ID", properties.getProperty("APPWRITE_USER_COLLECTION_ID"))
    buildConfigField("APPWRITE_CATEGORIES_COLLECTION_ID", properties.getProperty("APPWRITE_CATEGORIES_COLLECTION_ID"))
    buildConfigField("APPWRITE_MENU_COLLECTION_ID", properties.getProperty("APPWRITE_MENU_COLLECTION_ID"))
    buildConfigField("APPWRITE_CUSTOMIZATIONS_COLLECTION_ID", properties.getProperty("APPWRITE_CUSTOMIZATIONS_COLLECTION_ID"))
    buildConfigField("APPWRITE_MENU_CUSTOMIZATIONS_COLLECTION_ID", properties.getProperty("APPWRITE_MENU_CUSTOMIZATIONS_COLLECTION_ID"))
}

