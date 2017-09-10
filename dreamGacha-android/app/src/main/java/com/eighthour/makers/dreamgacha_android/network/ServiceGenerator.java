package com.eighthour.makers.dreamgacha_android.network;


import android.util.Base64;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator implements NetDefine {

	private static String token = "";
	private static HttpLoggingInterceptor logging;

	private static okhttp3.OkHttpClient httpClient = null;
	private static Retrofit.Builder builder =
			new Retrofit.Builder()
					.baseUrl( BASIC_PATH )
					.addConverterFactory( GsonConverterFactory.create( new GsonBuilder().setDateFormat( "yyyy-mm-dd HH:mm:ss" ).create() ) );


	private static boolean isFile = false;

	public static <S> S createService( Class<S> serviceClass ) {

		if ( logging == null ) {
			logging = new HttpLoggingInterceptor();
			logging.setLevel( HttpLoggingInterceptor.Level.BODY );
		}
		isFile = false;
		return createService( serviceClass, token );
	}

	public static <S> S createService( Class<S> serviceClass, boolean bool ) {

		if ( logging == null ) {
			logging = new HttpLoggingInterceptor();
			logging.setLevel( HttpLoggingInterceptor.Level.BODY );
		}

		if(httpClient == null){
			new okhttp3.OkHttpClient.Builder().addInterceptor(logging).build();
		}
		isFile = bool;

		//@TODO 임시로 토큰 저장
		token = "df1503a97f97ab911153b074535a196fc6a75b64";
		return createService( serviceClass, token );
	}

	public static void setToken( String newToken ) {

		token = newToken;
	}

	public static <S> S createService( Class<S> serviceClass, final String authToken ) {

		if ( authToken != null ) {
			httpClient = new okhttp3.OkHttpClient.Builder()
					.connectTimeout(100, TimeUnit.SECONDS)
					.readTimeout(100,TimeUnit.SECONDS)
					.addInterceptor( logging )
					.addInterceptor( new okhttp3.Interceptor() {

						@Override
						public okhttp3.Response intercept( Chain chain ) throws IOException {

							okhttp3.Request original = chain.request();

							// Request customization: add request headers
							okhttp3.Request.Builder requestBuilder = original.newBuilder()
									.header( "Content-Type", "application/json" )
									.method( original.method(), original.body() );
							if ( authToken.length() != 0 ) {
								requestBuilder = original.newBuilder()
										.header( "Authorization", "Token " + authToken )
										.header( "Content-Type", "application/json" )
										.method( original.method(), original.body() );
							}
							if ( isFile ) {
								requestBuilder.header( "Content-Type", "multipart/form-data" );
							}

							okhttp3.Request request = requestBuilder.build();
							return chain.proceed( request );
						}
					} ).build();
		}

		Retrofit retrofit = builder.client( httpClient ).build();
		return retrofit.create( serviceClass );
	}

}
