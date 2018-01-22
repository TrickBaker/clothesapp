package com.anthony.frameimageeffect.util;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.anthony.frameimageeffect.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.functions.Action1;
import timber.log.Timber;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Sandy on 2/22/16.
 */
public class Utils {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    // Pattern for recognizing a URL, based off RFC 3986
    private static final Pattern urlPattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    /**
     * Generate a value suitable for use.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    private static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    @SuppressLint("NewApi")
    public static int generateId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {

            return generateViewId();
        } else {

            return View.generateViewId();
        }
    }

    public static boolean validationEmail(final String email) {
        Matcher mMatcher = Patterns.EMAIL_ADDRESS.matcher(email);
        return mMatcher.matches();
    }

    public static boolean validationPhone(final String phone) {
        Matcher mMatcher = Patterns.PHONE.matcher(phone);
        if (mMatcher.matches()) {
            if (phone.startsWith("+")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        boolean isEmpty = false;
        if (str == null || str.trim().length() == 0 || ("null").equalsIgnoreCase(str.toLowerCase())) {
            isEmpty = true;
        }
        return isEmpty;
    }


    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public static int getRandomColor() {
        Random rand = new Random();
        int redValue = rand.nextInt(255);
        int greenValue = rand.nextInt(255);
        int blueValue = rand.nextInt(255);
        return Color.argb(255, redValue, greenValue, blueValue);
    }

    public static File savebitmap(Context context, Bitmap bitmap) {
        try {
            String fileName = "upload.jpg";
            final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);

            final File ExternalStorageDirectory = Environment.getExternalStorageDirectory();
            final File file = new File(ExternalStorageDirectory + File.separator + fileName);

            FileOutputStream fileOutputStream = null;

            RxPermissions.getInstance(context)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .filter(aBoolean -> aBoolean)
                    .subscribe(aBoolean -> {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            Timber.e(e.getMessage());
                        }
                    });

            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes.toByteArray());

            return file;
        } catch (Exception exp) {
            Timber.e(exp.getMessage());
        }
        return null;
    }

    public static void shareContent(String message, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "SkillTracker");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        activity.startActivity(Intent.createChooser(intent,"Complete action using"));
    }

    public static void shareContentFb(String message, Activity activity) {
        boolean hasFb = false;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Skill Tracker");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(intent, 0);
        for (final ResolveInfo app : activityList) {
            if ((app.activityInfo.name).contains("facebook")) {
                hasFb = true;
                final ActivityInfo activityInfo = app.activityInfo;
                final ComponentName name = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent.setComponent(name);
                activity.startActivity(intent);
                break;
            }
        }

        if (!hasFb) {
            DialogUtils.showDialogWithMessage(activity, "Please install the Facebook app to share your result");
        }
    }

    public static void shareContentTwitter(String message, Activity activity) {
        boolean hasTwitter = false;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Skill Tracker");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(intent, 0);
        for (final ResolveInfo app : activityList) {
            if (app.activityInfo.name.contains("twitter")) {
                hasTwitter = true;
                final ActivityInfo activityInfo = app.activityInfo;
                final ComponentName name = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent.setComponent(name);
                activity.startActivity(intent);
                break;
            }
        }

        if (!hasTwitter) {
            DialogUtils.showDialogWithMessage(activity, "Please install the twitter app to share your result");
        }
    }

    public static void shareContentGooglePlus(String message, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Skill Tracker");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(intent, 0);
        boolean hasLinkedIn = false;
        for (final ResolveInfo app : activityList) {
            if (app.activityInfo.name.contains("google")) {
                hasLinkedIn = true;
                final ActivityInfo activityInfo = app.activityInfo;
                final ComponentName name = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent.setComponent(name);
                activity.startActivity(intent);
                break;
            }
        }

        if (!hasLinkedIn) {
            DialogUtils.showDialogWithMessage(activity, "Please install the LinkedIn app to share your result");
        }
    }

    public static void shareContentLinkedIn(String message, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Skill Tracker");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(intent, 0);
        boolean hasLinkedIn = false;
        for (final ResolveInfo app : activityList) {
            if (app.activityInfo.name.contains("linkedin")) {
                hasLinkedIn = true;
                final ActivityInfo activityInfo = app.activityInfo;
                final ComponentName name = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent.setComponent(name);
                activity.startActivity(intent);
                break;
            }
        }

        if (!hasLinkedIn) {
            DialogUtils.showDialogWithMessage(activity, "Please install the LinkedIn app to share your result");
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }

    public static String encodeUTF8(String raw) {
        try {
            return new String(raw.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static float convertDpToPixel(Context context, float dp) {
        DisplayMetrics mMetrics;
        Resources res = context.getResources();
        mMetrics = res.getDisplayMetrics();
        if (mMetrics == null) {
            Log.e("MPChartLib-Utils",
                    "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertDpToPixel(...). Otherwise conversion does not take place.");
            return dp;
            // throw new IllegalStateException(
            // "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertDpToPixel(...).");
        }

        DisplayMetrics metrics = mMetrics;
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static String getDeviceId(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Timber.d("Android device id: " + android_id);
        return android_id;
    }

    public static String readAsset(AssetManager mgr, String path) {
        String contents = "";
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = mgr.open(path);
            reader = new BufferedReader(new InputStreamReader(is));
            contents = reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                contents += '\n' + line;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return contents;
    }


    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static String convertStringTime(String time) {
        Calendar calendar = Calendar.getInstance();
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(f.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
        String dateTime = new SimpleDateFormat("dd MMM yyyy").format(calendar.getTime());
        return dateTime;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * Returns a list with all links contained in the input
     */
    public static List<String> extractUrls(String text) {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }

    public static void animationExpand_CollapseHeight(final View billChoices, int oldHeight, int newHeight, int duration) {
        ValueAnimator valueAnimator;
        valueAnimator = ValueAnimator.ofInt(oldHeight, newHeight);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                Log.v("height", value + "");
                billChoices.getLayoutParams().height = value;
                billChoices.requestLayout();
            }
        });

        valueAnimator.start();
    }

    public static File bitmapToFile(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory()
                .toString();
        OutputStream outStream = null;

        File file = new File(bmp + ".png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, bmp + ".png");
            Log.e("file exist", "" + file + ",Bitmap= " + bmp);
        }
        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("file", "" + file);
        return file;

    }


    public static void copyTextToClipBoard(Context context,String text){
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager)context.getSystemService(CLIPBOARD_SERVICE);

        ClipData myClip;
        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);

    }



    public static String pullLinks(String text) {
        ArrayList links = new ArrayList();
        Matcher matcher = urlPattern.matcher(text);
        while(matcher.find()) {
            String urlStr = matcher.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            links.add(urlStr);
        }
        if(links.size() != 0)
            return links.get(0).toString();
        return null;
    }

    public static Drawable loadImageDrawable(Context context, String itemData[],int position)
    {
        String val = itemData[position];
        // load image
        try {
            // get input stream
            InputStream ims = context.getAssets().open("image/" + val);
            // load image as fDrawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            return d;
        }
        catch(IOException ex) {
            return null;
        }
    }


}
