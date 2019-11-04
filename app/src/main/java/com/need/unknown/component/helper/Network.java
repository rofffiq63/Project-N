package com.need.unknown.component.helper;

import android.os.Build;
import android.util.Log;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.himanshurawat.hasher.HashType;
import com.himanshurawat.hasher.Hasher;
import com.need.unknown.BuildConfig;
import com.need.unknown.NeedApp;
import com.need.unknown.view.BaseView;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Khadafi on 10/01/2018.
 */

public class Network {
    private static String TAG = "Network.class";
    private BaseView progressListener;
    private NeedApp app;
    private OkHttpClient.Builder okHttpClient;
//    private static String getStringResourceByName(String aString, Context context) {
//
//        try {
//            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//            String version = pInfo.versionCode + "";
//            return version;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            return "100";
//        }
//
////        String packageName = context.getPackageName();
////        int resId = context.getResources().getIdentifier(aString, "string", packageName);
////        return context.getString(resId);
//
//    }

    public void attachView(final BaseView progressListener) {
        this.progressListener = progressListener;
    }

    public Network builder(final NeedApp app, final String token) {
        this.app = app;
        Cache cache = null;
        File cacheFile = new File(app.getCacheDir(), "responses");
        try {
            cache = new Cache(cacheFile, 10 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ProviderInstaller.installIfNeeded(app);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance()
                    .showErrorNotification(app, e.getConnectionStatusCode());
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            //It's not a release version.
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

//        String type = sessionManager.getMode();
//        final String token;

        UserAgentInterceptor userAgentInterceptor = new UserAgentInterceptor(BuildConfig.VERSION_NAME, BuildConfig.VERSION_NAME);
//        switch (type) {
//            case BuildConfig.DEVLOCAL:
////                token = "59b068a49bc42e2aed722ff0cc961cb668ce8a1b";
//                token = sessionManager.getTokenUser(false);
//                break;
//            case BuildConfig.DEVBASEURL:
////                token = "59b068a49bc42e2aed722ff0cc961cb668ce8a1b";
//                token = sessionManager.getTokenUser(false);
//                break;
//            case BuildConfig.BASESTAGINGURL:
//                token = sessionManager.getTokenUser(false);
//                break;
//            default:
//                token = sessionManager.getTokenUser(false);
//                break;
//        }

        List<ConnectionSpec> specs = new ArrayList<ConnectionSpec>();

        specs.add(new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build());

        if (Build.VERSION.SDK_INT > 19)
            specs.add(new ConnectionSpec.Builder(ConnectionSpec.CLEARTEXT)
                    .build());

//        ((BaseActivity) app).addLog(token);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(userAgentInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        String timestamp = Utils.getTimeStamp();

                        HttpUrl originalHttpUrl = original.url();
                        HttpUrl url = originalHttpUrl.newBuilder()
                                .addQueryParameter("device_id", Utils.getDeviceId(app))
                                .addQueryParameter("version_name", BuildConfig.VERSION_NAME)
                                .addQueryParameter("version", BuildConfig.VERSION_CODE + "")
                                //                                .addQueryParameter("version", getStringResourceByName("Version_int", app))
                                .build();

                        String strfy = stringifyRequestBody(original);
                        String body = (strfy.trim().isEmpty() ? "" : strfy + "&") + url.query();
                        String stringToSign = String.format("%s:%s:%s:%s", original.url().scheme() + "://" + original.url().host() + original.url().encodedPath(), body, token, timestamp);
                        String hash = Hasher.Companion.hash(stringToSign, HashType.SHA_256);
                        Log.d(TAG, "Signature: " + hash);
                        Log.d(TAG, "Timestamp: " + timestamp);

                        Request request = original.newBuilder()
                                .addHeader("Authorization", "Bearer " + token)
                                .header("Content-Type", "application/json")
                                .header("Accept", "application/json")
                                .header("X-Signature", hash)
                                .header("X-Timestamp", timestamp)
                                //                                .header("X-Authorization", token)
                                //                                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36")
                                .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
                                .removeHeader("Pragma")
                                .url(url)
                                .build();

                        Response response = chain.proceed(request);
                        response.cacheResponse();
                        return response.newBuilder()
                                .body(new ProgressResponseBody(response.body(), progressListener))
                                .build();
                    }
                })

                .connectionSpecs(specs)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(cache)
                .certificatePinner(new CertificatePinner.Builder()
                        .add("*.bisatopup.co.id", "sha256/25Z4CdWgClGabaaBRLn8jY6J91peXlWGhcOSbh01OHw=")
                        .build());
        //                .cache(cache)

        return this;
    }

    public SubscriptionService build() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(app.getMode())
                .client(enableTls12OnPreLollipop(okHttpClient))
                .addConverterFactory(GsonConverterFactory.create(getGsonViaBuilder()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return new SubscriptionService(retrofit.create(EndPointService.class));
    }

    public static OkHttpClient enableTls12OnPreLollipop(OkHttpClient.Builder client) {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);
                client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs);
            } catch (Exception exc) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
            }
        }

        return client.build();
    }

    private static class ProgressResponseBody extends ResponseBody {
        private final ResponseBody responseBody;
        private final BaseView progressListener;
        private BufferedSource bufferedSource;

        ProgressResponseBody(ResponseBody responseBody, BaseView progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener.updateNetwork(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }

    private static Gson getGsonViaBuilder() {
        return new GsonBuilder().serializeNulls().create();
    }

    public static class Tls12SocketFactory extends SSLSocketFactory {
        private static final String[] TLS_V12_ONLY = {"TLSv1.2"};

        final SSLSocketFactory delegate;

        public Tls12SocketFactory(SSLSocketFactory base) {
            this.delegate = base;
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return patch(delegate.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
            return patch(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
            return patch(delegate.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return patch(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return patch(delegate.createSocket(address, port, localAddress, localPort));
        }

        private Socket patch(Socket s) {
            if (s instanceof SSLSocket) {
                ((SSLSocket) s).setEnabledProtocols(TLS_V12_ONLY);
            }
            return s;
        }
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String stringifyRequestBody(Request request) {
        if (request.body() != null) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return URLDecoder.decode(buffer.readUtf8(), "UTF-8");
            } catch (final IOException e) {
                Log.w(TAG, "Failed to stringify request body: " + e.getMessage());
            }
        }
        return "";
    }
}
