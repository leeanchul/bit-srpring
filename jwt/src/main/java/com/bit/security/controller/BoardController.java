package com.bit.security.controller;

import com.bit.security.model.FileDTO;
import com.bit.security.model.UserDTO;
import com.bit.security.service.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/board/")
public class BoardController {

    @Value("${upload.path}")
    private String path;

    @Autowired
    private FileService fileService;

    @ResponseBody
    @GetMapping("showAll")
    public String showAll(@AuthenticationPrincipal UserDTO userDTO) {
        return "boardController.showAll(hi)";
    }

    @ResponseBody
    @GetMapping("write")
    public String write() {
        return "boardController.write()";
    }

    @GetMapping("upload")
    public String upload(){
        return "board/upload";
    }

    @PostMapping("upload")
    public String upload(@RequestParam("file") List<MultipartFile> files) throws IOException {

        // 업로드할 폴더를 C 드라이브 안에 upload 폴더로 지정
        //String path="c://upload/";

        // 만약 해당 폴더가 존재하지 않으면 해당 폴더를 만든다.
        File dir=new File(path);
        if(! dir.exists()) {
            dir.mkdirs();
        }


        // 파일 1개만 업로드 시키는 코드!
        // 파라미터의 @RequestParam("file") MultipartFile f
        // 가 있어야 정상적으로 동작한다.
        //File result=new File(path+f.getOriginalFilename());
        //f.transferTo(result);

        // 파일 여러개 업로드 하는 코드
        // jsp 페이지의 form 태그 안에 input file 태그에 반드스 multiple="multiple" 어트리뷰트가 적혀있어야 하고
        // 메소드의 파라미터의 @RequestParam("file") List<MultipartFile> f
        // 가 있어야 한다.

        for(MultipartFile f : files){
            // UUID
            // Universal Unique ID(법용 고유 식별자)
            // 프로젝트 전역에서 사용될 수 있는 고유 식별아이디
            // 우리가 업로드한 파일 이름을 UUID로 바꿔서 저장하면
            // 해당 파일이 중복될 확률을 없앨 수 있다.

            // 1. 현재 파일의 확장자 가져오기
            String ext= f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf('.'));
            // .jpg 등 가져오는거
            
            // 2. 해당 파일을 업로드할 때 사용할 UUID 생성
            String uuid= UUID.randomUUID().toString();
            // 고유값 생성하는것이다.
            
            // DB에 저장하는 코드
            FileDTO fileDTO=new FileDTO();
            fileDTO.setFileName(f.getOriginalFilename());
            fileDTO.setFilePath(uuid+ext);
            fileService.insert(fileDTO);

            // 3. 해당 이름을 파일을 업로드 한다.
            File result = new File(path+uuid+ext);
            f.transferTo(result);
        }

        return "redirect:/board/download";
    }

    @GetMapping("download")
    public String download(Model model){
        List<FileDTO> list=fileService.selectAll();
        model.addAttribute("list",list);
        model.addAttribute("path",path);
        return "board/download";
    }
}