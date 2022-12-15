//import groovy.json.JsonOutput
//import org.apache.commons.lang.StringUtils
//import org.apache.http.HttpResponse
//import org.apache.http.NameValuePair
//import org.apache.http.client.HttpClient
//import org.apache.http.client.entity.UrlEncodedFormEntity
//import org.apache.http.client.methods.HttpGet
//import org.apache.http.client.methods.HttpPost
//import org.apache.http.conn.ClientConnectionManager
//import org.apache.http.conn.scheme.Scheme
//import org.apache.http.conn.scheme.SchemeRegistry
//import org.apache.http.conn.ssl.SSLSocketFactory
//import org.apache.http.entity.StringEntity
//import org.apache.http.impl.client.DefaultHttpClient
//import javax.net.ssl.SSLContext
//import javax.net.ssl.TrustManager
//import javax.net.ssl.X509TrustManager
//import java.security.cert.X509Certificate
//
//class HttpUtils {
//
//    /**
//     * get
//     * @param host
//     * @param path
//     * @param headers
//     * @param querys
//     * @throws Exception
//     */
//    static HttpResponse doGet(String host, String path, Map<String, String> headers, Map<String, String> querys) {
//        HttpClient httpClient = wrapClient(host)
//        HttpGet request = new HttpGet(buildUrl(host, path, querys))
//        for (Map.Entry<String, String> e : headers.entrySet()) {
//            request.addHeader(e.getKey(), e.getValue())
//        }
//        return httpClient.execute(request)
//    }
//
//    /**
//     * post form
//     *
//     * @param host
//     * @param path
//     * @param method
//     * @param headers
//     * @param querys
//     * @param bodys
//     * @return
//     * @throws Exception
//     */
//    static HttpResponse doPost(String host, String path,
//                               Map<String, String> headers,
//                               Map<String, String> querys,
//                               Map<String, String> bodys) {
//        HttpClient httpClient = wrapClient(host)
//
//        HttpPost request = new HttpPost(buildUrl(host, path, querys))
//        for (Map.Entry<String, String> e : headers.entrySet()) {
//            request.addHeader(e.getKey(), e.getValue())
//        }
//
//        if (bodys != null) {
//            String data = JsonOutput.toJson(bodys)
//            StringEntity entity = new StringEntity(data, "utf-8");
//            entity.setContentType("application/json")
//            request.setEntity(entity)
//        }
//
//        return httpClient.execute(request)
//    }
//
//    private static String buildUrl(String host, String path, Map<String, String> querys) {
//        StringBuilder sbUrl = new StringBuilder()
//        sbUrl.append(host)
//        if (!StringUtils.isBlank(path)) {
//            sbUrl.append(path)
//        }
//        if (null != querys) {
//            StringBuilder sbQuery = new StringBuilder()
//            for (Map.Entry<String, String> query : querys.entrySet()) {
//                if (0 < sbQuery.length()) {
//                    sbQuery.append("&")
//                }
//                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
//                    sbQuery.append(query.getValue())
//                }
//                if (!StringUtils.isBlank(query.getKey())) {
//                    sbQuery.append(query.getKey())
//                    if (!StringUtils.isBlank(query.getValue())) {
//                        sbQuery.append("=")
//                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"))
//                    }
//                }
//            }
//            if (0 < sbQuery.length()) {
//                sbUrl.append("?").append(sbQuery)
//            }
//        }
//
//        return sbUrl.toString()
//    }
//
//    private static HttpClient wrapClient(String host) {
//        HttpClient httpClient = new DefaultHttpClient()
//        if (host.startsWith("https://")) {
//            sslClient(httpClient)
//        }
//
//        return httpClient
//    }
//
//    private static void sslClient(HttpClient httpClient) {
//        SSLContext ctx = SSLContext.getInstance("TLS")
//        X509TrustManager tm = new X509TrustManager() {
//            X509Certificate[] getAcceptedIssuers() {
//                return null
//            }
//
//            void checkClientTrusted(X509Certificate[] xcs, String str) {
//
//            }
//
//            void checkServerTrusted(X509Certificate[] xcs, String str) {
//
//            }
//        }
//        TrustManager[] str = [tm]
//        ctx.init(null, str, null)
//        SSLSocketFactory ssf = new SSLSocketFactory(ctx)
//        ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
//        ClientConnectionManager ccm = httpClient.getConnectionManager()
//        SchemeRegistry registry = ccm.getSchemeRegistry()
//        registry.register(new Scheme("https", 443, ssf))
//    }
//}