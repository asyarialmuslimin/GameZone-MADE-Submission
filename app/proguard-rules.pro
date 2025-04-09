# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep all Kotlin stdlib classes
-keep class kotlin.** { *; }

# Keep synthetic methods (seperti sort dari CollectionsKt)
-keepclassmembers class kotlin.collections.CollectionsKt { *; }

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Kadang juga perlu ini
-dontwarn kotlin.**

-dontwarn androidx.work.Data$Builder
-dontwarn androidx.work.Data
-dontwarn androidx.work.OneTimeWorkRequest$Builder
-dontwarn androidx.work.OneTimeWorkRequest
-dontwarn androidx.work.Operation
-dontwarn androidx.work.OutOfQuotaPolicy
-dontwarn androidx.work.WorkManager
-dontwarn androidx.work.WorkRequest$Builder
-dontwarn androidx.work.WorkRequest
-dontwarn androidx.work.Worker
-dontwarn androidx.work.multiprocess.RemoteListenableWorker
-dontwarn androidx.work.ForegroundInfo
-dontwarn androidx.work.ListenableWorker$Result
-dontwarn androidx.work.WorkerParameters
-dontwarn androidx.work.impl.utils.futures.SettableFuture
-dontwarn android.support.v4.app.Fragment
-dontwarn android.support.v4.app.FragmentActivity
-dontwarn android.support.v4.app.FragmentManager$FragmentLifecycleCallbacks
-dontwarn android.support.v4.app.FragmentManager
