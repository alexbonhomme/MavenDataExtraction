package fr.lille1.maven_data_extraction.core.exceptions;

public class MavenGraphException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6595592268194973651L;

	/**
	 * 
	 */
	public MavenGraphException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MavenGraphException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MavenGraphException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public MavenGraphException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MavenGraphException(Throwable cause) {
		super(cause);
	}

}
