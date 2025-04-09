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

-keep class com.saifur.gamezone.core.di.CoreModuleKt { *; }
-keep interface com.saifur.gamezone.core.data.source.remote.config.Endpoint { *; }
-keep interface com.saifur.gamezone.core.data.source.remote.datasource.IRemoteDataSource { *; }
-keep class com.saifur.gamezone.core.data.source.remote.datasource.RemoteDataSource { *; }
-keep interface com.saifur.gamezone.core.domain.repository.IGameRepository { *; }
-keep class com.saifur.gamezone.core.data.source.repository.GameRepository { *; }
-keep interface com.saifur.gamezone.core.domain.usecase.IGameUseCase { *; }
-keep class com.saifur.gamezone.core.domain.usecase.GameUseCase { *; }
-keep class com.saifur.gamezone.core.domain.model.Game { *;}
-keep class com.saifur.gamezone.core.domain.model.GameDetail { *;}
-keep class com.saifur.gamezone.core.domain.model.FavouriteGame { *;}

-keep class com.saifur.gamezone.core.utils.StringUtil { *; }
-keep class com.saifur.gamezone.core.utils.Resource$Success { *; }
-keep class com.saifur.gamezone.core.utils.Resource$Loading { *; }
-keep class com.saifur.gamezone.core.utils.Resource$Error { *; }
-keep class com.saifur.gamezone.core.data.source.remote.config.ApiResponse.** { *; }
-keepclassmembers class com.saifur.gamezone.core.data.source.remote.response.** {
    <init>();
    <fields>;
 }

-keep class com.saifur.gamezone.core.utils.Resource { *; }

-keep interface com.saifur.gamezone.core.utils.FavouriteNavigator { *; }

-keep class com.saifur.gamezone.core.**.Fake* { *; }
-keep class com.saifur.gamezone.core.data.source.local.entity.GameDetailEntity { *; }
-keep class com.saifur.gamezone.core.data.source.local.entity.GameListEntity { *; }

-dontwarn java.lang.invoke.StringConcatFactory

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Prevent R8 from leaving Data object members always null
-keepclasseswithmembers class * {
    <init>(...);
    @com.google.gson.annotations.SerializedName <fields>;
}
# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken