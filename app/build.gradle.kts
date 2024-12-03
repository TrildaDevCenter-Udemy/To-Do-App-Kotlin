plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.room)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.safeargs)
}

android {
    namespace = "com.example.todoapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.todoapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type. Make sure to use a build
            // variant with `isDebuggable=false`.
            isMinifyEnabled = true

            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = true

            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("debug") {
            //applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }


    val javaVersion = JavaVersion.VERSION_17

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    composeCompiler {
        reportsDestination = layout.buildDirectory.dir("compose_compiler")
//        stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
    }


    kotlin {
        jvmToolchain(javaVersion.majorVersion.toInt())
    }

    lint {
        baseline = file("lint-baseline.xml")
    }

    buildFeatures {
        aidl = false
        buildConfig = false
        compose = true
        prefab = false
        renderScript = false
        resValues = true
        shaders = true
        viewBinding = true
        dataBinding = true
    }

    room {
        println("sqlite room database schema is generated as json at :\n $projectDir")
        schemaDirectory("$projectDir/schemas")
    }

    packagingOptions.resources {
        // The Rome library JARs embed some internal utils libraries in nested JARs.
        // We don't need them so we exclude them in the final package.
        //excludes += "/*.jar"

        // Multiple dependency bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        excludes.add("/META-INF/AL2.0")
        excludes.add("/META-INF/LGPL2.1")
    }
}

dependencies {
//    implementation fileTree(dir: 'libs', include: ['*.jar'])
//    implementation 'com.android.support:appcompat-v7:28.0.0'
//    implementation 'com.android.support.constraint:constraint-layout:2.0.4'
//    testImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'com.android.support.test:runner:1.0.2'
//    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'

    // work manager (workers)
    // this solves on recent Android  the crash signaled in Logcat with
    // Targeting S+ (version 31 and above) requires that one of FLAG_IMMUTABLE or FLAG_MUTABLE be specified
    // when creating a PendingIntent. Strongly consider using FLAG_IMMUTABLE, only use FLAG_MUTABLE
    // if some functionality depends on the PendingIntent being mutable, e.g. if it needs to be used
    // with inline replies or bubbles.
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.tools.core)
    implementation(libs.androidx.ui.viewbinding)
    implementation(libs.material)



    implementation (libs.androidx.work.mutiprocess)

    implementation (libs.navigation.fragment.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Compose
    //implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.graphics.android)

    // View interop,  compatibility with old layout xml filer
    // TOML version for Android in gradle does not yet support this kind of dependancy format
    // implementation(libs.androidx.compose.ui.viewinterop)
    //implementation("androidx.compose.ui.viewinterop:1.4.0")

    // compose layouts
    // compose constraints layout
    implementation (libs.androidx.constraintlayout.core)
    //implementation (libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.constraintlayout)

    // tooling for compose previews in android studio
    implementation(libs.androidx.compose.ui.tooling)

    // Navigation and compose
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.compose.runtime)

    implementation(libs.androidx.compose.runtime.live.data)

    ksp(libs.androidx.lifecycle.compiler)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)


    // Material
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.core.android)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material.icons.extended.android)

    // Material 3
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)

    //  Logging
    implementation (libs.timber)

    // Splash screen
    implementation (libs.androidx.core.splashscreen)

    // coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // hilt
    implementation (libs.androidx.hilt.android)
    ksp (libs.androidx.hilt.android.compiler)
    //implementation (libs.androidx.hilt.navigation.compose)
    implementation (libs.androidx.hilt.work)

    // add DataStore preferences
    // For Preferences DataStore
    implementation (libs.androidx.dataStore.preferences)
    implementation (libs.androidx.dataStore.core)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    //annotationProcessor(libs.room.compiler)

    // SQLLite Cipher - DO NOT REMOVE
    //implementation (libs.sqlcipher)
    implementation (libs.sqlite)

    // ML kit
    // Use this dependency to bundle the model with your app
    implementation (libs.mlkit.barcode.scanning)

    // google play services Ads
    implementation(libs.play.services.ads)

    // Use this dependency to use the dynamically downloaded model in Google Play Services
    //implementation ("com.google.android.gms:play-services-mlkit-barcode-scanning:18.3.0")

    // CameraX
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)


    // Coil image lib in kotlin
    implementation(libs.coil.kt)
    //implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.svg)
    implementation(libs.coil.kt.gif)

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.retrofit.kotlin.serialization)

    //okhttp
    implementation(libs.okhttp.logging)

    //GSON
    implementation (libs.gson)

    // Volley REST request lib
    implementation(libs.volley)
    //implementation(libs.volley.cronet)

    // Permissions
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.placeholder)

    // Test
    testImplementation(libs.junit4)

    // google truth
    testImplementation (libs.truth)

    // hilt test
    testImplementation(libs.androidx.hilt.android.testing)
    kspTest(libs.androidx.hilt.android.compiler)
    //testAnnotationProcessor(libs.androidx.hilt.android.compiler)


    // instrumented test ( androidTest)
    //androidTestImplementation(platform(libs.androidx.compose.bom))

    androidTestImplementation(libs.androidx.test.ext.ktx)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    androidTestImplementation (libs.androidx.arch.core.testing)
    androidTestImplementation (libs.kotlinx.coroutines.test)

    // google truth for android test
    androidTestImplementation (libs.truth)

    // hilt, android test
    androidTestImplementation(libs.androidx.hilt.android.testing)
    kspAndroidTest(libs.androidx.hilt.android.compiler)
    //androidTestAnnotationProcessor(libs.androidx.hilt.android.compiler)

    //navigation
    androidTestImplementation (libs.androidx.navigation.testing)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
