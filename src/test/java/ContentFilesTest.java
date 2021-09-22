import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import net.lingala.zip4j.ZipFile;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;


public class ContentFilesTest {

    @Test
    void checkContentTxtFile() throws Exception {
        File txtFile = new File("src/txtExample.txt");
        String parsedTxt;

        try (InputStream is = new FileInputStream(txtFile)) {
            parsedTxt = new String(is.readAllBytes(), "UTF-8");
        }

        assertThat(parsedTxt.contains("Test txt file"));
    }

    @Test
    void checkContentPdfFile() throws Exception {
        PDF pdfParsed = new PDF(new File("src/junit-user-guide-5.8.0.pdf"));
        assertThat(pdfParsed.text).contains("JUnit 5 User Guide");
    }

    @Test
    void checkContentXlsFile() throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("XLS50.xls")) {
            XLS parsedXls = new XLS(inputStream);
            String value = parsedXls.excel.getSheetAt(0).getRow(4).getCell(1).getStringCellValue();
            assertThat(value).isEqualTo("Kathleen");
        }
    }

    @Test
    void checkContentZipFile() throws Exception {
        ZipFile zipFile = new ZipFile("src/test/resources/text.zip");
        zipFile.setPassword("qwertyui".toCharArray());
        zipFile.extractAll("src/test/resources");

        File txtFile = new File("src/test/resources/text.txt");
        String parsedTxt;
        try (InputStream is = new FileInputStream(txtFile)) {
            parsedTxt = new String(is.readAllBytes(), "UTF-8");
        }
        assertThat(parsedTxt.contains("Hello everyone"));
    }
}
