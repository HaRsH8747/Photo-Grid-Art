-keep class com.android.vending.billing.**
-dontwarn com.squareup.okhttp.**
-dontobfuscate
-dontoptimize
-keepattributes *
-keep class !com.bumptech.glide.repackaged.**,com.bumptech.glide.**

-keep class com.bumptech.glide.annotation.compiler.GlideAnnotationProcessor


# "duplicate definition of library class"
-dontnote sun.applet.**
# "duplicate definition of library class"
-dontnote sun.tools.jar.**
# Reflective accesses in com.google.common.util.concurrent.* and some others
-dontnote com.bumptech.glide.repackaged.com.google.common.**
# com.google.common.collect.* and some others (….common.*.*)
-dontwarn com.google.j2objc.annotations.Weak
# com.google.common.util.concurrent.FuturesGetChecked$GetCheckedTypeValidatorHolder$ClassValueValidator
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#-dontwarn **

-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }


-keep class com.firebase.** { *; }
-keep class org.apache.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**