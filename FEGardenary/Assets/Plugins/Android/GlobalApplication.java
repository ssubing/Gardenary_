package com.DefaultCompany.FEGardenary;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.kakao.sdk.common.KakaoSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GlobalApplication extends Application {
        private static GlobalApplication instance;

        @Override
        public void onCreate() {
                super.onCreate();
                instance = this;
                //Log.d("KeyHash", getKeyHash());

                // 네이티브 앱 키로 초기화
                KakaoSdk.init(this, "e24441fe04b1978c7c179a9215a2ee2f");
        }

        // 키해시 얻기
        public String getKeyHash(){
                try{
                        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
                        if(packageInfo == null) return null;
                        for(Signature signature: packageInfo.signatures){
                                try{
                                        MessageDigest md = MessageDigest.getInstance("SHA");
                                        md.update(signature.toByteArray());
                                        return android.util.Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                                }catch (NoSuchAlgorithmException e){
                                        Log.w("getKeyHash", "Unable to get MessageDigest. signature="+signature, e);
                                }
                        }
                }catch(PackageManager.NameNotFoundException e){
                        Log.w("getPackageInfo", "Unable to getPackageInfo");
                }
                return null;
        }
}
