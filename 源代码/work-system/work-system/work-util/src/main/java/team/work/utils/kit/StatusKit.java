package team.work.utils.kit;

import org.springframework.http.HttpStatus;


public class StatusKit {
    public static HttpStatus http(int code){
        switch (code){
            case 500:
                return HttpStatus.INTERNAL_SERVER_ERROR;
            case 400:
                return HttpStatus.BAD_REQUEST;
            case 403:
                return HttpStatus.FORBIDDEN;
            case 404:
                return HttpStatus.NOT_FOUND;
            case 405:
                return HttpStatus.METHOD_NOT_ALLOWED;
            case 415:
                return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            default:
                return HttpStatus.OK;
        }
    }
}
