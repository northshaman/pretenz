package Utils.FTP;

import Utils.ConfigManager;
import Utils.TimerForLogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class for work  FTP
 */
public class FTPManager {
    private final String ftpUrlFormat = "ftp://%s:%s@%s/%s;type=i";
    private final String host = ConfigManager.GetProperty("ftpHost");
    private final String user = ConfigManager.GetProperty("ftpUser");
    private final String pass = ConfigManager.GetProperty("ftpPass");
    private final String ftpSOFFFilesPath = ConfigManager.GetProperty("ftpSOFFFilesPath");
    private final String ftpMAIKFilesPath = ConfigManager.GetProperty("ftpMAILFilesPath");
    private Logger logger = LoggerFactory.getLogger("pretenzLogFile");
    private TimerForLogs timer = new TimerForLogs();

    public FTPManager() {
        deleteOldDBFFiles();
    }

    /**
     * Gets URLConnection to current FTP servers dir and file from property file
     *
     * @param fileName - target file on FTP server (exmpl - SO01-01FL_1712.dbf)
     * @return URLConnection  {@link URLConnection}
     */
    private URLConnection getFTPConnection(String fileName, String FilesPath) throws ServletException {

        try {
            String ftpUrl = String.format(ftpUrlFormat, user, pass, host, FilesPath + fileName);
            URL url = new URL(ftpUrl);
            return url.openConnection();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            throw new ServletException(" getFTPConnection MalformedURLException " + ex.getMessage());
        } catch (IOException ei) {
            ei.printStackTrace();
            throw new ServletException(" getFTPConnection IOException " + ei.getMessage());
        }
    }

    private URLConnection getSOFFFTPConnection(String fileName) throws ServletException {
        return getFTPConnection(fileName, ftpSOFFFilesPath);
    }

    private URLConnection getMAILFTPConnection(String fileName) throws ServletException {
        return getFTPConnection(fileName, ftpMAIKFilesPath);
    }

    /**
     * Deleting old copies of files
     */
    private void deleteOldDBFFiles() {
        for (File myFile : new File(".").listFiles()) {
            if (myFile.isFile() && myFile != null) {
                String marker = myFile.getName().substring(myFile.getName().length() - 3, myFile.getName().length());
                if (marker.equals("dbf")) {
                    myFile.delete();
                }
            }
        }
    }

    /**
     * Gets filename and return local copy of them
     *
     * @param fileName - target file on FTP server (exmpl - SO01-01FL_1712.dbf or bd01_01.dbf)
     * @return File  {@link File}
     */
    public File getFileCopy(String fileName) throws ServletException {
        File file = new File(fileName);
        try {
            if (file.exists())
                file.delete();
            file.createNewFile();
            try (InputStream inputStream = this.getStreamFile(fileName);
                 FileOutputStream outputStream = new FileOutputStream(file.getPath())) {
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                timer.resetTimer();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                logger.info(" File " + fileName + " downloaded from FTP in " + timer.getRound() + " sec.");
                return new File(fileName);
            }
        } catch (IOException ex) {
            throw new ServletException(ex.getMessage());
        }
    }

    /**
     * Gets InputStream of target file  from server
     *
     * @param fileName - target file on FTP server (exmpl - SO01-01FL_1712.dbf)
     * @return InputStream  {@link InputStream}
     */
    private InputStream getStreamFile(String fileName) throws ServletException {
        try {
            String marker = fileName.substring(0, 2);
            if (marker.equals("bd"))
                return this.getMAILFTPConnection(fileName).getInputStream();
            else
                return this.getSOFFFTPConnection(fileName).getInputStream();
        } catch (IOException ex) {
            throw new ServletException("Не обнаружен файл на FTP сервере! ");
        }
    }

}
