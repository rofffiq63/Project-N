package com.need.unknown.component.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.need.unknown.NeedApp;
import com.need.unknown.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.lang.Class.forName;

/**
 * Created by Khadafi on 30/12/2017.
 */

public class Utils {

    private static final String PLAY_STORE_APP_ID = "com.amanahcorp.bisatopup";
    private static final String GOOGLE_PLAY = "com.android.vending";
    private static final String AMAZON_STORE = "com.android.vending";

    public static String conDateF(final String sdate, String from, String into) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(from, Locale.US);
        Date date;
        try {
            date = simpleDateFormat.parse(sdate);
        } catch (Exception e) {
            date = new Date(System.currentTimeMillis());
            e.printStackTrace();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(into, Locale.US);

        return dateFormat.format(date);
    }

    public static String toCamelCase(final String init) {
        if (init == null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length() == init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }

    public static Bitmap encodeAsBitmap(String str, int WIDTH) throws WriterException {
        MultiFormatWriter barcodeWriter = new MultiFormatWriter();

        BitMatrix barcodeBitMatrix = barcodeWriter.encode(str, BarcodeFormat.PDF_417, WIDTH, WIDTH / 3);
        int width = barcodeBitMatrix.getWidth();
        int height = barcodeBitMatrix.getHeight();
        int[] pixels = new int[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (barcodeBitMatrix.get(j, i)) {
                    pixels[i * width + j] = 0xff000000;
                } else {
                    pixels[i * width + j] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static String conDateF(Date sdate, String into) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(from, Locale.US);
//        Date date = new Date(sdate);
//        try {
//            date = simpleDateFormat.parse(sdate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(into, Locale.US);

        return dateFormat.format(sdate);
    }

    public static boolean checkDebuggable(Context context) {
        return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    public static boolean checkEmulator(Activity activity) {
        try {
            boolean goldfish = getSystemProperty("ro.hardware").contains("goldfish");
            boolean emu = getSystemProperty("ro.kernel.qemu").length() > 0;
            boolean sdk = getSystemProperty("ro.product.model").equals("sdk");
            if (emu || goldfish || sdk) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getSystemProperty(String name) throws Exception {
        @SuppressLint("PrivateApi")
        Class systemPropertyClazz = forName("android.os.SystemProperties");

        return (String) systemPropertyClazz.getMethod("get", new Class[]{String.class})
                .invoke(systemPropertyClazz, new Object[]{name});
    }

    public static boolean isOriginal(Context context) {
        //Renamed?
        if (context.getPackageName().compareTo(PLAY_STORE_APP_ID) != 0) {
            return true; // BOOM!
        }

        //Relocated?
        String installer = context.getPackageManager().getInstallerPackageName(PLAY_STORE_APP_ID);

        // BOOM!
        return installer == null || installer.compareTo(GOOGLE_PLAY) != 0 && installer.compareTo(AMAZON_STORE) != 0;
    }

    public static boolean checkAppSignature(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                byte[] signatureBytes = signature.toByteArray();
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                final String currentSignature = Base64.encodeToString(md.digest(), Base64.DEFAULT);

                NeedApp needApp = (NeedApp) context.getApplicationContext();

//                Log.d("REMOVE_ME", "Include this string as a value for SIGNATURE:" + currentSignature);
                //compare signatures
                if (needApp.signature.equals(currentSignature)) {
                    return true;
                }
            }

        } catch (Exception e) {

        }
        return false;
    }

    public static long dateToMilis(String sdate, String from) {
        SimpleDateFormat formatter = new SimpleDateFormat(from, Locale.getDefault());
        Date date = null;

        try {
            date = formatter.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date.getTime();
    }

    public static String formatUang(double money) {
        return formatUang(money, false);
    }

    public static String formatUang(double money, boolean withRp) {
//        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
//        format.setMinimumFractionDigits(0);
//        format.setCurrency(Currency.getInstance("IDR"));
//        return format.format(Float.parseFloat(money));

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        return withRp ? "Rp" + formatter.format(money) : formatter.format(money);
    }

    public static String getHariIndonesia(String date) {
        if (date.toLowerCase().contains("monday"))
            return getBulanIndonesia(date.replace("Monday", "Senin"));

        if (date.toLowerCase().contains("tuesday"))
            return getBulanIndonesia(date.replace("Tuesday", "Selasa"));

        if (date.toLowerCase().contains("wednesday"))
            return getBulanIndonesia(date.replace("Wednesday", "Rabu"));

        if (date.toLowerCase().contains("thursday"))
            return getBulanIndonesia(date.replace("Thursday", "Kamis"));

        if (date.toLowerCase().contains("friday"))
            return getBulanIndonesia(date.replace("Friday", "Jum\'at"));

        if (date.toLowerCase().contains("saturday"))
            return getBulanIndonesia(date.replace("Saturday", "Sabtu"));

        if (date.toLowerCase().contains("sunday"))
            return getBulanIndonesia(date.replace("Sunday", "Minggu"));

        return getBulanIndonesia(date);
    }

    private static String getBulanIndonesia(String date) {
        if (date.toLowerCase().contains("january"))
            return date.replace("January", "Januari");

        if (date.toLowerCase().contains("february"))
            return date.replace("February", "Februari");

        if (date.toLowerCase().contains("march"))
            return date.replace("March", "Maret");

        if (date.toLowerCase().contains("april"))
            return date.replace("April", "April");

        if (date.toLowerCase().contains("may"))
            return date.replace("May", "Mei");

        if (date.toLowerCase().contains("june"))
            return date.replace("June", "Juni");

        if (date.toLowerCase().contains("july"))
            return date.replace("July", "Juli");

        if (date.toLowerCase().contains("august"))
            return date.replace("August", "Agustus");

        if (date.toLowerCase().contains("september"))
            return date.replace("September", "September");

        if (date.toLowerCase().contains("october"))
            return date.replace("October", "Oktober");

        if (date.toLowerCase().contains("november"))
            return date.replace("November", "November");

        if (date.toLowerCase().contains("december"))
            return date.replace("December", "Desember");

        return date;
    }

    public static String SHA1(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] sha1hash = new byte[40];
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            sha1hash = md.digest();
            return convertToHex(sha1hash);
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

//    public static MaterialShowcaseSequence newShowSquence(Activity context, String seqId) {
//        ShowcaseConfig config = new ShowcaseConfig();
//        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(context, seqId);
//
//        config.setDelay(100); // half second between each showcase view
//
//        sequence.setConfig(config);
//        return sequence;
//    }
//
//    public static MaterialShowcaseView showCaseBuilder(Activity context, View target, String title, String content, String dismiss, boolean withrect) {
//        MaterialShowcaseView.Builder rectangleBuilder = new MaterialShowcaseView.Builder(context)
//                .setTarget(target)
//                .setDismissText(dismiss)
//                .setContentText(content)
//                .setTitleText(title)
//                .setTitleTextColor(context.getResources().getColor(R.color.white))
//                .setContentTextColor(context.getResources().getColor(R.color.grey_700))
//                .setDismissTextColor(context.getResources().getColor(R.color.white))
//                .setMaskColour(context.getResources().getColor(R.color.transparent_dark));
//
//        if (withrect)
//            rectangleBuilder.withRectangleShape();
//
//        return rectangleBuilder.build();
//    }

    public static String generateMoneyFormat(String number) {
        double amount = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("####.00");
        return formatter.format(amount);
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String getDateSql(String date) {
        Date date_ = null;
        SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        try {
            date_ = format2.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date_);

        SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        return format3.format(calendar.getTime());
    }

    public static <T> boolean contains(final T[] array, final T v) {
        if (v == null) {
            for (final T e : array)
                if (e == null)
                    return true;
        } else {
            for (final T e : array)
                if (e == v || v.equals(e))
                    return true;
        }

        return false;
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static int getTabsHeight(Context context) {
        return (int) context.getResources().getDimension(R.dimen.tabs_height);
    }

    public static void showToast(Context activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void ShowLog(String message) {
        System.out.print(message);
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.
                    getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static String getFirebaseInstanceId(Context context) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        String key = context.getString(R.string.pref_firebase_instance_id_key);
//        String default_value = context.getString(R.string.pref_firebase_instance_id_default_key);
//        return sharedPreferences.getString(key, default_value);
//    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context activity) {
        int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

            @SuppressLint({"MissingPermission", "HardwareIds"}) String tmDevice = tm.getDeviceId();
            @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            String serial = null;
            serial = Build.SERIAL;
            if (tmDevice != null) return "01" + tmDevice;
            if (androidId != null) return "02" + androidId;
            if (serial != null) return "03" + serial;
//
        }
        //            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_PHONE_STATE}, BaseActivity.REQUEST_READ_PHONE_STATE);
        return "ID-UNDEFINED";
        //TODO
        // other alternatives (i.e. Wi-Fi MAC, Bluetooth MAC, etc.)
    }

    public static Spanned fromHtml(String html) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
            } else {
                return Html.fromHtml(html);
            }
        } catch (Exception e) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml("", Html.FROM_HTML_MODE_LEGACY);
            } else {
                return Html.fromHtml("");
            }
        }
    }

    public static Spannable increaseFontSizeForPath(Spannable spannable, String path, float increaseTime) {
        int startIndexOfPath = spannable.toString().indexOf(path);
        spannable.setSpan(new RelativeSizeSpan(increaseTime), startIndexOfPath,
                startIndexOfPath + path.length(), 0);

        return spannable;
    }

    @SuppressLint("ResourceAsColor")
    public static void showMessage(Context activity, String title, String message, int duration) {
        showToast(activity, message);
//        Alerter.create(context)
//                .setTitle(title)
//                .setText(message)
//                .setDuration(duration)
//                .setIcon(R.drawable.ic_info_outline_black_24dp)
////			  .enableProgress(true)
//                .setBackgroundColorRes(R.color.red_500) // or setBackgroundColorInt(Color.CYAN)
//                .setProgressColorRes(R.color.white)
//                .show();
    }

    public static void showMessage(Activity activity, String title, String message) {
        showMessage(activity, title, message, 3000);
    }

    public static void showMessage(Activity activity, String message, int duration) {
        showMessage(activity, "Info", message, duration);
    }

    public static void showMessage(Context activity, String message) {
        showMessage(activity, "Info", message, 3000);
    }

    public static String format_tanggal(String datestr) {
        try {
            DateFormat formatter;
            Date date;
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = formatter.parse(datestr);
            return new SimpleDateFormat("d MMM YYYY, HH:mm").format(date);
        } catch (Exception e) {

        }
        return datestr;
    }

    private boolean isRootAvailable() {
        for (String pathDir : System.getenv("PATH").split(":")) {
            if (new File(pathDir, "su").exists()) {
                return true;
            }
        }
        return false;
    }

    public boolean isRootGiven() {
        if (isRootAvailable()) {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec(new String[]{"su", "-c", "id"});
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String output = in.readLine();
                if (output != null && output.toLowerCase().contains("uid=0"))
                    return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (process != null)
                    process.destroy();
            }
        }

        return false;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.UK);
        String formattedDate = sdf.format(new Date());
        return formattedDate;
    }
}
