package com.study.guliedu.subject.controller;

import com.study.guliedu.service_base.result.R;
import com.study.guliedu.subject.entity.EduSubject;
import com.study.guliedu.subject.service.ISubjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author liao.peng
 * @since 2021/7/2 10:30
 */
@Slf4j
@RestController
@RequestMapping("edu/subject")
public class SubjectController {

    @Resource(name = "subjectServiceImpl")
    private ISubjectService<EduSubject> subjectServiceImpl;

    @GetMapping("downlandTemplate")
    public void downlandTemplate(String name,HttpServletResponse response){
        String downloadUrl = "/static/"+name;
        ClassPathResource resource = null;
        try {
            //获取资源文件
            resource = new ClassPathResource(downloadUrl);
            //设置响应类型
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition",
                    " attachment;filename=" + new String(name.getBytes(), "iso-8859-1"));
        } catch (Exception e) {
            log.error("导出模板出错，错误信息为"+e.getMessage());
        }
        //获取资源文件输入流和httpServletResponse的输出流
        if (Objects.nonNull(resource)) {
            try (InputStream inputStream = resource.getInputStream(); ServletOutputStream servletOutputStream = response.getOutputStream()) {
                //把资源文件的二进制流数据copy到response的输出流中
                IOUtils.copy(inputStream, servletOutputStream);
                //清除flush所有的缓冲区中已设置的响应信息至客户端
                response.flushBuffer();
            } catch (Exception e) {
                //错误日志记录
                log.error(e.getMessage());
            }
        }
    }

    @PostMapping("import")
    public R importSubjectClassify(MultipartFile file){
        return subjectServiceImpl.importSubjectClassify(file);
    }

    @GetMapping("getSubjectClassifyTree")
    public R getSubjectClassifyTree(){
        return subjectServiceImpl.getSubjectClassifyTree();
    }

}
