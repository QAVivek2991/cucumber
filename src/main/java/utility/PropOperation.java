package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropOperation {
	public Properties properties;
	public PropOperation(String filePath) {
		File file = new File(filePath);
		try {
			FileInputStream inputStream = new FileInputStream(file);
			properties = new Properties();
			properties.load(inputStream);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public String getValue(String key) {
		return properties.getProperty(key);
	}
}
