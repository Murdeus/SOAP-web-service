package server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Use;
import java.util.List;


/**
 * Created by smile on 13-Mar-16.
 */

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public interface SoapServer {

    @WebMethod
    public List<String> getContent();

    @WebMethod
    public int getColumns();

    @WebMethod
    public void addContent(List<String> listContent);

    @WebMethod
    public void changeContent(List<String> personList, List<String> changeList);

    @WebMethod
    public void deleteContent(List<String> personList);
}
