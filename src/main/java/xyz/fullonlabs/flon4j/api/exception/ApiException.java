package xyz.fullonlabs.flon4j.api.exception;

/**
 * 
 * @author fullonlabs https://fullonlabs.xyz
 *
 */
public class ApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ApiError error;

	public ApiException(ApiError apiError) {
		this.error = apiError;
	}

	public ApiException(Throwable cause) {
		super(cause);
	}

	public ApiError getError() {
		return error;
	}

	public void setError(ApiError error) {
		this.error = error;
	}

	@Override
	public String getMessage() {
		if (error != null) {
			return error.getMessage();
		}
		return super.getMessage();
	}
}
