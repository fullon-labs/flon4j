package xyz.fullonlabs.flon4j.api.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import xyz.fullonlabs.flon4j.api.exception.ApiError;
import xyz.fullonlabs.flon4j.api.exception.ApiException;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Generator {

	private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

	private static Retrofit.Builder builder = new Retrofit.Builder()
			.addConverterFactory(JacksonConverterFactory.create());

	private static Retrofit retrofit;

	public static <S> S createService(Class<S> serviceClass, String baseUrl) {
		builder.baseUrl(baseUrl);
		builder.client(httpClient.build());
		builder.addConverterFactory(JacksonConverterFactory.create());
		retrofit = builder.build();
		return retrofit.create(serviceClass);
	}

	public static <T> T executeSync(Call<T> call) {
		try {
			Response<T> response = call.execute();
			if (response.isSuccessful()) {
				return response.body();
			} else {
				ApiError apiError = getApiError(response);

				// 输出完整 body，帮助定位链上报错详情
				String rawErrorBody = null;
				try {
					rawErrorBody = response.errorBody() != null ? response.errorBody().string() : null;
				} catch (Exception ex) {
					rawErrorBody = "errorBody 获取失败: " + ex.getMessage();
				}

				System.err.println("==[链错误捕获]==");
				System.err.println("apiError: " + apiError.getMessage());
				System.err.println("full errorBody: " + rawErrorBody);
				return null;

//				throw new ApiException(apiError); // 可以考虑把 body 传进异常
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException(e);
		}
	}
	private static ApiError getApiError(Response<?> response) throws IOException, ApiException {
		return (ApiError) retrofit.responseBodyConverter(ApiError.class, new Annotation[0]).convert(response.errorBody());
	}
}
