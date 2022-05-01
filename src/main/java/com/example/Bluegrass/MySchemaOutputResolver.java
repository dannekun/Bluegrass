package com.example.Bluegrass;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class MySchemaOutputResolver extends SchemaOutputResolver {
    FileConfigData configData = new FileConfigData();

    /**
     * Generates XSD file from class.
     * @param namespaceUri
     * @param suggestedFileName
     * @return
     * @throws IOException
     */
    @Override
    public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
        File file = new File(configData.getFileName() + ".xsd");
        StreamResult result = new StreamResult(file);
        result.setSystemId(file.getAbsolutePath());
        return result;
    }
}
