package controllers;

import Utils.CSVManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Controller for work with csv file
 **/
@Controller
@RequestMapping("/csv")
@CrossOrigin(value = "*")
public class CSVController {
    @Value("${csvStoragePath}")
    private String csvStoragePath;

    @RequestMapping(value = "/{fileName}", method = RequestMethod.GET,
            produces = "text/csv")
    public void getCSV(@PathVariable("fileName") String fileName, HttpServletResponse response) throws ServletException, IOException {
        CSVManager csvManager = new CSVManager(csvStoragePath+fileName+".csv");
        File outputFile = csvManager.getCSVFile();
        response.addHeader("Content-Disposition", "attachment; filename=\"" + outputFile.getName() + "\"");
        response.setContentLength((int) outputFile.length());

        try (BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(outputFile));
             BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
            org.apache.commons.io.IOUtils.copy(fileInputStream, outputStream);
        } catch (FileNotFoundException fnf) {
            throw new ServletException("Couldn't open csv file");
        }
    }
}
