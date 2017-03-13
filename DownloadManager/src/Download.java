import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;

import javax.net.ssl.HttpsURLConnection;

public class Download extends Observable implements Runnable {

	private static final int MAX_BUFFER_SIZE = 1024;

	public static final String STATUSES[] = { "Downloading", "Paused", "Complete", "Cancelled", "Error" };

	public static final int DOWNLOADING = 0;
	public static final int PAUSED = 1;
	public static final int COMPLETE = 2;
	public static final int CANCELLED = 3;
	public static final int ERROR = 4;
	private URL url;
	private int size;
	private int downloaded;
	private int status;

	public Download(URL url) {
		this.url = url;
		size = -1;
		downloaded = 0;
		status = DOWNLOADING;

		download();
	}

	public String getUrl() {
		return url.toString();
	}

	public int getSize() {
		return size;
	}

	public float getProgress() {
		return ((float) downloaded / size) * 100;
	}

	public int getStatus() {
		return status;
	}

	public void pause() {
		status = PAUSED;
		stateChanged();
	}

	public void resume() {
		status = DOWNLOADING;
		stateChanged();
	}

	public void cancel() {
		status = CANCELLED;
		stateChanged();
	}

	private void error() {
		status = ERROR;
		stateChanged();
	}

	private void download() {
		Thread thread = new Thread(this);
		thread.start();
	}

	private String getFileName(URL url) {
		String fileName = url.getFile();
		return fileName.substring(fileName.lastIndexOf('/') + 1);

	}

	// download file
	@Override
	public void run() {
		RandomAccessFile file = null;
		InputStream stream = null;

		try {
			// open connection
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			// determine what part of file needed download
			connection.setRequestProperty("Range", "bytes=" + downloaded + "-");

			// join to server
			connection.connect();

			// check if response code have range 200
			if (connection.getResponseCode() / 100 != 2) {
				error();
			}
			// check if contain have reasonable length
			int contentLenght = connection.getContentLength();
			if (contentLenght < 1) {
				error();
			}
			// set size downloading
			if (size == -1) {
				size = contentLenght;
				stateChanged();
			}

			// open file and found file end
			file = new RandomAccessFile(getFileName(url), "rw");
			file.seek(downloaded);

			stream = connection.getInputStream();

			while (status == DOWNLOADING) {

				// set buffer size to download rest part of file
				byte buffer[];

				if (size - downloaded > MAX_BUFFER_SIZE) {
					buffer = new byte[MAX_BUFFER_SIZE];
				} else {
					buffer = new byte[size - downloaded];
				}

				// read byte from server to buffer
				int read = stream.read(buffer);
				if (read == -1) {
					break;
				}

				// write buffer contains to file

				file.write(buffer, 0, read);
				downloaded += read;
				stateChanged();
			}
			// determine status like complete, because here process was finished
			if (status == DOWNLOADING) {
				status = COMPLETE;
				stateChanged();
			}

		} catch (IOException e) {
			error();
			e.printStackTrace();
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void stateChanged() {
		setChanged();
		notifyObservers();
	}
}
