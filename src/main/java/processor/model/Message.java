package processor.model;

import com.mongodb.BasicDBObject;
import lombok.Data;

import java.util.Date;

@Data
public class Message {

    private String language;
    private String content;
    private String languageDesc;
    private Date createdAt;

}
