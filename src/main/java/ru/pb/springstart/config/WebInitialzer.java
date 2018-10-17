package ru.pb.springstart.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by Pavel Barmyonkov on 10.10.18.
 * pbarmenkov@gmail.com
 */
public class WebInitialzer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected Class[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/",};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        HiddenHttpMethodFilter httpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{encodingFilter, httpMethodFilter};
    }
}
