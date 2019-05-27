package cn.wuxia.project.basic.mvc.controller;

import cn.wuxia.common.exception.AppWebException;
import cn.wuxia.common.util.ArrayUtil;
import cn.wuxia.project.common.api.ApiRequestBean;
import cn.wuxia.project.common.api.ApiResponseBean;
import com.google.common.collect.Lists;
import jodd.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

public abstract class RestBaseController extends BaseController {

    public byte[] getRequestBytes() {
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream == null) {
                return null;
            }
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new AppWebException("无法获取流对象", e);
        }
    }

    @Deprecated
    public <T> T getRequestObject(Class<T> clazz) {
        try {
            byte[] requestBytes = getRequestBytes();
            if (ArrayUtil.isEmpty(requestBytes)) {
                return null;
            }
            Object object = SerializationUtils.deserialize(requestBytes);
            if (object == null) {
                return null;
            }
            if (StringUtil.equals(object.getClass().getName(), clazz.getName())) {
                return (T) object;
            } else {
                return (T) object;
            }
        } catch (Exception e) {
            throw new AppWebException("无法获取流对象", e);
        }
    }

    protected ResponseEntity<ApiResponseBean> ok() {
        return ResponseEntity.ok(ApiRequestBean.ok());
    }

    protected ResponseEntity<ApiResponseBean> ok(byte[] content, String contentType) {
        try {
            return ResponseEntity.ok(ApiRequestBean.ok(content, contentType));
        } catch (Exception e) {
            return notok(e.getMessage());
        }
    }

    protected ResponseEntity<ApiResponseBean> ok(List list) {
        return ResponseEntity.ok(ApiRequestBean.ok(Lists.newArrayList(list)));
    }

    protected ResponseEntity<ApiResponseBean> ok(Serializable object) {
        if (object == null) {
            return ResponseEntity.ok(ApiRequestBean.ok());
        }
        return ResponseEntity.ok(ApiRequestBean.ok(object));

    }

    protected ResponseEntity<ApiResponseBean> okJson(List list) {
        return ResponseEntity.ok(ApiRequestBean.okJson(Lists.newArrayList(list)));
    }

    protected ResponseEntity<ApiResponseBean> okJson(Serializable object) {
        return ResponseEntity.ok(ApiRequestBean.okJson(object));
    }


    protected ResponseEntity<ApiResponseBean> notok(String message) {
        return ResponseEntity.ok(ApiRequestBean.notok(message));
    }

}
