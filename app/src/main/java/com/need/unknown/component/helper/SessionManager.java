package com.need.unknown.component.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.freshchat.consumer.sdk.Freshchat;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.need.unknown.BuildConfig;
import com.need.unknown.R;
import com.need.unknown.component.model.DataUser;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

/**
 * Created by User on 12/8/17.
 */

public class SessionManager {

    private static final String AIRPORT = "airport";
    private static final String LAST_FROM_AIRPORT = "lastFrom";
    private static final String LAST_DEST_AIRPORT = "lastDest";
    public static final String BANK_CODE = "bankCode";
    public static final String USER_BANK = "userBank";
    private final String USER_ID = "loggedIn_as";
    private final String USER_TOKEN = "userCoin_";
    private final String KEY_LOGIN = "isLogin";
    private final String KEY_FIRST_RUN = "isFirstRun";
    private final String PIN_VALIDATED = "isPinValidated";
    private final String DEV_MODE = "isDevMode";
    private final String STRUK_INFO = "customInfo";
    private final String ENABLE_LOGO = "enableLogo";
    private final String APP_VERSION = "appVersion";
    private final String USER_DATA = "user";
    private SharedPreferences pref;
    private boolean pinValidated;
    private SharedPreferences.Editor editor;
    private Context context;
    private boolean productLoaded;

