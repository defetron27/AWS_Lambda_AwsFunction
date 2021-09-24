import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ApiFunction implements RequestStreamHandler
{
    private JSONParser jsonParser = new JSONParser();

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String jsonResponseString;

        try
        {
            JSONObject jsonRequestObject = (JSONObject) jsonParser.parse(bufferedReader);

            jsonResponseString = jsonRequestObject.toString();
        }
        catch (ParseException e)
        {
            jsonResponseString = e.getLocalizedMessage();
        }

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        outputStreamWriter.write(jsonResponseString);
        outputStreamWriter.close();
    }
}
