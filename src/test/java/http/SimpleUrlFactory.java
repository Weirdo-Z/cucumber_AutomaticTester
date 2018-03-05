package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 简单URL构造工厂
 * Created by Baxter on 2015/5/20.
 */
public class SimpleUrlFactory {

    final static Logger log = LoggerFactory.getLogger(SimpleUrlFactory.class);

    public static String generate(String url, Map<String, String> params, String charset) {
        log.info("URL参数:{}", params);
        StringBuffer stringBuffer = new StringBuffer(url);
        if (params.size() > 0) {
            stringBuffer.append("?");
            int i = 0;
            for (String key : params.keySet()) {
                i++;
                try {
                    stringBuffer.append(URLEncoder.encode(key, charset));
                    stringBuffer.append("=");
                    stringBuffer.append(URLEncoder.encode(params.get(key), charset));
                    if (i != params.size()) {
                        stringBuffer.append("&");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();
    }
}
