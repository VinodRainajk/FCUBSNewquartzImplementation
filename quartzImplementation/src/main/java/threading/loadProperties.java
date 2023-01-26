package threading;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class loadProperties {

    public Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }
//

    public String getPropertyValue(String PropertyName)  {

      try {
          Properties prop = readPropertiesFile("src/main/resources/appilcation.properties");
          return prop.getProperty(PropertyName);
      }catch (Exception exp){
          System.out.println("PropertyFile not Found");
          exp.printStackTrace();
          return null;
      }





    }

}
