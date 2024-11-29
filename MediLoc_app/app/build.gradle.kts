plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mediloc"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mediloc"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.activity:activity:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    testImplementation("junit:junit:4.13.2")

    // Guava library
    implementation("com.google.guava:guava:31.1-android")

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0") // Ensure this is included
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.google.ai.client.generativeai:generativeai:0.1.1") // Ensure this is the correct version

    // Other dependencies
    //    implementation ("ai.picovoice:porcupine-android:1.9.5")


    implementation ("ai.picovoice:porcupine-android:3.0.0") // Replace with the latest version
    implementation ("com.google.guava:guava:31.0.1-android")


}