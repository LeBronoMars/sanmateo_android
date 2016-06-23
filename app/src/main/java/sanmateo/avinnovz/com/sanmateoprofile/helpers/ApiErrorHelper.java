package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;

/**
 * Created by rsbulanon on 6/23/16.
 */
public class ApiErrorHelper {

    public static ApiError parseError(Response<?> response) {
        Converter<ResponseBody, ApiError> converter =
                AppConstants.RETROFIT.responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }
        return error;
    }
}
