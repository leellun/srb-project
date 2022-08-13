package com.newland.srb.core.config.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;

/**
 * Author: leell
 * Date: 2022/8/11 22:44:01
 */
@Component
public class DecimalMessageConverter extends AbstractHttpMessageConverter<BigDecimal> {
    @Override
    protected boolean supports(Class<?> aClass) {
        return aClass == DecimalMessageConverter.class;
    }

    @Override
    protected BigDecimal readInternal(Class<? extends BigDecimal> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return new BigDecimal(StreamUtils.copyToString(httpInputMessage.getBody(), Charset.defaultCharset()));
    }

    @Override
    protected void writeInternal(BigDecimal bigDecimal, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        httpOutputMessage.getBody().write(bigDecimal.byteValueExact());
    }
}
