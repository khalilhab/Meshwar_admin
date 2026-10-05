package com.meshwar.dashboard.utils;


import static com.meshwar.dashboard.utils.AppSharedConstants.BEARER;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.data.prefs.AppPreferencesHelper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meshwar.dashboard.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AppSharedMethod {

    public static DecimalFormat decimalFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));

    private static final int CAMERA_IMAGE_MAX_SIZE = 1920; // longest side (px) of photos taken with the camera
    private static File pendingCameraFile; // where the camera app saves the full-resolution photo


    public static boolean checkBackgroundLocation(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        }
    }



    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
    }

    public static String getBearerToken() {
        AppPreferencesHelper preferencesHelper = new AppPreferencesHelper();
        User user = preferencesHelper.getUser();
        if (user != null){
            if (TextUtils.isEmpty(preferencesHelper.getUserToken())) {
                return "";
            }

            return BEARER + preferencesHelper.getUserToken().replace(BEARER, "");
        }
        return null;
    }


    public static void call(Context context, String mobile) {
        Uri number = Uri.parse(String.format("tel:%s", mobile));
        context.startActivity(new Intent(Intent.ACTION_DIAL, number));
    }

    public static void clearError(TextInputLayout...  textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts){
            textInputLayout.setErrorEnabled(false);
            textInputLayout.setError(null);
            textInputLayout.clearFocus();
        }
    }

    public static String checkMobileValidate(String mobile) {
        if (mobile.length()==10 && mobile.startsWith("05")){
            return "966"+mobile.replaceFirst("0","");
        }else if (mobile.length()==9 && mobile.startsWith("5")){
            return "966"+mobile;
        }
        return "";
    }


    public static void showError(TextInputLayout textInputLayout,String errorMsg) {
        textInputLayout.requestFocus();
        textInputLayout.setError(errorMsg);
        textInputLayout.setErrorIconDrawable(0);
    }

    public static void dir(Context context, String lat, String lng) {
        openUrl(context, String.format("https://www.google.com/maps/dir/?api=1&destination=%s,%s", lat, lng));
    }

    public static String getFullMobile(String prefix, String mobile) {
        return String.format("%s-%s", prefix, mobile);
    }

    public static <T> T toObject(String o, @NonNull Class<T> valueType) {
        return new Gson().fromJson(o, valueType);
    }

    public static <T> List<T> toObjects(String o, Class<T> valueType) {
        return new Gson().fromJson(o, TypeToken.getParameterized(List.class, valueType).getType());
    }


    public static void openUrl(Context context, String url) {

        if (TextUtils.isEmpty(url))
            url = "http://";

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(AppSharedConstants.TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    String token = task.getResult();
                    Log.i(AppSharedConstants.TAG, "Fetching FCM registration token success : " + token);
                    new AppPreferencesHelper().setFcmToken(token);
                });
    }

    // [Start] check Connect To Internet
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

        if (info != null) {
            for (NetworkInfo anInfo : info) {
                if (anInfo.getState() == NetworkInfo.State.CONNECTED)
                    return true;
            }
        }
        return false;
    }
    // [End] check Connect To Internet


    public static boolean validate(Context context, TextView... txtViews) {
        for (TextView textView : txtViews) {
            textView.setError(null);
        }

        for (TextView textView : txtViews) {
            if (isEmpty(textView)) {
                textView.setError(context.getString(R.string.this_field_is_required));
                textView.requestFocus();
                return false;
            }
        }

        return true;
    }

    public static boolean validateMobile(Context context, TextView... txtViews) {
        for (TextView textView : txtViews) {
            textView.setError(null);
        }

        for (TextView textView : txtViews) {
            if (!validateMobile(textView.getText().toString())) {
                textView.setError(context.getString(R.string.mobile_number_is_invalid));
                textView.requestFocus();
                return false;
            }
        }

        return true;
    }

    public static boolean validateEmail(Context context, TextView... txtViews) {
        for (TextView textView : txtViews) {
            textView.setError(null);
        }

        for (TextView textView : txtViews) {
            if (!validateEmail(textView.getText().toString())) {
                textView.setError(context.getString(R.string.email_is_invalid));
                textView.requestFocus();
                return false;
            }
        }

        return true;
    }

    public boolean validateGreaterThanZero(Context context, TextView... txtViews) {
        if (!validate(context, txtViews))
            return false;

        for (TextView textView : txtViews) {
            textView.setError(null);
        }

        for (TextView textView : txtViews) {
            if (!validateGreaterThanZero(textView.getText().toString())) {
//                textView.setError(context.getString(R.string.please_enter_a_value_greater_than_zero));
                textView.requestFocus();
                return false;
            }
        }

        return true;
    }

    public static boolean validateMobile(String... strings) {
        for (String s : strings) {
            if (s.length() < 5 || s.length() > 11) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateEmail(String... strings) {
        for (String s : strings) {
            if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateGreaterThanZero(String... strings) {
        for (String s : strings) {
            if (!validateGreaterThanZero(Double.parseDouble(s)))
                return false;
        }
        return true;
    }

    public static boolean validateGreaterThanZero(Double... doubles) {
        for (Double d : doubles) {
            if (d <= 0)
                return false;
        }
        return true;
    }

    public static boolean isEmpty(EditText editText) {
        return isEmpty(editText.getText());
    }

    public static boolean isEmpty(TextView textView) {
        return isEmpty(textView.getText());
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return isEmpty(charSequence.toString());
    }

    public static boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }


    public static String getText(TextView textView) {
        return textView.getText().toString();
    }


    public static RequestBody getPart(TextView textView) {
        return getPart(getText(textView));
    }

    public static MultipartBody.Part getPartString(String key, String value) {
        RequestBody body = new RequestBody() {
            @Override
            public okhttp3.MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(okio.BufferedSink sink) throws java.io.IOException {
                sink.writeUtf8(value);
            }
        };
        return MultipartBody.Part.createFormData(key, null, body);
    }

    public static RequestBody getPart(String value) {
        return new RequestBody() {
            @Override
            public okhttp3.MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(okio.BufferedSink sink) throws java.io.IOException {
                sink.writeUtf8(value);
            }
        };
    }


    public static MultipartBody.Part getPart(Context context, Uri selectedFileUri, String key) {
        File file = getFile(context, selectedFileUri);
        return file == null ? null : getPart(file, key);
    }

    // copies the picked file into the app cache through the ContentResolver, so no storage permission is needed
    public static File getFile(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        if ("file".equalsIgnoreCase(uri.getScheme())) {
            return new File(uri.getPath());
        }

        File file = null;
        try (InputStream in = context.getContentResolver().openInputStream(uri)) {
            if (in == null) {
                return null;
            }
            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType(uri));
            file = createCacheFile(context, "upload", extension == null ? "jpg" : extension);
            try (OutputStream out = new FileOutputStream(file)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
            }
            return file;
        } catch (IOException | SecurityException e) {
            Log.e(AppSharedConstants.TAG, "getFile: " + e.getMessage());
            if (file != null) {
                file.delete();
            }
            return null;
        }
    }

    private static File createCacheFile(Context context, String folder, String extension) {
        File dir = new File(context.getCacheDir(), folder);
        File[] oldFiles = dir.listFiles();
        if (oldFiles == null) {
            dir.mkdirs();
        } else {
            long dayAgo = System.currentTimeMillis() - 24 * 60 * 60 * 1000L;
            for (File oldFile : oldFiles) {
                if (oldFile.lastModified() < dayAgo) {
                    oldFile.delete();
                }
            }
        }
        return new File(dir, UUID.randomUUID() + "." + extension);
    }

    public static MultipartBody.Part getPart(File file, String key) {
        ProgressRequestBody requestFile = new ProgressRequestBody(file);

        String fileName = UUID.randomUUID().toString() +
                FileUtils.getExtension(file.toString());

        return MultipartBody.Part.createFormData(key, fileName, requestFile);
    }

    public static Uri getSelectedImageUri(Context context, int resultCode, Intent data) { // advance mohammed marwan
        return getSelectedImageUri(context, resultCode, data, false);
    }

    public static Uri getSelectedImageUri(Context context, int resultCode, Intent data, boolean compress) { // advance mohammed marwan
        File cameraFile = pendingCameraFile;
        pendingCameraFile = null;

        if (resultCode == Activity.RESULT_OK) {

            Uri selectedFileUri = null;

            if (cameraFile != null && cameraFile.length() > 0) {
                // full-resolution photo saved by the camera app
                selectedFileUri = getScaledImageUri(context, cameraFile);
            } else if (data != null) {
                selectedFileUri = data.getData();

                if (selectedFileUri == null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        if (imageBitmap != null) {
                            Uri tempUri = AppSharedMethod.getImageUri(context, imageBitmap);
                            if (tempUri != null) {
                                selectedFileUri = tempUri;
                            }
                        }
                    }
                }
            }

            if (selectedFileUri == null) {
                Log.e(AppSharedConstants.TAG, "selectedFileUri is null 0");
                return null;
            }

            if (compress){
                File file = getFile(context, selectedFileUri);
                Bitmap bitmap = file == null ? null : BitmapFactory.decodeFile(file.getPath());
                selectedFileUri = bitmap == null ? null : AppSharedMethod.getImageUri(context, Bitmap.createScaledBitmap(bitmap, 512, 512, true));
            }
            if (selectedFileUri == null) {
                Log.e(AppSharedConstants.TAG, "selectedFileUri is null 1");
                return null;
            }
            return selectedFileUri;
        }
        if (cameraFile != null) {
            cameraFile.delete();
        }
        return null;
    }

    // scales the camera photo down to CAMERA_IMAGE_MAX_SIZE and applies its EXIF rotation
    private static Uri getScaledImageUri(Context context, File photo) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photo.getPath(), options);
        int largestSide = Math.max(options.outWidth, options.outHeight);

        options.inJustDecodeBounds = false;
        options.inSampleSize = 1;
        while (largestSide / (options.inSampleSize * 2) >= CAMERA_IMAGE_MAX_SIZE) {
            options.inSampleSize *= 2;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(photo.getPath(), options);
        if (bitmap == null) {
            return Uri.fromFile(photo);
        }

        float scale = Math.min(1f, (float) CAMERA_IMAGE_MAX_SIZE / Math.max(bitmap.getWidth(), bitmap.getHeight()));
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        matrix.postRotate(getExifRotation(photo));
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        Uri uri = getImageUri(context, scaledBitmap);
        if (uri == null) {
            return Uri.fromFile(photo);
        }
        photo.delete();
        return uri;
    }

    private static int getExifRotation(File photo) {
        try {
            switch (new ExifInterface(photo.getPath()).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            return 0;
        }
    }

    public static Animation scaleView() {
        Animation anim = new ScaleAnimation(
                1f, 1.2f, 1f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setDuration(250);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(1);
        return anim;
    }

    public static Uri getImageUri(Context context, Bitmap bitmap) { // advance mohammed marwan
        File file = createCacheFile(context, "images", "jpg");
        try (OutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);
            return Uri.fromFile(file);
        } catch (IOException e) {
            Log.e(AppSharedConstants.TAG, "getImageUri: " + e.getMessage());
            return null;
        }
    }


    public static void requestNotificationPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    public static Intent checkPermissionStorage(Context context, int REQUEST_PERMISSIONS_CODE, String type, String source) { // advance mohammed marwan
        return checkPermissionStorage(context, REQUEST_PERMISSIONS_CODE, type, source, 0);
    }

    public static Intent checkPermissionStorage(Context context, int REQUEST_PERMISSIONS_CODE, String type, String source, int limit) { // advance mohammed marwan
        // no runtime permission is needed: the camera app and the system picker hand the image back through a uri

        switch (type) {

            case AppSharedConstants.TYPE_ALL:
                switch (source) {
                    case AppSharedConstants.TYPE_ALL:
                        return imageVideo();
                    case AppSharedConstants.TYPE_CAMERA:
                        return cameraImageVideo(limit);
                    case AppSharedConstants.TYPE_GALLERY:
                        return galleryImageVideo();
                }
                break;

            case AppSharedConstants.TYPE_IMAGE:
                switch (source) {
                    case AppSharedConstants.TYPE_ALL:
                        return cameraOrGalleryImage(context);
                    case AppSharedConstants.TYPE_CAMERA:
                        return cameraImage(context);
                    case AppSharedConstants.TYPE_GALLERY:
                        return galleryImage();
                }
                break;

            case AppSharedConstants.TYPE_VIDEO:
                switch (source) {
                    case AppSharedConstants.TYPE_ALL:
                        return cameraOrGalleryVideo();
                    case AppSharedConstants.TYPE_CAMERA:
                        return cameraVideo();
                    case AppSharedConstants.TYPE_GALLERY:
                        return galleryVideo();
                }

                break;
        }

        return null;
    }

    private static Intent imageVideo() { // advance mohammed marwan
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.setType("*/*");
        contentSelectionIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
        Intent chooserIntent = Intent.createChooser(takePictureIntent, "Capture Image or Video, or choose from Gallery");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takeVideoIntent, contentSelectionIntent});
        return chooserIntent;
    }

    private static Intent cameraImageVideo(int limit) { // advance mohammed marwan
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (limit > 0) {
            takeVideoIntent.putExtra("android.intent.extra.durationLimit", limit);
            takeVideoIntent.putExtra("EXTRA_VIDEO_QUALITY", 0);
        }
        Intent chooserIntent = Intent.createChooser(takePictureIntent, "Capture Image or Video");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takeVideoIntent});
        return chooserIntent;
    }

    private static Intent galleryImageVideo() { // advance mohammed marwan
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.setType("*/*");
        contentSelectionIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Choose an action");
        return chooserIntent;
    }

    public static boolean isVideoFile(Context context, Uri uri) { // advance mohammed marwan
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(context, uri);

        String hasVideo = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO);
        return "yes".equals(hasVideo);
    }

    private static Intent cameraOrGalleryImage(Context context) { // advance mohammed marwan
        Intent takePictureIntent = cameraImage(context);
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        Intent[] intentArray = new Intent[]{takePictureIntent};
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Choose an action");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        return chooserIntent;
    }

    private static Intent galleryImage() { // advance mohammed marwan
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Choose an action");
        return chooserIntent;
    }

    private static Intent galleryVideo() { // advance mohammed marwan
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.setType("video/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Choose an action");
        return chooserIntent;
    }

    public static void statusTRANSPARENT(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private static Intent cameraImage(Context context) { // advance mohammed marwan
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // let the camera app save the full-resolution photo instead of returning a small thumbnail
        pendingCameraFile = createCacheFile(context, "camera", "jpg");
        Uri photoUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", pendingCameraFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        takePictureIntent.setClipData(ClipData.newRawUri("", photoUri));
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        return takePictureIntent;
    }

    private static Intent cameraVideo() { // advance mohammed marwan
        return new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    }

    private static Intent cameraOrGalleryVideo() { // advance mohammed marwan
        Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.setType("video/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        Intent[] intentArray = new Intent[]{takePictureIntent};
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Choose an action");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        return chooserIntent;
    }

    public static Intent checkPermissionStorageAttachment(Context context, int REQUEST_PERMISSIONS_CODE) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSIONS_CODE);
            return null;
        }

        return galleryAttachment();
    }

    private static Intent galleryAttachment() {
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.setType("*/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Choose an action");
        return chooserIntent;
    }

    public static boolean checkRecordAudioPermission(Context context, boolean request, int REQUEST_PERMISSIONS_CODE) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (request)
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSIONS_CODE);
            return false;
        } else return true;
    }

   /* public static void setUpLocation(BaseActivity context, TaskListener<Location> taskListener, int requestCode) {
        if (checkLocationPermission(context, requestCode)) {
            checkSettingLocation(context, taskListener, requestCode);
        }
    }

    public static void setUpLocation(BaseActivity context, TaskListener<Location> taskListener) {
        if (checkLocationPermission(context, AppSharedConstants.REQUEST_PERMISSIONS_CODE_LOCATION)) {
            checkSettingLocation(context, taskListener);
        }
    }

    private static void checkSettingLocation(BaseActivity baseActivity, TaskListener<Location> taskListener, int requestCode) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));
        builder.setAlwaysShow(true);
        LocationSettingsRequest mLocationSettingsRequest = builder.build();

        SettingsClient mSettingsClient = LocationServices.getSettingsClient(baseActivity);

        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(locationSettingsResponse -> {
                    //Success Perform Task Here
                    Log.d(AppSharedConstants.TAG, "ch onSuccess");
                    displayLocation(baseActivity, taskListener);
                })
                .addOnFailureListener(e -> {
                    Log.d(AppSharedConstants.TAG, "ch onFailure");
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException rae = (ResolvableApiException) e;
                                rae.startResolutionForResult((Activity) baseActivity, requestCode);
                            } catch (IntentSender.SendIntentException sie) {
                                Log.e(AppSharedConstants.TAG, "GPS Unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.e(AppSharedConstants.TAG, "GPS Location settings are inadequate, and cannot be fixed here. Fix in Settings.");
                            break;
                    }
                })
                .addOnCanceledListener(() -> Log.e(AppSharedConstants.TAG, "GPS checkLocationSettings -> onCanceled"));
    }

    private static void checkSettingLocation(BaseActivity baseActivity, TaskListener<Location> taskListener) {
        checkSettingLocation(baseActivity, taskListener, AppSharedConstants.REQUEST_CHECK_SETTINGS);
    }


    private static void displayLocation(BaseActivity baseActivity, TaskListener<Location> taskListener) {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(baseActivity);

        if (ActivityCompat.checkSelfPermission(baseActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(baseActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null);
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Location mLastLocation = task.getResult(); // get last location

                final double lat = mLastLocation.getLatitude();
                final double lng = mLastLocation.getLongitude();

                Log.i(AppSharedConstants.TAG, String.format(Locale.ENGLISH, "Your location was changed : %f / %f", lat, lng));

                taskListener.onComplete(mLastLocation);

            } else {
                baseActivity.showMessage(R.string.try_again);
            }
        });
    }*/


    public static boolean checkLocationPermission(Context context, int REQUEST_PERMISSIONS_CODE) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context, new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WAKE_LOCK},
                    REQUEST_PERMISSIONS_CODE);


            return false;
        } else {
            return true;
        }
    }

    public static boolean in_array(int needle, int... values) {
        for (int i : values) {
            if (i == needle) {
                return true;
            }
        }
        return false;
    }

    public static boolean in_array(String needle, String... values) {
        for (String i : values) {
            if (!TextUtils.isEmpty(needle) && !TextUtils.isEmpty(i)) {
                if (i.equals(needle)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = ((Activity) context).getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(context);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    public static void forceHideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
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

    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    /**
     * Convert byte array to hex string
     *
     * @param bytes toConvert
     * @return hexValue
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sbuf = new StringBuilder();
        for (int idx = 0; idx < bytes.length; idx++) {
            int intVal = bytes[idx] & 0xff;
            if (intVal < 0x10) sbuf.append("0");
            sbuf.append(Integer.toHexString(intVal).toUpperCase());
        }
        return sbuf.toString();
    }

    /**
     * Get utf8 byte array.
     *
     * @param str which to be converted
     * @return array of NULL if error was found
     */
    public static byte[] getUTF8Bytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Load UTF8withBOM or any ansi text file.
     *
     * @param filename which to be converted to string
     * @return String value of File
     * @throws java.io.IOException if error occurs
     */
    public static String loadFileAsString(String filename) throws java.io.IOException {
        final int BUFLEN = 1024;
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), BUFLEN);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
            byte[] bytes = new byte[BUFLEN];
            boolean isUTF8 = false;
            int read, count = 0;
            while ((read = is.read(bytes)) != -1) {
                if (count == 0 && bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
                    isUTF8 = true;
                    baos.write(bytes, 3, read - 3); // drop UTF8 bom marker
                } else {
                    baos.write(bytes, 0, read);
                }
                count += read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8") : new String(baos.toByteArray());
        } finally {
            try {
                is.close();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Returns MAC address of the given interface name.
     *
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null) return "";
                StringBuilder buf = new StringBuilder();
                for (byte aMac : mac) buf.append(String.format("%02X:", aMac));
                if (buf.length() > 0) buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception ignored) {
        } // for now eat exceptions
        return "";
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        } // for now eat exceptions
        return "";
    }


//    public int getNavigationBarHeight(Context context) {
//        Resources resources = context.getResources();
//        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            return resources.getDimensionPixelSize(resourceId);
//        }

//        return 0;
//
//    }

//    public String getMobileWithoutPref(String mobile) {
//        if (mobile.startsWith(Constants.PREF_MOBILE))
//            return mobile.substring(Constants.PREF_MOBILE.length());
//        return mobile;
//    }

//    public static boolean isEmptyEditText(EditText editText) {
//        return TextUtils.isEmpty(editText.getText().toString().trim());
//    }
//
//    public static boolean isEmptyOtpText(OtpTextView otpText) {
//        return TextUtils.isEmpty(otpText.getOTP());
//    }
//
//    public static boolean isInvalidEmail(EditText editText) {
//        return !Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString().trim()).matches();
//    }
//
//    public static String getTextFromEditText(EditText editText) {
//        return editText.getText().toString().trim();
//    }
//
//    public static String getTextFromOtpText(OtpTextView otpText) {
//        return otpText.getOTP();
//    }
//
////    public static void setErrorEditText(EditText editText, String message) {
////        editText.setError(message);
////        editText.requestFocus();
////    }
//
//    public static void setErrorEditText(Activity activity, EditText editText, String message) {
//        Toasty.error(activity, message, Toast.LENGTH_SHORT).show();
//        editText.requestFocus();
//    }
//
//    public static void setWarningEditText(Activity activity, EditText editText, String message) {
//        Toasty.warning(activity, message, Toast.LENGTH_SHORT).show();
//        editText.requestFocus();
//    }
//    public static void setSuccessEditText(Context context, String message) {
//        Toasty.success(context, message, Toast.LENGTH_SHORT).show();
//    }
//
//    public static void setErrorOtpText(OtpTextView otpText, String message) {
//        otpText.showError();
//        otpText.requestFocus();
//    }
//
//
//    public static void statusBarLight(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.white, activity.getTheme()));
//            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.white));
//            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
//    }
//
//    public static void statusBarHide(View view) {
//        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_FULLSCREEN);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public static void statusTRANSPARENT(Activity activity) {
//        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//    }
//
//    public static void statusBarShow(Activity activity) {
//        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public static void setStatusBarGradient(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = activity.getWindow();
//            Drawable background = activity.getResources().getDrawable(R.drawable.gradient_theme);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
//            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
//            window.setBackgroundDrawable(background);
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public static void setStatusBarBlack(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = activity.getWindow();
//            Drawable background = activity.getResources().getDrawable(R.drawable.gallery_background);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
//            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
//            window.setBackgroundDrawable(background);
//        }
//    }
//
//    public static boolean isConnected(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
//    }
//
//
//    public static void setSwipeColor(SwipeRefreshLayout swipeRefreshLayout) {
//        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#009EE1"));
//        swipeRefreshLayout.setColorSchemeColors(Color.WHITE);
//    }
//
//
//    public static MultipartBody.Part getImagesFiles(Context context, Uri uri, String key) {
//        if (uri == null)
//            return null;
//        File file = FileUtils.getFile(context, uri);
//        String file_name = UUID.randomUUID().toString() +
//                FileUtils.getExtension(file.toString());
//        RequestBody requestImage = RequestBody.create(MediaType.parse("image/*"), file);
//        return MultipartBody.Part.createFormData(key, file_name, requestImage);
//    }
//
//
//
//    public static Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//
//    public static void restartApp(Activity mActivity) {
//        Intent intent = mActivity.getPackageManager().getLaunchIntentForPackage(mActivity.getPackageName());
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mActivity.startActivity(intent);
//    }

    public static void openLink(String url, Context activity) {
        if (url != null) {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity, "No link", Toast.LENGTH_SHORT).show();
        }
    }
}
