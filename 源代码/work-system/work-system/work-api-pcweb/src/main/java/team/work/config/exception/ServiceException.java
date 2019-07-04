package team.work.config.exception;

/**
 * @author Tanj
 * @create 2017-10-07 18:54
 * 异常管理
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String msg) {
        super(msg);
    }
}
