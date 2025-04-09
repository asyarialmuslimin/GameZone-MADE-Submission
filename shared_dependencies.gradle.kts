private fun DependencyHandler.implementation(dependency: Any) {
    add("implementation", dependency)
}
fun DependencyHandler.applySharedDeps() {
    implementation("io.insert-koin:koin-android:4.0.4")
    implementation("com.github.bumptech.glide:glide:4.16.0")
}

dependencies {
    applySharedDeps()
}