package cn.wuxia.project.basic.mvc.controller;

import cn.wuxia.common.exception.AppWebException;
import cn.wuxia.common.util.ArrayUtil;
import cn.wuxia.common.util.BytesUtil;
import cn.wuxia.project.common.bean.CallbackBean;
import jodd.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;

public class RestBaseController extends BaseController {

    public byte[] getRequestBytes() {
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream == null)
                return null;
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new AppWebException("无法获取流对象", e);
        }
    }

    public <T> T getRequestObject(Class<T> clazz) {
        try {
            byte[] requestBytes = getRequestBytes();
            if (ArrayUtil.isEmpty(requestBytes))
                return null;
            Object object = BytesUtil.bytesToObject(requestBytes);
            if (object == null) {
                return null;
            }
            if (StringUtil.equals(object.getClass().getName(), clazz.getName())) {
                return (T) object;
            } else {
                return (T) object;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new AppWebException("无法获取流对象", e);
        }
    }

    protected ResponseEntity<CallbackBean> ok() {
        return ResponseEntity.ok(CallbackBean.ok());
    }

    protected ResponseEntity<CallbackBean> ok(byte[] content, String contentType) {
        try {
            return ResponseEntity.ok(CallbackBean.ok(content, contentType));
        } catch (Exception e) {
            return notok(e.getMessage());
        }
    }

    protected ResponseEntity<CallbackBean> ok(Object object) {
        if (object == null)
            return ResponseEntity.ok(CallbackBean.ok());
        try {
            return ResponseEntity.ok(CallbackBean.ok(object));
        } catch (IOException e) {
            return notok(e.getMessage());
        }
    }

    protected ResponseEntity<CallbackBean> okJson(Object object) {
        try {
            return ResponseEntity.ok(CallbackBean.okJsonResult(object));
        } catch (IOException e) {
            return notok(e.getMessage());
        }
    }

    protected ResponseEntity<CallbackBean> okJson2(Object object) {
        try {
            return ResponseEntity.ok(CallbackBean.okJsonResult(object, false));
        } catch (IOException e) {
            return notok(e.getMessage());
        }
    }

    protected ResponseEntity<CallbackBean> notok(String message) {
        return ResponseEntity.ok(CallbackBean.notok(message));
    }

}
