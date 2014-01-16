package fr.lille1.maven_data_extraction.core.exceptions;

public class MavenDataExtractionException extends RuntimeException {

	private static final long serialVersionUID = -3950552488615943947L;

	/**
	 * 
	 */
	public MavenDataExtractionException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MavenDataExtractionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MavenDataExtractionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public MavenDataExtractionException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MavenDataExtractionException(Throwable cause) {
		super(cause);
	}

}
