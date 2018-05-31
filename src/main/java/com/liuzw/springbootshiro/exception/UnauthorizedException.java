package com.liuzw.springbootshiro.exception;


/**
 * 自定义没有权限异常
 *
 * @author liuzw
 */
public class UnauthorizedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public UnauthorizedException() {
      super();
  }

  public UnauthorizedException(String message) {
      super(message);
  }

  public UnauthorizedException(Throwable cause) {
      super(cause);
  }

  public UnauthorizedException(String message, Throwable cause) {
      super(message, cause);
  }
}
