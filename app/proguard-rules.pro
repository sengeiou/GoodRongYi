# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

-ignorewarnings
-optimizationpasses 9
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-dontwarn android.support.**
-dontwarn android.os.**
-keep class android.os.**{*;}
-keep class android.support.**{ *; }
-keep interface android.support.**{ *; }
-keep class sun.misc.Unsafe { *; }
-keep class com.fasterxml.jackson.**{ *; }
-keepnames class com.fasterxml.jackson.**{ *; }
-keep interface com.fasterxml.jackson.**{ *; }

-keep public class * extends android.support.**
-keep public class * extends android.app.*
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.view.View

#-dontwarn cn.jpush.**
#-keep class cn.jpush.** { *; }
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

-keepattributes Signature
-keepattributes *Annotation*, EnclosingMethod
# -keep public class com.tgf.exhibition.http.msg.** {
   #   public <fields>;
   #   public void set*(***);
   #   public *** get*();
   # }

-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
# keep rx
-keep class rx.**{*;}
-keep class android.net**{*;}
-dontwarn rx.**
-dontwarn android.net**
 #保持 Serializable 不被混淆
 -keepnames class * implements java.io.Serializable{}
 -keepattributes Signature
 -keep  class com.lichi.goodrongyi.mvp.**{*;}
 -keep  class com.lichi.goodrongyi.bean.**{*;}

 #greenDao混淆
-keep class de.greenrobot.dao.** {*;}
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
#===============okio==============
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn retrofit2.Platform$Java8
#======facebook=====
-keep class com.facebook.**{*;}
-keep interface com.facebook.** {*;}
-keep enum com.facebook.** {*;}
-dontwarn com.facebook.**
-keep class com.facebook.**
#=======squareup======
-keep class com.squareup.**{*;}
-dontwarn com.squareup.**


-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#=========友盟混淆===================
-keep class com.umeng.commonsdk.** {*;}
-dontusemixedcaseclassnames
    -dontshrink
    -dontoptimize
    -dontwarn com.google.android.maps.**
    -dontwarn android.webkit.WebView
    -dontwarn com.umeng.**
    -dontwarn com.tencent.weibo.sdk.**
    -keep public class javax.**
    -keep public class android.webkit.**
    -keepattributes Exceptions,InnerClasses,Signature
    -keepattributes *Annotation*
    -keepattributes SourceFile,LineNumberTable

    -keep public interface com.tencent.**
    -keep public interface com.umeng.socialize.**
    -keep public interface com.umeng.socialize.sensor.**
    -keep public interface com.umeng.scrshot.**
    -keep class com.android.dingtalk.share.ddsharemodule.** { *; }
    -keep public class com.umeng.socialize.* {*;}


    -keep class com.umeng.scrshot.**
    -keep public class com.tencent.** {*;}
    -keep class com.umeng.socialize.sensor.**
    -keep class com.umeng.socialize.handler.**
    -keep class com.umeng.socialize.handler.*
    -keep class com.umeng.weixin.handler.**
    -keep class com.umeng.weixin.handler.*
    -keep class com.umeng.qq.handler.**
    -keep class com.umeng.qq.handler.*
    -keep class UMMoreHandler{*;}
    -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
    -keep class com.tencent.mm.sdk.modelmsg.** implements   com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
    -keep class im.yixin.sdk.api.YXMessage {*;}
    -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
    -keep class com.tencent.mm.sdk.** {
     *;
    }
    -keep class com.tencent.mm.opensdk.** {
   *;
    }
    -dontwarn twitter4j.**
    -keep class twitter4j.** { *; }

    -keep class com.tencent.** {*;}
    -dontwarn com.tencent.**
    -keep public class com.umeng.com.umeng.soexample.R$*{
    public static final int *;
    }
    -keep public class com.linkedin.android.mobilesdk.R$*{
    public static final int *;
        }
    -keep class com.tencent.open.TDialog$*
    -keep class com.tencent.open.TDialog$* {*;}
    -keep class com.tencent.open.PKDialog
    -keep class com.tencent.open.PKDialog {*;}
    -keep class com.tencent.open.PKDialog$*
    -keep class com.tencent.open.PKDialog$* {*;}

    -keep class com.sina.** {*;}
    -dontwarn com.sina.**
    -keep class  com.alipay.share.sdk.** {
       *;
    }
    -keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
    }

    -keep class com.linkedin.** { *; }
    -keepattributes Signature


      #=============环信混淆=====
    -keep class  com.hianalytics.android.** {*;}
    -keep class  com.huawei.hms.** {*;}
    -keep class  com.xiaomi.** {*;}
    -keep class com.hyphenate.** {*;}
    -keep class com.superrtc.** {*;}
    -keep class com.hyphenate.chat.** {*;}
    -keep class org.jivesoftware.** {*;}
    -keep class org.apache.** {*;}
    -dontwarn  com.hyphenate.**
    -keepclasseswithmembers class * extends EaseChatRow{
    <fields>;
    <methods>;
    }

    # 极光推送
    -dontoptimize
    -dontpreverify
    -dontwarn cn.jpush.**
    -keep class cn.jpush.** { *; }

#=================支付宝================

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}

    -keep class **$Properties
   #记录生成的日志数据,gradle build时在本项目根目录输出
   # apk 包内所有 class 的内部结构
   -dump class_files.txt
   # 未混淆的类和成员
   -printseeds seeds.txt
   # 列出从 apk 中删除的代码
   -printusage unused.txt
   # 混淆前后的映射
   -printmapping mapping.txt