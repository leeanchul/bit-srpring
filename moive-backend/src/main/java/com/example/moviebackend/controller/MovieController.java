package com.example.moviebackend.controller;

import com.example.moviebackend.model.MovieDTO;
import com.example.moviebackend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/movie/")
@CrossOrigin("http://localhost:3000")
public class MovieController {

    @Value("${upload.path}")
    private String path;

    @Autowired
    private MovieService movieService;

    @GetMapping("movieAll/{page}")
    public Object movieAll(@PathVariable int page) {
        Map<String, Object> resultMap = new HashMap<>();
        List<MovieDTO> list = movieService.selectByPage(page);
        if (list.isEmpty()) {
            resultMap.put("result", "fail");
            resultMap.put("message", " 유효하지않은 페이지");
        } else {
            resultMap.put("result", "success");
            resultMap.put("list", list);

            int maxPage = movieService.maxPage();

            int startPage = page - 2;
            int endPage = page + 2;

            if (maxPage <= 5) {
                startPage = 1;
                endPage = maxPage;
            } else if (page <= 3) {
                startPage = 1;
                endPage = 5;
            } else if (page >= maxPage - 2) {
                startPage = maxPage - 4;
                endPage = maxPage;
            }
            resultMap.put("maxPage", maxPage);
            resultMap.put("startPage", startPage);
            resultMap.put("endPage", endPage);
            resultMap.put("currentPage", page);

        }

        return resultMap;
    }

    @PostMapping("insert")
    public Object insert(@ModelAttribute MovieDTO movieDTO,
                         @RequestParam("file") MultipartFile f) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String ext = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf('.'));
        // .jpg 등 가져오는거
        // 2. 해당 파일을 업로드할 때 사용할 UUID 생성
        String uuid = UUID.randomUUID().toString();
        // 고유값 생성하는것이다.

        // DB에 저장하는 코드
        movieDTO.setFileName(f.getOriginalFilename());
        movieDTO.setFilePath(uuid + ext);

        // 3. 해당 이름을 파일을 업로드 한다.
        File result = new File(path + uuid + ext);
        f.transferTo(result);
        try {
            movieService.insert(movieDTO);
            resultMap.put("result", "success");
            resultMap.put("boardDTO", movieDTO);
        } catch (Exception e) {
            resultMap.put("result", "fail");
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    @GetMapping("movieOne/{id}")
    public Object movieOne(@PathVariable int id) {
        Map<String, Object> resultMap = new HashMap<>();
        MovieDTO movieDTO = movieService.movieOne(id);
        if (movieDTO != null) {
            resultMap.put("result", "success");
            resultMap.put("movieDTO", movieDTO);
        } else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @GetMapping("delete/{id}")
    public Object delete(@PathVariable int id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            movieService.delete(id);
            resultMap.put("result", "success");
        } catch (Exception e) {
            resultMap.put("result", "fail");
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("update")
    public Object update(@RequestBody MovieDTO movieDTO) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            movieService.update(movieDTO);
            resultMap.put("result", "success");
        } catch (Exception e) {
            resultMap.put("result", "fail");
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }


    @ResponseBody
    @GetMapping(
            value = "/upload/{imageName}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE}
    )
    public byte[] image(@PathVariable("imageName") String name) throws IOException {
        if (name == null) {
            throw new IllegalArgumentException("Image name cannot be null");
        }
        System.out.println(name);

// 읽어들일 파일의 절대 경로
        String absolutePath = path + File.separator + name;

// 파일에서 읽어들일 InputStream
        try (InputStream is = new FileInputStream(absolutePath);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // InputStream에서 ByteArrayOutputStream으로 읽어들임
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            System.out.println("이미지출력:");
            // 최종적으로 ByteArrayOutputStream에서 byte[] 반환
            return baos.toByteArray();
        }

    }
}
