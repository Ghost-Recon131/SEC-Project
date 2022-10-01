package rmit.sec.webstorepmicroservice.utils;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
// This class converts string types to other data types
public class TypeConvertUtil {
    private final Logger logger = LogManager.getLogger(this.getClass());

    public Long convertToLong(String toConvert){
        Long converted = null;
        try{
            if(toConvert != null && !toConvert.isEmpty()){
                converted = Long.parseLong(toConvert);
            }
        }catch (Exception e){
            // Return placeholder value of -101 for error
            logger.warn("Invalid data type provided \n");
            logger.error(e.getMessage());
            converted = -101L;
        }
        return converted;
    }

    public Integer convertToInt(String toConvert){
        Integer converted = null;
        try{
            if(toConvert != null){
                converted = Integer.parseInt(toConvert);
            }
        }catch (Exception e){
            logger.warn("Invalid data type provided \n");
            logger.error(e.getMessage());
            converted = -101;
        }
        return converted;
    }

    public Double convertToDouble(String toConvert){
        Double converted = null;
        try{
            if(toConvert != null){
                converted = Double.parseDouble(toConvert);
            }
        }catch (Exception e){
            logger.warn("Invalid data type provided \n");
            logger.error(e.getMessage());
            converted = -101.101;
        }
        return converted;
    }

}
