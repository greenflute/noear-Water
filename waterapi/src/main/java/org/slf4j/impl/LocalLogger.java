package org.slf4j.impl;

import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.core.handle.Context;
import org.noear.water.WW;
import org.noear.water.log.Level;
import org.noear.water.log.LogEvent;
import org.noear.water.utils.Datetime;
import org.noear.water.utils.LogHelper;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;
import waterapi.Config;
import waterapi.dso.LogPipelineLocal;

import java.util.Map;

/**
 * @author noear
 * @since 1.0
 */
public class LocalLogger implements Logger {
    private final String name;
    private final Level level = Level.INFO;


    public LocalLogger(String loggerName) {
        if (loggerName.contains(".")) {
            this.name = WW.water_log_api;
        } else {
            this.name = loggerName;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isTraceEnabled() {
        return level.code <= Level.TRACE.code;
    }

    @Override
    public void trace(String s) {
        appendDo(Level.TRACE, s, null, null);
    }

    @Override
    public void trace(String s, Object o) {
        appendDo(Level.TRACE, null, s, new Object[]{o});
    }

    @Override
    public void trace(String s, Object o, Object o1) {
        appendDo(Level.TRACE, null, s, new Object[]{o, o1});
    }

    @Override
    public void trace(String s, Object... objects) {
        appendDo(Level.TRACE, null, s, objects);
    }

    @Override
    public void trace(String s, Throwable throwable) {
        appendDo(Level.TRACE, null, s, new Object[]{throwable});
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return isTraceEnabled();
    }

    @Override
    public void trace(Marker marker, String s) {
        trace(s);
    }

    @Override
    public void trace(Marker marker, String s, Object o) {
        trace(s, o);
    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {
        trace(s, o, o1);
    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {
        trace(s, objects);
    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {
        trace(s, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return level.code <= Level.DEBUG.code;
    }

    @Override
    public void debug(String s) {
        appendDo(Level.DEBUG, s, null, null);
    }

    @Override
    public void debug(String s, Object o) {
        appendDo(Level.DEBUG, null, s, new Object[]{o});
    }

    @Override
    public void debug(String s, Object o, Object o1) {
        appendDo(Level.DEBUG, null, s, new Object[]{o, o1});
    }

    @Override
    public void debug(String s, Object... objects) {
        appendDo(Level.DEBUG, null, s, objects);
    }

    @Override
    public void debug(String s, Throwable throwable) {
        appendDo(Level.DEBUG, null, s, new Object[]{throwable});
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return isDebugEnabled();
    }

    @Override
    public void debug(Marker marker, String s) {
        debug(s, s);
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
        debug(s, o);
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
        debug(s, o, o1);
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
        debug(s, objects);
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
        debug(s, throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return level.code <= Level.INFO.code;
    }

    @Override
    public void info(String s) {
        appendDo(Level.INFO, s, null, null);
    }

    @Override
    public void info(String s, Object o) {
        appendDo(Level.INFO, null, s, new Object[]{o});
    }

    @Override
    public void info(String s, Object o, Object o1) {
        appendDo(Level.INFO, null, s, new Object[]{o, o1});
    }

    @Override
    public void info(String s, Object... objects) {
        appendDo(Level.INFO, null, s, objects);
    }

    @Override
    public void info(String s, Throwable throwable) {
        appendDo(Level.INFO, null, s, new Object[]{throwable});
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return isInfoEnabled();
    }

    @Override
    public void info(Marker marker, String s) {
        info(s);
    }

    @Override
    public void info(Marker marker, String s, Object o) {
        info(s, o);
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
        info(s, o, o1);
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
        info(s, objects);
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        info(s, throwable);
    }

    @Override
    public boolean isWarnEnabled() {
        return level.code <= Level.WARN.code;
    }

    @Override
    public void warn(String s) {
        appendDo(Level.WARN, s, null, null);
    }

    @Override
    public void warn(String s, Object o) {
        appendDo(Level.WARN, null, s, new Object[]{o});
    }

    @Override
    public void warn(String s, Object... objects) {
        appendDo(Level.WARN, null, s, objects);
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        appendDo(Level.WARN, null, s, new Object[]{o, o1});
    }

    @Override
    public void warn(String s, Throwable throwable) {
        appendDo(Level.WARN, null, s, new Object[]{throwable});
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return isWarnEnabled();
    }

    @Override
    public void warn(Marker marker, String s) {
        warn(s);
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
        warn(s, o);
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
        warn(s, o, o1);
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
        warn(s, objects);
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        warn(s, throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return level.code <= Level.ERROR.code;
    }

    @Override
    public void error(String s) {
        appendDo(Level.ERROR, s, null, null);
    }

    @Override
    public void error(String s, Object o) {
        appendDo(Level.ERROR, null, s, new Object[]{o});
    }

    @Override
    public void error(String s, Object o, Object o1) {
        appendDo(Level.ERROR, null, s, new Object[]{o, o1});
    }

    @Override
    public void error(String s, Object... objects) {
        appendDo(Level.ERROR, null, s, objects);
    }

    @Override
    public void error(String s, Throwable throwable) {
        appendDo(Level.ERROR, null, s, new Object[]{throwable});
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return isErrorEnabled();
    }

    @Override
    public void error(Marker marker, String s) {
        error(s);
    }

    @Override
    public void error(Marker marker, String s, Object o) {
        error(s, o);
    }

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {
        error(s, o, o1);
    }

    @Override
    public void error(Marker marker, String s, Object... objects) {
        error(s, objects);
    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        error(s, throwable);
    }

    private void appendDo(Level level, String content, String format, Object[] args) {
        if (level.code < this.level.code) {
            return;
        }

        Throwable throwable = null;
        String throwableStr = null;

        if (format != null) {
            if (args != null && args.length > 0) {
                for (int i = 0, len = args.length; i < len; i++) {
                    if (args[i] instanceof Throwable) {
                        throwable = Utils.throwableUnwrap((Throwable) args[i]);
                        throwableStr = Utils.throwableToString(throwable);
                        args[i] = throwableStr;
                        break;
                    }
                }

                content = MessageFormatter.arrayFormat(format, args, null).getMessage();
            } else {
                content = format;
            }

            if (throwableStr != null) {
                //
                // 可能异常不在格式范围内...
                //
                if (Utils.isEmpty(content)) {
                    content = throwableStr;
                } else {
                    if (throwableStr.length() > content.length()) {
                        content = content + "\n" + throwableStr;
                    }
                }
            }
        }

        Datetime datetime = Datetime.Now();
        Context ctx = Context.current();
        LogEvent log = new LogEvent();

        log.group = Solon.cfg().appGroup();
        log.logger = name;
        if (ctx != null) {
            log.trace_id = ctx.header(WW.http_header_trace);
        }
        log.level = level.code;
        log.tag = MDC.get("tag0");
        log.tag1 = MDC.get("tag1");
        log.tag2 = MDC.get("tag2");
        log.tag3 = MDC.get("tag3");
        log.content = content;
        log.from = Config.localHost;
        log.log_date = datetime.getDate();
        log.log_fulltime = datetime.getFulltime();

        LogPipelineLocal.singleton().add(log);
    }
}