package bl;

import bo.UserAccount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import dl.UserAccountDL;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class UserAccountBL {

    UserAccountDL userAccountDL = new UserAccountDL();

    public String getById(String userId) throws JsonProcessingException {
        UserAccount userAccount = userAccountDL.selectByUserId(userId);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userAccount);
        return json;
    }

    public String getByUsernamePassword(String userName,String password) throws JsonProcessingException {
        UserAccount userAccount = userAccountDL.selectByUsernamePassword(userName,password);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userAccount);
        return json;
    }
    public String getByWord(String word) {
     try {

         List<UserAccount> users = userAccountDL.selectByWord(word);
         ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
         String arrayToJson = objectMapper.writeValueAsString(users);
         return arrayToJson;


       }
     catch (Exception ex)
     {
         return ex.getMessage();
     }
         }

    public String addUser(String firstName,String lastName,String userName,String password,Integer managerId,Integer unitId,boolean active,Integer createdBy) {
        String result = userAccountDL.insertUser(firstName,lastName,userName,password,managerId,unitId,active,createdBy);
        return result;
    }
    public String CheckDuplicateUserName(String userName) {
        String result = userAccountDL.selectbyUserName(userName);
        return result;
    }


}