    //konstruktor
    public SessionManager(Context context) {
        this.context = context;
        int PRIVATE_MODE = 0;
        String PREF_NAME = null;
        try {
            PREF_NAME = AESCrypt.encrypt(getAppToken(), "LoginStat").replaceAll("[^a-zA-Z0-9]", "");
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static String getAppToken() {
        return "a799f7784d905350eccd3054a840fa7cb61c39e0";
    }

    public boolean getState() {
        return pref.getBoolean(KEY_LOGIN, false);
    }

    //membuat session login
    public String createSession(DataUser dataUser, boolean state) {
        editor.putString(USER_DATA, new Gson().toJson(dataUser));
        editor.putBoolean(KEY_LOGIN, state);
        editor.putBoolean(ENABLE_LOGO, true);
        editor.putString(STRUK_INFO, "Solusi pembayaran dalam satu aplikasi\nCall Center: 021-2278-5123");
        editor.putString(APP_VERSION, BuildConfig.VERSION_NAME);
//        editor.putString(USER_TOKEN, token);
        editor.apply();
        return "Selamat datang, ";
        //apply(); digunakan untuk menyimpan perubahan
    }

    public boolean updateUserData(DataUser dataUser) {
        try {
            editor.putInt(USER_ID, dataUser.getData().getUser_id());
            editor.putString(USER_DATA, new Gson().toJson(dataUser));
            editor.apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public DataUser getUserData() {
        DataUser dataUser = new Gson().fromJson(pref.getString(USER_DATA, null), DataUser.class);

        if (dataUser != null)
            return dataUser;
        else return null;
    }

    public String getTokenUser(boolean encrypted) {
        DataUser dataUser = new Gson().fromJson(pref.getString(USER_DATA, null), DataUser.class);
        if (!encrypted)
            try {
                if (dataUser != null)
                    return AESCrypt.decrypt(getAppToken(), dataUser.getToken()  );
                else return getAppToken();
            } catch (Exception e) {
                e.printStackTrace();
                return getAppToken();
            }
        else return dataUser.getToken();
    }

    public String getAppVersion() {
        return pref.getString(APP_VERSION, "0");
    }

    public void simpanStrukKonfig(boolean logo, String info) {
        editor.putBoolean(ENABLE_LOGO, logo);
        editor.putString(STRUK_INFO, info);
        editor.apply();
    }

    public boolean getEnableLogo() {
        return pref.getBoolean(ENABLE_LOGO, true);
    }

    public String getInfoTambahan() {
        return pref.getString(STRUK_INFO, "Solusi pembayaran dalam satu aplikasi\nCall Center: 021-2278-5123");
    }

    public void updateSession(boolean state) {
        editor.putBoolean(KEY_LOGIN, state);
        editor.apply();
    }

    //mendapatkan token
    public Integer getIdUser() {
        return pref.getInt(USER_ID, 0);
    }

    public boolean getFirstRun() {
        return pref.getBoolean(KEY_FIRST_RUN, true);
    }

    public void setFirstRun(boolean state) {
        editor.putBoolean(KEY_FIRST_RUN, state);
        editor.apply();
    }

    public boolean getDevMode() {
        return pref.getBoolean(DEV_MODE, false);
    }

    public void setDevMode(boolean state) {
        editor.putBoolean(DEV_MODE, state);
        editor.apply();
    }

    public boolean isPinValidated() {
        pinValidated = pref.getBoolean(PIN_VALIDATED, false);
        return pinValidated;
    }

    public void setPinValidated(boolean pinValidated) {
        editor.putBoolean(PIN_VALIDATED, pinValidated);
        editor.apply();
        this.pinValidated = pinValidated;
    }

    //cek login
    public boolean isLogin() {
        return pref.getBoolean(KEY_LOGIN, false);
    }

    //logout user
    public void logout() {
        //        createSession(0, false);
        setFirstRun(true);
        setPinValidated(false);
//        updateSession(false);
        editor.clear();
        editor.apply();

        try {
            Freshchat.resetUser(context);
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getIsAgent() {
        return pref.getString("agent", "");
    }

    public void setIsAgent(String isAgent) {
        editor.putString("agent", isAgent);
        editor.apply();
    }

    public String getIsUpload() {
        return pref.getString("upload", "");
    }

    public void setIsUpload(String isUpload) {
        editor.putString("upload", isUpload);
        editor.apply();
    }

    public void setTotalAffiliasi(String totalAffiliasi) {
        editor.putString("affiliasi", totalAffiliasi);
        editor.apply();
    }

    public String getAffiliasi() {
        return pref.getString("affiliasi", "");
    }

    public String getTotalPendapatan() {
        return pref.getString("pendapatan", "");
    }

    public void setTotalPendapatan(String totalPendapatan) {
        editor.putString("pendapatan", totalPendapatan);
        editor.apply();
    }

    public String getKodeAffiliasi() {
        return pref.getString("kodeAff", "");
    }

    public void setKodeAffiliasi(String kodeAffiliasi) {
        editor.putString("kodeAff", kodeAffiliasi);
        editor.apply();
    }

    public String getNamaAgen() {
        return pref.getString("namaAgen", "");
    }

    public void setNamaAgen(String namaAgen) {
        editor.putString("namaAgen", namaAgen);
        editor.apply();
    }

    public String getGender() {
        return pref.getString("gender", "");
    }

    public void setGender(String gender) {
        editor.putString("gender", gender);
        editor.apply();
    }

    public String getBorn() {
        return pref.getString("born", "");
    }

    public void setBorn(String born) {
        editor.putString("born", born);
        editor.apply();
    }

    public String getPoin() {
        return pref.getString("poin", "");
    }

    public void setPoin(String poin) {
        editor.putString("poin", poin);
        editor.apply();
    }

    public String getWallet() {
        return pref.getString("wallet", "");
    }

    public void setWallet(double wallet) {
        editor.putString("wallet", String.valueOf(wallet));
        editor.apply();
    }

    public String getNama() {
        return pref.getString("nama", "");
    }

    public void setNama(String nama) {
        editor.putString("nama", nama);
        editor.apply();
    }

    public String getUsername() {
        return pref.getString("nama", "");
    }

    public void setUsername(String username) {
        editor.putString("username", username);
        editor.apply();
    }

    public String getId() {
        return pref.getString("id", "");
    }

    public void setId(String id) {
        editor.putString("id", id);
        editor.apply();
    }

    public String getEmail() {
        return pref.getString("email", "");
    }

    public void setEmail(String email) {
        editor.putString("email", email);
        editor.apply();
    }

    public String getFoto() {
        return pref.getString("foto", "");
    }

    public void setFoto(String foto) {
        editor.putString("foto", foto);
        editor.apply();
    }

    public String getPhone() {
        return pref.getString("phone", "");
    }

    public void setPhone(String phone) {
        editor.putString("phone", phone);
        editor.apply();
    }

    public boolean getFacebook() {
        return pref.getBoolean("facebook", false);
    }

    public void setFacebook(boolean status) {
        editor.putBoolean("facebook", status);
        editor.apply();
    }

    public boolean getUpgrade() {
        return pref.getBoolean("upgrade", false);
    }

    public void setUpgrade(boolean status) {
        editor.putBoolean("upgrade", status);
        editor.apply();
    }

    public String getFirebaseInstanceId() {
        String key = context.getString(R.string.pref_firebase_instance_id_key);
        String default_value = context.getString(R.string.pref_firebase_instance_id_default_key);
        return pref.getString(key, default_value);
    }

    public void setFirebaseInstanceId(String currentToken) {
        editor.putString(context.getString(R.string.pref_firebase_instance_id_key), currentToken);
        editor.apply();
    }

    public String getDeviceId() {
        return pref.getString(Constant.DEVICE_ID, Utils.getDeviceId(context));
    }

    public boolean isQPSC() {
        return pref.getBoolean("QPSC", false);
    }

    public void setQPSC(boolean b) {
        editor.putBoolean("QPSC", b);
        editor.apply();
    }

    public boolean isIntro() {
        return pref.getBoolean("intro", false);
    }

    public void setIntro(boolean b) {
        editor.putBoolean("intro", b);
        editor.apply();
    }

    public boolean isIntroVoucher() {
        return pref.getBoolean("introvoucher", false);
    }

    public void setIntroVoucher(boolean b) {
        editor.putBoolean("introvoucher", b);
        editor.apply();
    }

    public boolean isIntroCheckout() {
        return pref.getBoolean("introcheckout", false);
    }

    public void setIntroCheckout(boolean b) {
        editor.putBoolean("introcheckout", b);
        editor.apply();
    }

    public boolean isProductLoaded() {
        return pref.getBoolean("isLoaded", false);
    }

    public void setProductLoaded(boolean productLoaded) {
        this.productLoaded = productLoaded;
        editor.putBoolean("isLoaded", productLoaded);
        editor.apply();
    }
}
