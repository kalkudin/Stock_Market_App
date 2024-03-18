plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.finalproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.finalproject"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "BASE_URL_FMP",
                "\"https://financialmodelingprep.com/api/\"")
            buildConfigField(
                "String",
                "API_KEY",
                "\"b1UCP2fI4gkc8noMIL1yWEpZwoYhrsVE\"")
            buildConfigField(
                "String",
                "BASE_URL_MOCKY",
                "\"https://run.mocky.io/v3/\"")
        }
        release {
            buildConfigField(
                "String",
                "BASE_URL_FMP",
                "\"https://financialmodelingprep.com/api/\"")
            buildConfigField(
                "String",
                "API_KEY",
                "\"b1UCP2fI4gkc8noMIL1yWEpZwoYhrsVE\"")
            buildConfigField(
                "String",
                "BASE_URL_MOCKY",
                "\"https://run.mocky.io/v3/\"")

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("com.google.firebase:firebase-firestore:24.10.3")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")

    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")

    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    //
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    //circular imageview
    implementation("de.hdodenhof:circleimageview:3.1.0")
    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    //jsoup for parsing html
    implementation("org.jsoup:jsoup:1.14.3")
    //chart
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

}

kapt {
    correctErrorTypes = true
}